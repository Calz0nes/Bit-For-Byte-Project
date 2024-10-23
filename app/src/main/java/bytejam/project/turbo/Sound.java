package bytejam.project.turbo;
//import static org.lwjgl.stb.*;
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

        // Retrieve the extra information that was stored in the buffers by stb
        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();
        // free
        stackPop();
        stackPop();

        //find the correct openAL format
        int format = -1;
        if (channels == 1) {
            format = Al_FORMAT_MONO16;
        } else if (channels == 2) {
            format =Al_FORMAT_MONO16;
        }

        bufferId = alGenBuffers();
        alBufferData(bufferId, format, rawAudioBuffer, sampleRate);

        // generate the source 
        sourceID = alGenSources();

        alsourcei(soureId, AL_BUFFER, bufferId);
        alsourcei(sourceId, AL_LOOPING, loops ? 1: 0);
        alsourcei(sourceId, Al_POSITION, 0);
        alsourcef(soureId, AL_GAIN, 0.3f );


        //free stb raw audio buffer
        free(rawAudioBuffer);
    }

    public void delete() {
        alDeleteSources(soureId);
        alDeleteBuffers(bufferId);
    }

    public void play() {
        int state = alGetSourcei(soureId, AL_SOURVE_STATE);
        if (state == AL_STOPPED) {
            alSourcei(soureId, Al_POSITION, 0);
        }

    }



 
}
