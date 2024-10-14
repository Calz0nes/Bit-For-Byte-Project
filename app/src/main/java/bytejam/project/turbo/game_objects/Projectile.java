package bytejam.project.turbo.game_objects;

import org.joml.Vector2f;
import org.joml.Vector4f;

import bytejam.project.renderer.Texture;
import bytejam.project.turbo.util.Transform;

public class Projectile extends Entity {
    private final Vector4f defaultColor = new Vector4f(1, 1, 1,1);

    private Transform transform;
    private Texture texture;
    private Vector4f Color;

    /* Draw Manual */
    public Projectile(Vector4f Color, Transform transform) {
        this.texture = null;
        this.Color = Color;
        this.transform = transform;
    }

    /* Use Texture */
    public Projectile(Texture texture, Transform transform) {
        this.texture = texture;
        this.Color = defaultColor;
        this.transform = transform;
    }

    public Vector2f getPos() {
        return transform.Center;
    }

    @Override
    public Vector2f getSize() {
        return transform.Size;
    }

    public Vector4f getColor() {
        return this.Color;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }


    @Override
    public void setPos(Vector2f newPos) {
        this.transform.Center = newPos;
    }

    @Override
    public void setSize(Vector2f newSize) {
        this.transform.Size = newSize;
    }

    @Override
    public void setColor(Vector4f newColor) {
        this.Color = newColor;
    }

    @Override
    public void Bind() {
        this.texture.bind();
    }

    @Override
    public void unBind() {
        this.texture.unbind();
    }
}
