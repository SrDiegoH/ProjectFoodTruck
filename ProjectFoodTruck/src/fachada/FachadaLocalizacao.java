package fachada;

import controler.ControlerFactory;
import dao.DaoFactory;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		boolean isApagar = acao.equalsIgnoreCase("apagar");
		Integer id = Integer.parseInt(request.getParameter("id"));
		Double latitude = 0.0, longitude = 0.0;
		
		if(!isApagar){
			latitude = Double.parseDouble(request.getParameter("latitude"));
			longitude = Double.parseDouble(request.getParameter("longitude"));
		}
		
		Map<String, Object> hash = ControlerFactory.getFoodTruckControler().alterarLocalizacao(isApagar, id, latitude, longitude);
		hash.put("arrayLocais", DaoFactory.getLocalDao().filtrarPorFoodTruck(id));				
		if(hash.containsKey("json")){
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(hash.get("json").toString());
		} else {
			setarRequest(request, hash);

			rd = request.getRequestDispatcher("alterarLocalizacao.jsp");
			rd.forward(request, response);
		}
	}
}
