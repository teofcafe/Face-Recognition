package org.opencg.javac.facerecognition.cipher;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TripleDESCipher implements CipherInterface{

	private byte[] _chave;
	private MessageDigest _md;
	private byte[] _digestOfPassword;

	public TripleDESCipher(byte[] chave) {

		_chave = chave;

		try {

			_md = MessageDigest.getInstance("md5");

		} catch (NoSuchAlgorithmException e) {
		}

		_digestOfPassword = _md.digest(_chave);
	}

	@Override
	public byte[] cipher(byte[] bytes) {

		Cipher cipher = null;
		byte[] cipherBytes = null;
		final byte[] keyBytes = Arrays.copyOf(_digestOfPassword, 24);

		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);

		try {

			cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");

		} catch (NoSuchAlgorithmException e) {	
		} catch (NoSuchPaddingException e) {
		}

		try {

			cipher.init(Cipher.ENCRYPT_MODE, key, iv);

		} catch (InvalidKeyException e) {
		} catch (InvalidAlgorithmParameterException e) {
		}

		try {

			cipherBytes = cipher.doFinal(bytes);

		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}

		return cipherBytes;
	}

	@Override
	public byte[] decipher(byte[] cypherBytes) {

		Cipher decipher = null;
		byte[] bytes = null;
		final byte[] keyBytes = Arrays.copyOf(_digestOfPassword, 24);

		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);

		try {

			decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");

		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		}

		try {

			decipher.init(Cipher.DECRYPT_MODE, key, iv);

		} catch (InvalidKeyException e) {
		} catch (InvalidAlgorithmParameterException e) {
		}

		try {

			bytes = decipher.doFinal(cypherBytes);

		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}

		return bytes;
	}

}
