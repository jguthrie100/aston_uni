package file;

import java.io.Serializable;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * The FileIO class is used to allow objects to be saved to a file and
 * also get objects by loading existing files.
 * 
 * Code taken and modified from the following website URL...
 * http://www.exampledepot.com/egs/javax.crypto/EncryptObject.html?l=rel
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class ObjectED implements KeyStore.ProtectionParameter {

	private static byte[] keyBytes = { 3, 2, 5, 3, 8, 1, 1, 2, 7, 6, 3, 2, 3, 4, 1, 6 };
	
	/**
	 * Encrypts an object and saves it to a specified file using the key.
	 * 
	 * @param o		the object to be encrypted.
	 * @param filename		the filename where the object would be saved.
	 * @throws ClassNotFoundException	thrown if the class is not found.
	 * @throws KeyStoreException	thrown if the key hasent been encrypted.
	 * @throws CertificateException	thrown if the key is null or not a public key.
	 */
	public static void encryptObject(Object o, String filename) throws ClassNotFoundException, KeyStoreException, CertificateException {
		try {
			// Generate a temporary key. In practice, you would save this key.
			// See also e464 Encrypting with DES Using a Pass Phrase.
			SecretKey key = new SecretKeySpec(keyBytes, "AES");
			
			// Prepare the encrypter
			Cipher ecipher = Cipher.getInstance("AES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);

			// Seal (encrypt) the object
			SealedObject sealedObject = new SealedObject((Serializable) o, ecipher);

			// Save the encrypted object to a file
			FileIO.saveObject(sealedObject, filename);

		} catch (java.io.IOException e) {
		} catch (javax.crypto.IllegalBlockSizeException e) {
		} catch (javax.crypto.NoSuchPaddingException e) {
		} catch (java.security.NoSuchAlgorithmException e) {
		} catch (java.security.InvalidKeyException e) {
		}
	}

	/**
	 * Gets an object by Loading it from the file and then useing the key 
	 * to decrypt it.
	 * 
	 * @param filename	the filename of the file.
	 * @return	the decrypted object.
	 * @throws BadPaddingException	thrown when a particular padding mechanism 
	 * 								is expected for the input data.
	 * @throws ClassNotFoundException	thrown if the class has not been found.
	 */
	public static Object decryptObject (String filename) throws BadPaddingException, 
	ClassNotFoundException {
		Object decryptedObject = null;
		try {
			SecretKey key = new SecretKeySpec(keyBytes, "AES");
			
			// Prepare the decrypter
			Cipher dcipher = Cipher.getInstance("AES");
			dcipher.init(Cipher.DECRYPT_MODE, key);

			// Load the sealed object
			SealedObject ob = (SealedObject) FileIO.loadObject(filename);
			
			// Unseal (decrypt) the class
			decryptedObject = (Object)ob.getObject(dcipher);

		} catch (java.io.IOException e) {
		} catch (javax.crypto.IllegalBlockSizeException e) {
		} catch (javax.crypto.NoSuchPaddingException e) {
		} catch (java.security.NoSuchAlgorithmException e) {
		} catch (java.security.InvalidKeyException e) {
		}
		return decryptedObject;
	}

	/**
	 * Checks if a file exists by checking its filename.
	 * 
	 * @param filename	the filename of a file.
	 * @return	true if the file exists or false if otherwise.
	 */
	public static boolean checkFile(String filename) {
		return FileIO.checkFile(filename);
	}
	
}
