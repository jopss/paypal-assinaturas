package br.com.jopss.paypal.assinaturas.exception;

/**
 * Classe pai dos erros desta API.
 * 
 * @author João Paulo Sossoloti.
 */
public class PayPalException extends Exception {

	public PayPalException(String message) {
		super(message);
	}

	public PayPalException(Throwable cause) {
		super(cause);
	}
}
