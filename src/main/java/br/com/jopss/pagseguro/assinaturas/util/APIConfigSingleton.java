package br.com.jopss.pagseguro.assinaturas.util;

import br.com.jopss.pagseguro.assinaturas.exception.AutorizacaoInvalidaException;
import br.com.jopss.pagseguro.assinaturas.exception.ConfiguracaoInvalidaException;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

/**
 * Classe Singleton para guardar os dados de configurações, como email, token, charset, proxy, etc.
 * Utilizado internamente pela API.
 * 
 * @author João Paulo Sossoloti.
 */
public final class APIConfigSingleton {

	private boolean teste = false;
	private String id;
	private String secret;
	private String token;
	private Long expiracaoToken;
	private String charset = "UTF-8";

	private Integer proxyPorta;
	private String proxyURI;
	private String proxyUsuario;
	private String proxySenha;
	
	/**
	 * Instância Singleton.
	 */
	private static final APIConfigSingleton autenticacao = new APIConfigSingleton();

	/**
	 * Construtor padrão bloqueado.
	 */
	private APIConfigSingleton() {
	}
	
	/**
	 * Método central para retorno desta entidade.
	 * @return APIConfigSingleton.
	 */
	public static APIConfigSingleton get() {
		return APIConfigSingleton.autenticacao;
	}

	/**
	 * Limpa os dados armazenados na memória.
	 */
	public void limpar() {
		this.id = null;
		this.secret = null;
		teste = false;
	}
	
        private String getDefaultUrlOAuth(){
		return "https://api.sandbox.paypal.com/v1/oauth2/token";
	}
        
        private String getDefaultUrlPreAprovacao(){
		return "https://api.paypal.com/v1/payments/billing-plans";
	}
	
        private String getDefaultUrlFaturamento(){
		return "https://api.paypal.com/v1/payments/billing-agreements";
	}
        
	public boolean proxyConfigurado(){
		if(proxyPorta!=null && StringUtils.isNotBlank(proxyURI)){
			return true;
		}
		return false;
	}

	/**
	 * Retorna o e-mail associado a sua conta no PagSeguro. Caso o valor esteja inválido, lança exceção.
	 * 
	 * @return String.
	 * @throws br.com.jopss.pagseguro.assinaturas.exception.AutorizacaoInvalidaException 
	 */
	public String getId() throws AutorizacaoInvalidaException {
		if (StringUtils.isBlank(id)) {
			throw new AutorizacaoInvalidaException("Configuração: id obrigatório.");
		}
		return id;
	}

	/**
	 * Insere o e-mail na instância singleton. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @param id String.
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getSecret() throws AutorizacaoInvalidaException {
		if (StringUtils.isBlank(secret)) {
			throw new AutorizacaoInvalidaException("Configuração: secret obrigatório.");
		}
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
        
	public String getToken() throws AutorizacaoInvalidaException {
		if (StringUtils.isBlank(token)) {
			throw new AutorizacaoInvalidaException("Configuração: token obrigatório.");
		}
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

        public void setExpiracaoToken(Long expiracaoToken) {
                this.expiracaoToken = expiracaoToken;
        }
        
	/**
	 * Retorna o charset que será utilizado internamente pela API no acesso aos serviços remotos.
	 * Caso o valor esteja inválido, lança exceção. Já existe um valor padrão: "UTF-8".
	 * 
	 * @return String.
	 * @throws br.com.jopss.pagseguro.assinaturas.exception.ConfiguracaoInvalidaException 
	 */
	public String getCharset() throws ConfiguracaoInvalidaException {
		if (StringUtils.isBlank(charset)) {
			throw new ConfiguracaoInvalidaException("Configuração: Charset obrigatório. Default: UTF-8.");
		}
		return charset;
	}

	/**
	 * Insere o charset na instância singleton. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @param charset String.
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

        /**
	 * Retorna se o token está expirado.
	 * @return boolean.
	 */
	public boolean tokenExpirado() {
		return expiracaoToken != null && new Date(expiracaoToken).before(new Date());
	}
        
	public String getUrlOAuth() throws ConfiguracaoInvalidaException {
		String urlOAuth = this.getDefaultUrlOAuth();
		if (StringUtils.isBlank(urlOAuth)) {
			throw new ConfiguracaoInvalidaException("Configuração: urlOAuth obrigatório.");
		}
		if(APIConfigSingleton.get().isTeste()){
			urlOAuth = urlOAuth.replaceAll("api.paypal.com", "api.sandbox.paypal.com");
		}
		return urlOAuth;
	}
        
	public String getUrlPreAprovacao() throws ConfiguracaoInvalidaException {
		String urlPreAprovacao = this.getDefaultUrlPreAprovacao();
		if (StringUtils.isBlank(urlPreAprovacao)) {
			throw new ConfiguracaoInvalidaException("Configuração: urlPreAprovacao obrigatório.");
		}
		if(APIConfigSingleton.get().isTeste()){
			urlPreAprovacao = urlPreAprovacao.replaceAll("api.paypal.com", "api.sandbox.paypal.com");
		}
		return urlPreAprovacao;
	}
        
	public String getUrlFaturamento() throws ConfiguracaoInvalidaException {
		String urlFaturamento = this.getDefaultUrlFaturamento();
		if (StringUtils.isBlank(urlFaturamento)) {
			throw new ConfiguracaoInvalidaException("Configuração: urlFaturamento obrigatório.");
		}
		if(APIConfigSingleton.get().isTeste()){
			urlFaturamento = urlFaturamento.replaceAll("api.paypal.com", "api.sandbox.paypal.com");
		}
		return urlFaturamento;
	}
        
	/**
	 * Indica se está marcado para acesso ao ambiente de testes do PagSeguro. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @return boolean.
	 */
	public boolean isTeste() {
		return teste;
	}

	/**
	 * Indica que os acessos serão para o ambiente de testes do PagSeguro. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @param teste boolean.
	 */
	public void setTeste(boolean teste) {
		this.teste = teste;
	}
	
	/**
	 * Retorna a porta do proxy configurado. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @return Integer.
	 */
	public Integer getProxyPorta() {
		return proxyPorta;
	}

	/**
	 * Indica a porta do proxy da sua rede. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @param proxyPorta Integer.
	 */
	public void setProxyPorta(Integer proxyPorta) {
		this.proxyPorta = proxyPorta;
	}

	/**
	 * Retorna a URI do proxy configurado. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @return String.
	 */
	public String getProxyURI() {
		return proxyURI;
	}

	/**
	 * Indica a URI do proxy da sua rede. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @param proxyURI String.
	 */
	public void setProxyURI(String proxyURI) {
		this.proxyURI = proxyURI;
	}

	/**
	 * Retorna o usuário do proxy configurado. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @return String.
	 */
	public String getProxyUsuario() {
		return proxyUsuario;
	}

	/**
	 * Indica o usuário do proxy da sua rede. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @param proxyUsuario String.
	 */
	public void setProxyUsuario(String proxyUsuario) {
		this.proxyUsuario = proxyUsuario;
	}

	/**
	 * Retorna a senha do proxy configurado. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @return String.
	 */
	public String getProxySenha() {
		return proxySenha;
	}

	/**
	 * Indica a senha do proxy da sua rede. Será utilizado internamente pela API no acesso aos serviços remotos.
	 * 
	 * @param proxySenha String.
	 */
	public void setProxySenha(String proxySenha) {
		this.proxySenha = proxySenha;
	}

}
