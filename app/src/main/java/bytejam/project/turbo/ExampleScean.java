package bytejam.project.turbo;

import org.joml.Vector2f;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.game_objects.Background;
import bytejam.project.turbo.util.AssetPool;
import bytejam.project.turbo.util.Transform;

public class ExampleScean extends Scene{

    public Background Testscean;
    public Vector2f pose = new Vector2f(0,0);
   
    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));

        this.renderer = new Renderer();

        Testscean = new Background(AssetPool.getTexture("assets/images/GameBackground.jpg"), new Transform(new Vector2f(1080, 1920)));
        
        renderer.add(Testscean);
    }

    @Override
    public void update(float dt) {
        
        pose.x += dt;
        pose.y += dt;
        renderer.render();
        camera.setCamPosition(new Vector2f(pose.x += dt, pose.y += dt));
    }

}
