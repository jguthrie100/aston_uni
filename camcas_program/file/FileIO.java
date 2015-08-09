package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The FileIO class is used to allow objects to be saved to a file and
 * also get objects by loading existing files.
 * 
 * @author 		Tom
 * @version		1.0	
 */
public class FileIO {
	
	/**
	 * Saves an object to a specified filename.
	 * 
	 * @param o		the object to be saved to a file.
	 * @param filename		the filename where the object is to be saved to.
	 * @throws IOException	thrown if there is a problem reading the file.
	 */
	public static void saveObject(Object o, String filename) throws IOException
	{
		ObjectOutputStream os = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(filename)));
		os.writeObject(o);
		os.close();
	}
	
	/**
	 * Loads an object from a specified file.
	 * 
	 * @param filename	the filename where the object is to be gotten from.
	 * @return	the object loaded from the file.
	 * @throws IOException	thrown if there is a problem reading from the file.
	 * @throws ClassNotFoundException	thrown if there is no class found in the file.
	 */
	public static Object loadObject(String filename) throws IOException, ClassNotFoundException
	{
		ObjectInputStream is = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(filename)));
		Object o = null;
		try{
			o = is.readObject();
		} catch(Exception e) {
		};
		is.close();
		return o;
	}
	
	/**
	 * Checks a filename.
	 * 
	 * @param filename	the filename of a file.
	 * @return	true if it exists or false if otherwise.
	 */
	public static boolean checkFile(String filename)
	{
		File f = new File(filename);
		
		return (f.exists()) && (f.canRead()) && (f.isFile());
	}
}



