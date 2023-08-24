package br.com.banco.emprestimo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.banco.conta.ContaModel;
import br.com.banco.enums.StatusEmprestimo;

@Entity
public class Emprestimo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Double valor;
	private LocalDate dataSolicitacao;
	@Enumerated(EnumType.STRING)
	private StatusEmprestimo status;
	@OneToOne
	private ContaModel conta;
	
	public Emprestimo() {}
	public Emprestimo( Double valor, LocalDate dataSolicitacao, StatusEmprestimo status, ContaModel conta) {
		super();
		this.setValor(valor);
		this.setDataSolicitacao(dataSolicitacao);
		this.setStatus(status);
		this.setConta(conta);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDate getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(LocalDate dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public StatusEmprestimo getStatus() {
		return status;
	}

	public void setStatus(StatusEmprestimo status) {
		this.status = status;
	}

	public ContaModel getConta() {
		return conta;
	}

	public void setConta(ContaModel conta) {
		this.conta = conta;
	}
	
	
	
	
}
