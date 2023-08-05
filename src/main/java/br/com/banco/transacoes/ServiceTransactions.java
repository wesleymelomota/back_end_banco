package br.com.banco.transacoes;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.banco.conta.ContaModel;
import br.com.banco.conta.ContaService;
import br.com.banco.dtos.DepositoDto;
import br.com.banco.dtos.TransferenciaDto;
import br.com.banco.transferencia.TransferenciaModel;
import br.com.banco.transferencia.TransferenciaService;

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
	
	
	public ContaModel registrarSaque(String nomeResponsavel, Integer numeroConta, Double valor) {
		TransactionModel transaction = new TransactionModel();
		TransferenciaModel transferencia = new TransferenciaModel();
		ContaModel contaEntity = serviceConta.procurarContaPorNome(nomeResponsavel); 
		SaldoModel saldoModelConta = serviceSaldo.obterSaldoId(contaEntity.getSaldo().getId());
		if(saldoModelConta.getSaldo() > 0) {
			Double saldoAtual = contaEntity.getSaldo().getSaldo();
			
			transferencia.setNomeOperadorTransferencia(nomeResponsavel);
			transferencia.setTipo("SAQUE");
			transferencia.setValor(-valor);
			transferencia.setDataTransferencia(LocalDate.now());
			serviceTransferencia.registrarTransferencia(transferencia);
			
			transaction.setTransferencia(transferencia);
			transaction.setConta(contaEntity);
			transaction.setDataTransacao(LocalDateTime.now());
			saldoAtual -= valor;
			transaction.setSaldo(saldoAtual);
			repository.save(transaction);
			saldoModelConta.setSaldo(saldoAtual);
			
			serviceSaldo.salvarAlteracao(saldoModelConta);
			contaEntity.getTransacoes().add(transaction);
			serviceConta.salvar(contaEntity);
			return contaEntity;
		}else {
			return null;
		}
		
	};

	public ContaModel registrarDepositoEntrada(DepositoDto deposito) {
		
		TransactionModel transaction = new TransactionModel();
		TransferenciaModel transferencia = new TransferenciaModel();
		ContaModel contaEntity = serviceConta.procurarContaPorNome(deposito.getNomeResponsavelConta());
		SaldoModel saldo = serviceSaldo.obterSaldoId(contaEntity.getSaldo().getId());
		
		if(saldo != null && contaEntity != null) {
			
			transferencia.setNomeOperadorTransferencia(deposito.getNomeResponsavelConta());
			transferencia.setTipo("DEPOSITO");
			transferencia.setValor(deposito.getValor());
			transferencia.setDataTransferencia(LocalDate.now());
			
			Double saldoAtual = saldo.getSaldo();
			saldoAtual += deposito.getValor();
			transaction.setConta(contaEntity);
			transaction.setSaldo(saldoAtual);
			transaction.setDataTransacao(LocalDateTime.now());//
			transaction.setTransferencia(serviceTransferencia.registrarTransferencia(transferencia));
			saldo.setSaldo(saldoAtual);
			
			serviceSaldo.salvarAlteracao(saldo);

			contaEntity.getTransacoes().add(repository.save(transaction));
			contaEntity.setSaldo(saldo);

			return serviceConta.salvar(contaEntity);
			
		}else {
			return null;
		}
		
	};
	
	public ContaModel registrarTransferencia(TransferenciaDto transferenciaDto) {
			TransferenciaModel transferencia = new TransferenciaModel();
			BeanUtils.copyProperties(transferenciaDto, transferencia);
			serviceTransferencia.registrarTransferencia(transferencia);
		
			TransactionModel transaction = new TransactionModel();
			ContaModel conta = serviceConta.procurarContaPorNome(transferencia.getNomeOperadorTransferencia());
			SaldoModel saldoContaCreditado = serviceSaldo.obterSaldoId(conta.getSaldo().getId());
			
			if(transferencia.getTipo().equalsIgnoreCase("transferencia-entrada")) {
				Double saldoAtual = saldoContaCreditado.getSaldo();
				saldoAtual += transferencia.getValor();
				transaction.setConta(conta);
				transaction.setTransferencia(transferencia);
				transaction.setSaldo(saldoAtual);
				transaction.setDataTransacao(LocalDateTime.now());
				saldoContaCreditado.setSaldo(saldoAtual);
			
				conta.getTransacoes().add(repository.save(transaction));
				serviceSaldo.salvarAlteracao(saldoContaCreditado);
				serviceConta.salvar(conta);
				return conta;
			}else if(transferencia.getTipo().equalsIgnoreCase("transferencia-saida")) {
				
				transferencia.setValor(-transferenciaDto.getValor());
				Double saldoAtual = saldoContaCreditado.getSaldo();
				saldoAtual -= transferenciaDto.getValor();
				transaction.setConta(conta);
				transaction.setTransferencia(transferencia);
				transaction.setSaldo(saldoAtual);
				transaction.setDataTransacao(LocalDateTime.now());
				saldoContaCreditado.setSaldo(saldoAtual);
			
				conta.getTransacoes().add(repository.save(transaction));
				serviceSaldo.salvarAlteracao(saldoContaCreditado);
			
				 this.registrarTransferenciaSaida(transferenciaDto);
				 return serviceConta.salvar(conta);
			}else {
				return null;
			}
			
		};
	public void registrarTransferenciaSaida(TransferenciaDto transferenciaDto) {
		TransferenciaModel transferencia = new TransferenciaModel();
		BeanUtils.copyProperties(transferenciaDto, transferencia);
		transferencia.setTipo("transfencia-entrada");
		serviceTransferencia.registrarTransferencia(transferencia);
		TransactionModel transaction = new TransactionModel();
		ContaModel contaCreditada = serviceConta.procurarContaPorNome(transferenciaDto.getConta().getNomeResponsavel());
		SaldoModel saldoContaCreditado = serviceSaldo.obterSaldoId(contaCreditada.getSaldo().getId());
		Double saldoAtual = saldoContaCreditado.getSaldo();
		saldoAtual += transferencia.getValor();
		
		transaction.setConta(contaCreditada);
		transaction.setTransferencia(transferencia);
		transaction.setSaldo(saldoAtual);
		transaction.setDataTransacao(LocalDateTime.now());
		contaCreditada.getTransacoes().add(repository.save(transaction));
		saldoContaCreditado.setSaldo(saldoAtual);
		
		serviceSaldo.salvarAlteracao(saldoContaCreditado);
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
			return repository.findBytransferenciaDataTransferenciaBetween(LocalDate.parse(dataInicio),LocalDate.parse( dataFim));
		} 
		if(nomeOperador != null) {
			return repository.findBytransferenciaNomeOperadorTransferencia(nomeOperador);
		}
		if(numeroConta != null && dataInicio != null && dataFim != null && nomeOperador != null){
			return repository.findByContaNumeroAndTransferenciaDataTransferenciaBetweenAndNomeOperadorTransferencia(numeroConta,
					LocalDate.parse(dataInicio),LocalDate.parse( dataFim), nomeOperador);
		}
		if(dataInicio != null && dataFim != null && nomeOperador != null){
			
			return repository.findBytransferenciaDataTransferenciaBetweenAndNomeOperadorTransferencia(
					LocalDate.parse(dataInicio),LocalDate.parse( dataFim), nomeOperador);
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
		return conta.getSaldo();
	}
}
