import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * AudioVisual
 * @author Tara Reeves          <---- If you write anything in this file, please add your name like this!
 *
 * Purpose:
 *      The purpose of this class is to gather information from an audio file and draw an audio visual picture.
 */
public class AudioVisual {
    File audioFile;

    public AudioVisual(String fileName) {
        this.audioFile = new File(fileName);
        System.out.println(audioFile.getAbsoluteFile());

        try {
            // Get the audio stream from the file
            AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
            AudioInputStream rawDataStream = null;

            // Get the audio format and decode it
            AudioFormat format = stream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                                        format.getSampleRate(),
                                                        16,
                                                        format.getChannels(),
                                                        format.getChannels() * 2,
                                                        format.getSampleRate(),
                                                        false);
            rawDataStream = AudioSystem.getAudioInputStream(decodedFormat, stream);

            // Get PCM data
            int numBytes = rawDataStream.available();
            byte[] audioBytes = new byte[numBytes];
            rawDataStream.read(audioBytes);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawPicture(byte[] pcmData) {
        int width = 800, height = 600;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(Color.pink);

        try {
            ImageIO.write(img, "PNG", new File("YoMama.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
