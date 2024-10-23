package bytejam.project.turbo;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Sound {
    private int bufferId;
    private int soureId;
    private String filepath;
    private boolean isPlaying = false;

    private final String filePath;

    public Sound(String filePath, boolean loops) {
        this.filePath = filePath;

        // Allocate space to store the return information from stb
        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);

        ShortBuffer rawAudioBuffer = stb_vorbis_decode_filename(filePath, channelsBuffer, sampleRateBuffer);


        if (rawAudioBuffer == null){
            System.out.println("could not load sound'" + filePath + "'");
            stackPop();
            stackPop();
            return;
        }
    }

    private void stackPop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stackPop'");
    }

    private ShortBuffer stb_vorbis_decode_filename(String filePath2, IntBuffer channelsBuffer,
            IntBuffer sampleRateBuffer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stb_vorbis_decode_filename'");
    }

    private IntBuffer stackMallocInt(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stackMallocInt'");
    }

    private void stackPush() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stackPush'");
    }

    public void play() {

    }
}
