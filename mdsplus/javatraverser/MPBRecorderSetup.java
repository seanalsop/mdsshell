/*
		A basic implementation of the DeviceSetup class.
*/

import java.awt.*;

public class MPBRecorderSetup extends DeviceSetup
{
	public MPBRecorderSetup(Frame parent)
	{
		super(parent);
		
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setDeviceProvider("150.178.3.112");
		setDeviceTitle("MPB Event Recorder");
		setDeviceType("MPBRecorder");
		getContentPane().setLayout(null);
		setSize(491,165);
		deviceField1.setNumCols(20);
		deviceField1.setTextOnly(true);
		deviceField1.setOffsetNid(1);
		deviceField1.setLabelString("CAMAC Name:");
		getContentPane().add(deviceField1);
		deviceField1.setBounds(0,12,348,40);
		deviceField2.setNumCols(25);
		deviceField2.setTextOnly(true);
		deviceField2.setOffsetNid(3);
		deviceField2.setLabelString("Start Event: ");
		getContentPane().add(deviceField2);
		deviceField2.setBounds(0,60,396,40);
		getContentPane().add(deviceDispatch1);
		deviceDispatch1.setBounds(348,12,131,40);
		getContentPane().add(deviceButtons1);
		deviceButtons1.setBounds(84,108,324,40);
		//}}
	}

	public MPBRecorderSetup()
	{
		this((Frame)null);
	}

	public MPBRecorderSetup(String sTitle)
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
		(new MPBRecorderSetup()).setVisible(true);
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
	DeviceField deviceField2 = new DeviceField();
	DeviceDispatch deviceDispatch1 = new DeviceDispatch();
	DeviceButtons deviceButtons1 = new DeviceButtons();
	//}}

}