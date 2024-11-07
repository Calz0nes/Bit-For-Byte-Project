package bytejam.project.turbo.game_objects;

import org.joml.Vector2f;
import org.joml.Vector4f;

import bytejam.project.renderer.Texture;
import bytejam.project.turbo.util.Transform;

public abstract class Entity {

    Vector2f[] texCoordsRight = {
        new Vector2f(1, 0),
        new Vector2f(1, 1),
        new Vector2f(0, 1),
        new Vector2f(0, 0)
    };

    Vector2f[] texCoordsLeft = {
        new Vector2f(0, 0),
        new Vector2f(0, 1),
        new Vector2f(1, 1),
        new Vector2f(1, 0)
    };

    boolean isRight;

    
    public Entity() {
    }

    public abstract Texture getTexture();

    public abstract Vector2f getPos();

    public abstract Vector2f getSize();

    public abstract Vector4f getColor();

    public abstract Transform getTransform();

    public abstract void setPos(Vector2f newPos);

    public abstract void setSize(Vector2f newSize);
    
    public abstract void setColor(Vector4f newColor);

    public void setLeft() {
        isRight = false;
    }

    public void setRight() {
        isRight = true;
    }

    public Vector2f[] getTexCoords() {
       if (isRight) {
            return texCoordsRight; 
       } else {
            return texCoordsRight;
       }
    }


}
