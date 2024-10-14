package bytejam.project.turbo.goc;

import java.awt.Point;
import java.awt.Rectangle;

import org.joml.Vector2f;
import org.joml.Vector4f;

import bytejam.project.renderer.Texture;
import bytejam.project.turbo.Sound;

public class Entity {

    private Point prevPos, nextPos;
    private Texture texture;
    private Animation[] animations;
    private Sound[] sounds;
    private float playerSpeed, jumpSpeed;
    private Rectangle hitBox, size;
    private int animationSlot;
    private boolean isDone;
    private Vector2f[] texCoords;
    private Vector4f color;

    /* Draw manually */
    public Entity(Vector4f color) {
        this.color = color;
        this.texture = null;
    }

    /* Assign texture */
    public Entity(Texture texture, Point initPos, Rectangle size) {
        this.prevPos = initPos;
        this.nextPos = initPos;
        this.texture = texture;
        this.playerSpeed = 10;
        this.jumpSpeed = 10;
        this.size = size;
        this.isDone = true;
        this.color = new Vector4f(1, 1, 1, 1);

    }

    public void addSound(Sound sound, int slot) {
        this.sounds[slot] = sound;
    }

    public void playSound(int slot) {
        this.sounds[slot].play();
    } 

    public void addAnimation(Animation animation, int slot) {
        this.animations[slot] = animation;
    }

    public void runAnimation() {
        this.isDone = animations[animationSlot].bind();
    }

    // Default value is 0.
    public void bindAnimation(int slot) {
        this.animationSlot = slot;
    }

    // Int object can't be set to null.
    public void unbindAnimation() {
        this.animationSlot = 0;
    }

    /* 
    public void setPos(Point nextPos) {
        if (new Collision().isEntity(nextPos)) {
            prevPos = nextPos;
        } else {
            this.texture.setPos(prevPos);
        }
    }
    */

    public void setSize(Rectangle newSize) {
        size = newSize;
    }

    public Point getPos() {
        return prevPos;
    }

    public Rectangle getSize() {
        return size;
    } 
    
    public Vector2f[] getTexCoords() {
        Vector2f[] texCoords = {
            new Vector2f(1, 0),
            new Vector2f(1, 1),
            new Vector2f(0, 1),
            new Vector2f(0, 0)
        };
        return texCoords;
    }
    
    public Texture getTexture() {
        return this.texture;
    }

    public Vector4f getColor() {
        return this.color;
    }
    
    // Speed is set in pixles per second.
    public void setSpeed(float playerSpeed, float jumpSpeed) {
        this.playerSpeed = playerSpeed;
        this.jumpSpeed = jumpSpeed;
    }

    public void addHitbox(int width, int height) {
        hitBox = new Rectangle(width, height);
    }

    public boolean isHit(Projectile projectile) {
        hitBox.setLocation(prevPos);
        if (hitBox.contains(projectile.getPos())) {
            return true;
        } else {
            return false;
        }
    }

    public void bind() {
        if (isDone) {
            texture.bind();
        } else {
            animations[animationSlot].unbind();
        }
    }

    public void undbind() {
        texture.unbind();
        animations[animationSlot].unbind();
    }

}
