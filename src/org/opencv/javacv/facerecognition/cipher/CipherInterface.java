package org.opencv.javacv.facerecognition.cipher;

public interface CipherInterface {
	
	public byte[] cipher(byte[] conteudo);
	public byte[] decipher(byte[] conteudo);
}
