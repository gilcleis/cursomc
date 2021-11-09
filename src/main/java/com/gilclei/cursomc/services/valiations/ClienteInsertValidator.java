package com.gilclei.cursomc.services.valiations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.gilclei.cursomc.domain.Cliente;
import com.gilclei.cursomc.domain.enums.TipoCliente;
import com.gilclei.cursomc.dto.ClienteNewDTO;
import com.gilclei.cursomc.repositories.ClienteRepository;
import com.gilclei.cursomc.resources.exeption.FieldMessage;
import com.gilclei.cursomc.services.valiations.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		// inclua os testes aqui, inserindo erros na lista
		System.out.println(TipoCliente.PESSOAFISICA.getCod());
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpf_cnpj())) {
			list.add(new FieldMessage("cpf_cnpj", "CPF inválido"));
		}

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCPF(objDto.getCpf_cnpj())) {
			list.add(new FieldMessage("cpf_cnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repository.findByEmail(objDto.getEmail());
		if (aux!= null) {
			list.add(new FieldMessage("email", "Email já exitente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
