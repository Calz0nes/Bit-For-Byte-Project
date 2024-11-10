package bytejam.project.turbo.game_scenes;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.Camera;
import bytejam.project.turbo.KeyListener;
import bytejam.project.turbo.Scene;
import bytejam.project.turbo.Sound;
import static bytejam.project.turbo.Window.get;
import bytejam.project.turbo.game_objects.Background;
import bytejam.project.turbo.game_objects.Cursor;
import bytejam.project.turbo.game_objects.Player;
import bytejam.project.turbo.util.AssetPool;
import bytejam.project.turbo.util.Transform;

public class PastScene extends Scene{
    private Background background;
    private Cursor cursor;
    private Player player;
    private final Sound sound = new Sound("assets/sounds/EpicMusic.ogg", true);
    private final float Speed = 10;
    private final float jumpV = 20;
    private Vector2f Pose;
    private Vector2f V;
    private final float frictionSpeed = 1.2f;
    private final float gravity = 0.7f;
    private final Transform gameArea = new Transform(new Vector2f(600, 1050),new Vector2f(1200, 2100));
    private Renderer renderer;
    //private ProjectileManager PlayerProjectileManager;
    //private float attackCooldown;

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));
        this.V = new Vector2f(0, 0);
        this.Pose = new Vector2f(0, 0);


        this.renderer = new Renderer();
        //this.cursor = new Cursor(AssetPool.getTexture("assets/images/Crosshair.png"), new Transform(new Vector2f(30, 30)));
        //this.PlayerProjectileManager = new ProjectileManager(renderer, gameArea);
        this.player = new Player(AssetPool.getTexture("assets/images/Dave.png"), new Transform(new Vector2f(128, 91)));

        this.background = new Background(AssetPool.getTexture("assets/images/PastBackground.jpg"), new Transform(new Vector2f(20, -400),new Vector2f(1200, 2100)));
        
        //renderer.add(this.cursor);
        renderer.add(background);
        renderer.add(player);

        renderer.start();
        loadResources();
    }

    @Override
    public void update(float dt) {
        get();
        sound.play();
        
        if (KeyListener.isKeyPressed(GLFW_KEY_Y)) {
            get().changeScene(0);
            sound.stop();
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
            if (isOnFloor()) {
                V.y = jumpV;
            } else {
                V.y -= gravity;
            } 
        } else {
            V.y -= gravity;
        } 
        
        if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
            V.x = -Speed;
        } else if (V.x < 0) {
            V.x = V.x / frictionSpeed;
        }
    
        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            V.x = Speed;
        } else if (V.x > 0) {
            V.x = V.x / frictionSpeed;
        }

        Pose.add(V);
        
        /* 
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_1) && attackCooldown < 0) {
            PlayerProjectileManager.addProjectile(new Projectile(AssetPool.getTexture("assets/images/Crosshair.png"), 
                player.getTransform(), new Transform(new Vector2f(MouseListener.getX(), MouseListener.getY()), 
                new Vector2f(50, 50))));

            attackCooldown--;
        } else {
            attackCooldown = 20;
        }
        */


        playerBounderies(Pose);
        //PlayerProjectileManager.goNext(player);
        //cursor.setPos(new Vector2f(MouseListener.getX(), MouseListener.getY()));

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
    }

    private boolean isOnFloor() {
        return Pose.y <= (-1 * gameArea.Size.y/2) + gameArea.Center.y;
    }

    // Load resources during init to reduce lag.
    public void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.getTexture("assets/images/PastBackground.jpg");
        AssetPool.getTexture("assets/images/Dave.png");
    }
}
