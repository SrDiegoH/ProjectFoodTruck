package controler;

import java.util.Arrays;
import java.util.List;

public abstract class ControlerBase {
	private List<ControlerBase> listaSubControler;
	
	protected ControlerBase (ControlerBase... listaSubControler){
		this.listaSubControler = Arrays.asList(listaSubControler);
	}

	public List<ControlerBase> getListaSubControler() {
		return listaSubControler;
	}
	public void setListaSubControler(List<ControlerBase> listaSubControler) {
		this.listaSubControler = listaSubControler;
	}
}
