package br.com.sparkcommerce.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
@Path("success")
public class PagamentoController{
	
	@Inject Result result;
	
	@Post("")
	public void receiveNotification(HttpServletRequest request) {

	    // Captura as informações enviadas pelo Mercado Pago
	    StringBuilder stringBuilder = new StringBuilder();
	    String line;
		    try (BufferedReader reader = request.getReader()) {
	        while ((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    String jsonBody = stringBuilder.toString();
	    System.out.println("Corpo da requisição: " + jsonBody);
	
	    // Retorna um status HTTP 200 OK para indicar sucesso
	    result.nothing();
	}
}