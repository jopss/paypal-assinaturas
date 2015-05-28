package br.com.jopss.paypal.assinaturas.util;

import br.com.jopss.paypal.assinaturas.exception.AutorizacaoInvalidaException;
import br.com.jopss.paypal.assinaturas.exception.ConfiguracaoInvalidaException;
import br.com.jopss.paypal.assinaturas.exception.ErrosRemotosPayPalException;
import br.com.jopss.paypal.assinaturas.exception.ProblemaGenericoAPIException;
import br.com.jopss.paypal.assinaturas.modelos.ErrosPagSeguro;
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
import org.apache.commons.lang.StringUtils;

/**
 * Classe utilitária para acessos HTTP (GET ou POST).
 *
 * @author João Paulo Sossoloti.
 */
public final class AcessoPayPal {

        public <T> T acessoGET(String sUrl, Class<? extends RespostaPayPal> clazz) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, false, null, false);
        }

        public <T> T acessoPOST(String sUrl, Class<? extends RespostaPayPal> clazz, EnvioPayPal envio) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, true, JSonUtil.getJSon(envio), false);
        }

        public <T> T acessoPOSTToken(String sUrl, Class<? extends RespostaPayPal> clazz, String id, String secret) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, true, "grant_type=client_credentials", true);
        }

        private <T> T acessarURL(String sUrl, String requestQuery, Class<? extends RespostaPayPal> clazz, boolean post, String envio, boolean gerarToken) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                try {
                        URL url = new URL(sUrl + (StringUtils.isNotBlank(requestQuery) ? requestQuery : ""));

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

                        if (gerarToken) {
                                conn.setRequestProperty("Accept", "application/json");
                                conn.setRequestProperty("Authorization", "Basic " + APIConfigSingleton.get().getId() + ":" + APIConfigSingleton.get().getSecret());
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        } else {
                                conn.setRequestProperty("Authorization", "Bearer " + APIConfigSingleton.get().getToken());
                                conn.setRequestProperty("Content-Type", "application/json; charset=" + APIConfigSingleton.get().getCharset());
                        }

                        OutputStreamWriter writer = null;
                        if (post) {
                                conn.setDoOutput(true);
                                conn.setRequestProperty("Content-Length", "" + Integer.toString(envio.getBytes().length));
                                conn.setRequestProperty("Accept-Language", "pt_BR");
                                conn.setRequestMethod("POST");

                                writer = new OutputStreamWriter(conn.getOutputStream());
                                writer.write(envio);
                                writer.flush();
                        } else {
                                conn.setRequestProperty("Content-Type", "application/json; charset=" + APIConfigSingleton.get().getCharset());
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

                        RespostaPayPal resp = JSonUtil.getObject(json, clazz);

                        if (resp == null) {
                                throw new ProblemaGenericoAPIException("Sem acesso ao PayPal. O sistema por estar fora do ar. Verifique a URL ou tente novamente mais tarde. Tentativa em: '" + url + "'");
                        }

                        if(!resp.isValido()){
                                ErrosPagSeguro erros = JSonUtil.getObject(json, ErrosPagSeguro.class);
				throw new ErrosRemotosPayPalException(erros);
			}
                        return (T) resp;

                } catch (ConfiguracaoInvalidaException | IOException ex) {
                        throw new ProblemaGenericoAPIException(ex);
                }
        }

}
