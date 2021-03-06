/* $Id: WaveContainerEvent.java,v 1.20 2003/08/08 12:35:41 manduchi Exp $ */
import java.awt.AWTEvent;
import java.awt.Event;

public class WaveContainerEvent extends AWTEvent 
{
    static final int START_UPDATE   = AWTEvent.RESERVED_ID_MAX + 4;
    static final int END_UPDATE     = AWTEvent.RESERVED_ID_MAX + 5;
    static final int KILL_UPDATE    = AWTEvent.RESERVED_ID_MAX + 6;
    static final int WAVEFORM_EVENT = AWTEvent.RESERVED_ID_MAX + 7;
    String info;
    AWTEvent we;

    public WaveContainerEvent (Object source, int event_id, String info) 
    {
        super(source, event_id);
        this.info = info;
    }

    public WaveContainerEvent (Object source, AWTEvent we) 
    {
        super(source, WAVEFORM_EVENT);
        this.we = we;
    }

}
