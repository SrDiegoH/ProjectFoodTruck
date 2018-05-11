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
				if (acao.equalsIgnoreCase("cadastro")){
					Double preco = Double.parseDouble(request.getParameter("preco").trim().replace(",", "."));	
					String prato = request.getParameter("prato").trim();
					String descricao = request.getParameter("descricaoPrato");
					
					setarRequest(request, ControlerFactory.getPratoControler().cadastrar(cookie.getValue(), preco, prato, descricao));
						
					rd = request.getRequestDispatcher("cadastrarPrato.jsp");
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
