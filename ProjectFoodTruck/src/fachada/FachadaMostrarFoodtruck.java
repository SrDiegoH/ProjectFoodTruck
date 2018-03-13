package fachada;

import controler.ControlerFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FachadaMostrarFoodtruck")
public class FachadaMostrarFoodtruck extends FachadaBase {

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			 
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		  
		response.getWriter().write(ControlerFactory.getFoodTruckControler().carregarModal(Integer.parseInt(request.getParameter("id"))));
	}
}
