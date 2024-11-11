package bytejam.project.turbo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector2f;

import bytejam.project.renderer.Renderer;
import bytejam.project.turbo.game_objects.Entity;
import bytejam.project.turbo.game_objects.Projectile;

public class EnemyManager {

    private final int agroRadius = 15; // Radius in pixles
    private final int agroSpeed = 15;
    private final int randAttack = 10; // % chance per second.
    private final int speed = 15;

    private final Renderer renderer;
    private final Transform gameArea;
    private Entity target;

    private final List<Entity> entities;
    private final ProjectileManager enemyProjectileManager;

    public EnemyManager(Renderer renderer, ProjectileManager enemyProjectileManager, Transform gameArea) {
        this.renderer = renderer;
        this.gameArea = gameArea;
        this.target = null;
        this.entities = new ArrayList<>();
        this.enemyProjectileManager = enemyProjectileManager;
    }

    public void attachTarget(Entity target) {
        this.target = target;
    }

    public void Add(Entity entity) {
        this.entities.add(entity);
        this.renderer.add(entity);
    }

    public boolean update(ProjectileManager targetProjectileManager) {
        Vector2f nextPos = new Vector2f();

        Transform targetTransform = new Transform(target.getPos(), agroRadius);

        for (Projectile p: enemyProjectileManager.getProjectiles()) {
            if (targetProjectileManager.isCollision(p)) {
                // Removes both projectiles if they colide together.
                this.enemyProjectileManager.removeProjectile(p);
            }
        }

        for (Entity e: entities) {

            if (targetTransform.isInside(e.getTransform())) {
                // Pursue.
                if (target.getPos().x > e.getPos().x) {
                    nextPos.x += agroSpeed;
                } else {
                    nextPos.x -= agroSpeed;
                }

                if (target.getPos().y > e.getPos().y) {
                    nextPos.y += agroSpeed;
                } else {
                    nextPos.y -= agroSpeed;
                }

                entityMove(nextPos, entities.indexOf(e));

            } else if (Chance(randAttack)) {
                // Projectile attack.
                //addProjectile(new Projectile(, target.getTransform().Center, e.getTransform().Center));

            } else {
                // Just move.
                int x = new Random().nextInt(-speed, speed);
                int y = new Random().nextInt(-speed, speed);

                entityMove(new Vector2f(x, y), entities.indexOf(e));
            }

            if (targetProjectileManager.update(e, gameArea)) {
                entities.remove(e);
                renderer.remove(e);
            }
        }
        
        // checks to see if projectile hits target.
        return this.enemyProjectileManager.update(target, gameArea);
    }

    // I LOVE GAMBLING!
    private boolean Chance(int PercentChance) {
        // 60 (fps) * 100 / percent chance per second.
        int rand = new Random().nextInt(60 * 100/PercentChance);
        
        // Check to see if rand = 0 because if 100% chance then rand can only = 0.
        return rand == 0;
    }

    // Makes sure that the entity stays inside the game area.
    private void entityMove(Vector2f nextPos, int index) {
        if (nextPos.x > gameArea.Size.x/2 + gameArea.Center.x) {
            nextPos.x = gameArea.Size.x/2 + gameArea.Center.x;
        } else if (nextPos.x < (-1 * gameArea.Size.x/2) + gameArea.Center.x) {
            nextPos.x = (-1 * gameArea.Size.x/2) + gameArea.Center.x;
        }

        if (nextPos.y > gameArea.Size.y/2 + gameArea.Center.y) {
            nextPos.y = gameArea.Size.y/2 + gameArea.Center.y;
        } else if (nextPos.y < (-1 * gameArea.Size.y/2) + gameArea.Center.y) {
            nextPos.y = (-1 * gameArea.Size.y/2) + gameArea.Center.y;
        }

        entities.get(index).setPos(nextPos);
    }

    private void addProjectile(Projectile projectile) {
        enemyProjectileManager.addProjectile(projectile);
    }
}
