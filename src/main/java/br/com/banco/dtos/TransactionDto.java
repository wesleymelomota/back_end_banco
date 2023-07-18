package br.com.banco.dtos;

import java.util.List;

import br.com.banco.conta.ContaModel;
import br.com.banco.transferencia.TransferenciaModel;

public class TransactionDto {
	
	private ContaModel conta;
	private List<TransferenciaModel> transferencias;
	
	public ContaModel getConta() {
		return conta;
	}
	public void setConta(ContaModel conta) {
		this.conta = conta;
	}
	public List<TransferenciaModel> getTransferencias() {
		return transferencias;
	}
	public void setTransferencias(List<TransferenciaModel> transferencias) {
		this.transferencias = transferencias;
	}
	 
}
