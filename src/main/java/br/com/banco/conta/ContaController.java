package br.com.banco.conta;

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
import org.springframework.web.bind.annotation.RestController;


import br.com.banco.dtos.ContaDto;
import br.com.banco.transferencia.TransferenciaModel;

@RestController
@RequestMapping("/banco")
@CrossOrigin(origins = "http://localhost:3000")
public class ContaController {
	
	@Autowired
	private ContaService service;
	
	@PostMapping("/conta")
	public ResponseEntity<ContaModel> criarConta(@RequestBody ContaDto contaDto) {
		ContaModel contaEntity = new ContaModel();
		BeanUtils.copyProperties(contaDto, contaEntity);
		service.salvar(contaEntity);
		return new ResponseEntity<>(contaEntity, HttpStatus.CREATED);
	}
	@GetMapping("/contas")
	public  ResponseEntity<List<ContaModel>> obterContas(){
		return new ResponseEntity<>(service.obterTodasContas(), HttpStatus.OK);
	}
	@GetMapping("/transferencia/{numeroConta}")
	public ResponseEntity<ContaModel> obterTransferenciasNumeroConta(@PathVariable Integer numeroConta){
		return new ResponseEntity<>(service.procurarContaPorNumero(numeroConta), HttpStatus.OK);
	}
}
