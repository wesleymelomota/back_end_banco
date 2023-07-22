package br.com.banco.transacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.dtos.DepositoDto;
import br.com.banco.dtos.SaqueDto;

@RestController
@RequestMapping("/banco")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
	
	@Autowired
	private ServiceTransactions service;
	
	@PostMapping("/transaction/deposito")
	public ResponseEntity<TransactionModel> depositarSaldo(@RequestBody DepositoDto deposito){
		return new ResponseEntity<>(service.registrarDepositoEntrada(deposito) ,HttpStatus.CREATED); 
	}
	@PostMapping("/saque")
	public ResponseEntity<TransactionModel> sacar(@RequestBody SaqueDto saqueDto){
		return new ResponseEntity<>(service.registrarSaque(saqueDto.getNomeTitularConta(), saqueDto.getNumeroConta(),
				saqueDto.getValor()) ,HttpStatus.CREATED);
	}
	
	@GetMapping("/transactions")
	public ResponseEntity<Iterable<TransactionModel>> obterTransactions(
			@RequestParam(required = false) Integer numeroConta,
			@RequestParam(required = false) String dataInicio, 
			@RequestParam(required = false) String dataFim,
			@RequestParam(required = false) String nomeOperador,
			@RequestParam(required = false) Integer pagina){
		Iterable<TransactionModel> transactionsList = service.transactions(numeroConta, dataInicio, dataFim, nomeOperador, pagina);
		if(service.transactions(numeroConta, dataInicio, dataFim, nomeOperador, pagina) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			for(TransactionModel transaction: transactionsList) {
				transaction.add(Link.of("http://localhost:8080/banco/transaction/".concat(transaction.getId().toString())));
			}
			return new ResponseEntity<>(service.transactions(numeroConta, dataInicio, dataFim, nomeOperador, pagina), HttpStatus.OK);
		}
	}
	@GetMapping("/transaction/{id}")
	public ResponseEntity<TransactionModel> obterTransactionsPorId(@PathVariable String id){
		TransactionModel transaction = service.obterTransactionPorId(Long.parseLong(id));
		try {
			transaction.add(Link.of("http://localhost:8080/banco/transactions"));
			return new ResponseEntity<>(transaction, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/saldo/{numeroConta}")
	public ResponseEntity< SaldoModel> consultarSaldo(@PathVariable Integer numeroConta) {
		try {
			return new ResponseEntity<>( service.consultarSaldo(numeroConta), HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	}
	

}
