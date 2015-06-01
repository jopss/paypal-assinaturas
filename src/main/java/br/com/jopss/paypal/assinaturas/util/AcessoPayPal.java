package br.com.jopss.paypal.assinaturas.util;

import br.com.jopss.paypal.assinaturas.exception.AutorizacaoInvalidaException;
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
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.apache.http.config.Registry;

/**
 * Classe utilitária para acessos HTTP (GET ou POST).
 *
 * @author João Paulo Sossoloti.
 */
public class AcessoPayPal {

        public <T> T acessoGET(String sUrl, Class<? extends RespostaPayPal> clazz) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, TipoAcessoPayPal.GET, null, false, true);
        }

        public <T> T acessoPOST(String sUrl, Class<? extends RespostaPayPal> clazz) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessoPOST(sUrl, clazz, null);
        }

        public <T> T acessoPOST(String sUrl, Class<? extends RespostaPayPal> clazz, EnvioPayPal envio) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, TipoAcessoPayPal.POST, envio != null ? JSonUtil.getJSon(envio) : null, false, true);
        }

        public <T> T acessoPATCH(String sUrl, Class<? extends RespostaPayPal> clazz, EnvioPayPal envio) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, TipoAcessoPayPal.PATCH, envio != null ? JSonUtil.getJSon(envio) : null, false, false);
        }

        public <T> T acessoPOSTToken(String sUrl, Class<? extends RespostaPayPal> clazz, String id, String secret) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                return this.acessarURL(sUrl, null, clazz, TipoAcessoPayPal.POST, "grant_type=client_credentials", true, true);
        }

        private <T> T acessarURL(String sUrl, String requestQuery, Class<? extends RespostaPayPal> clazz, TipoAcessoPayPal tipo, String envio, boolean gerarToken, boolean parseJSonRetorno) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, AutorizacaoInvalidaException {
                try {
                        URL url = new URL(sUrl + (StringUtils.isNotBlank(requestQuery) ? requestQuery : ""));

                        Integer responseCode = null;
                        OutputStreamWriter writer = null;
                        BufferedReader bufferedreader = null;

                        if (tipo.equals(TipoAcessoPayPal.PATCH)) {
                                //CloseableHttpClient httpClient = HttpClients.createDefault();
                                SSLContextBuilder builder = SSLContexts.custom();
                                builder.loadTrustMaterial(null, new TrustStrategy() {
                                        @Override
                                        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                                return true;
                                        }
                                });
                                SSLContext sslContext = builder.build();
                                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                                        sslContext, new X509HostnameVerifier() {
                                                @Override
                                                public void verify(String host, SSLSocket ssl) throws IOException {
                                                }

                                                @Override
                                                public void verify(String host, X509Certificate cert) throws SSLException {
                                                }

                                                @Override
                                                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                                                }

                                                @Override
                                                public boolean verify(String s, SSLSession sslSession) {
                                                        return true;
                                                }
                                        }
                                );

                                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslsf).build();

                                PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                                CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

                                HttpPatch httpPatch = new HttpPatch(url.toURI());
                                httpPatch.addHeader("Authorization", "Bearer " + APIConfigSingleton.get().getToken());
                                httpPatch.addHeader("Content-Type", "application/json; charset=" + APIConfigSingleton.get().getCharset());
                                httpPatch.setEntity(new SerializableEntity(envio, false));

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
                                } else {
                                        conn.setRequestProperty("Content-Type", "application/json; charset=" + APIConfigSingleton.get().getCharset());
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

                        if (!parseJSonRetorno) {
                                if (responseCode >= 200 && responseCode <= 204) {
                                        return (T) clazz.newInstance();
                                } else {
                                        ErrosPagSeguro erros = JSonUtil.getObject(json, ErrosPagSeguro.class);
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
                                        ErrosPagSeguro erros = JSonUtil.getObject(json, ErrosPagSeguro.class);
                                        throw new ErrosRemotosPayPalException(erros);
                                }
                                return (T) resp;
                        }

                } catch (Exception ex) {
                        throw new ProblemaGenericoAPIException(ex);
                }
        }

        public static enum TipoAcessoPayPal {

                GET, POST, PATCH;
        }

}
