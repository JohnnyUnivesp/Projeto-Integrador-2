package br.com.sparkcommerce.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.model.Carrinho;
import br.com.sparkcommerce.model.ItemCarrinho;

@Controller	
@Path("finalizar")
public class FormCompraController {
	
	@Inject ProdutoDAO produtoDao;
    @Inject private Result result;
    @Inject Carrinho carrinho;
    @Inject HttpSession session;
    @Inject CarrinhoController verCarrinho;
    
    @Path("")
    public void finalizarCompra() {
    	Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
    	if (carrinho == null || carrinho.getItens().isEmpty()) {
            result.redirectTo(CarrinhoController.class).verCarrinho(); // Se o carrinho estiver vazio, redireciona para verCarrinho
            return;
        }
    }
    
    @Post("enviarDados")
    public void enviarDados(String nome, String email, String tipoDocumento, String numero, 
                            String cpf, String cep, String endereco, String enderecoN) {
    	Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
    	
    	MercadoPagoConfig.setAccessToken("APP_USR-7739258657737147-101913-7a661f0aec0e9b777ba06e1f7b3139d0-157689435");
    	
        // Criar o cliente do Mercado Pago
        PreferenceClient client = new PreferenceClient();

        // Configurar os itens do carrinho
        List<PreferenceItemRequest> items = new ArrayList<>();
        
        if (carrinho == null) {
            System.out.println("Carrinho está vazio ou não foi inicializado.");
        } else {
            System.out.println("Carrinho contém " + carrinho.getItens().size() + " itens.");
        }
        
        for (ItemCarrinho item : carrinho.getItens()) {
        	    PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
        	        .id(Integer.toString(item.getProduto().getId()))
        	        .title(item.getProduto().getNome())
        	        .pictureUrl(item.getProduto().getImagem().getNome())
        	        .quantity(item.getQuantidade())
        	        .currencyId("BRL")
        	        .unitPrice(BigDecimal.valueOf(item.getProduto().getValor()))
        	        .build();
        	    items.add(itemRequest);
        		}

        	PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .name(nome)
                    .email(email)
                    .phone(PhoneRequest.builder().areaCode("55").number(numero).build())
                    .identification(IdentificationRequest.builder().type(tipoDocumento).number(cpf).build())
                    .address(AddressRequest.builder().zipCode(cep).streetName(endereco).streetNumber(enderecoN).build())
                    .build();    
        	
        	PreferenceRequest preferenceRequest = PreferenceRequest.builder()
		        	.items(items)
		        	.backUrls(PreferenceBackUrlsRequest.builder()
		                    .success("/success")
		                    .failure("/failure")
		                    .pending("/pending")
		                    .build())
	                .payer(payer)
	                .autoReturn("all")
	                .binaryMode(true)
			        .build();
		
        	try {
		    Preference preference = client.create(preferenceRequest);
	        // Redirecionar para a URL de pagamento do Mercado Pago
		    System.out.println(preferenceRequest);
	        result.redirectTo(preference.getInitPoint());
		} catch (MPApiException e) {
		    // Tratamento de exceção da API do Mercado Pago
		    e.printStackTrace();
		    result.redirectTo(this).finalizarCompra();
		} catch (MPException e) {
		    // Tratamento de exceção genérica do Mercado Pago
	    	    e.printStackTrace();
	    	    result.redirectTo(this).finalizarCompra();
	        	}
	    }
}
