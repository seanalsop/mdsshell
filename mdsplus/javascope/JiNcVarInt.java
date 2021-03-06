/* $Id: JiNcVarInt.java,v 1.22 2003/08/08 12:35:38 manduchi Exp $ */
import java.io.IOException;
import java.util.*;

class JiNcVarInt extends JiNcVarImp
{
	public JiNcVarInt(RandomAccessData in, JiNcVar parent, long offset)
	  {
	  super(in, parent, offset);
	  }  
	public Object read(JiDim[] dims) throws IOException
	  {
	  return readInt(dims);
	  }  
	public int[] readInt(JiDim[] dims) throws IOException
	  {
	  int[] rval = null;
	  mParent.validateDims(dims);
	  JiSlabIterator itr = new JiSlabIterator((JiNcSource)mParent.getSource(), mParent, dims);
	  int size = itr.size();
	  rval = new int[size];
	  JiSlab slab;
	  int counter = 0;
	  while((slab = itr.next()) != null){
	      mRFile.seek(mOffset + slab.mOffset);
	      for (int i=0; i < slab.mSize; ++i){
		  rval[counter++] = mRFile.readInt();
	      }
	  }
	  return rval;
	  }  
	public int sizeof()
	  {
	  return 4;
	  }  
}
