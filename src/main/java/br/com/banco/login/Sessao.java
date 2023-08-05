package br.com.banco.login;

import br.com.banco.conta.ContaModel;

public class Sessao {
	
	private String userName;
	private String token;
	private ContaModel conta;
	
	public ContaModel getConta() {
		return conta;
	}
	public void setConta(ContaModel conta) {
		this.conta = conta;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
