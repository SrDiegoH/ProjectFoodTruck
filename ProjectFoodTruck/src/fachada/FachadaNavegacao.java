package fachada;

import controler.ControlerFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		
		try {
			Cookie cookie = Arrays.asList(request.getCookies()).stream()
								  .filter(c -> c.getName().equals("SESSION"))
								  .collect(Collectors.toList())
								  .get(0);
			
			if(cookie == null) {
				throw new Exception();
			}

			if(!ControlerFactory.getSessionControler().isOver(cookie.getValue())) {
				Map<String, Object> hash = ControlerFactory.getFoodTruckControler().carregaNavegacao(acao, cookie.getValue());
				
				String url = hash.get("url").toString();
				
				if(url.equals("login.jsp")) {
					String cookieName = "SESSION";
					String contextPath = request.getContextPath();
					
				    Cookie newCookie = new Cookie(cookieName, "");
				    	   newCookie.setPath(contextPath);
				    	   newCookie.setMaxAge(0);
				    	   
				    response.addCookie(newCookie);	
				}
				
				setarRequest(request, hash);
				
				rd = request.getRequestDispatcher(url);
				
				rd.forward(request, response);
			} else {
				throw new Exception();				
			}
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
		}
    }
}
