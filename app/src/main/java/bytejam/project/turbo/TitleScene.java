package bytejam.project.turbo;

import java.awt.Point;
import java.awt.Rectangle;

import org.joml.Vector2f;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.goc.Entity;
import bytejam.project.turbo.util.AssetPool;

public class TitleScene extends Scene{
    public Entity testSnail;
    public Entity testSnail2;

    public TitleScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));
        this.renderer = new Renderer();
        for (int n=-10; n <15; n++) {
            for (int i=-10; i < 55; i++) {
            renderer.add(new Entity(AssetPool.getTexture("assets/images/snail_07.png"), new Point(i * 25, n * 50), new Rectangle(50, 50)));
            }
        }
    
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
