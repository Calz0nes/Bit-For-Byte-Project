package bytejam.project.turbo;
//import static org.lwjgl.stb.*;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_SOURCE_STATE;
import static org.lwjgl.openal.AL10.AL_STOPPED;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.libc.LibCStdlib.free;


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
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_MONO16;
        }

        bufferId = alGenBuffers();
        alBufferData(bufferId, format, rawAudioBuffer, sampleRate);

        // generate the source 
        int sourceId = alGenSources();

        alSourcei(soureId, AL_BUFFER, bufferId);
        alSourcei(sourceId, AL_LOOPING, loops ? 1: 0);
        alSourcei(sourceId, AL_POSITION, 0);
        alSourcei(sourceId, AL_GAIN, 0.3f);
        //alSourcef(sourceId, format, format);(soureId, AL_GAIN, 0.3f );


        //free stb raw audio buffer
        free(rawAudioBuffer);
    }

    public void delete() {
        alDeleteSources(soureId);
        alDeleteBuffers(bufferId);
    }

    public void play() {
        int state = alGetSourcei(soureId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            alSourcei(soureId, AL_POSITION, 0);
        }
    }
    

    if (!isPlaying) {
        alSourcePlay(soureId);
        isPlaying = true;
    }

 
    public voind stop(){
        if (isPlaying) {
            alSourceStop(soureId);;
            isPlaying = false;
         }
    }

    public String getFilepath() {
        return this.filePath;
    }

    public boolean isPlaying() {
        int state = alGetSourcei(soureId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
        }
        return isPlaying;
    }
}
