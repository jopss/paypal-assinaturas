package br.com.jopss.paypal.assinaturas;

/**
 * Classe inicial ao uso da API.
 * 
 * @author João Paulo Sossoloti.
 */
public final class PayPalAPI {
	
	/**
	 * Construtor padrao privado (padrao Singleton).
	 */
	private PayPalAPI(){}
	
	/**
	 * Retorna a propria instancia Singleton.
	 * @return {@link br.com.jopss.paypal.assinaturas.PayPalAPI}.
	 */	
	public static PayPalAPI instance(){
		return new PayPalAPI();
	}
	
	/**
	 * Acessa os itens sobre configuração, como setar email e token dos acessos.
	 * @return {@link  br.com.jopss.paypal.assinaturas.ConfiguracaoAPI}.
	 */
	public ConfiguracaoAPI config(){
		return new ConfiguracaoAPI();
	}
	
	/**
	 * Acessa os itens sobre assinaturas, como pré requisição, redirecionamento ao pagamento, cobrança e cancelamento.
	 * @return {@link  br.com.jopss.paypal.assinaturas.RequisicaoAssinatura}.
	 */
	public RequisicaoAssinatura assinatura(){
		return new RequisicaoAssinatura();
	}
        
        public RequisicaoSeguranca seguranca(){
		return new RequisicaoSeguranca();
	}
	
}
