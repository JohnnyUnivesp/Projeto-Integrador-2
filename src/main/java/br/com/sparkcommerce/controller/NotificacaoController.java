package br.com.sparkcommerce.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.dao.VendaDAO;
import br.com.sparkcommerce.model.Produto;
import br.com.sparkcommerce.model.Venda;
import br.com.sparkcommerce.model.ItemVenda;

@Controller
public class NotificacaoController {

    @Inject HttpServletRequest request;
    @Inject Result result;
    @Inject ProdutoDAO produtoDao;
    @Inject VendaDAO vendaDao;

    @Post("notifications")
    public void notifications() throws JSONException, MPException, MPApiException {	
    	StringBuilder jsonBuilder = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"))) {
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonBody = jsonBuilder.toString();
        
        JsonObject jsonObject = null;
        
        // Parse do JSON para obter o `resource` id
       JSONObject json = new JSONObject(jsonBody);
       if (json.has("data")) {
	    	Long paymentId = json.getJSONObject("data").getLong("id");
	
	        // Configuração do access token do Mercado Pago
	        MercadoPagoConfig.setAccessToken("APP_USR-7739258657737147-101913-7a661f0aec0e9b777ba06e1f7b3139d0-157689435");
	        
	        // Cliente de pagamento do Mercado Pago
	        PaymentClient client = new PaymentClient();
	
	        // Realiza a chamada para obter o pagamento
	        Payment payment = client.get(paymentId);
	
	        // Acessa o conteúdo da resposta como JSON
	        String paymentDetails = payment.getResponse().getContent();
	
	        jsonObject = JsonParser.parseString(paymentDetails).getAsJsonObject();
       }
        // Verificar se o status do pagamento é "approved"
       if (jsonObject != null && jsonObject.has("status")) { 
	       if ("approved".equalsIgnoreCase(jsonObject.get("status").getAsString())) {
	            JsonObject additionalInfo = jsonObject.get("additional_info").getAsJsonObject();
	            
	            // Informações do comprador
	            JsonObject payer = additionalInfo.get("payer").getAsJsonObject();
	            String primeiroNome = payer.get("first_name").getAsString();
	            String sobreNome = payer.has("last_name") && !payer.get("last_name").isJsonNull() 
	                    ? payer.get("last_name").getAsString() 
	                    : "";
	            String compradorTelefone = payer.get("phone").getAsJsonObject().get("number").getAsString();
	            JsonObject address = payer.get("address").getAsJsonObject();
	            String rua = address.get("street_name").getAsString();
	            String numero = address.get("street_number").getAsString();
	            String cep = address.get("zip_code").getAsString();
	            Long paymentId = jsonObject.get("id").getAsLong();
	            
	            // Criar uma nova venda
	            Venda venda = new Venda();
	            venda.setPaymentId(paymentId);
	            venda.setCompradorNome(primeiroNome + " " + sobreNome);
	            venda.setCompradorTelefone(compradorTelefone);
	            venda.setEnderecoComprador(rua + ", " + numero + " - " + cep);
	            venda.setDataPagamento(LocalDateTime.now());
	
	            // Adicionar itens de venda
	            List<ItemVenda> itensVenda = new ArrayList<>();
	            JsonArray itemsArray = additionalInfo.get("items").getAsJsonArray();
	            for (int i = 0; i < itemsArray.size(); i++) {
	                JsonObject item = itemsArray.get(i).getAsJsonObject();
	                
	                // Dados do produto
	                String productId = item.get("id").getAsString();
	                int quantidade = item.get("quantity").getAsInt();
	
	                // Buscar o produto no banco
	                Produto produto = produtoDao.selectPorId(Integer.parseInt(productId));
	
	                // Criar e adicionar o ItemVenda à lista
	                ItemVenda itemVenda = new ItemVenda();
	                itemVenda.setProdutoId(Long.valueOf(produto.getId()));
	                itemVenda.setQuantidade(quantidade);
	                itensVenda.add(itemVenda);
	            }
	
	            // Associar os itens à venda e salvar
	            venda.setItens(itensVenda);
	            vendaDao.save(venda);
	
	        }
       }
	       
        result.nothing(); // Retorna uma resposta vazia com status 200
    }
}
