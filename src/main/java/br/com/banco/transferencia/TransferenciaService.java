package br.com.banco.transferencia;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.banco.conta.ContaRepository;

@Service
public class TransferenciaService {
	
	@Autowired
	private TransferenciaRepository repository;
	@Autowired
	ContaRepository contaRepository;
	
	public void registrarTransferencia(TransferenciaModel transferencia) {
		repository.save(transferencia);
		
	}
	
	public List<TransferenciaModel> transferencias(){
		return repository.findAll();
	}
	
	public List<TransferenciaModel> obterTransferenciaPorNumeroConta(Integer numeroConta){
		return null;
	}
}
