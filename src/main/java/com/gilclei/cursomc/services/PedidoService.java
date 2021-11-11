package com.gilclei.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gilclei.cursomc.domain.ItemPedido;
import com.gilclei.cursomc.domain.PagamentoBoleto;
import com.gilclei.cursomc.domain.Pedido;
import com.gilclei.cursomc.domain.enums.EstadoPagamento;
import com.gilclei.cursomc.repositories.ItemPedidoRepository;
import com.gilclei.cursomc.repositories.PagamentoRepository;
import com.gilclei.cursomc.repositories.PedidoRepository;
import com.gilclei.cursomc.services.exeptions.ObjectNotFoundExeption;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private BoletoService boletoService; 
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExeption(
				"Objeto não encontrado! Id: " + id + ", Tipo: " 
					+ Pedido.class.getSimpleName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pgto = (PagamentoBoleto) obj.getPagamento();
			boletoService.preencherPagamentoBoloeto(pgto,obj.getInstante());
		}
		
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItemPedidos()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItemPedidos());		
		return obj;
	}

}
