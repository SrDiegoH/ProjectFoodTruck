package fachada;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controler.ControlerFactory;
import dao.DaoFactory;
import model.FoodTruck;

@WebServlet("/FachadaAtualizacaoFoodTruck")
public class FachadaAtualizacaoFoodTruck extends FachadaBase {
	List<Cookie> cookies;	
	Cookie cookie;
     
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		
		cookie = Arrays.asList(request.getCookies()).stream()
													.filter(c -> c.getName().equals("idFoodTruck"))
													.collect(Collectors.toList())
													.get(0);
		
		String acao = request.getParameter("acao");
		if(acao == null && (cookie.getName().isEmpty())){
			response.sendRedirect("login.jsp");
			return;
		}
		
		if(ServletFileUpload.isMultipartContent(request)){
			try {
				Integer id = Integer.parseInt(cookie.getValue());
				String context = getServletContext().getRealPath("\\imagensFoodTruck");
				List<FileItem> fileItemsList = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				
				setarRequest(request, ControlerFactory.getFoodTruckControler().uploadImagem(id, context, fileItemsList));
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {	
			Integer id = Integer.parseInt(request.getParameter("id"));
			String descricao = request.getParameter("descricao");
			String foodtruck = request.getParameter("foodtruck");
			String email = request.getParameter("email");
			
			setarRequest(request, ControlerFactory.getFoodTruckControler().alterar(id, descricao, foodtruck, email));
		}
		
		rd = request.getRequestDispatcher("gerenciarFoodTruck.jsp");		
		
		rd.forward(request, response);
	}
}