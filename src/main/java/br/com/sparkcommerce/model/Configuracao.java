package br.com.sparkcommerce.model;

import javax.persistence.Entity;

import br.com.olimposistema.aipa.model.Model;

@Entity
public class Configuracao extends Model {

    private String nome = "tempoExibicaoBanner";
    private String valor; // Valor da configuração, que pode ser convertido conforme necessário

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
	public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
    
    // Método para obter o tempo de exibição do banner como int
    public int getTempoExibicaoBanner() {
        try {
            return Integer.parseInt(this.valor);
        } catch (NumberFormatException e) {
            // Define um valor padrão caso não consiga converter
            return 5; // Tempo padrão de 5 segundos
        }
    }
    // Método para definir o tempo de exibição do banner
    public void setTempoExibicaoBanner(int tempo) {
        this.valor = String.valueOf(tempo); // Converte o int para String e armazena no campo valor
    }
}
