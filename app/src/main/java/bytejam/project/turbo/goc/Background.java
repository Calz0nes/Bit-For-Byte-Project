package bytejam.project.turbo.goc;

import java.awt.Point;
import java.awt.Rectangle;

import bytejam.project.renderer.Texture;

public class Background {

    private Texture bgTexture;

    public Background(String textPath, Rectangle size) {
        bgTexture = new Texture(textPath);
        bgTexture.setPos(new Point(0, 0));
        bgTexture.setSize(size);
    }

    public void bind() {
        bgTexture.bind();
    }

    public void unbind() {
        bgTexture.unbind();
    }


}
