package com.gilclei.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gilclei.cursomc.domain.Categoria;
import com.gilclei.cursomc.dto.CategoriaDTO;
import com.gilclei.cursomc.repositories.CategoriaRepository;
import com.gilclei.cursomc.services.exeptions.DataIntegrityException;
import com.gilclei.cursomc.services.exeptions.ObjectNotFoundExeption;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExeption(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria objNew = find(obj.getId());
		updateData(objNew,obj);
		return repo.save(objNew);
	}

	private void updateData(Categoria objNew, Categoria obj) {
		objNew.setNome(obj.getNome());		
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer perPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, perPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(),objDTO.getNome());
	}
}
