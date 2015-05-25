package br.com.jopss.paypal.assinaturas;

import br.com.jopss.pagseguro.assinaturas.exception.AutorizacaoInvalidaException;
import br.com.jopss.pagseguro.assinaturas.exception.ConfiguracaoInvalidaException;
import br.com.jopss.pagseguro.assinaturas.exception.ProblemaGenericoAPIException;
import br.com.jopss.pagseguro.assinaturas.util.APIConfigSingleton;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public final class RequisicaoSeguranca {
	
	/**
	 * Método de acesso HTTP GET.
	 * Gera um novo Token a partir dos dados, sendo obrigatórios o 'ClientID' e 'Secret'.
         * 
	 * @return PayPalAPI finalizando o fluxo de segurança, sendo possível direcionar para outros fluxos.
         * @throws br.com.jopss.pagseguro.assinaturas.exception.ConfiguracaoInvalidaException
         * @throws br.com.jopss.pagseguro.assinaturas.exception.ProblemaGenericoAPIException
         * @throws br.com.jopss.pagseguro.assinaturas.exception.AutorizacaoInvalidaException
	 */
	public PayPalAPI gerarToken() throws ConfiguracaoInvalidaException, ProblemaGenericoAPIException, AutorizacaoInvalidaException {
		try {

			String id = APIConfigSingleton.get().getId();
			String senha = APIConfigSingleton.get().getSecret();

			OAuthClientRequest request = OAuthClientRequest.tokenLocation(APIConfigSingleton.get().getUrlOAuth()).setClientId(id).setClientSecret(senha).setGrantType(
				GrantType.CLIENT_CREDENTIALS).buildBodyMessage();

			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthAccessTokenResponse oauthResponse = oAuthClient.accessToken(request);
			APIConfigSingleton.get().setToken(oauthResponse.getAccessToken());
			APIConfigSingleton.get().setExpiracaoToken(oauthResponse.getExpiresIn());
			
		} catch (OAuthSystemException ex) {
			String msg = ex.getMessage();
			if(msg.contains("FileNotFoundException")){
				throw new ProblemaGenericoAPIException("Ops! O serviço remoto está indisponível. Verifique a URL ou tente novamente mais tarde.");
			}
			throw new AutorizacaoInvalidaException(msg);
		} catch (OAuthProblemException ex) {
			throw new ProblemaGenericoAPIException(ex.getError() + ":" + ex.getDescription());
		} catch ( AutorizacaoInvalidaException ex) {
			throw ex;
		}
		return PayPalAPI.instance();
	}
	
}
