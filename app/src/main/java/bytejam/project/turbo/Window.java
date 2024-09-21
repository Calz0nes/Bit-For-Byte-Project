package bytejam.project.turbo;

import org.lwjgl.Version;
import static org.lwjgl.glfw.GLFW.glfwInit;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Window {

    private int width, height;
    private String title;

    private static Window window = null;

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

    }

    public void loop() {

    }




}
