package br.com.jopss.paypal.assinaturas.exception;

/**
 * @author João Paulo Sossoloti.
 */
public class ConfiguracaoInvalidaException extends PayPalException {

	public ConfiguracaoInvalidaException(String message) {
		super(message);
	}

	public ConfiguracaoInvalidaException(Throwable cause) {
		super(cause);
	}
}
