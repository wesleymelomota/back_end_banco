package br.com.banco.transacoes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.banco.conta.ContaModel;

@Entity
public class SaldoModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double saldo;
	@JsonIgnore
	@OneToOne
	private ContaModel conta;
	
	public void depositarSaldo(Double saldo) {
		this.saldo += saldo;
	}
	public void debitarSaldo(Double credito) {
		this.saldo += saldo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public ContaModel getConta() {
		return conta;
	}
	public void setConta(ContaModel conta) {
		this.conta = conta;
	}
	
	
}
