package br.com.banco.transferencia;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.banco.conta.ContaModel;

@Entity
@Table(name ="transferencia_entity")
public class TransferenciaModel {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double valor;
	private String tipo;
	private String nomeOperadorTransferencia;
	private LocalDate dataTransferencia;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private ContaModel conta;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getDataTransferencia() {
		return dataTransferencia;
	}
	public void setDataTransferencia(LocalDate dataTransferencia) {
		this.dataTransferencia = dataTransferencia;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNomeOperadorTransferencia() {
		return nomeOperadorTransferencia;
	}
	public void setNomeOperadorTransferencia(String nomeOperadorTransferencia) {
		this.nomeOperadorTransferencia = nomeOperadorTransferencia;
	}
	public ContaModel getConta() {
		return conta;
	}
	public void setConta(ContaModel conta) {
		this.conta = conta;
	}
	
	
	
}
