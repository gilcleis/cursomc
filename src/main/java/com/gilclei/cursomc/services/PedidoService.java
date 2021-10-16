package com.gilclei.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gilclei.cursomc.domain.Pedido;
import com.gilclei.cursomc.repositories.PedidoRepository;
import com.gilclei.cursomc.services.exeptions.ObjectNotFoundExeption;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExeption(
				"Objeto não encontrado! Id: " + id + ", Tipo: " 
					+ Pedido.class.getSimpleName()));
	}

}
