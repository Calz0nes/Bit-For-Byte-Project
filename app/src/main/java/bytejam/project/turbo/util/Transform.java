package bytejam.project.turbo.util;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import org.joml.Vector2f;

public class Transform {

    private final Vector2f defaultCenter = new Vector2f(0, 0);
    private final Vector2f defaultSize = new Vector2f(128, 128);
    private final boolean defaultIsCircle = false;
    
    public Vector2f Center;
    public Vector2f Size;
    public boolean isCircle;

    public Transform(Vector2f Center, Vector2f Size) {
        this.Center = Center;
        this.Size = Size;
        this.isCircle = this.defaultIsCircle;
    }

    public Transform(Vector2f Size) {
        this.Center = this.defaultCenter;
        this.Size = this.Size;
        this.isCircle = this.defaultIsCircle;
    }

    public Transform() {
        this.Center = this.defaultCenter;
        this.Size = this.defaultSize;
        this.isCircle = this.defaultIsCircle;
    }

    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public boolean isInside(Transform bounds) {
        if (isCircle) {
            return new Ellipse2D.Float(bounds.Center.x, bounds.Center.y, bounds.Size.x, bounds.Size.y).contains(this.Center.x, this.Center.y, this.Size.x, this.Size.y);
        } else {
            return new Rectangle2D.Float(bounds.Center.x, bounds.Center.y, bounds.Size.x, bounds.Size.y).contains(this.Center.x, this.Center.y, this.Size.x, this.Size.y);
        }
    }
}
