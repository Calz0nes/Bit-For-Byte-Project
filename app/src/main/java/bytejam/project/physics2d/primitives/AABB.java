package bytejam.project.physics2d.primitives;

import org.joml.Vector2f;

// Axis Aligned Bounding Box
public class AABB {
    private Vector2f center = new Vector2f();
    private Vector2f size = new Vector2f();
    private RigidBody2D rigidbody = null;
   
    public AABB(){

   }

   public AABB(Vector2f min, Vector2f max){
        this.size = new Vector2f(max).sub(min);
        this.center = new Vector2f(min).add(new Vector2f(size).mul(0.5f));
   }

   
    public Vector2f getMin(){

    }

    public Vector2f getMax(){

    }
}

   