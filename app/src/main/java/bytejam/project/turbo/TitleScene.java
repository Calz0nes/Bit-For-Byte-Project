package bytejam.project.turbo;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import bytejam.project.renderer.Shader;
import bytejam.project.renderer.Texture;

public class TitleScene extends Scene{

    private float[] vertexArray = {
        // positon               // color                       // UV Coordinates 
         50.0f, -50.0f, 0.0f,      1.0f, 0.0f, 0.0f, 1.0f,      1, 1,// Bottom right
        -50.0f,  50.0f, 0.0f,      0.0f, 1.0f, 0.0f, 1.0f,      0, 0,// Top left
         50.0f,  50.0f, 0.0f,      0.0f, 0.0f, 1.0f, 1.0f,      1, 0,// Top right
        -50.0f, -50.0f, 0.0f,      1.0f, 1.0f, 0.0f, 1.0f,      0, 1 // Bottom left

    };

    // IMPORTANT: Must be in counter-clockwise order.
    private int[] elementArray = {
        2, 1, 0, // Top right triangle
        0, 1, 3  // Bottom left triangle
    };

    private int vaoID, vboID, eboID;

    private Shader defaultshader;
    private Texture testTexture;

    public TitleScene() {

    }

    @Override
    public void init() {
        defaultshader = new Shader("assets/shaders/default.glsl");
        defaultshader.compile();

        this.camera = new Camera(new Vector2f());
        this.testTexture = new Texture("assets/images/snail_07.png");


        // ===========================================================
        // Generate VAQ, VBO, and EBO buffer objects and send to GPU
        // ===========================================================
        
        // Create VAO.
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices.
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer.
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload.
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers.
        int positionSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionSize + colorSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    @Override
    public void update(float dt) {
        camera.setCamPosition(new Vector2f(dt -= 50.0f, dt -= 50.0f));

        // Upload texture to shader.
        defaultshader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        testTexture.bind();

        // Bind shader program.
        defaultshader.use();

        // Upload the matrixes used for finding the view port.
        defaultshader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultshader.uploadMat4f("uView", camera.getViewMatrix());
        
        // Bind the VAO that we're using.
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers.
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything.
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultshader.detach();

    }

}
