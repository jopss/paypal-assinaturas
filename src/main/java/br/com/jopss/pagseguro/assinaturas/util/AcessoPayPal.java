package br.com.jopss.pagseguro.assinaturas.util;

import br.com.jopss.pagseguro.assinaturas.exception.ConfiguracaoInvalidaException;
import br.com.jopss.pagseguro.assinaturas.exception.ErrosRemotosPagSeguroException;
import br.com.jopss.pagseguro.assinaturas.exception.ProblemaGenericoAPIException;
import br.com.jopss.pagseguro.assinaturas.modelos.ErrosPagSeguro;
import br.com.jopss.pagseguro.assinaturas.modelos.interfaces.EnvioPayPal;
import br.com.jopss.pagseguro.assinaturas.modelos.interfaces.RespostaPayPal;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import org.apache.commons.lang.StringUtils;

/**
 * Classe utilitária para acessos HTTP (GET ou POST).
 * 
 * @author João Paulo Sossoloti.
 */
public final class AcessoPayPal {
        
        private ObjectMapper jsonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	public <T> T acessoGET(String sUrl, Class<? extends RespostaPayPal> clazz) throws ProblemaGenericoAPIException, ErrosRemotosPagSeguroException {
		return this.acessarURL(sUrl, null, clazz, false, null);
	}
	
	public <T> T acessoPOST(String sUrl, Class<? extends RespostaPayPal> clazz, EnvioPayPal envio) throws ProblemaGenericoAPIException, ErrosRemotosPagSeguroException {
		return this.acessarURL(sUrl, null, clazz, true, envio );
	}

	private <T> T acessarURL(String sUrl, String requestQuery, Class<? extends RespostaPayPal> clazz, boolean post, EnvioPayPal envio) throws ProblemaGenericoAPIException, ErrosRemotosPagSeguroException {
		try {
			URL url = new URL(sUrl + (StringUtils.isNotBlank(requestQuery) ? requestQuery : ""));

			HttpURLConnection conn = null;
			if(APIConfigSingleton.get().proxyConfigurado()){
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(APIConfigSingleton.get().getProxyURI(), APIConfigSingleton.get().getProxyPorta()));
				Authenticator authenticator = new Authenticator() {
					@Override
					public PasswordAuthentication getPasswordAuthentication() {
						return (new PasswordAuthentication(APIConfigSingleton.get().getProxyUsuario(),
							APIConfigSingleton.get().getProxySenha().toCharArray()));
					}
				};
				Authenticator.setDefault(authenticator);
				conn = (HttpURLConnection) url.openConnection(proxy);
			}else{
				conn = (HttpURLConnection) url.openConnection();
			}
			
			conn.setUseCaches(false);
			
			if(APIConfigSingleton.get().isTeste()){
				conn.setRequestProperty("Access-Control-Allow-Origin", "https://sandbox.pagseguro.uol.com.br");
			}

			OutputStreamWriter writer = null;
			if (post) {
                                String jsonEnvio = jsonMapper.writeValueAsString(envio);
                                
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/xml; charset=" + APIConfigSingleton.get().getCharset());
				conn.setRequestProperty("Content-Length", "" + Integer.toString(jsonEnvio.getBytes().length));
				conn.setRequestMethod("POST");

				writer = new OutputStreamWriter(conn.getOutputStream());
				writer.write(jsonEnvio);
				writer.flush();
			} else {
				conn.setRequestProperty("Content-Type", "application/xml; charset=" + APIConfigSingleton.get().getCharset());
			}

			BufferedReader bufferedreader = null;
			try {
				bufferedreader = new BufferedReader(new InputStreamReader(conn.getInputStream(), APIConfigSingleton.get().getCharset()));
			} catch (IOException e) {
				InputStream in = conn.getErrorStream();
				if (in == null) {
					throw new ProblemaGenericoAPIException("Sem acesso ao recurso remoto. O sistema por estar fora do ar. Verifique a URL ou tente novamente mais tarde. Tentativa em: '" + url + "'");
				}
				bufferedreader = new BufferedReader(new InputStreamReader(in, APIConfigSingleton.get().getCharset()));
			}

			String json = "";
			String linha;
			while ((linha = bufferedreader.readLine()) != null) {
				json += linha;
			}

			if (writer != null) {
				writer.close();
			}
			bufferedreader.close();

			if (StringUtils.isBlank(json)) {
				throw new ProblemaGenericoAPIException("Sem acesso ao PayPal. O sistema por estar fora do ar. Verifique a URL ou tente novamente mais tarde. Tentativa em: '" + url + "'");
			}
                        
			RespostaPayPal resp = jsonMapper.readValue(json, clazz);
			
                        if(resp == null){
				throw new ProblemaGenericoAPIException("Sem acesso ao PayPal. O sistema por estar fora do ar. Verifique a URL ou tente novamente mais tarde. Tentativa em: '" + url + "'");
			}
                        
//                        if(!resp.isValido()){
//				if(resp.isTokenExpirado()){
//					throw new TokenExpiradoException(resp.getCodigoMensagem(), resp.getMensagem());
//				}
//				throw new ValidacaoRespostaRemotaException(resp.getCodigoMensagem(), resp.getMensagem());
//			}
			
			return (T) resp;

		} catch (ConfiguracaoInvalidaException | IOException ex) {
			throw new ProblemaGenericoAPIException(ex);
		}
	}

}
