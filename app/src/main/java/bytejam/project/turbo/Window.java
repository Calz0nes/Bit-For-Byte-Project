package bytejam.project.turbo;

import org.lwjgl.Version;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;

    private static Window window = null;

    private long glfwWindow;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Game Name";

    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        
        return Window.window;
    }
    
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();
    }

    public void init() {
        // Setup an error callback.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initalize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initalize GLFW");
        }

        // Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // the window will be maximized

		// Create the window
		glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		if ( glfwWindow == NULL )
			throw new IllegalStateException("Failed to create the GLFW window");

		// Make the OpenGL context current
		glfwMakeContextCurrent(glfwWindow);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(glfwWindow);


        GL.createCapabilities();
        
    }

    // This line is critical for LWJGL's interoperation with GLFW's
	// OpenGL context, or any context that is managed externally.
	// LWJGL detects the context that is current in the current thread,
	// creates the GLCapabilities instance and makes the OpenGL
	// bindings available for use.
    public void loop() {
        while ( !glfwWindowShouldClose(glfwWindow) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			// Poll for window events. 
			glfwPollEvents();

            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            
            glfwSwapBuffers(glfwWindow);

		}
    }




}
