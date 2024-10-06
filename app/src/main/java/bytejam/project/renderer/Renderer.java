package bytejam.project.renderer;

import java.util.ArrayList;
import java.util.List;

import bytejam.project.turbo.goc.Background;
import bytejam.project.turbo.goc.Entity;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void add(Entity entity) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom()) {
                batch.addEntity(entity);
                added = true;
                break;
            }

        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addEntity(entity);
        }
    }

    public void add(Background background) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom()) {
                batch.addBackground(background);
                added = true;
                break;
            }

        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addBackground(background);
        }
    }

    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }

}
