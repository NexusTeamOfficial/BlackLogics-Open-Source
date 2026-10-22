
package apksigner;


import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class Signature {

    private byte[] beforeAlgorithmIdBytes =  { 0x30, 0x21 };



    private byte[] algorithmIdBytes = {0x30, 0x09, 0x06, 0x05, 0x2B, 0x0E, 0x03, 0x02, 0x1A, 0x05, 0x00 }; 

    private byte[] afterAlgorithmIdBytes = { 0x04, 0x14 };

    private Cipher cipher;

    private MessageDigest md;
    private static Signature signature=new Signature();

    public static Signature getInstance(){
        
        return signature;
    }

    private Signature(){
    }

    public void initSign( PrivateKey privateKey) throws InvalidKeyException,Exception 
    {
        md = MessageDigest.getInstance("SHA1");
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
    }

    public void update( byte[] data) {
        md.update( data);
    }

    public byte[] sign() throws BadPaddingException, IllegalBlockSizeException 
    {
        cipher.update( beforeAlgorithmIdBytes);
        cipher.update( algorithmIdBytes);
        cipher.update( afterAlgorithmIdBytes);
        cipher.update( md.digest());        
        return cipher.doFinal();
    }
}
