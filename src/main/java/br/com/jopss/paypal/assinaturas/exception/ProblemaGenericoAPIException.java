package br.com.jopss.paypal.assinaturas.exception;

/**
 * @author João Paulo Sossoloti.
 */
public class ProblemaGenericoAPIException extends PayPalException {

	public ProblemaGenericoAPIException(String message) {
		super(message);
	}

	public ProblemaGenericoAPIException(Throwable cause) {
		super(cause);
	}
}
