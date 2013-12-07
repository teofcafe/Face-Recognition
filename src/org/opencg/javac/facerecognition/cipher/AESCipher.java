package org.opencg.javac.facerecognition.cipher;

import java.io.UnsupportedEncodingException;
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
	
	private byte[] _chave;
	private SecretKeySpec _key;
	private static String IV = "AAAAAAAAAAAAAAAA";
	private Cipher _cipher;

	public AESCipher(byte[] chave) {
		_chave = chave;
		_key = new SecretKeySpec(chave, "AES");
		try {
			_cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchProviderException e) {
		} catch (NoSuchPaddingException e) {
		}
	}

	@Override
	public byte[] cipher(byte[] conteudo) {
		
		try {
			_cipher.init(Cipher.ENCRYPT_MODE, _key,new IvParameterSpec(IV.getBytes("UTF-8")));
		} catch (InvalidKeyException e) {
		} catch (InvalidAlgorithmParameterException e) {
		} catch (UnsupportedEncodingException e) {
		}

	    try {
			return _cipher.doFinal(conteudo);
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}
		return null;
	}

	@Override
	public byte[] decipher(byte[] conteudo) {

		try {
			_cipher.init(Cipher.DECRYPT_MODE, _key,new IvParameterSpec(IV.getBytes("UTF-8")));
		} catch (InvalidKeyException e) {
		} catch (InvalidAlgorithmParameterException e) {
		} catch (UnsupportedEncodingException e) {
		}
	    try {
			return _cipher.doFinal(conteudo);
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}
		return null;
	}


}
