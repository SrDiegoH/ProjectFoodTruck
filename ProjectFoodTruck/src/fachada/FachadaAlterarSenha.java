package fachada;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controler.ControlerFactory;

@WebServlet("/FachadaAlterarSenha")
public class FachadaAlterarSenha extends FachadaBase {
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
		
		String senhaAtual = request.getParameter("senhaAtual").trim();
		String novaSenha = request.getParameter("novaSenha").trim();
		String confirmarNovaSenha = request.getParameter("confirmarNovaSenha").trim();
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		setarRequest(request, ControlerFactory.getFoodTruckControler().alterarSenha(id, senhaAtual, novaSenha, confirmarNovaSenha));
		
		rd = request.getRequestDispatcher("alterarSenha.jsp");
		rd.forward(request, response);
	}
}
