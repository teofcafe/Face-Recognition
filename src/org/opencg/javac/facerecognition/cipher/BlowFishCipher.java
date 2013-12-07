package org.opencg.javac.facerecognition.cipher;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class BlowFishCipher implements CipherInterface{
	
	private SecretKeySpec _chave;
	private Cipher _cipher;

	public BlowFishCipher(byte[] chave) {
		_chave = new SecretKeySpec(chave, "Blowfish");
		try {
			_cipher = Cipher.getInstance("Blowfish");
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		}
	}

	@Override
	public byte[] cipher(byte[] conteudo) {
		
		byte[] encrypted = null;
		
		try {
			_cipher.init(Cipher.ENCRYPT_MODE, _chave);
		} catch (InvalidKeyException e) {
		}
	    
		try {
			encrypted = _cipher.doFinal(conteudo);
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}
		
		return bytesToHex(encrypted).getBytes();
	}

	private String bytesToHex(byte[] input) {
		if (input == null) {
		      return null;
		    } else {
		      int len = input.length;
		      String resul = null;
		      for (int i = 0; i < len; i++) {
		        if ((input[i] & 0xFF) < 16)
		        	resul = resul + "0" + java.lang.Integer.toHexString(input[i] & 0xFF);
		        else
		        	resul = resul + java.lang.Integer.toHexString(input[i] & 0xFF);
		      }
		      return resul;
		    }
	}

	@Override
	public byte[] decipher(byte[] conteudo) {
		 byte[] decrypted = null;
		 
		try {
			
			_cipher.init(Cipher.DECRYPT_MODE, _chave);
			
		} catch (InvalidKeyException e) {
		}
	   
		try {
			
			decrypted = _cipher.doFinal(hexToBytes(conteudo.toString()));
			
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}
		
	    return bytesToHex(decrypted).getBytes();
	}

	private byte[] hexToBytes(String input) {
		
	    if (input == null) {
	        return null;
	      } else if (input.length() < 2) {
	        return null;
	      } else {
	        int len = input.length() / 2;
	        byte[] buffer = new byte[len];
	        
	        for (int i = 0; i < len; i++) {
	          buffer[i] = (byte) Integer.parseInt(input.substring(i * 2, i * 2 + 2), 16);
	        }
	        
	        return buffer;
	      }
	}


}
