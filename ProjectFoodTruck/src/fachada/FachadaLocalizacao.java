package fachada;

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

import controler.ControlerFactory;
import dao.DaoFactory;
import model.Session;

@WebServlet("/FachadaLocalizacao")
public class FachadaLocalizacao extends FachadaBase {
	
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
				//por essa parte no controler
				boolean isApagar = acao.equalsIgnoreCase("apagar");
				String hashValor = cookie.getValue();
				Double latitude = 0.0, longitude = 0.0;
				
				if(!isApagar){
					latitude = Double.parseDouble(request.getParameter("latitude"));
					longitude = Double.parseDouble(request.getParameter("longitude"));
				}
				
				Map<String, Object> hash = ControlerFactory.getFoodTruckControler().alterarLocalizacao(isApagar, hashValor, latitude, longitude);
				
				Session session = ControlerFactory.getSessionControler().buscarPorHashValor(hashValor);
				
				hash.put("arrayLocais", DaoFactory.getLocalDao().filtrarPorFoodTruck(session.getFoodTruck().getId()));				
				if(hash.containsKey("json")){
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(hash.get("json").toString());
				} else {
					setarRequest(request, hash);
		
					rd = request.getRequestDispatcher("alterarLocalizacao.jsp");
					rd.forward(request, response);
				}
			} else {
				throw new Exception();				
			}
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
		}
	}
}
