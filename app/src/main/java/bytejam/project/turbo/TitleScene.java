package bytejam.project.turbo;

import java.awt.Point;
import java.awt.Rectangle;

import org.joml.Vector2f;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.goc.Entity;
import bytejam.project.turbo.util.AssetPool;

public class TitleScene extends Scene{

    public TitleScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));

        Entity testSnail = new Entity(AssetPool.getTexture("assets/images/snail_07.png"), new Point(0, 0), new Rectangle(256, 256));
        Entity testSnail2 = new Entity(AssetPool.getTexture("assets/images/snail_07.png"), new Point(-100, -100), new Rectangle(256, 256));
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
