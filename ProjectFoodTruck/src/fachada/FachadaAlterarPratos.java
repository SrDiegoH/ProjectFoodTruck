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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controler.ControlerFactory;
import dao.DaoFactory;
import model.Prato;

@WebServlet("/FachadaAlterarPratos")
public class FachadaAlterarPratos extends FachadaBase {	
	private List<Cookie> cookies;	
	private Cookie cookie, cookie2;
     	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		String acao = request.getParameter("acao");
				
		if(acao.equalsIgnoreCase("alterar")){			
			Map<String, Object> hash = ControlerFactory.getPratoControler().alterar(Integer.parseInt(request.getParameter("id")));
			
			setarRequest(request, hash);
			
			response.addCookie(new Cookie("idPratoFoodTruck", hash.get("id").toString()));
			
			rd = request.getRequestDispatcher("alterarPrato.jsp");
			rd.forward(request, response);
		} else if (acao.equalsIgnoreCase("excluir")){
//			Prato prato = DaoFactory.getPratoDao().find(Integer.parseInt(request.getParameter("id")));
//			
//			DaoFactory.getPratoDao().delete(Integer.parseInt(request.getParameter("id")));
//						
//			request.setAttribute("foodtruck", prato.getFoodTruck().getNome());				
//			request.setAttribute("id", prato.getFoodTruck().getId());
//			request.setAttribute("arrayPratos", DaoFactory.getPratoDao().filtrarPorFoodTruck(prato.getFoodTruck().getId()));
			
			setarRequest(request, ControlerFactory.getPratoControler().excluir(Integer.parseInt(request.getParameter("id"))));
			
			rd = request.getRequestDispatcher("buscarPrato.jsp");
			
			rd.forward(request, response);
		} else if (acao.equalsIgnoreCase("pesquisar")){
			Map<String, Object> hash = ControlerFactory.getPratoControler().pesquisarPratoPorFoodTruckENome(Integer.parseInt(request.getParameter("id")), request.getParameter("nmPrato"));
			
			setarRequest(request, hash);
			
			rd = request.getRequestDispatcher(hash.get("url").toString());
			
			rd.forward(request, response);
		}
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		
		cookies = Arrays.asList(request.getCookies());
		
		cookie = cookies.stream()
				         .filter(c -> c.getName().equals("idPratoFoodTruck"))
				         .collect(Collectors.toList())
				         .get(0);
		
		cookie2 = cookies.stream()
						 .filter(c -> c.getName().equals("idFoodTruck"))
						 .collect(Collectors.toList())
						 .get(0);
		
		//coisas lokas de gabriel
		if(ServletFileUpload.isMultipartContent(request)){			  
//		Prato prato = DaoFactory.getPratoDao().find(Integer.parseInt(cookie.getValue()));				  
//		String retornoPrato = gravarImagem(request, cookie2.getValue(), cookie.getValue());
//		prato.setLocalImagem(retornoPrato);
			
			Prato prato = DaoFactory.getPratoDao().find(Integer.parseInt(cookie.getValue()));
				  prato.setLocalImagem(gravarImagem(request, cookie2.getValue(), cookie.getValue()));
			
			DaoFactory.getPratoDao().update(prato);
			
			request.setAttribute("foodtruck", prato.getFoodTruck().getNome());				
			request.setAttribute("id", prato.getFoodTruck().getId());
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
					double preco = Double.parseDouble(request.getParameter("preco").trim().replace(",", "."));
					
					Prato prato = DaoFactory.getPratoDao().find(Integer.parseInt(request.getParameter("id")));
						  prato.setNome(request.getParameter("prato").trim());
					      prato.setDescricao(request.getParameter("descricaoPrato"));
					      prato.setPreco(preco);
					      
					DaoFactory.getPratoDao().update(prato);
					
					request.setAttribute("foodtruck", prato.getNome());
					request.setAttribute("id", Integer.parseInt(request.getParameter("fk")));
					request.setAttribute("arrayPratos", DaoFactory.getPratoDao().filtrarPorFoodTruck(Integer.parseInt(request.getParameter("fk"))));
					
					rd = request.getRequestDispatcher("buscarPrato.jsp");
				} catch (Exception e) {
					request.setAttribute("retorno", "preco");
					request.setAttribute("mensagem", "Formato do preco invalido");

					request.setAttribute("foodtruck", request.getParameter("foodtruck"));
					request.setAttribute("fk", Integer.parseInt(request.getParameter("fk")));
					request.setAttribute("id", Integer.parseInt(request.getParameter("id")));
					request.setAttribute("prato", request.getParameter("prato"));
					request.setAttribute("descricaoPrato", request.getParameter("descricaoPrato"));
					
					rd = request.getRequestDispatcher("alterarPrato.jsp");
				}
				
				rd.forward(request, response);
			}
		}
	}	
	
	private String gravarImagem (HttpServletRequest request, String idFoodTruck, String idPrato) {
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
