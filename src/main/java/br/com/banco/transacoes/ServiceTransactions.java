package br.com.banco.transacoes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.banco.conta.ContaModel;
import br.com.banco.conta.ContaRepository;
import br.com.banco.conta.ContaService;
import br.com.banco.dtos.DepositoDto;
import br.com.banco.transferencia.TransferenciaModel;
import br.com.banco.transferencia.TransferenciaService;
/**/
@Service
public class ServiceTransactions {
	
	@Autowired
	private TransactionRepository repository;
	@Autowired
	private ContaService serviceConta;
	@Autowired
	private SaldoService serviceSaldo;
	@Autowired
	private TransferenciaService serviceTransferencia;
	
	
	public boolean registrarSaque(TransactionModel transaction) {
		
		ContaModel contaEntity = serviceConta.procurarContaPorNome(transaction.getTransferencia().getNomeOperadorTransferencia()); 
		SaldoModel saldoModelConta = serviceSaldo.obterSaldoId(contaEntity.getSaldo().getId());
		if(saldoModelConta.getSaldo() > 0) {
			Double saldoAtual = contaEntity.getSaldo().getSaldo();
			saldoAtual -= transaction.getTransferencia().getValor();
			saldoModelConta.setSaldo(saldoAtual);
			saldoModelConta.setConta(contaEntity);
			serviceSaldo.salvarAlteracao(saldoModelConta);
			repository.save(transaction);
			contaEntity.getTransacoes().add(transaction);
			serviceConta.salvar(contaEntity);
			return true;
		}else {
			return false;
		}
		
	};
	public boolean registrarSaque(Integer numeroConta, Double valor) {
		ContaModel contaEntity = serviceConta.procurarContaPorNumero(numeroConta); 
		SaldoModel saldoModelConta = serviceSaldo.obterSaldoId(contaEntity.getSaldo().getId());
		if(saldoModelConta.getSaldo() > 0) {
			Double saldoAtual = contaEntity.getSaldo().getSaldo();
			saldoAtual -= valor;
			saldoModelConta.setSaldo(saldoAtual);
			saldoModelConta.setConta(contaEntity);
			serviceSaldo.salvarAlteracao(saldoModelConta);
			return true;
		}else {
			return false;
		}
		
	};/**/
	
	public TransactionModel registrarDepositoEntrada(DepositoDto deposito) {
		
		TransactionModel transaction = new TransactionModel();
		TransferenciaModel transferencia = new TransferenciaModel();
		ContaModel contaEntity = serviceConta.procurarContaPorNome(deposito.getNomeResponsavelConta());
		SaldoModel saldo = serviceSaldo.obterSaldoId(contaEntity.getSaldo().getId());
		
		if(saldo != null && contaEntity != null) {
			transferencia.setContaId(contaEntity);
			transferencia.setNomeOperadorTransferencia(deposito.getNomeResponsavelConta());
			transferencia.setTipo("DEPOSITO");
			transferencia.setValor(deposito.getValor());
			
			Double saldoAtual = saldo.getSaldo();
			saldoAtual += deposito.getValor();
			transaction.setConta(contaEntity);
			transaction.setSaldo(saldoAtual);
			transaction.setDataTransacao(LocalDate.now());
			transaction.setTransferencia(serviceTransferencia.registrarTransferencia(transferencia));
			saldo.setSaldo(saldoAtual);
			saldo.setConta(contaEntity);
			serviceSaldo.salvarAlteracao(saldo);

			contaEntity.getTransacoes().add(repository.save(transaction));
			contaEntity.setSaldo(saldo);
			serviceConta.salvar(contaEntity);
			return transaction;
			
		}else {
			return null;
		}
		
	};
	
	public void registrarTransferencia(TransferenciaModel transferencia) {
			TransactionModel transaction = new TransactionModel();
			ContaModel contaCreditada = serviceConta.procurarContaPorNome(transferencia.getNomeOperadorTransferencia());
			SaldoModel saldoCreditado = serviceSaldo.obterSaldoId(contaCreditada.getSaldo().getId());
			if(transferencia.getTipo().equalsIgnoreCase("transferencia-entrada")) {
				Double saldoAtual = saldoCreditado.getSaldo();
				saldoAtual += transferencia.getValor();
				transaction.setConta(contaCreditada);
				transaction.setTransferencia(transferencia);
				transaction.setSaldo(saldoAtual);
				transaction.setDataTransacao(LocalDate.now());
				saldoCreditado.setSaldo(saldoAtual);
				saldoCreditado.setConta(contaCreditada);
				contaCreditada.getTransacoes().add(repository.save(transaction));
				serviceSaldo.salvarAlteracao(saldoCreditado);
				serviceConta.salvar(contaCreditada);
				
			}else if(transferencia.getTipo().equalsIgnoreCase("transferencia-saida")) {
				this.registrarTransferenciaSaida(transferencia);
			}
			
		};
	public void registrarTransferenciaSaida(TransferenciaModel transferencia) {
		TransactionModel transaction = new TransactionModel();
		ContaModel contaCreditada = serviceConta.procurarContaPorNome(transferencia.getNomeOperadorTransferencia());
		SaldoModel saldoCreditado = serviceSaldo.obterSaldoId(contaCreditada.getSaldo().getId());
		Double saldoAtual = saldoCreditado.getSaldo();
		saldoAtual += transferencia.getValor();
		
		transferencia.setTipo("transfencia-entrada");
		transaction.setConta(contaCreditada);
		transaction.setTransferencia(transferencia);
		transaction.setSaldo(saldoAtual);
		transaction.setDataTransacao(LocalDate.now());
		contaCreditada.getTransacoes().add(repository.save(transaction));
		saldoCreditado.setSaldo(saldoAtual);
		saldoCreditado.setConta(contaCreditada);
		serviceSaldo.salvarAlteracao(saldoCreditado);
		serviceConta.salvar(contaCreditada);
		
	};
	
	public TransactionModel obterTransactionPorId(Long id){
		return repository.findByid(id);
	}
	
	@SuppressWarnings("unused")		
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
