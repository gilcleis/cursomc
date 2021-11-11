package com.gilclei.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.gilclei.cursomc.domain.PagamentoBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamentoBoloeto(PagamentoBoleto pagamentoBoleto,Date instanteDoPedido) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(instanteDoPedido);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		pagamentoBoleto.setDataVencimento(calendar.getTime());
	}

}
