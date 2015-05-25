package br.com.jopss.pagseguro.assinaturas.exception;

import br.com.jopss.pagseguro.assinaturas.modelos.ErrosPagSeguro;

/**
 * @author João Paulo Sossoloti.
 */
public class ErrosRemotosPagSeguroException extends Exception {

	private final ErrosPagSeguro errosPagSeguro;
	
	public ErrosRemotosPagSeguroException(ErrosPagSeguro errosPagSeguro) {
		super(errosPagSeguro.toString());
		this.errosPagSeguro = errosPagSeguro;
	}

	public boolean contemErros(){
		return errosPagSeguro !=null;
	}
	
	public ErrosPagSeguro getErrosPagSeguro() {
		return errosPagSeguro;
	}
	
}
