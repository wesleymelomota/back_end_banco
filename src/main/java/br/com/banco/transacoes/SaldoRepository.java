package br.com.banco.transacoes;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.banco.conta.ContaModel;

public interface SaldoRepository extends JpaRepository<SaldoModel, Long>{
	
	SaldoModel findByconta(ContaModel conta);
	SaldoModel findByid(Long id);
}
