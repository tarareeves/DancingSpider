import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;

/**
 * Created by treeves1 on 2/25/2016.
 */
public class Audio {
    private File audioFile;
    private byte[] leftSamples;
    private byte[] rightSamples;

    public Audio(String filePath) {
        audioFile = new File(filePath);

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat audioFormat = audioInputStream.getFormat();
            float sampleRate = audioFormat.getSampleRate();
            int size = audioInputStream.available();
            byte[] data = new byte[size];
            audioInputStream.read(data);


        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try {
            byte[] fileData = Files.readAllBytes(path);

            // Get the number of channels from the wav file
            int numChannels = fileData[22];

            // Get number of samples in data
            int position = 12;      // This is the first subchunk
            while (!)
            for(int i = 0; i < numChannels; i++) {

            }

        } catch (IOException e) {
            System.out.println("Error: Could not read file at " + filePath);
            e.printStackTrace();
        }
        */
    }
}
