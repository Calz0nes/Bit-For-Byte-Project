package bytejam.project.turbo.goc;

import java.awt.Point;
import java.awt.Rectangle;
public class Collision {

    private Rectangle CamCollision, EntityCollision;

    public Collision() {
        CamCollision = new Rectangle(100, 100);
        EntityCollision = new Rectangle(100, 100);
    }   

    public boolean isCam(Point point) {
        return CamCollision.contains(point);
    }

    public boolean isEntity(Point point) {
        return EntityCollision.contains(point);
    }
}
