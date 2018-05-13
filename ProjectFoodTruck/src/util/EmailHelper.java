package util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

public class EmailHelper {
	public static void enviarEmail(String email, String foodTruck, String codigo){
		try{
			final String ENDERECO= "http://localhost:8080";
			final String USERNAME = "foodtrucklocalizador@gmail.com";
			final String PASSWORD = "foodtruck1";
			
			String mensagem =  new StringBuffer().append("<p>Olá administrador(a) do(a) <b>")
												 .append(foodTruck)
												 .append("</b>,")
												 .append("<br/>")
												 .append("<br/>")
												 .append("Seja bem vindo ao Localizador de FoodTruck.")
												 .append("<br/>")
												 .append("<br/>")
												 .append("Por favor confirme o seu e-mail clicando <b><a href='")
												 .append(ENDERECO)
												 .append("/ProjectFoodTruck/FachadaCadastrarFoodTruck?id=")													   
												 .append(codigo) //inves do id usar um codigo gerado aleatorimente
												 .append("&acao=email'>aqui</a></b>.</p>")
												 .toString();

			Properties props = new Properties();
					   props.put("mail.smtp.auth", "true");
					   props.put("mail.smtp.starttls.enable", "true");
					   props.put("mail.smtp.host", "smtp.gmail.com");
					   props.put("mail.smtp.port", "587");

		    //bugga se usar lambda :S
			Session session = Session.getInstance(props,
			  new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			  }
			);

			Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("teste@gmail.com"));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));			
					message.setSubject("Seja bem vindo");
					message.setContent(mensagem, "text/html; charset=utf-8");
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();			
		}		
	}
	
	public static void emailNovaSenha(String email, String foodTruck, String senha, String id){
		try{
			final String ENDERECO= "http://localhost:8080";
			final String USERNAME = "foodtrucklocalizador@gmail.com";
			final String PASSWORD = "foodtruck1";
			
			String mensagem =  new StringBuffer().append("<p>Olá administrador(a) do(a) <b>")
												 .append(foodTruck)
												 .append("</b>,")
												 .append("<br/>")
												 .append("<br/>")
												 .append("Sua senha foi redefinida para ")
												 .append(senha)
												 .append(".<br/>")
												 .append("<br/>")
												 .append("Você pode acessar diretamente seu cadastro clicando em <b><a href='")
												 .append(ENDERECO)
												 .append("/ProjectFoodTruck/FachadaCadastrarFoodTruck?id=")													   
												 .append(id)
												 .append("&acao=novaSenha'>Aqui</a></b>.</p>")
												 .toString();

			Properties props = new Properties();
					   props.put("mail.smtp.auth", "true");
					   props.put("mail.smtp.starttls.enable", "true");
					   props.put("mail.smtp.host", "smtp.gmail.com");
					   props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
			  new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			  }
			);

			Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("teste@gmail.com"));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));			
					message.setSubject("Seja bem vindo");
					message.setContent(mensagem, "text/html; charset=utf-8");
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();			
		}		
	}		
}
