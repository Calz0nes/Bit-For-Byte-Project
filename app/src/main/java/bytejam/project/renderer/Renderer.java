package bytejam.project.renderer;

import java.util.ArrayList;
import java.util.List;

import bytejam.project.turbo.game_objects.Entity;

public class Renderer {
    private final int MAX_BATCH_SIZE = 100;
    private final List<RenderBatch> batches;

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

    public void remove(Entity entity) {
        for (RenderBatch batch : batches) {
            if (batch.hasEntity(entity)) {
                batch.remove(entity);
            }
        }
    }

    public void render() {
        System.out.println(batches.size());
        for (RenderBatch batch : batches) {
            batch.render();
            
        }
    }

}
