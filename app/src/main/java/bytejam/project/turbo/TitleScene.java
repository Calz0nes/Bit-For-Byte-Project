package bytejam.project.turbo;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TitleScene extends Scene{

    private String vertexShaderSrc = "#version 330 core\n" + //
                "layout (location=0) in vec3 aPos;\n" + //
                "layout (location=1) in vec4 aColor;\n" + //
                "\n" + //
                "out vec4 fColor;\n" + //
                "\n" + //
                "void main()\n" + //
                "{\n" + //
                "   fColor = aColor;\n" + //
                "   gl_Position = vec4(aPos, 1.0);\n" + //
                "}";
    
    private String fragmentShaderSrc = "#version 330 core\n" + //
                "\n" + //
                "in vec4 fColor;\n" + //
                "\n" + //
                "out vec4 color;\n" + //
                "\n" + //
                "void main() \n" + //
                "{\n" + //
                "   color = fColor;\n" + //
                "}";
    
    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
        // positon               // color
         0.5f, -0.5f, 0.0f,      1.0f, 0.0f, 0.0f, 1.0f, // Bottom right
        -0.5f,  0.5f, 0.0f,      0.0f, 1.0f, 0.0f, 1.0f, // Top left
         0.5f,  0.5f, 0.0f,      0.0f, 0.0f, 1.0f, 1.0f, // Top right
        -0.5f, -0.5f, 0.0f,      1.0f, 1.0f, 0.0f, 1.0f  // Bottom left

    };

    // IMPORTANT: Must be in counter-clockwise order.
    private int[] elementArray = {
        2, 1, 0, // Top right triangle
        0, 1, 3  // Bottom left triangle
    };

    private int vaoID, vboID, eboID;

    public TitleScene() {

    }

    @Override
    public void init() {
        //======================================================
        // Compile and link shaders.
        //======================================================

        // First load and compile the vertex shader.
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // Pass the shader source to the GPU.
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        // Check for errors in compilation.
        int success_Vertex = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success_Vertex == GL_FALSE) {
            int lenVert = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, lenVert));
            assert false : "";
        }

        // Now load and compile the fragment shader.
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // Pass the shader source to the GPU.
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        // Check for errors in compilation.
        int success_Fragment = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success_Fragment == GL_FALSE) {
            int lenFrag = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tFragment shader comilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, lenFrag));
            assert false : "";

        }

        // Link shader and check for errors.
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check for linking errors.
        int success_Program = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success_Program == GL_FALSE) {
            int lenPrgm = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tLinking of shaders failed.");
            System.out.println(glGetShaderInfoLog(shaderProgram, lenPrgm));
        }

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
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float dt) {

        // Bind shader program.
        glUseProgram(shaderProgram);
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

        glUseProgram(0);

    }

}
