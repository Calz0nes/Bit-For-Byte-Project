package bytejam.project.turbo.game_scenes;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.Camera;
import bytejam.project.turbo.KeyListener;
import bytejam.project.turbo.Scene;
import bytejam.project.turbo.Sound;
import bytejam.project.turbo.Window;
import bytejam.project.turbo.game_objects.Player;
import bytejam.project.turbo.util.AssetPool;
import bytejam.project.turbo.util.Transform;


public class TitleScene extends Scene{
    public Player player;
    // private Sound sound;
    private Renderer renderer;
    private final Sound sound = new Sound("assets/sounds/file_example_OOG_1MG.ogg", true);
    public TitleScene() {
        
    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));
        this.renderer = new Renderer();
        this.player = new Player(AssetPool.getTexture("assets/images/TitleScene.png"), new Transform(new Vector2f(0,0), new Vector2f(1300, 675)));
        // this.sound = new Sound("assets/sounds/file_example_OOG_1MG.ogg");
        
        renderer.add(player);

        renderer.start();
        loadResources();
    }

    // Load resources during init to reduce lag.
    public void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {
        this.sound.play();
        
        if (KeyListener.isKeyPressed(GLFW_KEY_ENTER)) {
            Window.get().changeScene(1);
            this.sound.stop();
        }
        
        // Render new frame.
        this.renderer.render();
    }

}
