package br.com.jopss.pagseguro.assinaturas.exception;

/**
 * Classe pai dos erros desta API.
 * 
 * @author João Paulo Sossoloti.
 */
public class PagSeguroException extends Exception {

	public PagSeguroException(String message) {
		super(message);
	}

	public PagSeguroException(Throwable cause) {
		super(cause);
	}
}
