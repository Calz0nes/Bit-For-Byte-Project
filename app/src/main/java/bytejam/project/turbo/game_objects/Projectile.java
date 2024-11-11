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
    private Vector2f target;
    private final Transform transform;
    private final Texture texture;
    private Vector4f Color;

    /* Draw Manual */
    public Projectile(Vector4f Color, Vector2f target, Vector2f orgin) {
        this.texture = null;
        this.Color = Color;
        this.target = target;
        this.transform = new Transform(orgin, new Vector2f(30,30));
        this.vAdd = new Vector2f(0,0);
        this.V = defualtV;
        plot();
    }

    /* Use Texture */
    public Projectile(Texture texture, Vector2f target, Vector2f orgin) {
        this.texture = texture;
        this.Color = defaultColor;
        this.target = target;
        this.transform = new Transform(orgin, new Vector2f(30,30));
        this.vAdd = new Vector2f(0,0);
        this.V = defualtV;
        plot();
        System.out.println("You can see this.");
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
        float xAdd = this.transform.Center.x + target.x;
        float yAdd = this.transform.Center.y + target.y;

        double angle = Math.tanh(yAdd/xAdd);
        
        xAdd = V * (float)Math.cos(angle);
        yAdd = V * (float)Math.sin(angle);

        
        this.vAdd = new Vector2f(xAdd, yAdd);
    }

    public void update() {
        this.transform.Center.add(vAdd);
    }
}
