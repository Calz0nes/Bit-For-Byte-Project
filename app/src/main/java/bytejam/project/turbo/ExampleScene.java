package bytejam.project.turbo;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.game_objects.Background;
import bytejam.project.turbo.game_objects.Player;
import bytejam.project.turbo.util.AssetPool;
import bytejam.project.turbo.util.Transform;

public class ExampleScene extends Scene{

    private Background background;
    private Player player;
    private Vector2f Pose;
    private Sound sound;
    private final float Speed = 10;
   
    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));
        this.Pose = new Vector2f(0, 0);

        this.renderer = new Renderer();
        this.player = new Player(AssetPool.getTexture("assets/images/snail_07.png"), new Transform(new Vector2f(50, 50)));
        this.background = new Background(AssetPool.getTexture("assets/images/GameBackground.jpg"), new Transform(new Vector2f(20, -400),new Vector2f(1200, 2100)));
        
        sound = new Sound("assets/sounds/file_example_OOG_1MG.ogg", true);
        renderer.add(background);
        renderer.add(player);
    }

    @Override
    public void update(float dt) {

        

        sound.play();
        
        if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
            Pose.y += Speed;
        } 
        
        if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
            Pose.x -= Speed;
        } 
        
        if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
            Pose.y -= Speed;
        } 
        
        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            Pose.x += Speed;
        }

        player.setPos(this.Pose);

        renderer.render();
    }

}
