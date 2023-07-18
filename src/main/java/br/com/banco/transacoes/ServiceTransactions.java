package br.com.banco.transacoes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.banco.conta.ContaModel;
import br.com.banco.conta.ContaRepository;
import br.com.banco.conta.ContaService;
import br.com.banco.transferencia.TransferenciaModel;
//commit mudando o formato da data
@Service
public class ServiceTransactions {
	/*serviço para registrar movimentações.
	 * a primeira função recebe uma transação e confere o tipo e encaminha a requisição para o tratamento adquado. 
	 * */
	@Autowired
	private TransactionRepository repository;
	@Autowired
	private ContaService serviceConta;
	@Autowired
	private SaldoService serviceSaldo;
	
	
	public void registrarTransacao(TransferenciaModel transferencia) {
		TransactionModel transacao = new TransactionModel();
		boolean existConta = serviceConta.checarSeContaExiste(transferencia.getNomeOperadorTransferencia());
		System.out.println();
		if(existConta) {
			ContaModel conta = serviceConta.procurarContaPorNome(transferencia.getContaId().getNomeResponsavel());
			transacao.setTransferencia(transferencia);
			transacao.setConta(conta);
			transacao.setSaldo(transferencia.getValor());
			transacao.setDataTransacao(LocalDate.now());	
		}
		
		if(transacao.getTransferencia().getTipo().equalsIgnoreCase("Saque")) {
			this.registrarSaque(transacao.getConta().getNumeroConta(), transacao.getTransferencia().getValor());
		}
		if(transacao.getTransferencia().getTipo().equalsIgnoreCase("deposito")) {
			transacao.setSaldo(transferencia.getValor());
			this.registrarDeposito(transacao.getConta().getNumeroConta(), transferencia.getValor());
		}
		if(transacao.getTransferencia().getTipo().equalsIgnoreCase("transferencia")) {
			this.registrarTransferencia(transacao.getConta().getNumeroConta(), 
					transacao.getTransferencia().getNomeOperadorTransferencia(), transacao.getTransferencia().getValor());
		}
		repository.save(transacao);
	}
	
	public boolean registrarSaque(Integer numeroConta, Double valorSaque) {
		ContaModel contaEntity = serviceConta.procurarContaPorNumero(numeroConta); 
		SaldoModel saldoModelConta = serviceSaldo.obterSaldoId(contaEntity.getSaldo().getId());
		if(saldoModelConta.getSaldo() > 0) {
			Double saldoAtual = contaEntity.getSaldo().getSaldo();
			saldoAtual -= valorSaque;
			saldoModelConta.setSaldo(saldoAtual);
			saldoModelConta.setConta(contaEntity);
			serviceSaldo.salvarAlteracao(saldoModelConta);
			return true;
		}else {
			return false;
		}
		
	};
	
	public void registrarDeposito(Integer numeroConta, Double valorDepositado) {
		ContaModel contaEntity = serviceConta.procurarContaPorNumero(numeroConta);
		SaldoModel saldo = serviceSaldo.obterSaldoId(contaEntity.getSaldo().getId());
		if(saldo != null) {
			saldo.depositarSaldo(valorDepositado);
			serviceSaldo.salvarAlteracao(saldo);
			contaEntity.setSaldo(saldo);
			serviceConta.salvar(contaEntity);
		}
		
	};
	
	public void registrarTransferencia(Integer numeroContaDebitada,String  nomeResponsavelContaCreditado,  Double valorTransferido) {
		this.registrarSaque(numeroContaDebitada, valorTransferido);
	
		ContaModel contaCreditada = serviceConta.procurarContaPorNome(nomeResponsavelContaCreditado);
		SaldoModel saldoCreditado = serviceSaldo.obterSaldoId(contaCreditada.getSaldo().getId());
		saldoCreditado.setSaldo(valorTransferido);
		saldoCreditado.setConta(contaCreditada);
		serviceConta.salvar(contaCreditada);
		serviceSaldo.salvarAlteracao(saldoCreditado);
		
	};
	
	public TransactionModel obterTransactionPorId(Long id){
		return repository.findByid(id);
	}
	
	@SuppressWarnings("unused")		//nome: filtrarTransactions
	public Iterable<TransactionModel> transactions(Integer numeroConta, String dataInicio, String dataFim, 
			String nomeOperador, Integer pagina){
		if(numeroConta != null) {
			
			return repository.findBycontaNumeroConta(numeroConta);
		}
		if(dataInicio != null && dataFim != null) {
			return repository.findBydataTransacaoBetween(LocalDateTime.parse(dataInicio),LocalDateTime.parse( dataFim));
		}
		if(nomeOperador != null) {
			return repository.findBytransferenciaNomeOperadorTransferencia(nomeOperador);
		}
		if(numeroConta != null && dataInicio != null && dataFim != null && nomeOperador != null){
			return repository.findByContaNumeroAndDataTransacaoBetweenAndNomeOperadorTransferencia(numeroConta,
					LocalDateTime.parse(dataInicio), LocalDateTime.parse(dataFim), nomeOperador);
		}
		if(dataInicio != null && dataFim != null && nomeOperador != null){
			return repository.findBydataTransacaoBetweenAndNomeOperadorTransferencia(
					LocalDateTime.parse(dataInicio), LocalDateTime.parse(dataFim), nomeOperador);
		}
		else {
			if(pagina != null) {
				Pageable page = PageRequest.of(pagina, 4);
				return repository.findAll(page);							
			}else {
				return repository.findAll();
			}
		}
	}
	public SaldoModel consultarSaldo(Integer numeroConta) {
		ContaModel conta = serviceConta.procurarContaPorNumero(numeroConta);
		return serviceSaldo.obterSaldoConta(conta);
	}
}
