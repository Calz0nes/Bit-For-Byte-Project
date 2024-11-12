package bytejam.project.turbo.game_scenes;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.Camera;
import bytejam.project.turbo.KeyListener;
import bytejam.project.turbo.MouseListener;
import bytejam.project.turbo.Scene;
import bytejam.project.turbo.Sound;
import static bytejam.project.turbo.Window.get;
import bytejam.project.turbo.game_objects.Background;
import bytejam.project.turbo.game_objects.Cursor;
import bytejam.project.turbo.game_objects.Player;
import bytejam.project.turbo.game_objects.Projectile;
import bytejam.project.turbo.util.AssetPool;
import bytejam.project.turbo.util.EnemyManager;
import bytejam.project.turbo.util.ProjectileManager;
import bytejam.project.turbo.util.Transform;

public class CombatScene extends Scene {
    
    private final Renderer renderer = new Renderer();
    
    private final Transform presentGameArea = new Transform(new Vector2f(575,1050),new Vector2f(1200, 2100));
    private final Transform pastGameArea = new Transform(new Vector2f(10000, 10000),new Vector2f(1200, 2100));

    // Define all sounds here.
    private final Sound sound = new Sound("assets/sounds/EpicMusic.ogg", true);
    
    // Define all entities here.
    private Background presentBackground;
    private Background pastBackground;
    private Cursor cursor;
    private Player player;
    private ProjectileManager DaveProjectileManager;
    private EnemyManager pastEnemyManager;
    private EnemyManager presentEnemyManager;

    // Dave variables.
    private final float Speed = 10;
    private final float jumpV = 20;
    private Vector2f Pose;
    private Vector2f V;
    private final float frictionSpeed = 1.2f;
    private final float gravity = 0.7f;
    private float attackCooldown;
    private float timeTravelCooldown;


    private boolean isPresent = false;
    private boolean timeInit;

    @Override
    public void init() {
        // Init Camera.
        this.camera = new Camera(new Vector2f(0, 0));

        Pose = new Vector2f(0, 0);
        V = new Vector2f(0, 0);

        // Init entities.
        this.cursor = new Cursor(AssetPool.getTexture("assets/images/Crosshair.png"), new Transform(new Vector2f(30, 30)));

        this.player = new Player(AssetPool.getTexture("assets/images/Dave.png"), new Transform(new Vector2f(128, 91)));

        this.presentBackground = new Background(AssetPool.getTexture("assets/images/PresentBackground.jpg"), new Transform(new Vector2f(50, -300),new Vector2f(1200, 2100)));
        this.pastBackground = new Background(AssetPool.getTexture("assets/images/PastBackground.jpg"), new Transform(new Vector2f(10000 + 50, 10000 - 300),new Vector2f(1200, 2100)));

        
        // Init managers.
        this.DaveProjectileManager = new ProjectileManager(this.renderer);

        this.pastEnemyManager = new EnemyManager(this.renderer, this.DaveProjectileManager, AssetPool.getTexture("assets/images/EnemyProjectile.png"), AssetPool.getTexture("assets/images/Enemy.png"), this.pastGameArea);
        this.presentEnemyManager = new EnemyManager(this.renderer, this.DaveProjectileManager, AssetPool.getTexture("assets/images/EnemyProjectile.png"), AssetPool.getTexture("assets/images/Enemy.png"), this.presentGameArea);
        this.pastEnemyManager.attachTarget(player);
        this.presentEnemyManager.attachTarget(player);

        

        // Add objects in the order that you want them to show up on screen.
        renderer.add(this.presentBackground);
        renderer.add(this.pastBackground);
        renderer.add(this.cursor);
        renderer.add(this.player);

        renderer.start();
        loadResources();
    }

    @Override
    public void update(float dt) {
        
        // Get instance of Window class.
        get();

        // Play sound.
        //sound.play();

        // Move Dave.
        Move();

        if (KeyListener.isKeyPressed(GLFW_KEY_SPACE) && timeTravelCooldown <= 0) {
            isPresent = !isPresent;
            timeInit = false;
            timeTravelCooldown = 10;
        }

        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_1) && attackCooldown <= 0) {
            DaveProjectileManager.addProjectile(new Projectile(AssetPool.getTexture("assets/images/DaveProjectile.png"), player.getPos(), cursor.getPos()));
            attackCooldown = 10;
        }

        if (isPresent) {
            Present();
        } else {
            Past();
        }

        cursor.update(isPresent);

        timeTravelCooldown--;
        attackCooldown--;
        
        // Render new frame.
        renderer.render();
    }

    private void Move() {
        if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
            if (isOnFloor()) {
                V.y = jumpV;
            } else {
                //V.y -= gravity;
            } 
        } else {
            //V.y -= gravity;
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

        playerBounderies(Pose);
    }

    // Makes sure that the entity stays inside the game area.
    private void playerBounderies(Vector2f nextPos) {
        Transform gameArea;

        if (isPresent) {
            gameArea = presentGameArea;
        } else {
            gameArea = pastGameArea;
            nextPos.add(10000, 10000);
        }

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
        Transform gameArea;

        if (isPresent) {
            gameArea = presentGameArea;
        } else {
            gameArea = pastGameArea;
        }

        return Pose.y <= (-1 * gameArea.Size.y/2) + gameArea.Center.y;
    }

    // Load resources during init to reduce lag.
    public void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.getTexture("assets/images/PresentBackground.jpg");
        AssetPool.getTexture("assets/images/PastBackground.jpg");
        AssetPool.getTexture("assets/images/Dave.png");
        AssetPool.getTexture("assets/images/DaveProjectile.png");
        AssetPool.getTexture("assets/images/Enemy.png");
        AssetPool.getTexture("assets/images/EnemyProjectile.png");

    }
     
    private void Past() {
        if (!timeInit) {
            // Put code here for init.
            camera.setCamPosition(new Vector2f(10000,10000));
        }

        pastEnemyManager.update(DaveProjectileManager);

        timeInit = true;
    }

    private void Present() {
        if (!timeInit) {
            // Put code here for init.
            camera.setCamPosition(new Vector2f(0,0));
        }

        presentEnemyManager.update(DaveProjectileManager);

        timeInit = true;
    }
    
}
