package bytejam.project.turbo.goc;

import java.awt.Point;

public class Projectile {

    private float COG;

    public Projectile() {
        COG = 0.0f;
    }

    public Point getPos() {
        return new Point(0, 0);
    }

    public void setCOG(float COG) {
        this.COG = COG;
    }

}
