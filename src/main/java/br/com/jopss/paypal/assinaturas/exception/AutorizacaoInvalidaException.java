package br.com.jopss.paypal.assinaturas.exception;

/**
 * @author João Paulo Sossoloti.
 */
public class AutorizacaoInvalidaException extends PayPalException {

	public AutorizacaoInvalidaException(String message) {
		super(message);
	}

	public AutorizacaoInvalidaException(Throwable cause) {
		super(cause);
	}
}
