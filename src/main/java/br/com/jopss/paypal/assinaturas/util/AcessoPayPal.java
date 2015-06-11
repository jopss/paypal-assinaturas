package br.com.jopss.paypal.assinaturas.util;

import br.com.jopss.paypal.assinaturas.exception.AutorizacaoInvalidaException;
import br.com.jopss.paypal.assinaturas.exception.ErrosRemotosPayPalException;
import br.com.jopss.paypal.assinaturas.exception.ProblemaGenericoAPIException;
import br.com.jopss.paypal.assinaturas.modelos.ErroPayPal;
import br.com.jopss.paypal.assinaturas.modelos.interfaces.EnvioPayPal;
import br.com.jopss.paypal.assinaturas.modelos.interfaces.RespostaPayPal;
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
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.security.cert.X509Certificate;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Classe utilitária para acessos HTTP (GET ou POST).
 *
 * @author João Paulo Sossoloti.
 */
public class AcessoPayPal {
        
        public <T> T acessoGET(String sUrl, Class<? extends RespostaPayPal> clazz) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, TipoAcessoPayPal.GET, null, false, true, false);
        }

        public <T> T acessoPOST(String sUrl, Class<? extends RespostaPayPal> clazz) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessoPOST(sUrl, clazz, null);
        }
        
        public String acessoPOSTComRequisicao(String sUrl, Map<String,String> valoresRequisicao) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                StringBuilder str = new StringBuilder();
                for(String chave : valoresRequisicao.keySet()){
                        String valor = valoresRequisicao.get(chave);
                        str.append("&"+chave+"="+valor);
                }
                return this.acessarURL(sUrl, str.toString(), null, TipoAcessoPayPal.POST, null, false, true, true);
        }

        public <T> T acessoPOSTSemParseJSon(String sUrl, Class<? extends RespostaPayPal> clazz, String envio) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, TipoAcessoPayPal.POST, envio, false, false, false);
        }
        
        public <T> T acessoPOST(String sUrl, Class<? extends RespostaPayPal> clazz, EnvioPayPal envio) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, TipoAcessoPayPal.POST, envio != null ? JSonUtil.getJSon(envio) : null, false, true, false);
        }

        public <T> T acessoPATCH(String sUrl, Class<? extends RespostaPayPal> clazz, EnvioPayPal[] envio) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, TipoAcessoPayPal.PATCH, envio != null ? JSonUtil.getJSon(envio) : null, false, false, false);
        }

        public <T> T acessoPOSTToken(String sUrl, Class<? extends RespostaPayPal> clazz, String id, String secret) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, TipoAcessoPayPal.POST, "grant_type=client_credentials", true, true, false);
        }

        private <T> T acessarURL(String sUrl, String requestQuery, Class<? extends RespostaPayPal> clazz, TipoAcessoPayPal tipo, String envio, boolean gerarToken, boolean parseJSonRetorno, boolean retornarDireto) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                try {
                        URL url = new URL(sUrl + (StringUtils.isNotBlank(requestQuery) ? requestQuery : ""));

                        Integer responseCode = null;
                        OutputStreamWriter writer = null;
                        BufferedReader bufferedreader = null;

                        if (tipo.equals(TipoAcessoPayPal.PATCH)) {
                                CloseableHttpClient httpClient = generateHttpClientWithProxyAuth(url.toString());
                                HttpPatch httpPatch = generateHttpPatchWithProxy(url.toString());
                                httpPatch.addHeader("Authorization", "Bearer " + APIConfigSingleton.get().getToken());
                                httpPatch.addHeader("Content-Type", "application/json; charset=" + APIConfigSingleton.get().getCharset());
                                httpPatch.addHeader("Accept-Language", "pt_BR");
                                httpPatch.setEntity(new StringEntity(envio, ContentType.APPLICATION_JSON));

                                CloseableHttpResponse response = httpClient.execute(httpPatch);
                                responseCode = response.getStatusLine().getStatusCode();
                                bufferedreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), APIConfigSingleton.get().getCharset()));
                        } else {
                                HttpURLConnection conn = null;
                                if (APIConfigSingleton.get().proxyConfigurado()) {
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
                                } else {
                                        conn = (HttpURLConnection) url.openConnection();
                                }

                                conn.setUseCaches(false);
                                conn.setRequestMethod(tipo.name());

                                if (gerarToken) {
                                        conn.setRequestProperty("Accept", "application/json");
                                        conn.setRequestProperty("Authorization", "Basic " + Base64.encodeBase64String((APIConfigSingleton.get().getId() + ":" + APIConfigSingleton.get().getSecret()).getBytes()));
                                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                } else {
                                        conn.setRequestProperty("Authorization", "Bearer " + APIConfigSingleton.get().getToken());
                                        conn.setRequestProperty("Content-Type", "application/json; charset=" + APIConfigSingleton.get().getCharset());
                                }

                                if (tipo.equals(TipoAcessoPayPal.POST)) {
                                        conn.setDoOutput(true);
                                        conn.setRequestProperty("Accept-Language", "pt_BR");

                                        if (envio != null) {
                                                conn.setRequestProperty("Content-Length", "" + Integer.toString(envio.getBytes().length));

                                                writer = new OutputStreamWriter(conn.getOutputStream());
                                                writer.write(envio);
                                                writer.flush();
                                        }
                                }

                                try {
                                        bufferedreader = new BufferedReader(new InputStreamReader(conn.getInputStream(), APIConfigSingleton.get().getCharset()));
                                } catch (IOException e) {
                                        InputStream in = conn.getErrorStream();
                                        if (in == null) {
                                                throw new ProblemaGenericoAPIException("Sem acesso ao recurso remoto. O sistema por estar fora do ar. Verifique a URL ou tente novamente mais tarde. Tentativa em: '" + url + "'");
                                        }
                                        bufferedreader = new BufferedReader(new InputStreamReader(in, APIConfigSingleton.get().getCharset()));
                                }
                                responseCode = conn.getResponseCode();
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

                        if(retornarDireto){
                                return (T) json;
                        }
                        
                        if (!parseJSonRetorno) {
                                if (responseCode >= 200 && responseCode <= 204) {
                                        return (T) clazz.newInstance();
                                } else {
                                        ErroPayPal erros = JSonUtil.getObject(json, ErroPayPal.class);
                                        throw new ErrosRemotosPayPalException(erros);
                                }
                        } else {
                                if (StringUtils.isBlank(json)) {
                                        throw new ProblemaGenericoAPIException("Sem acesso ao PayPal. O sistema por estar fora do ar. Verifique a URL ou tente novamente mais tarde. Tentativa em: '" + url + "'");
                                }

                                RespostaPayPal resp = JSonUtil.getObject(json, clazz);

                                if (resp == null) {
                                        throw new ProblemaGenericoAPIException("Sem acesso ao PayPal. O sistema por estar fora do ar. Verifique a URL ou tente novamente mais tarde. Tentativa em: '" + url + "'");
                                }

                                if (!resp.isValido()) {
                                        ErroPayPal erros = JSonUtil.getObject(json, ErroPayPal.class);
                                        throw new ErrosRemotosPayPalException(erros);
                                }
                                return (T) resp;
                        }

                } catch (Exception ex) {
                        throw new ProblemaGenericoAPIException(ex);
                }
        }
        
        private static CloseableHttpClient generateHttpClientWithProxyAuth(String url) {
		String proxyHost = getProxyHost();
		String proxyPort = getProxyPort();
		String proxyUser = getProxyUser();
		String proxyPass = getProxyPass();

		HttpClientBuilder builder = null;

		if (proxyHost != null && proxyPass != null && proxyPort != null && proxyUser != null) {
			builder = HttpClients.custom();
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(proxyHost, Integer.valueOf(proxyPort)), new UsernamePasswordCredentials(proxyUser, proxyPass));
			builder.setDefaultCredentialsProvider(credsProvider);
		}

		if (url.startsWith("https")) {
			if (builder == null) {
				builder = HttpClients.custom();
			}
			builder.setSSLSocketFactory( configureHttps() );
		}

		if (builder != null) {
			return builder.build();
		} else {
			return HttpClients.createDefault();
		}
	}
        
        private static SSLConnectionSocketFactory configureHttps() {
		try {

			SSLContextBuilder builder = SSLContexts.custom();
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
					return true;
				}
			});

			SSLContext sslContext = builder.build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

			return sslsf;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
        
        private static HttpPatch generateHttpPatchWithProxy(String url) {
		String proxyHost = getProxyHost();
		String proxyPort = getProxyPort();

		HttpPatch ret = new HttpPatch(url);
		addHeader(ret);
		if (proxyHost != null && proxyPort != null) {
			ret.setConfig(generateProxyConfig(proxyHost, proxyPort));
		}
		return ret;
	}

	private static void addHeader(HttpRequestBase base) {
		RequestConfig.Builder requestConfig = RequestConfig.custom();
		requestConfig.setConnectTimeout(30 * 1000);
		requestConfig.setConnectionRequestTimeout(30 * 1000);
		requestConfig.setSocketTimeout(30 * 1000);
		base.setConfig(requestConfig.build());
	}

	private static RequestConfig generateProxyConfig(String proxyHost, String proxyPort) {
		HttpHost proxy = new HttpHost(proxyHost, Integer.valueOf(proxyPort));
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		return config;
	}

	private static String getProxyHost() {
		return getProxyInfo("https.proxyHost", "http.proxyHost");
	}

	private static String getProxyPort() {
		return getProxyInfo("https.proxyPort", "http.proxyPort");
	}

	private static String getProxyUser() {
		return getProxyInfo("https.proxyUser", "http.proxyUser");
	}

	private static String getProxyPass() {
		return getProxyInfo("https.proxyPass", "http.proxyPass");
	}
        
        private static String getProxyInfo(String mainName, String altName) {
		String info = System.getProperty(mainName);
		if (altName != null && info == null) {
			info = System.getProperty(altName);
		}
		return info;
	}
        
        public static enum TipoAcessoPayPal {

                GET, POST, PATCH;
        }

}
