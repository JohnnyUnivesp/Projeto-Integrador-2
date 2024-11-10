package br.com.sparkcommerce.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.*;

import br.com.olimposistema.aipa.model.Model;

@Entity
public class Venda extends Model {

	@Column(name = "payment_id", nullable = false, unique = true) // Define o paymentId como único
    private Long paymentId;
	
    @ManyToOne
    private Produto produto;
	private String compradorNome;
    private String compradorTelefone;
    private String enderecoComprador;
    private LocalDateTime dataPagamento;

    @ElementCollection
    private List<ItemVenda> itens;
    
    @Column(nullable = false)
    private String status = "Em processamento"; // Define status inicial

    private String codigoRastreio;
    
    public String getDataPagamentoFormatada() {
        if (dataPagamento != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return dataPagamento.format(formatter);
        }
        return "";
    }
    
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCodigoRastreio() {
		return codigoRastreio;
	}
	public void setCodigoRastreio(String codigoRastreio) {
		this.codigoRastreio = codigoRastreio;
	}
	public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public String getCompradorNome() { return compradorNome; }
    public void setCompradorNome(String compradorNome) { this.compradorNome = compradorNome; }

    public String getCompradorTelefone() { return compradorTelefone; }
    public void setCompradorTelefone(String compradorTelefone) { this.compradorTelefone = compradorTelefone; }

    public String getEnderecoComprador() { return enderecoComprador; }
    public void setEnderecoComprador(String enderecoComprador) { this.enderecoComprador = enderecoComprador; }

    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }

    @Override
    public String toString() {
        return "Venda{" +
        		"paymentId=" + paymentId +
                ", compradorNome='" + compradorNome + '\'' +
                ", compradorTelefone='" + compradorTelefone + '\'' +
                ", enderecoComprador='" + enderecoComprador + '\'' +
                ", dataPagamento=" + dataPagamento +
                ", itens=" + itens +
                '}';
    }
}
