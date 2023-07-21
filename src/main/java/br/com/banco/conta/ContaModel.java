package br.com.banco.conta;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.banco.transacoes.SaldoModel;
import br.com.banco.transacoes.TransactionModel;
import br.com.banco.transferencia.TransferenciaModel;
import lombok.Data;

@Entity
@Table(name = "conta_entity")
public class ContaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idConta;
	@Column(unique = true)
	private String nomeResponsavel;
	@Column(unique = true)
	private Integer numeroConta;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	private List<TransactionModel> transacoes;
	@OneToOne(fetch = FetchType.EAGER)
	private SaldoModel saldo;
	
	public ContaModel(){}
	
	public ContaModel(String nomeResponsavel, Integer numeroConta) {
		this.nomeResponsavel = nomeResponsavel;
		this.numeroConta = numeroConta;
	}
	
	
	
	public List<TransactionModel> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<TransactionModel> transacoes) {
		this.transacoes = transacoes;
	}

	public SaldoModel getSaldo() {
		return saldo;
	}

	public void setSaldo(SaldoModel saldo) {
		this.saldo = saldo;
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}
	public Long getIdConta() {
		return idConta;
	}
	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	
}
