package bytejam.project.turbo;

import org.joml.Vector2f;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.game_objects.Player;
import bytejam.project.turbo.util.AssetPool;
import bytejam.project.turbo.util.Transform;

public class TitleScene extends Scene{
    public Player player;
    private Renderer renderer;

    public TitleScene() {
        
    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));
        this.renderer = new Renderer();
        this.player = new Player(AssetPool.getTexture("assets/images/snail_07.png"), new Transform(new Vector2f(0,0), new Vector2f(50, 50)));
        
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

       
        // Render new frame.
        this.renderer.render();
    }

}
