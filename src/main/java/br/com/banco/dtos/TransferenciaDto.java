package br.com.banco.dtos;



import br.com.banco.conta.ContaModel;

public class TransferenciaDto {
	
	private Double valor;
	private String tipo;
	private String nomeOperadorTransferencia;
	private ContaModel contaId;
	
	
	
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
	public ContaModel getContaId() {
		return contaId;
	}
	public void setContaId(ContaModel contaId) {
		this.contaId = contaId;
	}
	
	
	
}
