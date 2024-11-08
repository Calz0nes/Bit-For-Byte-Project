package bytejam.project.turbo.game_objects;

import org.joml.Vector2f;
import org.joml.Vector4f;

import bytejam.project.renderer.Texture;
import bytejam.project.turbo.util.Transform;

public class Projectile extends Entity {
    private final Vector4f defaultColor = new Vector4f(1, 1, 1,1);

    // Velocity in pixles per second.
    private final float defualtV = 8.0f;

    private float V;
    private Vector2f vAdd;
    private final Transform targetTransform;
    private final Transform transform;
    private final Texture texture;
    private Vector4f Color;

    /* Draw Manual */
    public Projectile(Vector4f Color, Transform targetTransform, Transform entityTransform) {
        this.texture = null;
        this.Color = Color;
        this.targetTransform = targetTransform;
        this.transform = entityTransform;
        this.vAdd = new Vector2f(0,0);
        this.V = defualtV;
        plot();
    }

    /* Use Texture */
    public Projectile(Texture texture, Transform targetTransform, Transform entityTransform) {
        this.texture = texture;
        this.Color = defaultColor;
        this.targetTransform = targetTransform;
        this.transform = entityTransform;
        this.vAdd = new Vector2f(0,0);
        this.V = defualtV;
        plot();
    }

    @Override
    public Vector2f getPos() {
        return transform.Center;
    }

    @Override
    public Vector2f getSize() {
        return transform.Size;
    }

    @Override
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
    public Transform getTransform() {
        return this.transform;
    }

    public void setSpeed(float v) {
        this.V = v;
    }

    private void plot() {
        float angle = transform.Center.angle(targetTransform.Center);

        float xAdd = (float)Math.cos(V/60 * angle);
        float yAdd = (float)Math.sin(V/60 * angle);

        this.vAdd = new Vector2f(xAdd, yAdd);
    }

    public void goNext() {
        this.transform.Center.add(vAdd);
    }
}
