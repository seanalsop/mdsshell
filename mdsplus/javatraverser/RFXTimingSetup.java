import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
class RFXTimingSetup extends DeviceSetup
{
    static String topTiming = "\\TIMING";
    static final int USAGE_DEVICE = 3;
    static final int K_NODES_PER_CHANNEL = 3;
    static final int N_CHANNEL_1 = 3;
    static final int N_CHAN_EVENT = 1;
    static final int N_CHAN_TRIG = 2;
    static final int N_RECORDER_EVENTS = 4;
    static final int N_RECORDER_TIMES = 5;
    
    static final int RFX_CLOCK = 1;
    static final int RFX_GCLOCK = 2;
    static final int RFX_DCLOCK = 3;
    static final int RFX_PULSE = 14;
    
    static final int N_CLOCK_DECODER = 2;
    static final int N_CLOCK_CHAN = 3;
    static final int N_CLOCK_FREQ = 4;
    static final int N_PULSE_CHAN = 3;
    static final int N_PULSE_TRIG_MODE = 4;
    static final int N_PULSE_EVENT = 5;
    static final int N_PULSE_EXT_TRIGGER = 6;
    static final int N_PULSE_OUTPUT_MODE = 14;
    static final int N_PULSE_DELAY      =  7;
    static final int N_PULSE_DURATION    =  8;
    static final int N_DCLOCK_DECODER   =     2;
    static final int N_DCLOCK_GATE_CHAN =     3;
    static final int N_DCLOCK_CLOCK_CHAN=     4;
    static final int N_DCLOCK_TRIG_MODE =     5;
    static final int N_DCLOCK_EVENT     =     6;
    static final int N_DCLOCK_EXT_TRIG  =     7;
    static final int N_DCLOCK_DELAY     =     8;
    static final int N_DCLOCK_FREQUENCY1=     9;
    static final int N_DCLOCK_FREQUENCY2=     10;
    static final int N_DCLOCK_DURATION  =     11;
    static final int N_DCLOCK_OUTPUT_MODE=    12;
    static final int N_GCLOCK_DECODER   =     2;
    static final int N_GCLOCK_GATE_CHAN =     3;
    static final int N_GCLOCK_CLOCK_CHAN=     4;
    static final int N_GCLOCK_TRIG_MODE =     5;
    static final int N_GCLOCK_EVENT     =     6;
    static final int N_GCLOCK_EXT_TRIG  =     7;
    static final int N_GCLOCK_DELAY     =     8;
    static final int N_GCLOCK_FREQUENCY =     9;
    static final int N_GCLOCK_DURATION  =     10;
    static final int N_GCLOCK_OUTPUT_MODE=    11;


    static final int SIGNAL_HEIGHT = 55;
    static final int WAVE_HEIGHT = 20;
    static final int LINE_HEIGHT = 12;
    static final int WAVE_X = 10;

    Hashtable eventTimes = new Hashtable();
    Vector rfxDevices = new Vector();
    Vector mpbDecoders = new Vector();
    Vector rfxDeviceCongloms = new Vector();
    Vector rfxDeviceNids = new Vector();
    Vector mpbDecoderNids = new Vector();
    Vector errorStrings = new Vector();
    JTable eventTable;
    String evNames[];
    Data evTimes[];
    String evRecTimes[];
    String fullRecEvents[];
    float fullRecTimes[];
    NidData recorderNid = null;
    boolean firstScan = true;
    JSplitPane splitP;
    
    class Decoder
    {
        String path;
        Device[] devices = new Device[6];
        boolean [] isGate = new boolean[6];
        public Decoder(String path) {this.path = path; }
        public boolean setChan(int chan, Device device, boolean gate)
        {
            if(devices[chan - 1] != null) return false;
            devices[chan - 1] = device;
            isGate[chan - 1] = gate;
            return true;
        }
        public String getPath() {return path; }
    }
        
