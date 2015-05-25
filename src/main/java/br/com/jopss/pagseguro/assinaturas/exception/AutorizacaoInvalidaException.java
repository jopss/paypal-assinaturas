package br.com.jopss.pagseguro.assinaturas.exception;

/**
 * @author João Paulo Sossoloti.
 */
public class AutorizacaoInvalidaException extends PagSeguroException {

	public AutorizacaoInvalidaException(String message) {
		super(message);
	}

	public AutorizacaoInvalidaException(Throwable cause) {
		super(cause);
	}
}
