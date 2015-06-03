package br.com.jopss.paypal.assinaturas.exception;

import br.com.jopss.paypal.assinaturas.modelos.ErroPayPal;

/**
 * @author João Paulo Sossoloti.
 */
public class ErrosRemotosPayPalException extends Exception {

	private final ErroPayPal errosPayPal;
	
	public ErrosRemotosPayPalException(ErroPayPal errosPagSeguro) {
		super(errosPagSeguro.toString());
		this.errosPayPal = errosPagSeguro;
	}

	public boolean contemErros(){
		return errosPayPal !=null;
	}
	
	public ErroPayPal getErrosPayPal() {
		return errosPayPal;
	}
	
}
