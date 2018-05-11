package fachada;

import controler.ControlerFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

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
		
		try {
			Cookie cookie = Arrays.asList(request.getCookies()).stream()
								  .filter(c -> c.getName().equals("SESSION"))
								  .collect(Collectors.toList())
								  .get(0);
			if(cookie == null) {
				throw new Exception();
			}

			if(!ControlerFactory.getSessionControler().isOver(cookie.getValue())) {	
				Map<String, Object> hash = ControlerFactory.getFoodTruckControler().pegarFoodTruckPorHashValor(cookie.getValue());
				
				setarRequest(request, hash);
				
				rd = request.getRequestDispatcher("gerenciarFoodTruck.jsp");
			} else {
				throw new Exception();
			}
		} catch (Exception e) {		
			String email = request.getParameter("email"); 
			String senha = request.getParameter("senha");
			
			Map<String, Object> hash = ControlerFactory.getFoodTruckControler().loggar(email, senha);		
			
			setarRequest(request, hash);
			
			if(hash.containsKey("mensagem")){				
				rd = request.getRequestDispatcher("login.jsp");
			} else {
				String cookieName = "SESSION";
			    String cookieValue = hash.get("hash").toString();
				String contextPath = request.getContextPath();
				Integer prazo = (int) ((Date) hash.get("prazo")).getTime();
				
			    Cookie newCookie = new Cookie(cookieName, cookieValue);
			    	   newCookie.setPath(contextPath);
			    	   newCookie.setMaxAge(prazo);
			    	   
			    response.addCookie(newCookie);	
			    
				rd = request.getRequestDispatcher("gerenciarFoodTruck.jsp");
			}
		}
		
		rd.forward(request, response);	
    }
}
