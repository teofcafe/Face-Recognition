package org.opencv.javacv.facerecognition.cipher;

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
		
		try {
			
			_cipher.init(Cipher.ENCRYPT_MODE, _chave);
			
		} catch (InvalidKeyException e) {
		}
	    
		try {
			
			return _cipher.doFinal(conteudo);
			
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}
		
		return null;
	}

	@Override
	public byte[] decipher(byte[] conteudoCifrado) {
		 
		try {
			
			_cipher.init(Cipher.DECRYPT_MODE, _chave);
			
		} catch (InvalidKeyException e) {
		}
	   
		try {
			
			return _cipher.doFinal(conteudoCifrado);
			
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}
		
	    return null;
	}

}
