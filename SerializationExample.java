import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

/**
 * Example of how to serialize multiple complex objects to a binary file and retrieve them. 
 * 
 * @author ajtrantham
 *
 */
public class SerializationExample {
	
	// static variables
	static RandomAccessFile raf; // the file we are writing to and reading from MUST BE STATIC
	static int offset = 0; // the position we are writing/reading in the raf
	static int maxNodeSize = 400; // the largest expected size  in bytes of the node (this will vary depending on what you are serializing)
	
	/**
	 * Serializes the object by converting it into a array of bytes
	 * @param node
	 * @return
	 */
	private static byte[] serialize(DummyNode node) {
		
		byte[] stream = null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(node);
			stream = baos.toByteArray();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
		return stream;
	}
	
	/**
	 * Deserializes an object by converting an array of bytes into an object
	 * @param b
	 * @return DummyNode
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static DummyNode deserialize(byte[] b) throws ClassNotFoundException, IOException {

		ByteArrayInputStream in = new ByteArrayInputStream(b);
		ObjectInputStream is = new ObjectInputStream(in);
		
		return (DummyNode)is.readObject();
	}
	
	/**
	 * Converts an object and all its properties to a array
	 * of bytes and then stores those bytes in the binary file. (RAF)
	 */
	public static void diskWrite(DummyNode node, int pos) {
		
		byte[] b = serialize(node);
		
		try {
			raf.seek(pos);
			raf.write(b);
			
			offset += maxNodeSize;
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Reads an array of bytes from a binary file, and converts 
	 * those bytes back to an deserialized object.
	 * @param pos
	 * @return
	 */
	public static DummyNode diskRead(int pos) {
		
		byte[] b = new byte[maxNodeSize];
		DummyNode copyNode = null;
		
		try {
			raf.seek(pos);
			raf.read(b);
			copyNode = deserialize(b);
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return copyNode;
		
	}
	
	// test the serialize and deserialize read and write methods
	public static void main(String[] args) {
		
		try {
			raf = new RandomAccessFile("SerExample.ser", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Make a new DummyNode and write it to the raf
		DummyNode sampleNode = new DummyNode(offset);
		System.out.println(sampleNode.toString() + "\n");
		diskWrite(sampleNode, offset);
		
		// read that node from the raf and store in a new node
		DummyNode newNode = diskRead(sampleNode.getOffset());
		System.out.println(newNode.toString()+ "\n");
		
		//Write another DummyNode to the file
		DummyNode nextSampleNode = new DummyNode(offset);
		System.out.println(nextSampleNode.toString()+ "\n"); 
		diskWrite(nextSampleNode, offset);
		
		//Read the nextSampleNode back from disk
		DummyNode nextCopyNode = diskRead(nextSampleNode.getOffset());
		System.out.println(nextCopyNode.toString()+ "\n");
	}
}

