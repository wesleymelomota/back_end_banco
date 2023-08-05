package br.com.banco.transacoes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.banco.conta.ContaModel;
import br.com.banco.transferencia.TransferenciaModel;

public interface TransactionRepository extends PagingAndSortingRepository<TransactionModel, Integer>{
	
	TransactionModel findBydataTransacao(LocalDateTime dataTransacao);
	TransactionModel findByconta(ContaModel conta);
	TransactionModel findByid(Long id);
	List<TransactionModel> findBycontaNumeroConta(Integer contaNumero);
	List<TransactionModel> findBydataTransacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
	List<TransactionModel> findBytransferenciaDataTransferenciaBetween(LocalDate dataInicio, LocalDate dataFim);
	List<TransactionModel> findBytransferenciaNomeOperadorTransferencia(String nome);
	
	 @Query("SELECT t FROM TransactionModel t " +
	            "WHERE t.transferencia.dataTransferencia BETWEEN :dataInicio AND :dataFim " +
	            "AND t.transferencia.nomeOperadorTransferencia = :nomeOperadorTransferencia")
	    List<TransactionModel> findBytransferenciaDataTransferenciaBetweenAndNomeOperadorTransferencia(
	            @Param("dataInicio") LocalDate dataInicio,
	            @Param("dataFim") LocalDate dataFim,
	            @Param("nomeOperadorTransferencia") String nomeOperadorTransferencia);
	 
	 
	 @Query("SELECT t FROM TransactionModel t " +
	            "WHERE t.conta.numeroConta = :contaNumero " +
	            "AND t.transferencia.dataTransferencia BETWEEN :dataInicio AND :dataFim " +
	            "AND t.transferencia.nomeOperadorTransferencia = :nomeOperadorTransferencia")
	    List<TransactionModel> findByContaNumeroAndTransferenciaDataTransferenciaBetweenAndNomeOperadorTransferencia(
	            @Param("contaNumero") Integer contaNumero,
	            @Param("dataInicio") LocalDate dataInicio,
	            @Param("dataFim") LocalDate dataFim,
	            @Param("nomeOperadorTransferencia") String nomeOperadorTransferencia);
	 
}
