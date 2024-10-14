package bytejam.project.turbo.game_objects;

import org.joml.Vector2f;
import org.joml.Vector4f;

import bytejam.project.renderer.Texture;

public abstract class Entity {

    public Entity() {
    }

    public abstract Texture getTexture();

    public abstract Vector2f getPos();

    public abstract Vector2f getSize();

    public abstract Vector4f getColor();

    public abstract void setPos(Vector2f newPos);

    public abstract void setSize(Vector2f newSize);
    
    public abstract void setColor(Vector4f newColor);

    public abstract void Bind();
    
    public abstract void unBind();

    public Vector2f[] getTexCoords() {
        Vector2f[] texCoords = {
            new Vector2f(1, 0),
            new Vector2f(1, 1),
            new Vector2f(0, 1),
            new Vector2f(0, 0)
        };
        return texCoords;
    }


}
