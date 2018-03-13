package fachada;

import controler.ControlerFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FachadaAvaliacao")
public class FachadaAvaliacao extends FachadaBase {

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer operacao = Integer.parseInt(request.getParameter("operacao"));
		
		if(Integer.parseInt(request.getParameter("quemAvaliar")) == 0)//para os pratos
			ControlerFactory.getPratoControler().avaliarPrato(Integer.parseInt(request.getParameter("id")), operacao);
		else //para os foodtruck
			ControlerFactory.getFoodTruckControler().avaliarFoodTruck(Integer.parseInt(request.getParameter("fk")), operacao);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		response.getWriter().write(ControlerFactory.getFoodTruckControler().carregarModal(Integer.parseInt(request.getParameter("fk"))));
	}
}
