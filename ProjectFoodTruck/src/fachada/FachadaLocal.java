package fachada;

import controler.ControlerFactory;
import dao.DaoFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FachadaLocal")
public class FachadaLocal extends FachadaBase {
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher rd = null;
		
		Map<String, Object> hash = new HashMap<>();
		
		String acao = request.getParameter("acao");
		if(acao == null){
			response.sendRedirect("login.jsp");
			return;
		} else if (acao.equalsIgnoreCase("excluir")){			
			hash = ControlerFactory.getLocalControler().excluir(Integer.parseInt(request.getParameter("id")));
		} else if (acao.equalsIgnoreCase("adicionarNaLista")){
			String nome = request.getParameter("nome");
			Integer id = Integer.parseInt(request.getParameter("id"));
			Double latitude = Double.parseDouble(request.getParameter("latitude"));
			Double longitude = Double.parseDouble(request.getParameter("longitude"));
	
			hash = ControlerFactory.getLocalControler().cadastrar(id, latitude, longitude, nome);
		} else if (acao.equalsIgnoreCase("utilizar")){			
			hash = ControlerFactory.getLocalControler().atualizarLocal(Integer.parseInt(request.getParameter("id")));
		}
		
		setarRequest(request, hash);
		
		rd = request.getRequestDispatcher("alterarLocalizacao.jsp");
		
		rd.forward(request, response);		
	}
}
