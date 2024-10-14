package bytejam.project.turbo.game_objects;

import org.joml.Vector2f;
import org.joml.Vector4f;

import bytejam.project.renderer.Texture;
import bytejam.project.turbo.util.Transform;

public class Player extends Entity {

    private final float defaultplayerSpeed = 5;
    private final float defaultplayerJumpSpeed = 5;

    private Transform transform;
    private Texture defaultTexture, activeTexture;
    private Animation[] animations;
    private Sound[] sounds;
    private float playerSpeed, playerJumpSpeed;
    private int animationSlot, numAnimations;
    private boolean animationIsDone;
    private Vector4f color;

    /* Draw manually */
    public Player(Vector4f color, Transform transform) {
        this.color = color;
        this.transform = transform;
        this.defaultTexture = null;
    }

    /* Assign texture */
    public Player(Texture texture, Transform transform) {
        this.transform = transform;
        this.defaultTexture = texture;
        this.playerSpeed = defaultplayerSpeed;
        this.playerJumpSpeed = defaultplayerJumpSpeed;
        //this.numAnimations = 0;
        //this.animationIsDone = true;
        this.color = new Vector4f(1, 1, 1, 1);

    }
    /* NOTE use AssetPool class to optimise all resources in this class. */
    public void addSound(Sound sound, int slot) {
        this.sounds[slot] = sound;
    }

    public void playSound(int slot) {
        this.sounds[slot].play();
    } 

    /* 
    public void addAnimation(Animation animation, int slot) {
        this.animations[slot] = animation;
    }

    public void runAnimation(int slot) {
        this.animationIsDone = animations[animationSlot].bind();
    }

    // Default value is 0.
    public void bindAnimation(int slot) {
        this.animationSlot = slot;
    }

    public void unbindAnimation() {
        this.animationSlot = 0;
    }
    */
    
    @Override
    public Vector2f getPos() {
        return transform.Center;
    }
    @Override
    public Vector2f getSize() {
        return transform.Size;
    } 
    
    @Override
    public Texture getTexture() {
        return this.defaultTexture;
    }

    @Override
    public Vector4f getColor() {
        return this.color;
    }

    // Allows user to set position but only if next position is inside map.
    @Override
    public void setPos(Vector2f nextPos) {
        /* 
        if (transform.isInside(bounds)) {
            transform.Center = nextPos;
        }
        */
        this.transform.Center = nextPos;
    }

    @Override
    public void setSize(Vector2f newSize) {
        this.transform.Size = newSize;
    }

    @Override
    public void setColor(Vector4f newColor) {
        this.color = newColor;
    }
    
    // Speed is set in pixles per second.
    public void setSpeed(float playerSpeed, float playerJumpSpeed) {
        this.playerSpeed = playerSpeed;
        this.playerJumpSpeed = playerJumpSpeed;
    }

    @Override
    public void Bind() {
        /* 
        if (isDone) {
            texture.bind();
        } else {
            animations[animationSlot].bind();
        }
        */
        if (this.getTexture() != null) {
            this.defaultTexture.bind();
        }
    }

    @Override
    public void unBind() {
        if (this.getTexture() != null) {
            this.defaultTexture.unbind();
        }
        //animations[animationSlot].unbind();
    }

    /* 
    public boolean isHit(Projectile projectile) {
        if (projectile.isInside()) {
            return true;
        } else {
            return false;
        }
    }
    */
}
