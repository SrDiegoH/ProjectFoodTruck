package fachada;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controler.ControlerFactory;
import dao.DaoFactory;
import model.FoodTruck;
import model.Prato;

@WebServlet("/FachadaCadastrarPratos")
public class FachadaCadastrarPratos extends FachadaBase {

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		
		String acao = request.getParameter("acao");		
		if(acao == null){
			response.sendRedirect("login.jsp");
			return;
		} else if (acao.equalsIgnoreCase("cadastro")){
			Double preco = Double.parseDouble(request.getParameter("preco").trim().replace(",", "."));	
			String prato = request.getParameter("prato").trim();
			String descricao = request.getParameter("descricaoPrato");
			
			String foodtruck = request.getParameter("foodtruck");
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			setarRequest(request, ControlerFactory.getPratoControler().cadastrar(id, foodtruck, preco, prato, descricao));
				
			rd = request.getRequestDispatcher("cadastrarPrato.jsp");
			rd.forward(request, response);
		}		
	}
}
