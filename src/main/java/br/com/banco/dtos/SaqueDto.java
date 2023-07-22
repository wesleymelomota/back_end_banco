package br.com.banco.dtos;

public class SaqueDto {
	
	private String nomeTitularConta;
	private Integer numeroConta;
	private Double valor;
	
	public String getNomeTitularConta() {
		return nomeTitularConta;
	}
	public void setNomeTitularConta(String nomeTitularConta) {
		this.nomeTitularConta = nomeTitularConta;
	}
	public Integer getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	
}
