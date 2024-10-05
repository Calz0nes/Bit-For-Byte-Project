package bytejam.project.turbo.goc;

import java.awt.Point;
import java.awt.Rectangle;
public class Collision {

    private Rectangle CamCollision, PlayerCollision, EnemyCollision;

    public Collision() {
        CamCollision = new Rectangle(100, 100);
        PlayerCollision = new Rectangle(100, 100);
        EnemyCollision = new Rectangle(100, 100);
    }   

    public boolean isCam(Point point) {
        return CamCollision.contains(point);
    }

    public boolean isPlayer(Point point) {
        return PlayerCollision.contains(point);
    }

    public boolean isEnemy(Point point) {
        return EnemyCollision.contains(point);
    }
}