    class Device 
    {
        String path;
        int type;
        boolean initialHigh = false;
        Data []times;
        float [] freqs;
        public Device(int type, String path, Data [] times, float [] freqs, boolean initialHigh)
        {
            this.path = path;
            this.type = type;
            this.times = times;
            this.freqs = freqs;
            this.initialHigh = initialHigh;
        }
        public void draw(Graphics g, int idx, float start, float convFact, int endX,
            String decPath, int decChan) throws IllegalDataException, DatabaseException, java.rmi.RemoteException
        {
            g.drawString(path + "    Decoder:" + decPath + "   Chan"+ (decChan + 1), 0, idx * SIGNAL_HEIGHT - WAVE_HEIGHT - LINE_HEIGHT - 4);
            switch(type) {
                case RFX_CLOCK:
                    g.drawString("f: "+freqs[0], 0, idx * SIGNAL_HEIGHT -WAVE_HEIGHT - 4);
                    for(int i = WAVE_X; i < endX; i++)
                    {
                        if(i % 5 == 0)
                            g.drawLine(i, idx * SIGNAL_HEIGHT, i, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                    }
                    g.drawLine(WAVE_X, idx * SIGNAL_HEIGHT, endX, idx * SIGNAL_HEIGHT);
                    g.drawLine(WAVE_X, idx * SIGNAL_HEIGHT - WAVE_HEIGHT, endX, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                    break;
                case RFX_PULSE:
                    float ftime1 = subtree.evaluateData(times[0], Tree.context).getFloat();
                    if(times.length == 1)
                    {
                        g.drawString("t1: "+ftime1, 0, idx * SIGNAL_HEIGHT -WAVE_HEIGHT - 4);
                        int time1 = WAVE_X + (int)((ftime1 - start)*convFact);
                        int lev = (initialHigh)?idx * SIGNAL_HEIGHT - WAVE_HEIGHT:idx * SIGNAL_HEIGHT;
                        g.drawLine(WAVE_X, lev, time1, lev);
                        int lev1 = (!initialHigh)?idx * SIGNAL_HEIGHT - WAVE_HEIGHT:idx * SIGNAL_HEIGHT;
                        g.drawLine(time1, lev, time1, lev1);
                        g.drawLine(time1, lev1, endX, lev1);
                    }
                    else
                    {
                        float ftime2 = subtree.evaluateData(times[1], Tree.context).getFloat();
                        g.drawString("t1: "+ftime1+"    t2: " + ftime2, 0, idx * SIGNAL_HEIGHT -WAVE_HEIGHT - 4);
                        int time1 = WAVE_X + (int)((ftime1 - start)*convFact);
                        int time2 = WAVE_X + (int)((ftime2 - start)*convFact);
                        int lev = (initialHigh)?idx * SIGNAL_HEIGHT - WAVE_HEIGHT:idx * SIGNAL_HEIGHT;
                        g.drawLine(WAVE_X, lev, time1, lev);
                        int lev1 = (!initialHigh)?idx * SIGNAL_HEIGHT - WAVE_HEIGHT:idx * SIGNAL_HEIGHT;
                        g.drawLine(time1, lev, time1, lev1);
                        g.drawLine(time1, lev1, time2, lev1);
                        g.drawLine(time2, lev1, time2, lev);
                        g.drawLine(time2, lev, endX, lev);
                    }
                    break;
                case RFX_DCLOCK:
                    g.drawLine(WAVE_X, idx * SIGNAL_HEIGHT, endX, idx * SIGNAL_HEIGHT);
                    g.drawLine(WAVE_X, idx * SIGNAL_HEIGHT - WAVE_HEIGHT, endX, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                    ftime1 = subtree.evaluateData(times[0], Tree.context).getFloat();
                    if(times.length == 1)
                    {
                        g.drawString(" f1: "+ freqs[0] + "    f2: " + freqs[1] + "    t1: "+ftime1, 0, idx * SIGNAL_HEIGHT -WAVE_HEIGHT - 4);
                        int time1 = WAVE_X + (int)((ftime1 - start)*convFact);
                        int delta = (freqs[0] < freqs[1])?5:3;
                        for(int i = WAVE_X; i < time1; i++)
                        {
                            if(i % delta == 0)
                                g.drawLine(i, idx * SIGNAL_HEIGHT, i, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        }
                        delta = (freqs[0] < freqs[1])?3:5;
                        for(int i = time1; i < endX; i++)
                        {
                            if(i % delta == 0)
                                g.drawLine(i, idx * SIGNAL_HEIGHT, i, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        }
                    }
                    else
                    {
                        float ftime2 = subtree.evaluateData(times[1], Tree.context).getFloat();
                        g.drawString("f1: "+ freqs[0] + "   f2: " + freqs[1] + "    t1: "+ftime1+"   t2: " + ftime2, 
                            0, idx * SIGNAL_HEIGHT -WAVE_HEIGHT - 4);
                        int time1 = WAVE_X + (int)((ftime1 - start)*convFact);
                        int time2 = WAVE_X + (int)((ftime2 - start)*convFact);
                        int delta = (freqs[0] < freqs[1])?5:3;
                        for(int i = WAVE_X; i < time1; i++)
                        {
                            if(i % delta == 0)
                                g.drawLine(i, idx * SIGNAL_HEIGHT, i, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        }
                        delta = (freqs[0] < freqs[1])?3:5;
                        for(int i = time1; i < time2; i++)
                        {
                            if(i % delta == 0)
                                g.drawLine(i, idx * SIGNAL_HEIGHT, i, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        }
                        delta = (freqs[0] < freqs[1])?5:3;
                         for(int i = time2; i < endX; i++)
                        {
                            if(i % delta == 0)
                                g.drawLine(i, idx * SIGNAL_HEIGHT, i, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        }
                    }
                    break;
                case RFX_GCLOCK:
                    ftime1 = subtree.evaluateData(times[0], Tree.context).getFloat();
                    if(times.length == 1)
                    {
                        g.drawString("f: "+ freqs[0] + "    t1: "+ftime1, 0, idx * SIGNAL_HEIGHT -WAVE_HEIGHT - 4);
                        int time1 = WAVE_X + (int)((ftime1 - start)*convFact);
                        g.drawLine(WAVE_X, idx * SIGNAL_HEIGHT, time1, idx * SIGNAL_HEIGHT);
                        g.drawLine(time1, idx * SIGNAL_HEIGHT, time1, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        g.drawLine(time1, idx * SIGNAL_HEIGHT, endX, idx * SIGNAL_HEIGHT);
                        g.drawLine(time1, idx * SIGNAL_HEIGHT - WAVE_HEIGHT, endX, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        for(int i = time1; i < endX; i++)
                        {
                            if(i % 5 == 0)
                                g.drawLine(i, idx * SIGNAL_HEIGHT, i, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        }
                    }
                    else
                    {
                        float ftime2 = subtree.evaluateData(times[1], Tree.context).getFloat();
                        g.drawString("f: "+ freqs[0] + "    t1: "+ftime1+"   t2: " + ftime2, 0, idx * SIGNAL_HEIGHT -WAVE_HEIGHT - 4);
                        int time1 = WAVE_X + (int)((ftime1 - start)*convFact);
                        int time2 = WAVE_X + (int)((ftime2 - start)*convFact);
                        g.drawLine(WAVE_X, idx * SIGNAL_HEIGHT, time1, idx * SIGNAL_HEIGHT);
                        g.drawLine(time1, idx * SIGNAL_HEIGHT, time1, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        g.drawLine(time1, idx * SIGNAL_HEIGHT, time2, idx * SIGNAL_HEIGHT);
                        g.drawLine(time1, idx * SIGNAL_HEIGHT - WAVE_HEIGHT, time2, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        for(int i = time1; i < time2; i++)
                        {
                            if(i % 5 == 0)
                                g.drawLine(i, idx * SIGNAL_HEIGHT, i, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        }
                        g.drawLine(time2, idx * SIGNAL_HEIGHT, time2, idx * SIGNAL_HEIGHT - WAVE_HEIGHT);
                        g.drawLine(time2, idx * SIGNAL_HEIGHT, endX, idx * SIGNAL_HEIGHT);
                    }
                    break;
            }
        }
    }
            
            
        
    void scan()
    {
        rfxDevices = new Vector();
        mpbDecoders = new Vector();
        rfxDeviceCongloms = new Vector();
        rfxDeviceNids = new Vector();
        mpbDecoderNids = new Vector();
        errorStrings = new Vector();
        NidData []deviceNids = null;
        try {
            try {
                NidData nid = subtree.resolve(new PathData(topTiming), Tree.context);
                subtree.setDefault(nid, Tree.context);
            }catch(Exception exc)
            {
                subtree.setDefault(new NidData(0), Tree.context);
            }
            deviceNids = subtree.getWild(USAGE_DEVICE, Tree.context);
        }catch(Exception exc)
        {System.err.println("RFXTimingSetup: Error reading device data: "+ exc);}
        for(int idx = 0; idx < deviceNids.length; idx++)
        {
            try {
                if(!subtree.isOn(deviceNids[idx], Tree.context)) continue;
                ConglomData conglom = (ConglomData)subtree.getData(deviceNids[idx], Tree.context);
                String model = subtree.dataToString(conglom.getModel());
                if(model.equals("\"MPBRecorder\""))
                    recorderNid = deviceNids[idx];
                if(model.equals("\"RFXClock\"") || model.equals("\"RFXDClock\"") ||
                        model.equals("\"RFXGClock\"") || model.equals("\"RFXPulse\""))
                {
                    rfxDeviceCongloms.addElement(conglom);
                    rfxDeviceNids.addElement(deviceNids[idx]);
                }
                else if(model.equals("\"MPBDecoder\""))
                    mpbDecoderNids.addElement(deviceNids[idx]);
                else if(model.equals("\"MPBEncoder\""))
                {
                    int baseNid = deviceNids[idx].getInt();
                    for(int chan = 0; chan < 7; chan++)
                    {
                        try {
                            if(!subtree.isOn(new NidData(baseNid + chan * K_NODES_PER_CHANNEL + N_CHANNEL_1), Tree.context))
                                continue;
                            String event = (subtree.getData(new NidData(baseNid + chan * K_NODES_PER_CHANNEL + 
                                N_CHANNEL_1 + N_CHAN_EVENT), Tree.context)).getString();
                            Data time = subtree.getData(new NidData(baseNid + chan * K_NODES_PER_CHANNEL + 
                                N_CHANNEL_1 + N_CHAN_TRIG), Tree.context);
                            if(!event.trim().equals(""))
                                eventTimes.put(event, time);
                        }catch(Exception exc){}
                    }
                }
            }
            catch(Exception exc){}
        }
        for(int idx = 0; idx < mpbDecoderNids.size(); idx++)
        {
            try {
                NidData nid = (NidData)mpbDecoderNids.elementAt(idx);
                String path = subtree.getInfo(nid, Tree.context).getFullPath();
                mpbDecoders.add(new Decoder(path));
            }catch(Exception exc){}
        }
        for(int idx = 0; idx < rfxDeviceCongloms.size(); idx++)
        {
            ConglomData conglom = (ConglomData)rfxDeviceCongloms.elementAt(idx);
            String model;
            try {
                model = subtree.dataToString(conglom.getModel());
            }catch(Exception exc){continue;}
            if(model.equals("\"RFXClock\""))
            {
                try {
                    Data decoder = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt()
                        + N_CLOCK_DECODER), Tree.context);
                    if(decoder instanceof PathData)
                        decoder = subtree.resolve((PathData)decoder, Tree.context);
                    if(decoder instanceof NidData)
                    {
                        int decIdx = mpbDecoderNids.indexOf(decoder);
                        if(decIdx == -1) continue;
                        int chan = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_CLOCK_CHAN), Tree.context)).getInt();
                        float freq = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_CLOCK_FREQ), Tree.context)).getFloat();
                            
                        Device device = new Device(RFX_CLOCK, 
                            subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                            new Data[]{}, new float[]{freq}, false);    
                        Decoder mpb = (Decoder)mpbDecoders.elementAt(decIdx);
                        if(!mpb.setChan(chan, device, false))
                            errorStrings.addElement("Duplicated usage of channel "+ chan 
                                + " in decoder " + mpb.getPath());
                        rfxDevices.addElement(device);
                    }
                }catch(Exception exc) {}
            }
            else if(model.equals("\"RFXPulse\""))
            {
                try {
                    Data decoder = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt()
                        + N_CLOCK_DECODER), Tree.context);
                    if(decoder instanceof PathData)
                        decoder = subtree.resolve((PathData)decoder, Tree.context);
                    if(decoder instanceof NidData)
                    {
                        int decIdx = mpbDecoderNids.indexOf(decoder);
                        if(decIdx == -1) continue;
                        int chan = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_PULSE_CHAN), Tree.context)).getInt();
                        String triggerMode = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_PULSE_TRIG_MODE), Tree.context)).getString();
                        Data delay = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_PULSE_DELAY), Tree.context);
                        Data duration = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_PULSE_DURATION), Tree.context);
                        Data trigTime;
                        if(triggerMode.equals("EVENT"))
                        {
                            String event = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                                N_PULSE_EVENT), Tree.context)).getString();
                            trigTime = (Data)eventTimes.get(event);
                            if(trigTime == null)
                            {
                                errorStrings.addElement("Cannot resolve time for event " + event + 
                                    " in RFXPulse device ");
                                continue;
                            }
                            
                            subtree.putData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                                N_PULSE_EXT_TRIGGER), trigTime, Tree.context);
                        }
                        else
                            trigTime = subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_PULSE_EXT_TRIGGER), Tree.context);
                        String outputMode = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_PULSE_OUTPUT_MODE), Tree.context)).getString();

                        Data trig1 = subtree.dataFromExpr("" + subtree.dataToString(trigTime) + " + " + subtree.dataToString(delay));
                        Device device;
                        if(outputMode.equals("DOUBLE TOGGLE: INITIAL HIGH"))
                        {
                            Data trig2 = subtree.dataFromExpr("" + subtree.dataToString(trigTime) + " + " + subtree.dataToString(delay) +"+"+subtree.dataToString(duration) );
                            device = new Device(RFX_PULSE, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                                new Data[]{trig1, trig2}, new float[]{}, true);
                        }
                        else if(outputMode.equals("DOUBLE TOGGLE: INITIAL LOW"))
                        {
                            Data trig2 = subtree.dataFromExpr("" + subtree.dataToString(trigTime) + " + " + subtree.dataToString(delay) +"+"+subtree.dataToString(duration) );
                            device = new Device(RFX_PULSE, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                                new Data[]{trig1, trig2}, new float[]{}, false);
                        }
                        else if(outputMode.equals("HIGH PULSE"))
                            device = new Device(RFX_PULSE, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                                new Data[]{trig1, trig1}, new float[]{}, false);
                        else if(outputMode.equals("LOW PULSE"))
                                device = new Device(RFX_PULSE, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                            new Data[]{trig1, trig1}, new float[]{}, true);
                        else if(outputMode.equals("SINGLE TOGGLE: INITIAL HIGH"))
                            device = new Device(RFX_PULSE, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                                new Data[]{trig1}, new float[]{}, true);
                        else if(outputMode.equals("SINGLE TOGGLE: INITIAL LOW"))
                            device = new Device(RFX_PULSE, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                                new Data[]{trig1}, new float[]{}, false);
                        else continue;
                        Decoder mpb = (Decoder)mpbDecoders.elementAt(decIdx);
                        if(!mpb.setChan(chan, device, false))
                            errorStrings.addElement("Duplicated usage of channel "+ chan 
                                + " in decoder " + mpb.getPath());
                        rfxDevices.addElement(device);
                    }
                }catch(Exception exc) {}
            }
            else if(model.equals("\"RFXDClock\""))
            {
                try {
                    Data decoder = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt()
                        + N_DCLOCK_DECODER), Tree.context);
                    if(decoder instanceof PathData)
                        decoder = subtree.resolve((PathData)decoder, Tree.context);
                    if(decoder instanceof NidData)
                    {
                        int decIdx = mpbDecoderNids.indexOf(decoder);
                        if(decIdx == -1) continue;
                        int gate_chan = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_DCLOCK_GATE_CHAN), Tree.context)).getInt();
                        int clock_chan = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_DCLOCK_CLOCK_CHAN), Tree.context)).getInt();
                        String triggerMode = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_DCLOCK_TRIG_MODE), Tree.context)).getString();
                        Data trigTime;
                        if(triggerMode.equals("EVENT"))
                        {
                            String event = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                                N_DCLOCK_EVENT), Tree.context)).getString();
                            trigTime = (Data)eventTimes.get(event);
                            if(trigTime == null)
                            {
                                errorStrings.addElement("Cannot resolve time for event " + event + 
                                    " in RFXDClock device ");
                                continue;
                            }
                            
                             subtree.putData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                                N_DCLOCK_EXT_TRIG), trigTime, Tree.context);
                        }
                        else
                            trigTime = subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_DCLOCK_EXT_TRIG), Tree.context);
                        String outputMode = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_DCLOCK_OUTPUT_MODE), Tree.context)).getString();
                            
                        Data freq1 = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_DCLOCK_FREQUENCY1), Tree.context);
                        Data freq2 = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_DCLOCK_FREQUENCY2), Tree.context);
                        Data delay = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_DCLOCK_DELAY), Tree.context);
                        Data duration = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_DCLOCK_DURATION), Tree.context);
                        
                        Device device;
                        Data trig1 = subtree.dataFromExpr("" + subtree.dataToString(trigTime) + " + " + subtree.dataToString(delay));
                        if(outputMode.equals("DOUBLE SWITCH: TOGGLE") ||
                            outputMode.equals("DOUBLE SWITCH: HIGH PULSES") ||
                            outputMode.equals("DOUBLE SWITCH: LOW PULSES"))
                        {
                            Data trig2 = subtree.dataFromExpr("" + subtree.dataToString(trigTime) + " + " + subtree.dataToString(delay) + " + " + subtree.dataToString(duration));
                            device = new Device(RFX_DCLOCK, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                                new Data[]{trig1, trig2}, new float[]{freq1.getFloat(), freq2.getFloat()}, true);
                        }
                        else 
                            device = new Device(RFX_DCLOCK, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                                new Data[]{trig1}, new float[]{freq1.getFloat(), freq2.getFloat()}, true);
                            
                        Decoder mpb = (Decoder)mpbDecoders.elementAt(decIdx);
                        if(!mpb.setChan(clock_chan, device, false))
                            errorStrings.addElement("Duplicated usage of channel "+ clock_chan 
                                + " in decoder " + mpb.getPath());
                        if(!mpb.setChan(gate_chan, device, true))
                            errorStrings.addElement("Duplicated usage of channel "+ gate_chan 
                                + " in decoder " + mpb.getPath());
                        rfxDevices.addElement(device);
                    }
                }catch(Exception exc) {}
            }
            else if(model.equals("\"RFXGClock\""))
            {
                try {
                    Data decoder = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt()
                        + N_GCLOCK_DECODER), Tree.context);
                    if(decoder instanceof PathData)
                        decoder = subtree.resolve((PathData)decoder, Tree.context);
                    if(decoder instanceof NidData)
                    {
                        int decIdx = mpbDecoderNids.indexOf(decoder);
                        if(decIdx == -1) continue;
                        int gate_chan = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_GCLOCK_GATE_CHAN), Tree.context)).getInt();
                        int clock_chan = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_GCLOCK_CLOCK_CHAN), Tree.context)).getInt();
                        String triggerMode = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_GCLOCK_TRIG_MODE), Tree.context)).getString();
                        Data trigTime;
                        if(triggerMode.equals("EVENT"))
                        {
                            String event = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                                N_GCLOCK_EVENT), Tree.context)).getString();
                            trigTime = (Data)eventTimes.get(event);
                            if(trigTime == null)
                            {
                                errorStrings.addElement("Cannot resolve time for event " + event + 
                                    " in RFXGClock device ");
                                continue;
                            }
                            
                             subtree.putData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                                N_GCLOCK_EXT_TRIG), trigTime, Tree.context);
                        }
                        else
                            trigTime = subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_GCLOCK_EXT_TRIG), Tree.context);
                        String outputMode = (subtree.evaluateData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_GCLOCK_OUTPUT_MODE), Tree.context)).getString();
                            
                        Data freq = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_GCLOCK_FREQUENCY), Tree.context);
                        Data delay = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_GCLOCK_DELAY), Tree.context);
                        Data duration = subtree.getData(new NidData(((NidData)rfxDeviceNids.elementAt(idx)).getInt() +
                            N_GCLOCK_DURATION), Tree.context);
                        
                        Device device;
                        Data trig1 = subtree.dataFromExpr("" + subtree.dataToString(trigTime) + " + " + subtree.dataToString(delay));
                        if(outputMode.equals("DOUBLE SWITCH: TOGGLE") ||
                            outputMode.equals("DOUBLE SWITCH: HIGH PULSES") ||
                            outputMode.equals("DOUBLE SWITCH: LOW PULSES"))
                        {
                            Data trig2 = subtree.dataFromExpr("" + subtree.dataToString(trigTime) + " + " + 
                                subtree.dataToString(delay) + " + " + subtree.dataToString(duration));
                            device = new Device(RFX_GCLOCK, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                                new Data[]{trig1, trig2}, new float[]{freq.getFloat()}, true);
                        }
                        else 
                            device = new Device(RFX_GCLOCK, 
                                subtree.getInfo((NidData)rfxDeviceNids.elementAt(idx), Tree.context).getFullPath(),
                                new Data[]{trig1}, new float[]{freq.getFloat()}, true);
                            
                        Decoder mpb = (Decoder)mpbDecoders.elementAt(decIdx);
                        if(!mpb.setChan(clock_chan, device, false))
                            errorStrings.addElement("Duplicated usage of channel "+ clock_chan 
                                + " in decoder " + mpb.getPath());
                        if(!mpb.setChan(gate_chan, device, true))
                            errorStrings.addElement("Duplicated usage of channel "+ gate_chan 
                                + " in decoder " + mpb.getPath());
                        rfxDevices.addElement(device);
                    }
                }catch(Exception exc) {}
            }
        }
        reportEvents();
        if(firstScan)
            firstScan = false;
        else
        {
            String errors = getErrors();
            if(errors != null)
                JOptionPane.showMessageDialog(RFXTimingSetup.this, errors, "Errors in timing configuration",
                    JOptionPane.WARNING_MESSAGE);
            
        }
    }
 
    
    
    void showSignals(Graphics g, Dimension d)
    {
        Decoder decoder;
        float minStart = (float)1E6;
        float maxEnd = (float)-1E6;
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.black);
        for(int idx = 0; idx < mpbDecoders.size(); idx++)
        {
            decoder = (Decoder)mpbDecoders.elementAt(idx);
            for(int chan = 0; chan < 6; chan++)
            {
                Device device = null;
                if(!decoder.isGate[chan])
                    device = decoder.devices[chan];
                if(device == null) continue;
                for(int i = 0; i < device.times.length; i++)
                {
                    float time = 0;
                    try {
                        time = subtree.evaluateData(device.times[i], Tree.context).getFloat();
                    }catch(Exception exc){}
                    if(time < minStart) minStart = time;
                    if(time > maxEnd) maxEnd = time;
                }
            }
        }
        int chanIdx = 1;
        float start = minStart - (maxEnd - minStart)/4;
        float convFact = d.width / (maxEnd - minStart + (maxEnd - minStart)/2);
        for(int idx = 0; idx < mpbDecoders.size(); idx++)
        {
            decoder = (Decoder)mpbDecoders.elementAt(idx);
            for(int chan = 0; chan < 5; chan++)
            {
                Device device = null;
                if(!decoder.isGate[chan])
                {
                    device = decoder.devices[chan];
                    if(device == null) continue;
                    try {
                        device.draw(g, chanIdx, start, convFact, d.width, decoder.path, chan);
                        chanIdx++;
                    }catch(Exception exc){System.err.println("Error drawing output for device " + device.path + " (channel " + chan + 
                        " of decoder " + decoder.path + "): " + exc); }
                }
            }
        }           
    }
        
     
    public String getErrors() 
    {
        if(errorStrings.size() == 0) return null;
        String errors = "";
        for(int i = 0; i < errorStrings.size(); i++)
            errors += "\n" + (String)errorStrings.elementAt(i);
        return errors;
    }    

    public void configure(RemoteTree subtree, int baseNid)
    {
        this.subtree = subtree;
        scan();
        //getContentPane().add(buildEventTable(), "Center");
        JComponent eventT = buildEventTable();
        splitP.setBottomComponent(eventT);
        pack();
        javax.swing.Timer timer = new javax.swing.Timer(500, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String errors = getErrors();
                
                if(errors != null)
                    JOptionPane.showMessageDialog(RFXTimingSetup.this, errors, "Errors in timing configuration",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    void reportEvents()
    {
        int nEvents = eventTimes.size();
        evNames = new String[nEvents];
        evTimes = new Data[nEvents];
        evRecTimes = new String[nEvents];
        Enumeration names = eventTimes.keys();
        int i = 0;
        while(names.hasMoreElements())
        {
            evNames[i] = (String)names.nextElement();
            evTimes[i] = (Data)eventTimes.get(evNames[i]);
            evRecTimes[i] = "";
            i++;
        }
    }
    
    void reportRecEvents()
    {
        if(recorderNid == null) return;
        try {
            int shot = subtree.getShot();
            RemoteTree exp;
            boolean currentOpen = false;
            if(shot == -1 && !Tree.isRemote())
            {
                try {
                    exp = new Database(subtree.getName(), 0);
                    exp.open();
                    currentOpen = true;
                }catch(Exception ecx)
                {
                    exp = new Database(subtree.getName(), -1);
                    exp.open();
                }
            }
            else
                exp = subtree;
            Data recEventsData = (exp.evaluateData(new NidData((recorderNid).getInt() +
                            N_RECORDER_EVENTS), Tree.context));
            System.out.println(recEventsData);
            fullRecEvents = ((StringArray)recEventsData).getStringArray();   
            Data recTimesData = (exp.evaluateData(new NidData((recorderNid).getInt() +
                            N_RECORDER_TIMES), Tree.context));
            System.out.println(recTimesData);                
            fullRecTimes = recTimesData.getFloatArray();
            if(currentOpen)
                exp.close(Tree.context);
        }catch(Exception exc)
        {
            System.out.println(exc);
        }
        Vector recEventsV = new Vector();
        for(int i = 0; i < fullRecEvents.length; i++)
        {
            recEventsV.addElement(fullRecEvents[i]);
            i++;
        }
        for(int i = 0; i < evNames.length; i++)
        {
            int idx = recEventsV.indexOf(evNames[i]);
            if( idx != -1)
                evRecTimes[i] = ""+fullRecTimes[idx];
            else
                evRecTimes[i] = "";
        }
    }
     
    JDialog recordedDialog = null;
            
    void showRecordedEvents()
    {
       if(recordedDialog == null)
       {
            recordedDialog = new JDialog(this, "Recorded timing events");
            recordedDialog.getContentPane().setLayout(new BorderLayout());
            JTable recEventTable = new JTable(new  AbstractTableModel() {
                public String getColumnName(int col)
                {
                    switch(col)
                    {
                        case 0: return "Event";
                        case 1: return "Time";
                    }
                    return "";
                }
                public int getRowCount()  {return fullRecEvents.length; }
                public int getColumnCount() {return 2;  }
                public boolean isCellEditable(int row, int col) {return false; }
                public Object getValueAt(int row, int col)
                {
                    switch(col)
                    {
                        case 0: return fullRecEvents[row];
                        case 1: return ""+fullRecTimes[row];
                    }
                    return "";
                }
            });
            JScrollPane scroll = new JScrollPane(recEventTable);
            recEventTable.setPreferredScrollableViewportSize(new Dimension(250,100));
            recordedDialog.getContentPane().add(scroll, "Center");
            JButton closeB = new JButton("Close");
            closeB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {recordedDialog.hide();}
            });
            JPanel jp = new JPanel();
            jp.add(closeB);
            recordedDialog.getContentPane().add(jp, "South");
            recordedDialog.pack();
       }
       recordedDialog.show();
       eventTable.repaint();
    }
    
            
    JComponent buildEventTable()
    {
        eventTable = new JTable(createEventTableModel());
        JScrollPane scroll = new JScrollPane(eventTable);
        eventTable.setPreferredScrollableViewportSize(new Dimension(450,100));
        return (scroll);
    }
    
   
    private TableModel createEventTableModel()
    {
        return new AbstractTableModel() {
            public String getColumnName(int col)
            {
                switch(col)
                {
                    case 0: return "Event";
                    case 1: return "Time";
                    case 2: return "Recorded Time";
                }
                return "";
            }
            
            public int getRowCount() 
            { 
              if(evNames == null) return 0;
              return evNames.length;
            }
            public int getColumnCount() {return 3;  }
            public boolean isCellEditable(int row, int col) 
            {
                return false; 
            }
            public Object getValueAt(int row, int col)
            {
                switch(col)
                {
                    case 0: return evNames[row];
                    case 1: 
                        try {
                            return subtree.dataToString(subtree.evaluateData(evTimes[row], Tree.context));
                        }catch(Exception exc){return "";}
                    //return evTimes[row].toString();
                    case 2: return evRecTimes[row];
                }
                return "";
            }
        };
    }
    
    
    public RFXTimingSetup()
    {
        super();
        splitP = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(splitP, "Center");
        setTitle("Timing supervisor");
        JComponent waves = new JComponent()
        {
            public void paintComponent(Graphics g)
            {
                //super.paintComponent(g);
                showSignals(g, getSize());
                setPreferredSize(new Dimension(500, mpbDecoders.size() * 5 * SIGNAL_HEIGHT + SIGNAL_HEIGHT));
            }
        };
        JScrollPane scroll = new JScrollPane(waves);
        scroll.setPreferredSize(new Dimension(500, 300));
        splitP.setTopComponent(scroll);
        //getContentPane().add(scroll, "North");
        JPanel jp = new JPanel();
        JButton cancelB = new JButton("Cancel");
        cancelB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {dispose();}
        });
        jp.add(cancelB);
        JButton recordedB = new JButton("Last recorded Events");
        recordedB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reportRecEvents();
                showRecordedEvents();
            }});
        jp.add(recordedB);
        JButton updateB = new JButton("Update");
        updateB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scan();
                repaint();
            }
        });
        jp.add(updateB);
        getContentPane().add(jp, "South");
    
		//{{INIT_CONTROLS
		//}}
	}
        
    
    
	//{{DECLARE_CONTROLS
	//}}
}                         
        

    
    
    