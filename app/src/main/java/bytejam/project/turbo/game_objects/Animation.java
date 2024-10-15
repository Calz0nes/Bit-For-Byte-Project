package bytejam.project.turbo.game_objects;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import bytejam.project.renderer.Texture;

public class Animation {

    private float Time;
    private float delta;
    private int Frame;
    private final Texture[] frames;

    public Animation() {
        frames = new Texture[10];
        Time = (float)glfwGetTime();
        Frame = 0;
    }

    /* 
    public void addFrames(String[] texturePaths) {
        for (String f : texturePaths) {
            frames[f.indexOf(f)] = new Texture(f);
            frames[f.indexOf(f)].setSize(entity.getSize());
        }
    }
    */

    public void setSpeed(float delta) {
        this.delta = delta;
    }

    public boolean bind() {
        //frames[prevFrame].setPos(entity.getPos());
        

        if ((float)glfwGetTime() - Time > delta) {
            frames[Frame].bind();

            Time = (float)glfwGetTime();
            Frame += 1;
            if (Frame == frames.length) {
                Frame = 0;
                return true;
            } else {
                return false;
            }
        } else {
            frames[Frame].bind();
            return false;
        }
    }

    public void unbind() {
        frames[Frame].unbind();
    }
}

