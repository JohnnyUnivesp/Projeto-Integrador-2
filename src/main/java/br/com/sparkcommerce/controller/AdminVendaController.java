package br.com.sparkcommerce.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.dao.VendaDAO;
import br.com.sparkcommerce.interceptors.SomenteAdmin;
import br.com.sparkcommerce.model.Categoria;
import br.com.sparkcommerce.model.ItemVenda;
import br.com.sparkcommerce.model.Produto;
import br.com.sparkcommerce.model.Venda;

@Controller
@Path("vendas")
public class AdminVendaController {

    @Inject private VendaDAO vendaDao;
    @Inject private ProdutoDAO produtoDao;
    @Inject private Result result;
    @Inject HttpSession session;

    @Get("") @SomenteAdmin
    public void listarVendas(int page, String filtro, String status) {
    	if (page == 0) {page = 1;}
    	int limite = 20;
        List<Venda> vendas;
        int totalVendas;

        if ((filtro != null && !filtro.isEmpty()) || (status != null && !status.equalsIgnoreCase("todos"))) {
            vendas = vendaDao.buscarPorNomeOuPagamento(filtro, status, page, limite);
            totalVendas = vendaDao.totalVendasComFiltro(filtro, status);
        } else {
            vendas = vendaDao.buscarTodas(status, page, limite);
            totalVendas = vendaDao.totalVendas();
        }
        
        // Para cada venda, associe os produtos nos itens
        for (Venda venda : vendas) {
            for (ItemVenda item : venda.getItens()) {
                Produto produto = produtoDao.selectPorIdLong(item.getProdutoId());
                if (produto != null) {
                    item.setProduto(produto);  // Associa o produto ao item
                }
            }
        }

        result.include("vendas", vendas);
        result.include("paginaAtual", page);
        result.include("totalPaginas", (int) Math.ceil((double) totalVendas / limite));
        result.include("filtro", filtro);
        result.include("status", status);
    }
    
    @Post("atualizarStatus")
    public void atualizarStatus(String novoStatus, String codigoRastreio, Long vendaId) {
    	// Criar um objeto Venda temporário com o ID
    	Venda venda = vendaDao.buscarPorPaymentId(vendaId);
    	venda.setStatus(novoStatus);
    	if ("Enviado".equalsIgnoreCase(novoStatus)) {
    		venda.setCodigoRastreio(codigoRastreio);
    	}
    	vendaDao.update(venda);
    	result.redirectTo(this).listarVendas(1, null, null);
    }
    
    @Get("venda/{paymentId}")
    public void detalhesVenda(Long paymentId) {
        Venda venda = vendaDao.buscarPorPaymentId(paymentId);

        for (ItemVenda item : venda.getItens()) {
            Produto produto = produtoDao.selectPorIdLong(item.getProdutoId());
            Categoria categoria = produto.getCategoria(); // Acesso direto à categoria
            produto.setCategoria(categoria);
            item.setProduto(produto); // Associa o produto ao item para exibição
        }

        result.include("venda", venda);
    }
}
