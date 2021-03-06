import javax.swing.*;
import java.awt.*;
public abstract class DeviceComponent extends JPanel
{
    RemoteTree subtree;

    public int mode = DATA;
    public static final int DATA = 0, STATE = 1, DISPATCH = 2;
    public int baseNid = 0, offsetNid = 0;
    protected Data curr_data, init_data;
    protected boolean curr_on, init_on;
    protected NidData nidData;
    protected NidData baseNidData;
    protected String identifier;
    protected String updateIdentifier;
    protected boolean editable = true;
    private boolean is_initialized = false;
    private boolean enabled = true;



    void setSubtree(RemoteTree subtree) {this.subtree = subtree; }
    RemoteTree getSubtree(){return subtree; }
    public void setBaseNid(int nid) {baseNid = nid; }
    public int getBaseNid() {return baseNid; }
    public void setOffsetNid(int nid) {offsetNid = nid; }
    public int getOffsetNid() {return offsetNid; }
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }
    public String getIdentifier()
    {
        return identifier;
    }
    public void setUpdateIdentifier(String updateIdentifier)
    {
        this.updateIdentifier = updateIdentifier;
    }
    public String getUpdateIdentifier() {return updateIdentifier; }


    public void configure(int baseNid)
    {
        this.baseNid = baseNid;
        nidData = new NidData(baseNid+offsetNid);
        baseNidData = new NidData(baseNid);
        if(mode == DATA)
        {
            try {
                init_data = curr_data = subtree.getData(nidData, Tree.context);
            }catch(Exception e) {init_data = curr_data = null;}



        }
        else init_data = null;
        //if(mode != DISPATCH)
        {
            try {
                init_on = curr_on = subtree.isOn(nidData, Tree.context);
            } catch(Exception e)
            {
                System.out.println("Error configuring device: " + e);
            }
        }
        if(!is_initialized)
        {
            initializeData(curr_data, curr_on);
            is_initialized = true;
        }
        else
            displayData(curr_data, curr_on);
    }

    public void reset()
    {
        curr_data = init_data;
        curr_on = init_on;
        displayData(curr_data, curr_on);
    }

    public void apply() throws Exception
    {
        if(!enabled) return;
        if(mode == DATA)
        {
            curr_data = getData();
/*            if(curr_data instanceof PathData)
            {
                try {
                    curr_data = subtree.resolve((PathData)curr_data, Tree.context);
                }catch(Exception exc){}
            }
  */
            if(editable)
            {
                try {
                subtree.putData(nidData, curr_data, Tree.context);
                } catch(Exception e)
                {
                    System.out.println("Error writing device data: " + e);
                    System.out.println(curr_data);
                    throw e;
                }
            }
        }
        if(mode != DISPATCH && supportsState())
        {
            curr_on = getState();
            try {
                subtree.setOn(nidData, curr_on, Tree.context);
            }catch(Exception e)
            {
                System.out.println("Error writing device state: " + e);
            }
        }
    }

    protected void redisplay()
    {
        Container curr_container;
        Component curr_component = this;
        do {
            curr_container = curr_component.getParent();
            curr_component = curr_container;
        }while ((curr_container != null) && !(curr_container instanceof Window));
       /* if(curr_container != null)
        {
            ((Window)curr_container).pack();
            ((Window)curr_container).show();
        }*/
    }

//Event handling in DW setup
    DeviceSetup master = null;
    public String getUpdateId(DeviceSetup master)
    {
        this.master = master;
        return updateIdentifier;
    }
    public void fireUpdate(String updateId, Data newExpr){}
    //To be subclassed
    protected abstract void initializeData(Data data, boolean is_on);
    protected abstract void displayData(Data data, boolean is_on);
    protected abstract Data getData();
    protected abstract boolean getState();
    public void postConfigure(){}
    void postApply(){}
    protected boolean supportsState(){return false;}
    public void setEnable()
    {
        enabled = true;
    }
    public void setDisable()
    {
        enabled = false;
    }

    public void reportDataChanged(Object data)
    {
      if(master == null) return;
      master.propagateData(offsetNid, data);
    }

    public void reportStateChanged(boolean state)
    {
      if(master == null) return;
      master.propagateState(offsetNid, state);
    }

    protected void dataChanged(int offsetNid, Object data){}
    protected void stateChanged(int offsetNid, boolean state){}
}








