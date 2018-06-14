package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
			return CifraSimetrica.criptografar(puroTexto, lerArquivoChave());
		} catch (Exception e) {
			return null;
		}
	}

	public static String descriptografar(String cifroTexto) {
		try {
			return CifraSimetrica.descriptografar(cifroTexto, lerArquivoChave());
		} catch (Exception e) {
			return null;
		}
	}

	private static String lerArquivoChave() {
		BufferedReader leitor = null;
		try  {
		    File arquivo = new File("C:\\");
        	if (!arquivo.exists())
        		arquivo.mkdir();

            arquivo = new File(arquivo, "temp.txt");
            
			leitor = new BufferedReader(new FileReader(arquivo));

			StringBuilder texto = new StringBuilder();
	        String linha;
	        while ((linha = leitor.readLine()) != null){
               texto.append(linha);
	        }
		
	        leitor.close();
	        
	        return CifraBase64.descriptografar(texto.toString());
		} catch (Exception e) {
			try {
				leitor.close();				
			} catch (Exception e2) {
			}
		}
			
		return null;
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
		// https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
		private static final String ALGORITIMO = "AES"; // AES RC4

		public static String criptografar(String puroTexto, String chave) throws Exception {
			// pegar chave
			SecureRandom sr = new SecureRandom(chave.getBytes());

			KeyGenerator geradorChave = KeyGenerator.getInstance(ALGORITIMO);
			geradorChave.init(sr);

			SecretKey chaveSecreta = geradorChave.generateKey();

			// criptografar
			Cipher cifra = Cipher.getInstance(ALGORITIMO);
			cifra.init(Cipher.ENCRYPT_MODE, chaveSecreta);

			byte[] arrBytes = cifra.doFinal(puroTexto.getBytes());

			// tranformar array de bytes em um bloco de texto
			StringBuffer cifroTexto = new StringBuffer();
			
			for (byte cipherTextByte : arrBytes) {
				cifroTexto.append(String.format("%02X", 0xFF & cipherTextByte));
			}
			
			return cifroTexto.toString();
		}

		public static String descriptografar(String cifroTexto, String chave) throws Exception {
			byte [] cipherTextInBytes = new byte[cifroTexto.length()/2];
			
		    char [] cipherTextToCharArray = cifroTexto.toCharArray();
		    
		    StringBuffer currentChar;
		    
		    for (int i = 0; i < cipherTextToCharArray.length; i += 2) {
		        currentChar = new StringBuffer(2);
		        currentChar.append(cipherTextToCharArray[i]).append(cipherTextToCharArray[i + 1]);
		        
		        cipherTextInBytes[i/2] = (byte) Long.parseLong(currentChar.toString(), 16);
		    }

			// pegar chave
			SecureRandom sr = new SecureRandom(chave.getBytes());

			KeyGenerator geradorChave = KeyGenerator.getInstance(ALGORITIMO);
			geradorChave.init(sr);

			SecretKey chaveSecreta = geradorChave.generateKey();

			// descriptografar
			Cipher cifra = Cipher.getInstance(ALGORITIMO);
			cifra.init(Cipher.DECRYPT_MODE, chaveSecreta);

			byte[] descriptografar = cifra.doFinal(cipherTextInBytes);

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
		// https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
		private static final String ALGORITIMO = "RSA";

		public static KeyPair gerarParChaves(String chave) throws NoSuchAlgorithmException {
			SecureRandom sr = new SecureRandom(chave.getBytes());
			KeyPairGenerator gerador = KeyPairGenerator.getInstance("RSA");
			gerador.initialize(512, sr); // 512 bytes é o tamanho minimo dessa chave para esta cifra

			KeyPair parChaves = gerador.generateKeyPair();

			return parChaves;
		}

		public static byte[] criptografar(String puroTexto, PublicKey chavePublica) throws InvalidKeyException,
				NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
			Cipher cifra = Cipher.getInstance(ALGORITIMO);
			cifra.init(Cipher.ENCRYPT_MODE, chavePublica);

			return cifra.doFinal(puroTexto.getBytes());
		}

		public static String descriptografar(byte[] cifroTexto, PrivateKey chavePrivada)
				throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException,
				BadPaddingException, IllegalBlockSizeException {
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
