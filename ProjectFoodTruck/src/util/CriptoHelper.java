package util;

import org.apache.commons.codec.binary.Base64;

public class CriptoHelper {
	private static Base64 cripto;
	
	static {
		if(cripto == null) {
			cripto = new Base64();
		}
	}

	public static String criptorafar(String puroTexto){
        return cripto.encodeToString(puroTexto.getBytes());
	}
	
	public static String descriptorafar(String cifroTexto){
        return new String(cripto.decode(cifroTexto.getBytes()));
	}		
}
