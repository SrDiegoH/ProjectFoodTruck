package util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

public class CriptoHelper {	

	public static String criptografar(String puroTexto) {
		try {
			return CifraSimetrica.criptografar(puroTexto, "CHAVE");
		} catch (Exception e) {
			return null;
		}
	}

	public static String descriptografar(String cifroTexto) {
		try {
			return CifraSimetrica.descriptografar(cifroTexto, "CHAVE");
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String puroTexto = "Este é o puro texto";	
		System.out.println("Texto: " + puroTexto);
		
		System.out.print("Criptografar: ");
		String cifroTexto = CriptoHelper.criptografar(puroTexto);
		System.out.println(cifroTexto.toString());
		
		System.out.print("Decriptografar: ");
		String novoPuroTexto = CriptoHelper.descriptografar(cifroTexto);
		System.out.println(novoPuroTexto);
	}
	
	public static class CifraBase64 {
		private static Base64 cripto;
		
		static {
			if (cripto == null)
				cripto = new Base64();
		}

		public static String criptografar(String puroTexto) {
			return cripto.encodeToString(puroTexto.getBytes());
		}

		public static String descriptografar(String cifroTexto) {
			return new String(cripto.decode(cifroTexto.getBytes()));
		}
		
		public static void main(String[] args) throws Exception {
			String puroTexto = "Este é o puro texto";	
			System.out.println("Texto: " + puroTexto);
			
			System.out.print("Criptografar: ");
			String cifroTexto = CifraBase64.criptografar(puroTexto);
			System.out.println(cifroTexto.toString());
			
			System.out.print("Decriptografar: ");
			String novoPuroTexto = CifraBase64.descriptografar(cifroTexto);
			System.out.println(novoPuroTexto);
		}
	}
	
	
	public static class CifraSimetrica {
		//https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
		private static final String ALGORITIMO = "RC4"; //"AES";
		
		public static String criptografar(String puroTexto, String chave) throws Exception {
			//pegar chave
			SecureRandom sr = new SecureRandom(chave.getBytes());
			
			KeyGenerator geradorChave = KeyGenerator.getInstance(ALGORITIMO);
						 geradorChave.init(sr);
						 
			SecretKey chaveSecreta = geradorChave.generateKey();
	
			//criptografar
			Cipher cifra = Cipher.getInstance(ALGORITIMO);
	               cifra.init(Cipher.ENCRYPT_MODE, chaveSecreta);
	
			byte[] arrBytes = cifra.doFinal(puroTexto.getBytes());
			
			//tranformar array de bytes em um bloco de texto
			String cifroTexto = "";
			for (byte b : arrBytes) {
				cifroTexto += b + "|";
			}
			cifroTexto = cifroTexto.substring(0, cifroTexto.length() -1);
			return cifroTexto;
		}
	
		public static String descriptografar(String cifroTexto, String chave) throws Exception {
			//transformar bloco de texto em array de bytes
			String [] arrString = cifroTexto.split("\\|");
			byte[] arrBytes = new byte[arrString.length];
			for (int i = 0; i < arrString.length; i++) {
				arrBytes[i] = Byte.parseByte(arrString[i]);
			}
			
			//pegar chave
			SecureRandom sr = new SecureRandom(chave.getBytes());
			
			KeyGenerator geradorChave = KeyGenerator.getInstance(ALGORITIMO);
						 geradorChave.init(sr);
						 
			SecretKey chaveSecreta = geradorChave.generateKey();
	
			//descriptografar
			Cipher cifra = Cipher.getInstance(ALGORITIMO);
				   cifra.init(Cipher.DECRYPT_MODE, chaveSecreta);
			
			byte[] descriptografar = cifra.doFinal(arrBytes);
	
			return new String(descriptografar);
		}
		
		public static void main(String[] args) throws Exception {
			String puroTexto = "Este é o puro texto";	
			System.out.println("Texto: " + puroTexto);
			
			System.out.print("Criptografar: ");
			String cifroTexto = CifraSimetrica.criptografar(puroTexto, "CHAVE");
			System.out.println(cifroTexto.toString());
			
			System.out.print("Decriptografar: ");
			String novoPuroTexto = CifraSimetrica.descriptografar(cifroTexto, "CHAVE");
			System.out.println(novoPuroTexto);
		}
	}
	
	
	public static class CifraAssimetrica {
		//https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
		private static final String ALGORITIMO = "RSA";  //pode ser RSA, 
		
	    public static KeyPair gerarParChaves(String chave) throws NoSuchAlgorithmException {
			SecureRandom sr = new SecureRandom(chave.getBytes());
	        KeyPairGenerator gerador = KeyPairGenerator.getInstance("RSA");
	        				 gerador.initialize(512, sr); //512 bytes é o tamanho minimo dessa chave para esta cifra
	        				 
	        KeyPair parChaves = gerador.generateKeyPair();
	
	        return parChaves;
	    }
	
	    public static byte[] criptografar(String puroTexto, PublicKey chavePublica) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
	        Cipher cifra = Cipher.getInstance(ALGORITIMO);
	        	   cifra.init(Cipher.ENCRYPT_MODE, chavePublica);
	        	   
	        return cifra.doFinal(puroTexto.getBytes());
	    }
	
	    public static String descriptografar(byte[] cifroTexto, PrivateKey chavePrivada) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
	        Cipher cifra = Cipher.getInstance(ALGORITIMO);
	        	   cifra.init(Cipher.DECRYPT_MODE, chavePrivada);
	        	   
	        return new String(cifra.doFinal(cifroTexto));
	    }
		
		public static void main(String[] args) throws Exception {
			String puroTexto = "Este é o puro texto";
			
			KeyPair parChaves = CifraAssimetrica.gerarParChaves("CHAVE");
			
			System.out.println("Texto: " + puroTexto);
			
			System.out.print("Criptografar: ");
			byte[] cifroTexto = CifraAssimetrica.criptografar(puroTexto, parChaves.getPublic());
			System.out.println(cifroTexto);
			
			System.out.print("Decriptografar: ");
			String novoPuroTexto = CifraAssimetrica.descriptografar(cifroTexto, parChaves.getPrivate());
			System.out.println(novoPuroTexto);
		}
	}
}
