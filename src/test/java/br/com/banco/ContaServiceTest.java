package br.com.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.banco.conta.ContaModel;
import br.com.banco.conta.ContaRepository;
import br.com.banco.conta.ContaService;
import br.com.banco.transacoes.SaldoModel;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {
	
	@InjectMocks
	ContaService serviceConta;
	
	@Mock
	ContaRepository contaRepository;
	
	ContaModel conta;
	SaldoModel saldo;
	
	@BeforeEach
	public void setUp() {
		saldo = new SaldoModel(0.0);
		conta = new ContaModel("wesley", 1234567);
		conta.setSaldo(saldo);
	}
	
	@Test
    void buscarContaPorNumero() {
    	when(contaRepository.findBynumeroConta(conta.getNumeroConta())).thenReturn(conta);
    	
    	ContaModel contaModel = serviceConta.procurarContaPorNumero(conta.getNumeroConta());
    	
    	assertEquals(conta, contaModel);
    	verify(contaRepository).findBynumeroConta(conta.getNumeroConta());
    	verifyNoMoreInteractions(contaRepository);
    }
	@Test
	void buscarPorNome() {
		when(contaRepository.findBynomeResponsavel(conta.getNomeResponsavel())).thenReturn(conta);
    	
    	ContaModel contaModel = serviceConta.procurarContaPorNome(conta.getNomeResponsavel());
    	
    	assertEquals(conta, contaModel);
    	verify(contaRepository).findBynomeResponsavel(conta.getNomeResponsavel());
    	verifyNoMoreInteractions(contaRepository);
	}
	@Test
	void buscarContas() {
		when(contaRepository.findAll()).thenReturn(Collections.singletonList(conta));
    	
    	List<ContaModel> contaModel = serviceConta.obterContas();
    	
    	assertEquals(Collections.singletonList(conta), contaModel);
    	verify(contaRepository).findAll();
    	verifyNoMoreInteractions(contaRepository);
	}
	@Test
	void salvarConta() {
		when(contaRepository.save(conta)).thenReturn(conta);
    	
    	ContaModel contaModel = serviceConta.salvar(conta);
    	
    	assertEquals(conta, contaModel);
    	verify(contaRepository).save(conta);
    	verifyNoMoreInteractions(contaRepository);
	}
}
