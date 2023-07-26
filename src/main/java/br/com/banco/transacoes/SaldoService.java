package br.com.banco.transacoes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.banco.conta.ContaModel;

@Service
public class SaldoService {
	
	@Autowired
	private SaldoRepository repository;
	//obter saldo de uma determinada conta
	/*public SaldoModel obterSaldoConta(ContaModel conta) {
		return repository.findByconta(conta);
	}*/
	//salva qualquer tipo de manipulação com o saldo
	public void salvarAlteracao(SaldoModel novoSaldo) {
		repository.save(novoSaldo);
	}
	//retorna todos saldos
	public List<SaldoModel> obterSaldos(){
		return repository.findAll();
	}
	//obter um saldo especifico apenas pelo id
	public SaldoModel obterSaldoId(Long id){
		return repository.findByid(id);
	}
}
