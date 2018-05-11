package fachada;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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

@WebServlet("/FachadaLocal")
public class FachadaLocal extends FachadaBase {
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
				Map<String, Object> hash = new HashMap<>();
				
				if (acao.equalsIgnoreCase("excluir")){			
					hash = ControlerFactory.getLocalControler().excluir(Integer.parseInt(request.getParameter("id")));
				} else if (acao.equalsIgnoreCase("adicionarNaLista")){
					String nome = request.getParameter("nome");
					Double latitude = Double.parseDouble(request.getParameter("latitude"));
					Double longitude = Double.parseDouble(request.getParameter("longitude"));
			
					hash = ControlerFactory.getLocalControler().cadastrar(cookie.getValue(), latitude, longitude, nome);
				} else if (acao.equalsIgnoreCase("utilizar")){
					hash = ControlerFactory.getLocalControler().atualizarLocal(Integer.parseInt(request.getParameter("id")));
				}
				
				setarRequest(request, hash);
				
				rd = request.getRequestDispatcher("alterarLocalizacao.jsp");
				
				rd.forward(request, response);	
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
		}	
	}
}
