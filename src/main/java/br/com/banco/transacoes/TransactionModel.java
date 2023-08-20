package br.com.banco.transacoes;


import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.banco.conta.ContaModel;
import br.com.banco.transferencia.TransferenciaModel;
/**/
@Table(name =  "transaction")
@Entity
public class TransactionModel extends RepresentationModel<TransactionModel>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime dataTransacao;
	@JsonIgnore
	@ManyToOne
	private ContaModel conta;
	@OneToOne(cascade = CascadeType.ALL)
	private TransferenciaModel transferencia;
	private Double saldo;
	
	
	public TransactionModel() {}
	public TransactionModel(LocalDateTime dataTransacao,ContaModel conta, TransferenciaModel transferencia,
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
	public LocalDateTime getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(LocalDateTime dataTransacao) {
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
