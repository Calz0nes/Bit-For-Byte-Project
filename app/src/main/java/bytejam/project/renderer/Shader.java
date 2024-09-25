package bytejam.project.renderer;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Shader {

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
    private int vaoID, vboID, eboID;
    
    public Shader() {
        
    }


    public void compile() {
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
            System.out.println("ERROR: 'default.glsl'\n\tLinking of shaders failed.");
            System.out.println(glGetShaderInfoLog(shaderProgram, lenPrgm));
        }
    }

    public void use() {
        glUseProgram(shaderProgram);
    }

    public void detach() {
        glUseProgram(0);
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderProgram, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }
}

