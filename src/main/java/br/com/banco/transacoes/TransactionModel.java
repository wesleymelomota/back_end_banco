package br.com.banco.transacoes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.hateoas.RepresentationModel;

import br.com.banco.conta.ContaModel;
import br.com.banco.transferencia.TransferenciaModel;
/*commit - mudando o formato da data do atributo dataTransacao*/
@Entity
public class TransactionModel extends RepresentationModel<TransactionModel>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dataTransacao;
	@ManyToOne
	private ContaModel conta;
	@OneToOne
	private TransferenciaModel transferencia;
	private Double saldo;
	
	
	public TransactionModel() {}
	public TransactionModel(LocalDate dataTransacao,ContaModel conta, TransferenciaModel transferencia,
			Double saldo) {
		this.dataTransacao = dataTransacao;
		this.conta = conta;
		this.transferencia = transferencia;
		this.saldo = saldo;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(LocalDate dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	public ContaModel getConta() {
		return conta;
	}
	public void setConta(ContaModel conta) {
		this.conta = conta;
	}
	public TransferenciaModel getTransferencia() {
		return transferencia;
	}
	public void setTransferencia(TransferenciaModel transferencia) {
		this.transferencia = transferencia;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public void adicionarFundoSaldo(Double valor) {
		saldo += valor;
	};
	public void retirarFundoSaldo(Double valor) {
		saldo -= valor;
	};
}
