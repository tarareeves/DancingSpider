import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;

/**
 * AudioVisualTest
 * @author Tara Reeves          <---- If you write anything in this file, please add your name like this!
 *
 * Purpose:
 *      The purpose of this class is to test the functionality of the AudioVisual class.
 */
public class AudioVisualTest {
    public JFrame frame;
    public AudioVisual audioVisual;

    public AudioVisualTest() {
        // Create GUI
        frame = new JFrame("Audio Visualizer");
        JPanel mainPanel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Create menu
        createMenu();
    }

    public static void main(String[] args) {
        // Set look and feel to that of the system
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new AudioVisualTest();
    }

    /**
     * Create the menu at the top of the frame
     */
    public void createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem audioChoose = new JMenuItem("Choose Audio File");
        fileMenu.add(audioChoose);
        audioChoose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileFilter audioFilter = new FileNameExtensionFilter("WAV Files", "wav");
                fileChooser.setFileFilter(audioFilter);
                int retVal = fileChooser.showOpenDialog(null);
                if(retVal == JFileChooser.APPROVE_OPTION) {
                    audioVisual = new AudioVisual(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }

        });

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
    }

}
