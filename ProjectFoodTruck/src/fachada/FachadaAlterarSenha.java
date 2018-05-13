package fachada;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		
		try {
			Cookie cookie = Arrays.asList(request.getCookies()).stream()
								  .filter(c -> c.getName().equals("SESSION"))
								  .collect(Collectors.toList())
								  .get(0);
			
			if(cookie == null) {
				throw new Exception();
			}

			if(!ControlerFactory.getSessionControler().isOver(cookie.getValue())) {
				String hashValor = cookie.getValue();
				String senhaAtual = request.getParameter("senhaAtual").trim();
				String novaSenha = request.getParameter("novaSenha").trim();
				String confirmarNovaSenha = request.getParameter("confirmarNovaSenha").trim();
				
				setarRequest(request, ControlerFactory.getFoodTruckControler().alterarSenha(hashValor, senhaAtual, novaSenha, confirmarNovaSenha));
				
				rd = request.getRequestDispatcher("alterarSenha.jsp");
				
				rd.forward(request, response);
			} else {
				throw new Exception();				
			}
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
		}
	}
}
