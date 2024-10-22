//package br.com.sparkcommerce.controller;
//
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//
//import com.mercadopago.client.payment.PaymentClient;
//import com.mercadopago.exceptions.MPApiException;
//import com.mercadopago.exceptions.MPException;
//import com.mercadopago.resources.payment.Payment;
//
//import br.com.caelum.vraptor.Controller;
//import br.com.caelum.vraptor.Path;
//import br.com.caelum.vraptor.Post;
//import br.com.caelum.vraptor.Result;
//
//@Controller
//@Path("notification")
//public class NotificationController {
//	
//	@Inject Result result;
//
//    @Post("")
//    public void receiveNotification(HttpServletRequest request) {
//        // Captura as informações enviadas pelo Mercado Pago
//        String topic = request.getParameter("topic");
//        String id = request.getParameter("id");
//        
////        request.getParameterMap().forEach((key, value) -> 
////        System.out.println("Chave: " + key + ", Valor: " + String.join(",", value))
////    );
//
//        // Retorna um status HTTP 200 OK para indicar sucesso
//        result.nothing();
//        result.redirectTo(ProdutosController.class).produtos(null);
//
//    }
//}