package bytejam.project.renderer;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import bytejam.project.turbo.Window;
import bytejam.project.turbo.game_objects.Entity;
import bytejam.project.turbo.util.AssetPool;

public class RenderBatch {
    
    /*                            Attribute Map
     * ===========================================================================
     *  Pos               Color                          tex coords        tex id
     *  float, float,     float, float, float, float     float, float,     float
     * ===========================================================================
     */
    
    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int TEX_COORDS_SIZE = 2;
    private final int TEX_ID_SiZE = 1;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
    private final int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 9;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    private final Entity[] entities;
    private int Index;
    private final List<Texture> textures;
    private boolean hasRoom;
    private final float[] vertices;
    private final int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    private int vaoID, vboID;
    private final int maxBatchSize;
    private final Shader shader;

    public RenderBatch(int maxBatchSize) {
        // Always pull shader object from assetpool to reduce lag.
        this.shader = AssetPool.getShader("assets/shaders/default.glsl");

        // Max amount of entities minus one for the background.
        this.entities = new Entity[maxBatchSize];
        this.textures = new ArrayList<>();

        this.maxBatchSize = maxBatchSize;

        // Total amount of memeory needed for batch size.
        this.vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];

        this.hasRoom = true;
    }

    public void start() {

        // ===========================================================
        // Generate VAQ, VBO, and EBO buffer objects and send to GPU
        // ===========================================================

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space for vertices.
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);
        
        // Create and upload indices buffer.
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        /* Here we give openGL our attribute map. */

        // Assign vertex pos.
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        // Assign vertex color.
        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);

        // Assign texture uv coords.
        glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);
        glEnableVertexAttribArray(2);

        // Assign texture id.
        glVertexAttribPointer(3, TEX_ID_SiZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
        glEnableVertexAttribArray(3);
    }

    public void addEntity(Entity entity) {
        // Get index and add renderObject.
        int index = this.Index;
        this.entities[index] = entity;
        this.Index += 1;

        
        if(entity.getTexture() != null) {
            if (!textures.contains(entity.getTexture())) {
                textures.add(entity.getTexture());
            }
        }

        // Add properties to local vertices array.
        loadVertexProperties(index);

        if (entities.length >= this.maxBatchSize - 1) {
            this.hasRoom = false;
        }
    }

    public void render() {
    
        // Set to rebuffer all data every frame.
        glBindBuffer(GL_ARRAY_BUFFER, vaoID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        // Bind.
        shader.bind();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        for (int i=0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();
        }
        shader.uploadIntArray("uTextures", texSlots);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Render.
        glDrawElements(GL_TRIANGLES, (this.entities.length) * 6, GL_UNSIGNED_INT, 0);

        // Unbind.
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        for (int i=0; i < textures.size(); i++) {
            textures.get(i).unbind();
        }

        shader.unbind();
    }

    private void loadVertexProperties(int index) {
        Entity entity = this.entities[index];

        // Find offset within array (4 vertices per sprite)
        int offset = index * 4 * VERTEX_SIZE;
        
        Vector4f color = entity.getColor();
        Vector2f[] texCoords = entity.getTexCoords();

        int texId = 0;

        // Loop through textures we have loaded to find the right one for this entity object.
        if (entity.getTexture() != null) {
            for (int t=0; t < textures.size(); t++) {
                if (textures.get(t) == entity.getTexture()) {
                    texId = t + 1;
                    break;
                }
            }

        }
        

        // Add vertice with the appropriate properties.
        float xAdd = 1.0f;
        float yAdd = 1.0f;

        // Loop through each vertex where i is = to current vertex.
        for (int i=0; i < 4; i++) {
            switch (i) {
                case 1 -> yAdd = 0.0f;
                case 2 -> xAdd = 0.0f;
                case 3 -> yAdd = 1.0f;
                default -> {
                }
            }

            // Load position.
            vertices[offset] = entity.getPos().x + (xAdd * entity.getSize().x);
            vertices[offset + 1] = entity.getPos().y + (yAdd * entity.getSize().y);

            // Load color.
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            // Load texture coords.
            vertices[offset + 6] = texCoords[i].x;
            vertices[offset + 7] = texCoords[i].y;

            // Load texture id.
            vertices[offset + 8] = texId;

            /* Debug.
            for (int n=0; n< 9; n++ ) {
                System.out.print(vertices[offset + n]);
                System.out.println();
            }
            */

            offset += VERTEX_SIZE;
        }
    } 

    private int[] generateIndices() {
        // 6 indices per quad (3 per triangle)
        int[] elements = new int[6 * maxBatchSize];
        for (int i=0; i < maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }

        return elements;
    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        // ===========================================
        // Tri 1                    Tri 2
        // 3, 2, 0, 0, 2, 1         7, 6, 4, 4, 6, 5
        // ===========================================

        // Tri 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        // Tri 2
        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public  boolean hasRoom() {
        return this.hasRoom;
    }

}
