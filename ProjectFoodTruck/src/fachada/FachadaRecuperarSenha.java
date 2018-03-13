package fachada;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controler.ControlerFactory;

@WebServlet("/FachadaRecuperarSenha")
public class FachadaRecuperarSenha extends FachadaBase {
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

		String email = request.getParameter("email").trim();
		
		Map<String, Object> hash = new HashMap<>();

		if(ControlerFactory.getFoodTruckControler().existeEmail(email)){
			ControlerFactory.getFoodTruckControler().enviarNovaSenhaPorEmail(email);
			hash.put("mensagem", "Nova Senha enviada para o e-mail.");
			rd = request.getRequestDispatcher("login.jsp");
		} else {
			hash.put("mensagem", "E-mail nao existe.");			
			rd = request.getRequestDispatcher("recuperarSenha.jsp");
		}
		
		hash.put("retorno", "login");
		setarRequest(request, hash);
		rd.forward(request, response);		
	}
}
