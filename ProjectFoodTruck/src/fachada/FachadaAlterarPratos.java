package fachada;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controler.ControlerFactory;
import dao.DaoFactory;
import model.FoodTruck;
import model.Prato;
import model.Session;

@WebServlet("/FachadaAlterarPratos")
public class FachadaAlterarPratos extends FachadaBase {
     	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				if(acao.equalsIgnoreCase("alterar")) {
					Map<String, Object> hash = ControlerFactory.getPratoControler().alterar(Integer.parseInt(request.getParameter("id")));
					
					setarRequest(request, hash);

					request.setAttribute("idPrato", hash.get("id").toString());
				    Cookie newCookie = new Cookie("idPrato", hash.get("id").toString());
				    	   newCookie.setPath(request.getContextPath());
		    	    response.addCookie(newCookie);	
					
					rd = request.getRequestDispatcher("alterarPrato.jsp");
					rd.forward(request, response);
				} else if (acao.equalsIgnoreCase("excluir")){
					setarRequest(request, ControlerFactory.getPratoControler().excluir(Integer.parseInt(request.getParameter("id"))));
					
					rd = request.getRequestDispatcher("buscarPrato.jsp");
					
					rd.forward(request, response);
				} else if (acao.equalsIgnoreCase("pesquisar")){
					Map<String, Object> hash = ControlerFactory.getPratoControler().pesquisarPratoPorFoodTruckENome(cookie.getValue(), request.getParameter("nmPrato"));
					
					setarRequest(request, hash);
					
					rd = request.getRequestDispatcher(hash.get("url").toString());
					
					rd.forward(request, response);
				}
			} else {
				throw new Exception();				
			}
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
		}
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;

		
		try {
			Cookie cookie = Arrays.asList(request.getCookies()).stream()
								  .filter(c -> c.getName().equals("SESSION"))
								  .collect(Collectors.toList())
								  .get(0);
			
			if(cookie == null) {
				throw new Exception();
			}
	
			if(!ControlerFactory.getSessionControler().isOver(cookie.getValue())) {
				Session session = ControlerFactory.getSessionControler().buscarPorHashValor(cookie.getValue());
				
				if(ServletFileUpload.isMultipartContent(request)){
					Cookie cookie2 = Arrays.asList(request.getCookies()).stream()
										  .filter(c -> c.getName().equals("idPrato"))
										  .collect(Collectors.toList())
										  .get(0);
					
					Prato prato = DaoFactory.getPratoDao().find(Integer.parseInt(cookie2.getValue()));
						  prato.setLocalImagem(gravarImagem(request, session.getFoodTruck().getId().toString(), cookie2.getValue()));
					
					DaoFactory.getPratoDao().update(prato);
					
					request.setAttribute("arrayPratos", DaoFactory.getPratoDao().filtrarPorFoodTruck(prato.getFoodTruck().getId()));
					
					rd = request.getRequestDispatcher("buscarPrato.jsp");			
					rd.forward(request, response);
				} else {
					String acao = request.getParameter("acao");
					
					if(acao == null){
						response.sendRedirect("login.jsp");
						return;
					} else if (acao.equalsIgnoreCase("alterar")){
						try {
							String senha = request.getParameter("inputPassword");
							FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(session.getFoodTruck().getId());
							Prato prato = DaoFactory.getPratoDao().find(Integer.parseInt(request.getParameter("id")));
							
							String senhaCriptografada = DigestUtils.sha256Hex(senha);
							if(!foodTruck.getSenha().equals(senhaCriptografada)) {
								request.setAttribute("retorno", "erro");
								request.setAttribute("mensagem", "Senha incorreta");
			
								request.setAttribute("id", prato.getId());
								request.setAttribute("prato", prato.getNome());
								request.setAttribute("preco", prato.getPreco().toString());
								request.setAttribute("descricaoPrato", prato.getDescricao());
								
								rd = request.getRequestDispatcher("alterarPrato.jsp");
							} else {
								double preco = Double.parseDouble(request.getParameter("preco").trim().replace(",", "."));
								
							    prato.setNome(request.getParameter("prato").trim());
						        prato.setDescricao(request.getParameter("descricaoPrato"));
						        prato.setPreco(preco);
								      
								DaoFactory.getPratoDao().update(prato);
															
								request.setAttribute("arrayPratos", DaoFactory.getPratoDao().filtrarPorFoodTruck(session.getFoodTruck().getId()));
								
								rd = request.getRequestDispatcher("buscarPrato.jsp");
							}
						} catch (Exception e) {
							request.setAttribute("retorno", "erro");
							request.setAttribute("mensagem", "Formato do preço inválido");
		
							request.setAttribute("id", Integer.parseInt(request.getParameter("id")));
							request.setAttribute("prato", request.getParameter("prato"));
							request.setAttribute("preco", request.getParameter("preco"));
							request.setAttribute("descricaoPrato", request.getParameter("descricaoPrato"));
							
							rd = request.getRequestDispatcher("alterarPrato.jsp");
						}
						
						rd.forward(request, response);
					}
				}
			} else {
				throw new Exception();				
			}
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
		}
	}	
	
	private String gravarImagem(HttpServletRequest request, String idFoodTruck, String idPrato) {
		try {			
			ServletFileUpload fileUp = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> fileItemsList = fileUp.parseRequest(request);
			String nome = "";
			
			for (FileItem item : fileItemsList){
				String diretorio = getServletContext().getRealPath("\\imagensPratos");				
			    File arquivo = new File(diretorio);
            	if (!arquivo.exists())
            		arquivo.mkdir();
            	
            	nome ="foodTruck" +  idFoodTruck + "prato" +idPrato + ".png";
	            arquivo = new File(arquivo,nome );
	            		
	            FileOutputStream output = new FileOutputStream(arquivo);
	            
	            InputStream is = item.getInputStream();

	            byte[] buffer = new byte[2048];

	            int nLidos;
	            while ((nLidos = is.read(buffer)) >= 0)
	                output.write(buffer, 0, nLidos);
	            
	            output.flush();
	            output.close();				
			}
			
			return nome;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return null;
	}
}
