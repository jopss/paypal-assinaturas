package br.com.jopss.paypal.assinaturas.exception;

import br.com.jopss.paypal.assinaturas.modelos.ErrosPagSeguro;

/**
 * @author João Paulo Sossoloti.
 */
public class ErrosRemotosPayPalException extends Exception {

	private final ErrosPagSeguro errosPagSeguro;
	
	public ErrosRemotosPayPalException(ErrosPagSeguro errosPagSeguro) {
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
