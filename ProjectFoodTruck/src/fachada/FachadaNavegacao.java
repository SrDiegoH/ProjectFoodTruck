package fachada;

import controler.ControlerFactory;
import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FachadaNavegacao")
public class FachadaNavegacao extends FachadaBase {

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		String acao = request.getParameter("acao");		    
		
		if(acao == null){
			response.sendRedirect("login.jsp");
			return;
		}
		
		Map<String, Object> hash = ControlerFactory.getFoodTruckControler().carregaNavegacao(acao, Integer.parseInt(request.getParameter("id")));
		
		setarRequest(request, hash);
		
		rd = request.getRequestDispatcher(hash.get("url").toString());
		
		rd.forward(request, response);
	}
}
