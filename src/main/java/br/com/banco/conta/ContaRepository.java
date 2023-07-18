package br.com.banco.conta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<ContaModel, Integer>{
	
	ContaModel findBynumeroConta(Integer numeroConta);
	ContaModel findByidConta(Long id);
	ContaModel findBynomeResponsavel(String nome);
	boolean existsBynomeResponsavel(String nome);
}
