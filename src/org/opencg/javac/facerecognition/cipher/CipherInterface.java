package org.opencg.javac.facerecognition.cipher;

public interface CipherInterface {
	
	public byte[] cipher(byte[] conteudo);
	public byte[] decipher(byte[] conteudo);
}
