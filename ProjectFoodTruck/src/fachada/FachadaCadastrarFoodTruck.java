package fachada;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controler.ControlerFactory;

@WebServlet("/FachadaCadastrarFoodTruck")
public class FachadaCadastrarFoodTruck extends FachadaBase {

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
		
		if(acao.equals("email")){
			Map<String, Object> hash = ControlerFactory.getFoodTruckControler().alterarEmail(request.getParameter("id"));		
			
			setarRequest(request, hash);
			String cookieName = "SESSION";
		    String cookieValue = hash.get("hash").toString();
			String contextPath = request.getContextPath();
			Integer prazo = (int) ((Date) hash.get("prazo")).getTime();
			
		    Cookie newCookie = new Cookie(cookieName, cookieValue);
		    	   newCookie.setPath(contextPath);
		    	   newCookie.setMaxAge(prazo);
		    	   
		    response.addCookie(newCookie);	
		    
			rd = request.getRequestDispatcher("gerenciarFoodTruck.jsp");
			rd.forward(request, response);
			return;
		} else if(acao.equals("novaSenha")){
			Map<String, Object> hash = ControlerFactory.getFoodTruckControler().buscarPorId(request.getParameter("id"));		
			
			setarRequest(request, hash);
			String cookieName = "SESSION";
		    String cookieValue = hash.get("hash").toString();
			String contextPath = request.getContextPath();
			Integer prazo = (int) ((Date) hash.get("prazo")).getTime();
			
		    Cookie newCookie = new Cookie(cookieName, cookieValue);
		    	   newCookie.setPath(contextPath);
		    	   newCookie.setMaxAge(prazo);
		    	   
		    response.addCookie(newCookie);	
			
			rd = request.getRequestDispatcher("gerenciarFoodTruck.jsp");
			rd.forward(request, response);
			return;
		}
		
		
		String foodtruck = request.getParameter("foodtruck").trim();
		String email = request.getParameter("email").trim();
		String descricao = request.getParameter("descricao").trim();
		String senha = request.getParameter("senha");
		String senha2 = request.getParameter("confirmarSenha");
				
		HashMap<String, String> hashMap = new HashMap<>();
							    hashMap.put("foodtruck", foodtruck);
						    	hashMap.put("email", email);
								hashMap.put("descricao", descricao);						    		

		if(ControlerFactory.getFoodTruckControler().existeEmail(email)){
			retornaMensagemErro(request, rd, "O email ja foi cadastrado.", "email", hashMap);
			rd = request.getRequestDispatcher("cadastrarFoodTruck.jsp");
		} else if(!(senha.length() >= 8 && senha.length() <= 16)){
			retornaMensagemErro(request, rd, "A senha deve ter entre 8 a 16 caracteres.", "senha", hashMap);
			rd = request.getRequestDispatcher("cadastrarFoodTruck.jsp");
		} else if(!(senha.matches(".*[a-z].*") && senha.matches(".*[A-Z].*") && senha.matches(".*[0-9].*"))){
			retornaMensagemErro(request, rd, "A senha deve conter letras maiúsculas, minúsculas e números.", "senha", hashMap);
			rd = request.getRequestDispatcher("cadastrarFoodTruck.jsp");
		} else if(!senha.equals(senha2)){
			retornaMensagemErro(request, rd, "As senhas sao diferentes.", "senha", hashMap);
			rd = request.getRequestDispatcher("cadastrarFoodTruck.jsp");
		} else {
			ControlerFactory.getFoodTruckControler().cadastrar(foodtruck, email, descricao, senha, senha2);			
			rd = request.getRequestDispatcher("login.jsp");			
		}
		
		rd.forward(request, response);		
	}
	
	private void retornaMensagemErro(HttpServletRequest request, RequestDispatcher rd, String mensagem, String retorno, HashMap<String, String> hashMap){
		hashMap.put("mensagem", mensagem);
		hashMap.put("retorno", retorno);
		
		request = addParamRequest(request, hashMap);
	}
	
	private HttpServletRequest addParamRequest(HttpServletRequest request, HashMap<String, String> parametros){		
		for(Entry<String, String> entry : parametros.entrySet())
			request.setAttribute(entry.getKey(), entry.getValue());
		
		return request;
	}
}
