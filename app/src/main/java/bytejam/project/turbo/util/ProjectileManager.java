package bytejam.project.turbo.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.game_objects.Entity;
import bytejam.project.turbo.game_objects.Projectile;

public class ProjectileManager {
    private final List<Projectile> projectiles;
    private final Renderer renderer;

    public ProjectileManager(Renderer renderer) {
        this.projectiles = new ArrayList<>();
        this.renderer = renderer;
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
        this.renderer.add(projectile);
    }

    public void removeProjectile(Projectile projectile) {
        this.renderer.remove(projectile);
        this.projectiles.remove(projectile);
    }


    public boolean isCollision(Entity entity) {
        for (Iterator<Projectile> it = projectiles.iterator(); it.hasNext(); ) {
            Projectile p = it.next();
            if (entity.getTransform().isInside(p.getTransform())) {
                it.remove();
                this.renderer.remove(p);
                return true;
            }
        }
        
        return false;
    }

    public List<Projectile> getProjectiles() {
        return this.projectiles;
    }                

    // Update position and collision data.
    public boolean update(Entity target, Transform gameArea) {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile p = iterator.next();
            p.update();
            if (!p.getTransform().isInside(gameArea)) {
                iterator.remove();
                this.renderer.remove(p);
            }
        }
        
        return this.isCollision(target);
    }
}
