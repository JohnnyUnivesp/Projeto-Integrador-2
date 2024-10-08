package br.com.sparkcommerce.rn;

import java.text.NumberFormat;
import java.util.Locale;

public class FormataDeDoubleParaReais {
	
	private final Locale brasil = new Locale("pt","BR");

	public String executa(Double valor) {
		
		return NumberFormat.getCurrencyInstance(brasil).format(valor);
	}

}
