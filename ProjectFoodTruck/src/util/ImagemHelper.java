package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.fileupload.FileItem;

public class ImagemHelper {
	public static String gravarImagemFoodTruck (String id, String context, List<FileItem> fileItemsList) {
		try {
			String nomeArquivo = "foodTruck" + id + ".png";
			
			for (FileItem item : fileItemsList){
			    File arquivo = new File(context);
            	if (!arquivo.exists())
            		arquivo.mkdir();

	            arquivo = new File(arquivo, nomeArquivo);
	            		
	            FileOutputStream output = new FileOutputStream(arquivo);
	            
	            InputStream is = item.getInputStream();

	            byte[] buffer = new byte[2048];

	            int nLidos;
	            while ((nLidos = is.read(buffer)) >= 0)
	                output.write(buffer, 0, nLidos);
	            
	            output.flush();
	            output.close();				
			}

			return nomeArquivo;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return null;
	}
}
