package bytejam.project.turbo;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.game_objects.Background;
import bytejam.project.turbo.game_objects.Player;
import bytejam.project.turbo.util.AssetPool;
import bytejam.project.turbo.util.Transform;

public class gameScene extends Scene{
    private Background background;
    private Player player;
    private Sound sound;
    private final float Speed = 10;
    private final float jumpV = 20;
    private Vector2f Pose;
    private Vector2f V;
    private final float frictionSpeed = 7;
    private final float gravity = 7;
    private final Transform gameArea = new Transform(new Vector2f(600, 1050),new Vector2f(1200, 2100));
    
    public gameScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));
        this.V = new Vector2f(0, 0);
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
            if (isOnFloor()) {
                V.y = jumpV;
            } else {
                V.y -= gravity;
            } 
        }
        
        if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
            V.x = -Speed;
        } else {
            if (V.x <= 0) {
                V.x += frictionSpeed;
            }
        }
    
        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            V.x = Speed;
        } else {
            if (V.x >= 0) {
                V.x -= frictionSpeed;
            }
        }
            
        Pose.add(V);
        
    
        playerBounderies(Pose);

        renderer.render();
    }

    // Makes sure that the entity stays inside the game area.
    private void playerBounderies(Vector2f nextPos) {
        if (nextPos.x > gameArea.Size.x/2 + gameArea.Center.x) {
            nextPos.x = gameArea.Size.x/2 + gameArea.Center.x;
        } else if (nextPos.x < (-1 * gameArea.Size.x/2) + gameArea.Center.x) {
            nextPos.x = (-1 * gameArea.Size.x/2) + gameArea.Center.x;
        }

        if (nextPos.y > gameArea.Size.y/2 + gameArea.Center.y) {
            nextPos.y = gameArea.Size.y/2 + gameArea.Center.y;
        } else if (nextPos.y < (-1 * gameArea.Size.y/2) + gameArea.Center.y) {
            nextPos.y = (-1 * gameArea.Size.y/2) + gameArea.Center.y;
        }

        player.setPos(nextPos);
        System.out.println("Pos (" + nextPos.x + ","+ nextPos.y + ")");
    }

    private boolean isOnFloor() {
        return Pose.x == (-1 * gameArea.Size.y/2) + gameArea.Center.y;
    }
}
