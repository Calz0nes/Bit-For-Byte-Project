package bytejam.project.turbo.util;

import java.util.ArrayList;
import java.util.List;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.game_objects.Entity;
import bytejam.project.turbo.game_objects.Projectile;

public class ProjectileManager {
    private final List<Projectile> projectiles;
    private final Renderer renderer;
    private final Transform gameArea;

    public ProjectileManager(Renderer renderer, Transform gameArea) {
        this.projectiles = new ArrayList<>();
        this.renderer = renderer;
        this.gameArea = gameArea;
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
        this.renderer.add(projectiles.get(projectiles.indexOf(projectile)));
    }

    public void removeProjectile(Projectile projectile) {
        this.projectiles.remove(projectile);
        this.renderer.remove(projectile);
    }

    public boolean isCollision(Entity entity) {
        boolean collision = false;
        for (Projectile p: projectiles) {
            if (entity.getTransform().isInside(p.getTransform())) {
                collision = true;
                removeProjectile(p);
            }
        }

        return collision;
    }

    public List<Projectile> getProjectiles() {
        return this.projectiles;
    }   

    // Goes to the next 
    public boolean goNext(Entity target) {
        
        for (Projectile p: projectiles) {
            p.goNext();
            System.out.println(projectiles.size());
            /*if (p.getTransform().isInside(gameArea)) {
                removeProjectile(p);
                
            }*/
            
        }
        return isCollision(target);
    }
}
