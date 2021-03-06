package com.alex.projeto_correios;

import com.alex.projeto_correios.domain.*;
import com.alex.projeto_correios.domain.enums.Perfil;
import com.alex.projeto_correios.repositories.*;
import com.alex.projeto_correios.domain.enums.Status;
import com.alex.projeto_correios.utils.CodeGenerator;
import com.alex.projeto_correios.utils.EncomendaIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;


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

	@Autowired
	public CodeGenerator codeGenerator;

	@Autowired
	private BCryptPasswordEncoder pe;


	public static void main(String[] args) {
		SpringApplication.run(ProjetoCorreiosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", new Locale("pt-BR", "BR"));

		Estado sp = new Estado(null, "São Paulo");
		Estado mg = new Estado(null, "Minas Gerais");

		Cidade cruzeiro = new Cidade(null, "Cruzeiro");
		Cidade campinas = new Cidade(null, "Campinas");
		Cidade itajuba = new Cidade(null, "Itajubá");

		sp.setCidades(Arrays.asList(cruzeiro, campinas));
		mg.setCidades(Arrays.asList(itajuba));

		Cliente alex = new Cliente(null, "Alex Silva Rodrigues", "alex.rodrigues23997@gmail.com", pe.encode("123"));
		alex.addPerfil(Perfil.ADMIN);

		Cliente bruna = new Cliente(null, "Bruna", "bruna@gmail.com", pe.encode("123"));

		Endereco endereco1 = new Endereco(null, alex, mg.getNome(), itajuba.getNome(), "Centro", "Rua Nova", "399");
		alex.setEnderecos(Arrays.asList(endereco1));

		Encomenda encomenda1 = new Encomenda(
				codeGenerator.newCode(),
				endereco1,
				sdf.parse("22-01-2021"),
				sdf.parse("30-01-2021"),
				Status.ENTREGUE);

		Encomenda encomenda2 = new Encomenda(
				codeGenerator.newCode(),
				endereco1,
				sdf.parse("09-03-2022"),
				sdf.parse("15-03-2022"),
				Status.PENDENTE_DE_ENVIO);

		Encomenda encomenda3 = new Encomenda(
				codeGenerator.newCode(),
				endereco1,
				sdf.parse("12-03-2022"),
				sdf.parse("17-03-2022"),
				Status.ENVIADO);

		encomenda1.setCliente(alex);
		encomenda2.setCliente(alex);


		estadoRepository.saveAll(Arrays.asList(sp, mg));
		cidadeRepository.saveAll(Arrays.asList(itajuba, campinas, cruzeiro));
		clienteRepository.saveAll(Arrays.asList(alex, bruna));
		enderecoRepository.saveAll(Arrays.asList(endereco1));
		encomendaRepository.saveAll(Arrays.asList(encomenda1, encomenda2, encomenda3));
	}
}
