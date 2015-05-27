package br.com.jopss.paypal.assinaturas;

import br.com.jopss.paypal.assinaturas.exception.AutorizacaoInvalidaException;
import br.com.jopss.paypal.assinaturas.exception.ConfiguracaoInvalidaException;
import br.com.jopss.paypal.assinaturas.exception.ErrosRemotosPayPalException;
import br.com.jopss.paypal.assinaturas.exception.ProblemaGenericoAPIException;
import br.com.jopss.paypal.assinaturas.modelos.RespostaToken;
import br.com.jopss.paypal.assinaturas.util.APIConfigSingleton;
import br.com.jopss.paypal.assinaturas.util.AcessoPayPal;

public final class RequisicaoSeguranca {

        /**
         * Método de acesso HTTP GET. Gera um novo Token a partir dos dados,
         * sendo obrigatórios o 'ClientID' e 'Secret'.
         *
         * @return PayPalAPI finalizando o fluxo de segurança, sendo possível direcionar para outros fluxos.
         * @throws br.com.jopss.paypal.assinaturas.exception.AutorizacaoInvalidaException
         * @throws br.com.jopss.paypal.assinaturas.exception.ProblemaGenericoAPIException
         * @throws br.com.jopss.paypal.assinaturas.exception.ConfiguracaoInvalidaException
         * @throws br.com.jopss.paypal.assinaturas.exception.ErrosRemotosPayPalException
         */
        public PayPalAPI gerarToken() throws AutorizacaoInvalidaException, ProblemaGenericoAPIException, ConfiguracaoInvalidaException, ErrosRemotosPayPalException {

                String id = APIConfigSingleton.get().getId();
                String senha = APIConfigSingleton.get().getSecret();
                String urlOAuth = APIConfigSingleton.get().getUrlOAuth();

                RespostaToken resposta = new AcessoPayPal().acessoPOSTToken(urlOAuth, RespostaToken.class, id, senha);

                APIConfigSingleton.get().setToken(resposta.getToken());
                APIConfigSingleton.get().setExpiracaoToken(resposta.getExpiraEm());

                return PayPalAPI.instance();
        }

}
