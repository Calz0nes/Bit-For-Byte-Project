package bytejam.project.turbo;

import org.joml.Vector2f;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.game_objects.Player;
import bytejam.project.turbo.util.AssetPool;
import bytejam.project.turbo.util.Transform;

public class TitleScene extends Scene{
    public Player testSnail;
    public Player testSnail2;

    public TitleScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));

        this.testSnail = new Player(AssetPool.getTexture("assets/images/snail_07.png"), new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        this.testSnail2 = new Player(AssetPool.getTexture("assets/images/snail_07.png"), new Transform(new Vector2f(256, 256)));
        this.renderer = new Renderer();
        
        renderer.add(testSnail);
        renderer.add(testSnail2);


        loadResources();
    }

    // Load resources during init to reduce lag.
    public void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {
        this.renderer.render();
    }

}
