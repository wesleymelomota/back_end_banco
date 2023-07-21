package br.com.banco.transferencia;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.banco.conta.ContaModel;
import br.com.banco.conta.ContaService;
import br.com.banco.dtos.TransferenciaDto;
import br.com.banco.transacoes.ServiceTransactions;


@RestController
@RequestMapping("/banco")
@CrossOrigin(origins = "http://localhost:3000")
public class TransferenciaController {
	
	@Autowired
	private TransferenciaService service;
	@Autowired
	private ServiceTransactions transactions;
	
	@PostMapping("/transferencia")
	public ResponseEntity<TransferenciaModel> transferir(@RequestBody TransferenciaDto transferenciaDto) {
		TransferenciaModel transferencia = new TransferenciaModel();
		
		BeanUtils.copyProperties(transferenciaDto, transferencia);
		service.registrarTransferencia(transferencia);
		transactions.registrarTransferencia(transferencia);
		return new ResponseEntity<>(transferencia, HttpStatus.CREATED);
	}
	@GetMapping("/transferencias")
	public ResponseEntity<List<TransferenciaModel>> transferencias(){
		return new ResponseEntity<>(service.transferencias(), HttpStatus.OK);
	}
}
