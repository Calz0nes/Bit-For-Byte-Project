package bytejam.project.turbo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

import bytejam.project.turbo.Scene;
import bytejam.project.turbo.game_objects.Entity;
import bytejam.project.turbo.game_objects.Projectile;

public class AI {

    private final int agroRadius = 15; // Radius in pixles
    private final int agroSpeed = 15;
    private final int randAttack = 10; // % chance per second.
    private final int attackDurration = 2; // Durration of agro in seconds.
    private final int speed = 15;

    private Transform gameArea;
    private Random random;
    private Entity target;

    private final List<Entity> entities;
    private final List<Projectile> projectiles;

    public AI(Scene scene, Transform gameArea) {
        this.gameArea = gameArea;
        this.target = null;
        this.entities = new ArrayList<>();
        this.projectiles = new ArrayList<>();
    }

    public void Attach(Entity entity) {
        this.target = entity;
    }

    public void Add(Entity entity) {
        this.entities.add(entity);
    }

    public void Go() {
        float time = (float)glfwGetTime();
        Vector2f nextPos = new Vector2f();

        Transform targeTransform = new Transform(target.getPos(), agroRadius);
        for (int i=0; i < entities.size(); i++) {
            if (targeTransform.isInside(entities.get(i).getTransform())) {
                // Pursue.
                if (target.getPos().x > entities.get(i).getPos().x) {
                    nextPos.x += agroSpeed;
                } else {
                    nextPos.x -= agroSpeed;
                }

                if (target.getPos().y > entities.get(i).getPos().y) {
                    nextPos.y += agroSpeed;
                } else {
                    nextPos.y -= agroSpeed;
                }

                entityMove(nextPos, i);

            } else if (Chance(randAttack)) {
                // Projectile attack.

                

            } else {
                // Just move.
                int x = new Random().nextInt(-speed, speed);
                int y = new Random().nextInt(-speed, speed);

                entityMove(new Vector2f(x, y), i);
            }
        }
    }

    // I LOVE GAMBLING!
    private boolean Chance(int PercentChance) {
        // 60 (fps) * 100 / percent chance per second.
        int rand = new Random().nextInt(60 * 100/PercentChance);
        
        // Check to see if rand = 0 because if 100% chance then rand can only = 0.
        if (rand == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Makes sure that the entity stays inside the game area.
    private void entityMove(Vector2f nextPos, int index) {
        if (nextPos.x > gameArea.Size.x/2 + gameArea.Center.x) {
            nextPos.x = gameArea.Size.x/2 + gameArea.Center.x;
        } else if (nextPos.x < -1 * (gameArea.Size.x/2 + gameArea.Center.x)) {
            nextPos.x = -1 * (gameArea.Size.x/2 + gameArea.Center.x);
        }

        if (nextPos.y > gameArea.Size.y/2 + gameArea.Center.y) {
            nextPos.y = gameArea.Size.y/2 + gameArea.Center.y;
        } else if (nextPos.y < -1 * (gameArea.Size.y/2 + gameArea.Center.y)) {
            nextPos.y = -1 * (gameArea.Size.y/2 + gameArea.Center.y);
        }

        entities.get(index).setPos(nextPos);
    }
}
