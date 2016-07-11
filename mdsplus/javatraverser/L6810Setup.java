/*
		A basic implementation of the DeviceSetup class.
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class L6810Setup extends DeviceSetup
{
	public L6810Setup(Frame parent)
	{
		super(parent);
		
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setDeviceTitle("LeCroy L6810 Transient Recorder");
		getContentPane().setLayout(null);
		setSize(658,583);
		deviceField1.setNumCols(30);
		deviceField1.setTextOnly(true);
		deviceField1.setOffsetNid(2);
		deviceField1.setLabelString("Comment: ");
		getContentPane().add(deviceField1);
		deviceField1.setBounds(12,12,420,24);
		getContentPane().add(deviceDispatch1);
		deviceDispatch1.setBounds(444,12,136,36);
		deviceField3.setNumCols(3);
		deviceField3.setOffsetNid(3);
		deviceField3.setLabelString("Memories: ");
		getContentPane().add(deviceField3);
		deviceField3.setBounds(240,48,130,40);
		deviceChoice1.setOffsetNid(4);
		{
			String[] tempString = new String[3];
			tempString[0] = "1";
			tempString[1] = "2";
			tempString[2] = "4";
			deviceChoice1.setChoiceItems(tempString);
		}
		deviceChoice1.setLabelString("Channels: ");
		deviceChoice1.setChoiceIntValues(new int[] {(int)1,(int)2,(int)4});
		getContentPane().add(deviceChoice1);
		deviceChoice1.setBounds(372,48,122,40);
		JPanel1.setBorder(lineBorder1);
		JPanel1.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		getContentPane().add(JPanel1);
		JPanel1.setBounds(12,108,576,120);
		deviceChoice2.setOffsetNid(5);
		{
			String[] tempString = new String[2];
			tempString[0] = "INTERNAL";
			tempString[1] = "EXTERNAL";
			deviceChoice2.setChoiceItems(tempString);
		}
		deviceChoice2.setLabelString("Source: ");
		JPanel1.add(deviceChoice2);
		deviceChoice2.setBounds(17,6,159,34);
		deviceChoice3.setOffsetNid(6);
		{
			String[] tempString = new String[2];
			tempString[0] = "ENABLED";
			tempString[1] = "DISABLED";
			deviceChoice3.setChoiceItems(tempString);
		}
		deviceChoice3.setLabelString("Holdoff: ");
		JPanel1.add(deviceChoice3);
		deviceChoice3.setBounds(181,6,155,34);
		deviceChoice4.setOffsetNid(7);
		{
			String[] tempString = new String[6];
			tempString[0] = "POSITIVE_SLOPE";
			tempString[1] = "NEGATIVE_SLOPE";
			tempString[2] = "WINDOW";
			tempString[3] = "POSITIVE_HISTERESYS";
			tempString[4] = "NEGATIVE_HISTERESYS";
			tempString[5] = "TTL";
			deviceChoice4.setChoiceItems(tempString);
		}
		deviceChoice4.setLabelString("Slope");
		JPanel1.add(deviceChoice4);
		deviceChoice4.setBounds(341,6,218,34);
		deviceChoice5.setOffsetNid(8);
		{
			String[] tempString = new String[4];
			tempString[0] = "DC";
			tempString[1] = "LOW_PASS";
			tempString[2] = "AC";
			tempString[3] = "HIGH_PASS";
			deviceChoice5.setChoiceItems(tempString);
		}
		deviceChoice5.setLabelString("Coupling: ");
		JPanel1.add(deviceChoice5);
		deviceChoice5.setBounds(38,45,174,34);
		deviceField4.setNumCols(6);
		deviceField4.setOffsetNid(9);
		deviceField4.setLabelString("Upper level: ");
		JPanel1.add(deviceField4);
		deviceField4.setBounds(217,47,155,29);
		deviceField5.setNumCols(6);
		deviceField5.setOffsetNid(10);
		deviceField5.setLabelString("Lower Level: ");
		JPanel1.add(deviceField5);
		deviceField5.setBounds(377,47,161,29);
		deviceField6.setNumCols(30);
		deviceField6.setOffsetNid(11);
		deviceField6.setLabelString("Ext. trigger: ");
		JPanel1.add(deviceField6);
		deviceField6.setBounds(79,84,418,29);
		//$$ lineBorder1.move(36,648);
		JLabel1.setText("Trigger Information:");
		getContentPane().add(JLabel1);
		JLabel1.setBounds(12,84,132,24);
		JPanel2.setBorder(lineBorder2);
		JPanel2.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		getContentPane().add(JPanel2);
		JPanel2.setBounds(12,252,576,96);
		deviceChoice6.setOffsetNid(13);
		{
			String[] tempString = new String[5];
			tempString[0] = "SINGLE_CLOCK";
			tempString[1] = "DOUBLE_CLOCK_1";
			tempString[2] = "DOUBLE_CLOCK_2";
			tempString[3] = "DOUBLE_CLOCK_3";
			tempString[4] = "EXTERNAL";
			deviceChoice6.setChoiceItems(tempString);
		}
		deviceChoice6.setLabelString("Mode: ");
		JPanel2.add(deviceChoice6);
		deviceChoice6.setBounds(47,6,197,34);
		deviceField7.setNumCols(6);
		deviceField7.setOffsetNid(18);
		deviceField7.setLabelString("Near time:");
		JPanel2.add(deviceField7);
		deviceField7.setBounds(249,8,144,29);
		deviceChoice7.setIdentifier("freq1");
		deviceChoice7.setChoiceFloatValues(new float[] {(float)20.0,(float)50.0,(float)100.0,(float)200.0,(float)500.0,(float)1000.0,(float)2000.0,(float)5000.0,(float)10000.0,(float)20000.0,(float)50000.0,(float)100000.0,(float)200000.0,(float)500000.0,(float)1000000.0,(float)2000000.0,(float)5000000.0});
		deviceChoice7.setOffsetNid(19);
		{
			String[] tempString = new String[17];
			tempString[0] = "20";
			tempString[1] = "50";
			tempString[2] = "100";
			tempString[3] = "200";
			tempString[4] = "500";
			tempString[5] = "1E3";
			tempString[6] = "2E3";
			tempString[7] = "5E3";
			tempString[8] = "10E3";
			tempString[9] = "20E3";
			tempString[10] = "50E3";
			tempString[11] = "100E3";
			tempString[12] = "200E3";
			tempString[13] = "500E3";
			tempString[14] = "1E6";
			tempString[15] = "2E6";
			tempString[16] = "5E6";
			deviceChoice7.setChoiceItems(tempString);
		}
		deviceChoice7.setLabelString("Freq. 1: ");
		JPanel2.add(deviceChoice7);
		deviceChoice7.setBounds(398,6,131,34);
		deviceChoice8.setIdentifier("freq2");
		deviceChoice8.setChoiceFloatValues(new float[] {(float)20.0,(float)50.0,(float)100.0,(float)200.0,(float)500.0,(float)1000.0,(float)2000.0,(float)5000.0,(float)10000.0,(float)20000.0,(float)50000.0,(float)100000.0,(float)200000.0,(float)500000.0,(float)1000000.0,(float)2000000.0,(float)5000000.0});
		deviceChoice8.setOffsetNid(20);
		{
			String[] tempString = new String[17];
			tempString[0] = "20";
			tempString[1] = "50";
			tempString[2] = "100";
			tempString[3] = "200";
			tempString[4] = "500";
			tempString[5] = "1E3";
			tempString[6] = "2E3";
			tempString[7] = "5E3";
			tempString[8] = "10E3";
			tempString[9] = "20E3";
			tempString[10] = "50E3";
			tempString[11] = "100E3";
			tempString[12] = "200E3";
			tempString[13] = "500E3";
			tempString[14] = "1E6";
			tempString[15] = "2E6";
			tempString[16] = "5E6";
			deviceChoice8.setChoiceItems(tempString);
		}
		deviceChoice8.setLabelString("Freq. 2: ");
		JPanel2.add(deviceChoice8);
		deviceChoice8.setBounds(43,45,131,34);
		deviceField8.setNumCols(25);
		deviceField8.setOffsetNid(12);
		deviceField8.setLabelString("Ext clock: ");
		JPanel2.add(deviceField8);
		deviceField8.setBounds(179,47,353,29);
		//$$ lineBorder2.move(84,648);
		JLabel2.setText("Clock Information: ");
		getContentPane().add(JLabel2);
		JLabel2.setBounds(12,228,120,24);
		deviceField2.setTextOnly(true);
		deviceField2.setOffsetNid(1);
		deviceField2.setLabelString("CAMAC name:");
		getContentPane().add(deviceField2);
		deviceField2.setBounds(12,48,216,24);
		{
			String[] tempString = new String[2];
			tempString[0] = "Clock frequency 1 cannot be greater than clock frequency 2 ";
			tempString[1] = "End time must be greater than start time for channel 1";
			deviceButtons1.setCheckMessages(tempString);
		}
		{
			String[] tempString = new String[2];
			tempString[0] = "_freq2 > _freq1";
			tempString[1] = "_end1 > _start1";
			deviceButtons1.setCheckExpressions(tempString);
		}
		deviceButtons1.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		getContentPane().add(deviceButtons1);
		deviceButtons1.setBounds(96,540,480,36);
		getContentPane().add(JTabbedPane1);
		JTabbedPane1.setBounds(12,360,624,156);
		deviceChannel1.setOffsetNid(24);
		deviceChannel1.setLabelString("Channel 1:");
		deviceChannel1.setBorderVisible(true);
		JTabbedPane1.add(deviceChannel1);
		deviceChannel1.setBounds(2,27,619,126);
		deviceChoice9.setOffsetNid(33);
		{
			String[] tempString = new String[2];
			tempString[0] = "ENABLED";
			tempString[1] = "DISABLED";
			deviceChoice9.setChoiceItems(tempString);
		}
		deviceChoice9.setLabelString("Baseline: ");
		deviceChannel1.add(deviceChoice9);
		deviceChoice9.setBounds(6,6,164,34);
		deviceChoice10.setChoiceFloatValues(new float[] {(float)0.04096,(float)1.024,(float)4.096,(float)10.24,(float)25.6,(float)51.2,(float)102.4});
		deviceChoice10.setOffsetNid(25);
		{
			String[] tempString = new String[7];
			tempString[0] = ".04096";
			tempString[1] = "1.024";
			tempString[2] = "4.096";
			tempString[3] = "10.24";
			tempString[4] = "25.60";
			tempString[5] = "51.20";
			tempString[6] = "102.4";
			deviceChoice10.setChoiceItems(tempString);
		}
		deviceChoice10.setLabelString("Range: ");
		deviceChannel1.add(deviceChoice10);
		deviceChoice10.setBounds(175,6,132,34);
		deviceChoice11.setOffsetNid(28);
		{
			String[] tempString = new String[2];
			tempString[0] = "DC";
			tempString[1] = "AC";
			deviceChoice11.setChoiceItems(tempString);
		}
		deviceChoice11.setLabelString("Coupling: ");
		deviceChannel1.add(deviceChoice11);
		deviceChoice11.setBounds(312,6,123,34);
		deviceChoice12.setOffsetNid(27);
		{
			String[] tempString = new String[4];
			tempString[0] = "NON INVERTING";
			tempString[1] = "INVERTING";
			tempString[2] = "DOUBLE ENDED";
			tempString[3] = "GROUNDED";
			deviceChoice12.setChoiceItems(tempString);
		}
		deviceChoice12.setLabelString("Input: ");
		deviceChannel1.add(deviceChoice12);
		deviceChoice12.setBounds(440,6,174,34);
		deviceField9.setNumCols(4);
		deviceField9.setOffsetNid(26);
		deviceField9.setLabelString("Offset: ");
		deviceChannel1.add(deviceField9);
		deviceField9.setBounds(36,45,105,29);
		deviceField10.setNumCols(15);
		deviceField10.setOffsetNid(29);
		deviceField10.setLabelString("Start: ");
		deviceChannel1.add(deviceField10);
		deviceField10.setBounds(146,45,219,29);
		deviceField11.setNumCols(15);
		deviceField11.setOffsetNid(30);
		deviceField11.setLabelString("End: ");
		deviceChannel1.add(deviceField11);
		deviceField11.setBounds(370,45,212,29);
		deviceChannel2.setOffsetNid(35);
		deviceChannel2.setLabelString("Channel 2:");
		deviceChannel2.setBorderVisible(true);
		JTabbedPane1.add(deviceChannel2);
		deviceChannel2.setBounds(2,27,619,126);
		deviceChoice13.setOffsetNid(44);
		{
			String[] tempString = new String[2];
			tempString[0] = "ENABLED";
			tempString[1] = "DISABLED";
			deviceChoice13.setChoiceItems(tempString);
		}
		deviceChoice13.setLabelString("Baseline: ");
		deviceChannel2.add(deviceChoice13);
		deviceChoice13.setBounds(6,6,164,34);
		deviceChoice14.setOffsetNid(36);
		{
			String[] tempString = new String[7];
			tempString[0] = ".04096";
			tempString[1] = "1.024";
			tempString[2] = "4.096";
			tempString[3] = "10.24";
			tempString[4] = "25.60";
			tempString[5] = "51.20";
			tempString[6] = "102.4";
			deviceChoice14.setChoiceItems(tempString);
		}
		deviceChoice14.setLabelString("Range: ");
		deviceChannel2.add(deviceChoice14);
		deviceChoice14.setBounds(175,6,132,34);
		deviceChoice15.setOffsetNid(39);
		{
			String[] tempString = new String[2];
			tempString[0] = "DC";
			tempString[1] = "AC";
			deviceChoice15.setChoiceItems(tempString);
		}
		deviceChoice15.setLabelString("Coupling: ");
		deviceChannel2.add(deviceChoice15);
		deviceChoice15.setBounds(312,6,123,34);
		deviceChoice16.setOffsetNid(38);
		{
			String[] tempString = new String[4];
			tempString[0] = "NON INVERTING";
			tempString[1] = "INVERTING";
			tempString[2] = "DOUBLE ENDED";
			tempString[3] = "GROUNDED";
			deviceChoice16.setChoiceItems(tempString);
		}
		deviceChoice16.setLabelString("Input: ");
		deviceChannel2.add(deviceChoice16);
		deviceChoice16.setBounds(440,6,174,34);
		deviceField12.setNumCols(4);
		deviceField12.setOffsetNid(37);
		deviceField12.setLabelString("Offset: ");
		deviceChannel2.add(deviceField12);
		deviceField12.setBounds(36,45,105,29);
		deviceField13.setNumCols(15);
		deviceField13.setOffsetNid(40);
		deviceField13.setLabelString("Start: ");
		deviceChannel2.add(deviceField13);
		deviceField13.setBounds(146,45,219,29);
		deviceField14.setNumCols(15);
		deviceField14.setOffsetNid(41);
		deviceField14.setLabelString("End: ");
		deviceChannel2.add(deviceField14);
		deviceField14.setBounds(370,45,212,29);
		deviceChannel3.setOffsetNid(46);
		deviceChannel3.setLabelString("Channel 3:");
		deviceChannel3.setBorderVisible(true);
		JTabbedPane1.add(deviceChannel3);
		deviceChannel3.setBounds(2,27,619,126);
		deviceChoice17.setOffsetNid(55);
		{
			String[] tempString = new String[2];
			tempString[0] = "ENABLED";
			tempString[1] = "DISABLED";
			deviceChoice17.setChoiceItems(tempString);
		}
		deviceChoice17.setLabelString("Baseline: ");
		deviceChannel3.add(deviceChoice17);
		deviceChoice17.setBounds(6,6,164,34);
		deviceChoice18.setOffsetNid(47);
		{
			String[] tempString = new String[7];
			tempString[0] = ".04096";
			tempString[1] = "1.024";
			tempString[2] = "4.096";
			tempString[3] = "10.24";
			tempString[4] = "25.60";
			tempString[5] = "51.20";
			tempString[6] = "102.4";
			deviceChoice18.setChoiceItems(tempString);
		}
		deviceChoice18.setLabelString("Range: ");
		deviceChannel3.add(deviceChoice18);
		deviceChoice18.setBounds(175,6,132,34);
		deviceChoice19.setOffsetNid(50);
		{
			String[] tempString = new String[2];
			tempString[0] = "DC";
			tempString[1] = "AC";
			deviceChoice19.setChoiceItems(tempString);
		}
		deviceChoice19.setLabelString("Coupling: ");
		deviceChannel3.add(deviceChoice19);
		deviceChoice19.setBounds(312,6,123,34);
		deviceChoice20.setOffsetNid(49);
		{
			String[] tempString = new String[4];
			tempString[0] = "NON INVERTING";
			tempString[1] = "INVERTING";
			tempString[2] = "DOUBLE ENDED";
			tempString[3] = "GROUNDED";
			deviceChoice20.setChoiceItems(tempString);
		}
		deviceChoice20.setLabelString("Input: ");
		deviceChannel3.add(deviceChoice20);
		deviceChoice20.setBounds(440,6,174,34);
		deviceField15.setNumCols(4);
		deviceField15.setOffsetNid(48);
		deviceField15.setLabelString("Offset: ");
		deviceChannel3.add(deviceField15);
		deviceField15.setBounds(36,45,105,29);
		deviceField16.setNumCols(15);
		deviceField16.setOffsetNid(51);
		deviceField16.setLabelString("Start: ");
		deviceChannel3.add(deviceField16);
		deviceField16.setBounds(146,45,219,29);
		deviceField17.setNumCols(15);
		deviceField17.setOffsetNid(52);
		deviceField17.setLabelString("End: ");
		deviceChannel3.add(deviceField17);
		deviceField17.setBounds(370,45,212,29);
		deviceChannel4.setOffsetNid(57);
		deviceChannel4.setLabelString("Channel 1:");
		deviceChannel4.setBorderVisible(true);
		JTabbedPane1.add(deviceChannel4);
		deviceChannel4.setBounds(2,27,619,126);
		deviceChoice21.setOffsetNid(66);
		{
			String[] tempString = new String[2];
			tempString[0] = "ENABLED";
			tempString[1] = "DISABLED";
			deviceChoice21.setChoiceItems(tempString);
		}
		deviceChoice21.setLabelString("Baseline: ");
		deviceChannel4.add(deviceChoice21);
		deviceChoice21.setBounds(6,6,164,34);
		deviceChoice22.setOffsetNid(58);
		{
			String[] tempString = new String[7];
			tempString[0] = ".04096";
			tempString[1] = "1.024";
			tempString[2] = "4.096";
			tempString[3] = "10.24";
			tempString[4] = "25.60";
			tempString[5] = "51.20";
			tempString[6] = "102.4";
			deviceChoice22.setChoiceItems(tempString);
		}
		deviceChoice22.setLabelString("Range: ");
		deviceChannel4.add(deviceChoice22);
		deviceChoice22.setBounds(175,6,132,34);
		deviceChoice23.setOffsetNid(61);
		{
			String[] tempString = new String[2];
			tempString[0] = "DC";
			tempString[1] = "AC";
			deviceChoice23.setChoiceItems(tempString);
		}
		deviceChoice23.setLabelString("Coupling: ");
		deviceChannel4.add(deviceChoice23);
		deviceChoice23.setBounds(312,6,123,34);
		deviceChoice24.setOffsetNid(60);
		{
			String[] tempString = new String[4];
			tempString[0] = "NON INVERTING";
			tempString[1] = "INVERTING";
			tempString[2] = "DOUBLE ENDED";
			tempString[3] = "GROUNDED";
			deviceChoice24.setChoiceItems(tempString);
		}
		deviceChoice24.setLabelString("Input: ");
		deviceChannel4.add(deviceChoice24);
		deviceChoice24.setBounds(440,6,174,34);
		deviceField18.setNumCols(4);
		deviceField18.setOffsetNid(59);
		deviceField18.setLabelString("Offset: ");
		deviceChannel4.add(deviceField18);
		deviceField18.setBounds(36,45,105,29);
		deviceField19.setNumCols(15);
		deviceField19.setIdentifier("start1");
		deviceField19.setOffsetNid(62);
		deviceField19.setLabelString("Start: ");
		deviceChannel4.add(deviceField19);
		deviceField19.setBounds(146,45,219,29);
		deviceField20.setNumCols(15);
		deviceField20.setIdentifier("end1");
		deviceField20.setOffsetNid(63);
		deviceField20.setLabelString("End: ");
		deviceChannel4.add(deviceField20);
		deviceField20.setBounds(370,45,212,29);
		JTabbedPane1.setTitleAt(0,"Channel 1");
		JTabbedPane1.setTitleAt(1,"Channel 2");
		JTabbedPane1.setTitleAt(2,"Channel 3");
		JTabbedPane1.setTitleAt(3,"Channel 4");
		//}}
	}

	public L6810Setup()
	{
		this((Frame)null);
	}

	public L6810Setup(String sTitle)
	{
		this();
		setTitle(sTitle);
	}

	public void setVisible(boolean b)
	{
		if (b)
			setLocation(50, 50);
		super.setVisible(b);
	}

	static public void main(String args[])
	{
		(new L6810Setup()).setVisible(true);
	}

	public void addNotify()
	{
		// Record the size of the window prior to calling parents addNotify.
		Dimension size = getSize();

		super.addNotify();

		if (frameSizeAdjusted)
			return;
		frameSizeAdjusted = true;

		// Adjust size of frame according to the insets
		Insets insets = getInsets();
		setSize(insets.left + insets.right + size.width, insets.top + insets.bottom + size.height);
	}

	// Used by addNotify
	boolean frameSizeAdjusted = false;

	//{{DECLARE_CONTROLS
	DeviceField deviceField1 = new DeviceField();
	DeviceDispatch deviceDispatch1 = new DeviceDispatch();
	DeviceField deviceField3 = new DeviceField();
	DeviceChoice deviceChoice1 = new DeviceChoice();
	JPanel JPanel1 = new JPanel();
	DeviceChoice deviceChoice2 = new DeviceChoice();
	DeviceChoice deviceChoice3 = new DeviceChoice();
	DeviceChoice deviceChoice4 = new DeviceChoice();
	DeviceChoice deviceChoice5 = new DeviceChoice();
	DeviceField deviceField4 = new DeviceField();
	DeviceField deviceField5 = new DeviceField();
	DeviceField deviceField6 = new DeviceField();
	LineBorder lineBorder1 = new LineBorder(Color.black, 1);
	JLabel JLabel1 = new JLabel();
	JPanel JPanel2 = new JPanel();
	DeviceChoice deviceChoice6 = new DeviceChoice();
	DeviceField deviceField7 = new DeviceField();
	DeviceChoice deviceChoice7 = new DeviceChoice();
	DeviceChoice deviceChoice8 = new DeviceChoice();
	DeviceField deviceField8 = new DeviceField();
	LineBorder lineBorder2 = new LineBorder(Color.black, 1);
	JLabel JLabel2 = new JLabel();
	DeviceField deviceField2 = new DeviceField();
	DeviceButtons deviceButtons1 = new DeviceButtons();
	JTabbedPane JTabbedPane1 = new JTabbedPane();
	DeviceChannel deviceChannel1 = new DeviceChannel();
	DeviceChoice deviceChoice9 = new DeviceChoice();
	DeviceChoice deviceChoice10 = new DeviceChoice();
	DeviceChoice deviceChoice11 = new DeviceChoice();
	DeviceChoice deviceChoice12 = new DeviceChoice();
	DeviceField deviceField9 = new DeviceField();
	DeviceField deviceField10 = new DeviceField();
	DeviceField deviceField11 = new DeviceField();
	DeviceChannel deviceChannel2 = new DeviceChannel();
	DeviceChoice deviceChoice13 = new DeviceChoice();
	DeviceChoice deviceChoice14 = new DeviceChoice();
	DeviceChoice deviceChoice15 = new DeviceChoice();
	DeviceChoice deviceChoice16 = new DeviceChoice();
	DeviceField deviceField12 = new DeviceField();
	DeviceField deviceField13 = new DeviceField();
	DeviceField deviceField14 = new DeviceField();
	DeviceChannel deviceChannel3 = new DeviceChannel();
	DeviceChoice deviceChoice17 = new DeviceChoice();
	DeviceChoice deviceChoice18 = new DeviceChoice();
	DeviceChoice deviceChoice19 = new DeviceChoice();
	DeviceChoice deviceChoice20 = new DeviceChoice();
	DeviceField deviceField15 = new DeviceField();
	DeviceField deviceField16 = new DeviceField();
	DeviceField deviceField17 = new DeviceField();
	DeviceChannel deviceChannel4 = new DeviceChannel();
	DeviceChoice deviceChoice21 = new DeviceChoice();
	DeviceChoice deviceChoice22 = new DeviceChoice();
	DeviceChoice deviceChoice23 = new DeviceChoice();
	DeviceChoice deviceChoice24 = new DeviceChoice();
	DeviceField deviceField18 = new DeviceField();
	DeviceField deviceField19 = new DeviceField();
	DeviceField deviceField20 = new DeviceField();
	//}}

}