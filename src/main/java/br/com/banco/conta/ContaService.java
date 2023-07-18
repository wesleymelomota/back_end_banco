package br.com.banco.conta;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.banco.transacoes.SaldoModel;
import br.com.banco.transacoes.SaldoService;

@Service
public class ContaService {
	@Autowired
	private ContaRepository repository;
	@Autowired
	private SaldoService serviceSaldo;
	
	public ContaModel salvar(ContaModel conta) {
		if(conta.getSaldo() == null) {			
			SaldoModel saldo = new SaldoModel();
			saldo.setSaldo(0.0);
			conta.setSaldo(saldo);
			serviceSaldo.salvarAlteracao(saldo);
		}
		Random geradorNumeroAleatorioConta = new Random();
	
		conta.setNumeroConta(geradorNumeroAleatorioConta.nextInt(1000000, 9999999));
		repository.save(conta);
		return conta;
	}
	public List<ContaModel> obterTodasContas(){
		return repository.findAll();
	}
	
	public ContaModel procurarContaPorNumero(Integer numeroConta) {
		return repository.findBynumeroConta(numeroConta);
	}
	public ContaModel procurarContaPorNome(String nome) {
		return repository.findBynomeResponsavel(nome);
	}
	public boolean checarSeContaExiste(String nome) {
		return repository.existsBynomeResponsavel(nome);
	}
	
}
