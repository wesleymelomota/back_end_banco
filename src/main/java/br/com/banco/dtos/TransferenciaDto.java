package br.com.banco.dtos;



import java.time.LocalDate;

import br.com.banco.conta.ContaModel;

public class TransferenciaDto {
	
	private Double valor;
	private String tipo;
	private String nomeOperadorTransferencia;
	private LocalDate dataTransferencia;
	private ContaModel conta;
	
	
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
