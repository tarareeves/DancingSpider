import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jssc.SerialPort;
import jssc.SerialPortException;


public class SpiderDance {
	public JMenuBar menuBar;
	public JFrame frame;
	public Midi midi;
	public static Timer timer;
	
	public static Spider spider;
    public static int i = 0;

	public static int dir = 10;
    public static int allButtons;
    public static SerialPort sPort;
	public static int randomNum;
	public static boolean isUpDown;
	public static Random rand;
    
    static class Task extends TimerTask{

		@Override
		public void run() {
			
			i++;

			if(i%randomNum == 0) {
				randomNum = rand.nextInt((20-8) + 1) + 8;

				isUpDown = !isUpDown;
			}
			
			if(isUpDown)
				upAndDown();
			else
				slideMove();
						
			spider.sendMessage();
		}
    
    }
    
    public SpiderDance(){
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("Closing...");
				if(spider != null){
					if(spider.getSerialPort().isOpened()){
						if(timer != null){
							timer.cancel();
							timer.purge();
						}

						
						try {
							spider.getSerialPort().closePort();
						} catch (SerialPortException e) {
							e.printStackTrace();
						}
						
						System.out.println("Closed.");
					}
				}
			}
    		
    	}, "Shutdown-thread"));
		
		frame = new JFrame("Dancing Spider");
		frame.setSize(600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		createMenu();
		frame.setVisible(true);
		
		midi = Midi.getInstance();

		spider = new Spider();
		spider.setSerialPort(new SerialPort("COM7"));

		if(!spider.connect()){
			System.out.println("Couldn't connect to serial port!");
			return;
		}

		rand = new Random();
		randomNum = rand.nextInt((20-8) + 1) + 8;
		isUpDown = true;

		for(int i = 0; i < 200; i++){
			spider.sendMessage();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Spider should now be up.");
		
		spider.setButtonVal(16);
		spider.sendMessage();
		spider.setButtonVal(128);
		
		while(true){
			spider.sendMessage();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

	public static void main(String[] args) {
		new SpiderDance();
	}
	
	public void createMenu(){
		menuBar = new JMenuBar();
		
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem midiChoose = new JMenuItem("Choose Midi");
		fileMenu.add(midiChoose);
		midiChoose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileFilter midiFilter = new FileNameExtensionFilter("Midi Files", "mid");
				fileChooser.setFileFilter(midiFilter);
				int retVal = fileChooser.showOpenDialog(null);
				if(retVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					Midi.getInstance().setMidiFile(file);
				}
				
				midi.play();
				
				double BPM = midi.getBPM();
				double delayF = ((60000/BPM));
				
				timer = new Timer();
				//timer.schedule(new Task(), 0, (long) delayF/4);
				timer.schedule(new Task(), 0, (long) delayF);
			}
			
		});
		
		menuBar.add(fileMenu);
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem about = new JMenuItem("About Spider Dance");
		helpMenu.add(about);
		about.addActionListener((e) -> {
				String message = "The purpose of this program is to " +
								 "provide functionality that will let " +
								 "the iCORE Robot Spider dance.\nSimply " +
								 "provide a Midi file and the robot " +
								 "will dance to it autonomously based on " +
								 "the song's BPM.\n\nCreated By: Tara Reeves" +
								 "\nVersion: 0.1";
				JOptionPane.showMessageDialog(frame, 
						message, 
						"About Spider Dance", 
						JOptionPane.PLAIN_MESSAGE);
			}
		);
		
		menuBar.add(helpMenu);
		
		frame.setJMenuBar(menuBar);
		
		
	}
	
	public static void slideMove(){
		if(spider.getRightHoriz() == 10){
			spider.setRightHoriz(245);
		}else{
			spider.setRightHoriz(10);
		}
		
		if(i % 2 == 0){		
			if(spider.getLeftHoriz() == 30){
				spider.setLeftHoriz(220);
			}else{
				spider.setLeftHoriz(30);
			}
		}
	}

	public static void newMove() {
		
	}
	
	public static void upAndDown(){
		if(spider.getRightVertical() == 30){
			spider.setRightVertical(128);
			spider.sendMessage();
		}else{
			spider.setRightVertical(30);
			spider.sendMessage();
		}
	}
	
	public static void circle(){
		//In circle, back and forth in directions
		if(i%16 == 0){
			if(dir == 10){
				dir = 246;
				spider.setRightHoriz(127);
				return;
			}else{
				dir = 10;

				spider.setRightHoriz(127);
				return;
			}
		}
		
		spider.setRightHoriz(dir);
	}
	
}
