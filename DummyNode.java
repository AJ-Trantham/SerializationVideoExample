import java.io.Serializable;
import java.util.ArrayList;

public class DummyNode implements Serializable{
         
        private boolean bool;
        private int offset;
        private ArrayList<Integer> numbers;

        /**
         * Constructor for a Dummy Node
         * @param offset
         */
       public DummyNode(int offset) {
    	   this.bool = true;
    	   this.offset = offset;
           numbers = new ArrayList<Integer>();
           numbers.add(5);
           numbers.add(10);
           numbers.add(55);            
       }

       /**
        * Removes and object from the ArrayList
        */
       public void removeThing() {
    	   numbers.remove(0);
       }
       
       /**
        * returns the offset location at which the node is located in the file
        * @return
        */
       public int getOffset() {
    	   return offset;
       }
       /**
        * Creates readable string to verify data is not corrupted
        */
       public String toString() {
           String str = "";
            str += "offset :" + offset + "\n" + "Boolean: " + bool;

            return str;
       }
       		                
	}

