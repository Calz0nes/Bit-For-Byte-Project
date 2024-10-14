package bytejam.project.turbo.goc;

import java.awt.Rectangle;

import bytejam.project.renderer.Texture;

public class Background {

    private Texture bgTexture;

    public Background(String textPath, Rectangle size) {
        bgTexture = new Texture(textPath);
    }

    public void bind() {
        bgTexture.bind();
    }

    public void unbind() {
        bgTexture.unbind();
    }


}
