package br.com.banco.dtos;

public class DepositoDto {
	
	private String nomeResponsavelConta;
	private Integer numeroConta;
	private Double valor;
	
	
	
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getNomeResponsavelConta() {
		return nomeResponsavelConta;
	}
	public void setNomeResponsavelConta(String nomeResponsavelConta) {
		this.nomeResponsavelConta = nomeResponsavelConta;
	}
	public Integer getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}
	
	
}
