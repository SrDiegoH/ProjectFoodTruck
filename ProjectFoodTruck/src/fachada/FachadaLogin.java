package fachada;

import controler.ControlerFactory;
import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FachadaLogin")
public class FachadaLogin extends FachadaBase {
	
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
		
		String email = request.getParameter("email"); 
		String senha = request.getParameter("senha");
		
		Map<String, Object> hash = ControlerFactory.getFoodTruckControler().loggar(email, senha);		
		
		setarRequest(request, hash);
		
		if(hash.containsKey("mensagem")){				
			rd = request.getRequestDispatcher("login.jsp");
		} else {
			String contextPath = request.getContextPath();
			String cookieName = "idFoodTruck";
		    String cookieValue =  hash.get("id").toString();
		    
		    Cookie newCookie = new Cookie(cookieName, cookieValue);
		    	   newCookie.setPath(contextPath);
		    	   
		    response.addCookie(newCookie);			
			rd = request.getRequestDispatcher("gerenciarFoodTruck.jsp");
		}
		
		rd.forward(request, response);	
    }
}
