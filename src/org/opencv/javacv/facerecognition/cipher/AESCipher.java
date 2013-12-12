package org.opencv.javacv.facerecognition.cipher;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher implements CipherInterface{
	
	private final IvParameterSpec IV = new IvParameterSpec(new byte[8]);
	private SecretKeySpec _chave;
	private Cipher _cipher;
	private Cipher _decipher;

	public AESCipher(byte[] chave) {
		
		_chave = new SecretKeySpec(chave, "AES");
		
		try {
			
			_cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
			_cipher.init(Cipher.DECRYPT_MODE, _chave, IV);
			_decipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
			_decipher.init(Cipher.DECRYPT_MODE, _chave, IV);
			
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchProviderException e) {
		} catch (NoSuchPaddingException e) {
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public byte[] cipher(byte[] conteudo) {
		
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
	    	
			return _cipher.doFinal(conteudoCifrado);
			
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}
	    
		return null;
	}


}
