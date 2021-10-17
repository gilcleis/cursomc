package com.gilclei.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gilclei.cursomc.domain.Categoria;
import com.gilclei.cursomc.domain.Cidade;
import com.gilclei.cursomc.domain.Cliente;
import com.gilclei.cursomc.domain.Endereco;
import com.gilclei.cursomc.domain.Estado;
import com.gilclei.cursomc.domain.ItemPedido;
import com.gilclei.cursomc.domain.Pagamento;
import com.gilclei.cursomc.domain.PagamentoBoleto;
import com.gilclei.cursomc.domain.PagamentoCartao;
import com.gilclei.cursomc.domain.Pedido;
import com.gilclei.cursomc.domain.Produto;
import com.gilclei.cursomc.domain.enums.EstadoPagamento;
import com.gilclei.cursomc.domain.enums.TipoCliente;
import com.gilclei.cursomc.repositories.CategoriaRepository;
import com.gilclei.cursomc.repositories.CidadeRepository;
import com.gilclei.cursomc.repositories.ClienteRepository;
import com.gilclei.cursomc.repositories.EnderecoRepository;
import com.gilclei.cursomc.repositories.EstadoRepository;
import com.gilclei.cursomc.repositories.ItemPedidoRepository;
import com.gilclei.cursomc.repositories.PagamentoRepository;
import com.gilclei.cursomc.repositories.PedidoRepository;
import com.gilclei.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository ;
	

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");			
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));	
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "333333333333", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("8000038850","88633565566"));
		
		Endereco e1 = new Endereco(null, "Rua das Flores", "300", "Apto 303", "jardim", "3825545456", cli1, c1);
		Endereco e2 = new Endereco(null, "Av matos", "105", "sala 800", "centro", "3825545456", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1,e2)); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItemPedidos().addAll(Arrays.asList(ip1, ip2));
		ped2.getItemPedidos().addAll(Arrays.asList(ip3));

		p1.getItemPedidos().addAll(Arrays.asList(ip1));
		p2.getItemPedidos().addAll(Arrays.asList(ip3));
		p3.getItemPedidos().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}

}
