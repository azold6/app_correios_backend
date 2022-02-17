package com.alex.projeto_correios;

import domain.*;
import domain.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import repositories.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class ProjetoCorreiosApplication implements CommandLineRunner {

	@Autowired
	public CidadeRepository cidadeRepository;

	@Autowired
	public EstadoRepository estadoRepository;

	@Autowired
	public ClienteRepository clienteRepository;

	@Autowired
	public EnderecoRepository enderecoRepository;

	@Autowired
	public EncomendaRepository encomendaRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoCorreiosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		Estado sp = new Estado(null, "São Paulo");
		Estado mg = new Estado(null, "Minas Gerais");

		Cidade cruzeiro = new Cidade(null, "Cruzeiro", sp);
		Cidade campinas = new Cidade(null, "Campinas", sp);
		Cidade itajuba = new Cidade(null, "Itajubá", mg);

		sp.setCidades(Arrays.asList(cruzeiro, campinas));
		mg.setCidades(Arrays.asList(itajuba));

		Cliente alex = new Cliente(null, "Alex Silva Rodrigues", "alex@gmail.com");

		Endereco endereco1 = new Endereco(null, alex, mg, itajuba, "Centro", "Rua Nova", "399");
		alex.setEnderecos(Arrays.asList(endereco1));

		Encomenda encomenda1 = new Encomenda(
				null,
				endereco1,
				sdf.parse("22-01-2021"),
				sdf.parse("30-01-2021"),
				"BRX674032",
				Status.ENTREGUE);

		estadoRepository.saveAll(Arrays.asList(sp, mg));
		cidadeRepository.saveAll(Arrays.asList(itajuba, campinas, cruzeiro));
		clienteRepository.saveAll(Arrays.asList(alex));
		enderecoRepository.saveAll(Arrays.asList(endereco1));
		encomendaRepository.saveAll(Arrays.asList(encomenda1));

	}
}
