import jssc.SerialPort;
import jssc.SerialPortException;


public class Spider {
	private int rightVertical;
	private int rightHoriz;
	private int leftVertical;
	private int leftHoriz;
	
	private int buttonVal;
	private SerialPort serialPort;
    
    public Spider(){
    	rightVertical = 128;
    	rightHoriz = 128;
    	leftVertical = 128;
    	leftHoriz = 128;
    }
    
    public void sendMessage(){
    	if(serialPort.isOpened()){
    		try {
    			int checksum = (255-(rightVertical+rightHoriz+
    					leftVertical+leftHoriz+buttonVal)%256);
    				
    			serialPort.writeByte((byte)0xff);			//Header
    			serialPort.writeByte((byte)rightVertical);	//rightV
    			serialPort.writeByte((byte)rightHoriz);		//rightH
    			serialPort.writeByte((byte)leftVertical);	//leftV
    			serialPort.writeByte((byte)leftHoriz);		//leftH
    			serialPort.writeByte((byte)buttonVal);		//Buttons
    			serialPort.writeByte((byte)0);				//send 0
    			serialPort.writeByte((byte)checksum);
    		} catch (SerialPortException e) {
    			e.printStackTrace();
    			return;
    		}	
    	
    	} else {
    		System.out.println("The serial port is not yet opened.");
    		System.out.println("No data can be sent.");
    		return;
    	}
   }
	
	public boolean connect(){
		try {
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_38400, 
							SerialPort.DATABITS_8, 
							SerialPort.STOPBITS_1, 
							SerialPort.PARITY_NONE);
			return true;
		} catch (SerialPortException e) {
			e.printStackTrace();
			return false;
		}
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	public int getRightVertical() {
		return rightVertical;
	}

	public void setRightVertical(int rightVertical) {
		this.rightVertical = (rightVertical % 255);
	}

	public int getRightHoriz() {
		return rightHoriz;
	}

	public void setRightHoriz(int rightHoriz) {
		this.rightHoriz = (rightHoriz % 255);
	}

	public int getLeftVertical() {
		return leftVertical;
	}

	public void setLeftVertical(int leftVertical) {
		this.leftVertical = (leftVertical % 255);
	}

	public int getLeftHoriz() {
		return leftHoriz;
	}

	public void setLeftHoriz(int leftHoriz) {
		this.leftHoriz = (leftHoriz % 255);
	}
	
	public int getButtonVal() {
		return buttonVal;
	}

	public void setButtonVal(int buttonVal) {
		this.buttonVal = buttonVal;
	}

	
}
