package fachada;

import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public abstract class FachadaBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FachadaBase() {
        super();
    }
    
	protected void setarRequest(HttpServletRequest request, Map<String, Object> hash){		
		for (Entry<String, Object> entry : hash.entrySet()){
			request.setAttribute(entry.getKey(), entry.getValue());
		}
	}
}
