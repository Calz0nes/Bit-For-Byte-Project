package bytejam.project.turbo.game_objects;

import org.joml.Vector2f;
import org.joml.Vector4f;

import bytejam.project.renderer.Texture;
import bytejam.project.turbo.util.Transform;

public class Cursor extends Entity{
    private final Vector4f defaultColor = new Vector4f(1, 1, 1, 1);

    private Texture texture;
    private Vector4f Color;
    private Transform transform;


    public Cursor(Vector4f Color, Transform transform) {
        this.texture = null;
        this.Color = Color;
        this.transform = transform;
    }

    public Cursor(Texture texture, Transform transform) {
        this.texture = texture;
        this.Color = this.defaultColor;
        this.transform = transform;
    }
    @Override
    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public Vector2f getPos() {
        return this.transform.Center;
    }

    @Override
    public Vector2f getSize() {
        return this.transform.Size;
    }

    @Override
    public Vector4f getColor() {
        return this.Color;
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
    public Transform getTransform() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTransform'");
    }

}
