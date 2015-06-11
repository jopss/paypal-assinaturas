package br.com.jopss.paypal.assinaturas;

import br.com.jopss.paypal.assinaturas.exception.AutorizacaoInvalidaException;
import br.com.jopss.paypal.assinaturas.exception.ConfiguracaoInvalidaException;
import br.com.jopss.paypal.assinaturas.exception.ErrosRemotosPayPalException;
import br.com.jopss.paypal.assinaturas.exception.ProblemaGenericoAPIException;
import br.com.jopss.paypal.assinaturas.modelos.EnvioAtivacao;
import br.com.jopss.paypal.assinaturas.modelos.EnvioContrato;
import br.com.jopss.paypal.assinaturas.modelos.EnvioPreAprovacao;
import br.com.jopss.paypal.assinaturas.modelos.RespostaContrato;
import br.com.jopss.paypal.assinaturas.modelos.RespostaFaturamento;
import br.com.jopss.paypal.assinaturas.modelos.Resposta;
import br.com.jopss.paypal.assinaturas.modelos.RespostaPreAprovacao;
import br.com.jopss.paypal.assinaturas.modelos.enums.RetornoVerificacaoNotificacao;
import br.com.jopss.paypal.assinaturas.modelos.enums.SituacaoFaturamento;
import br.com.jopss.paypal.assinaturas.util.APIConfigSingleton;
import br.com.jopss.paypal.assinaturas.util.AcessoPayPal;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe de serviço para acessos a registro de assinaturas.
 * Entende-se por assinatura a recorrência de um pagamento por um período de tempo.
 * 
 * @author João Paulo Sossoloti.
 */
public final class RequisicaoAssinatura {
	
	/**
	 * Método de acesso HTTP POST.
	 * Passo inicial para o registro de uma assinatura. O PagSeguro somente retorna a pré aprovação com um código do cliente, e data.
         * 
	 * Nesta etapa, passamos todos os dados de registro da assinatura, com valores, período, etc. Não é possível passar uma data futura para início do pagamento, 
         * nem um valor para ser pago inicialmente diferente da assinatura. Para isso, use o método 'preAprovacaoComCheckout'.
         * 
	 * Com o código retornado em mãos, podemos efetivar a autorização redirecionando para a página de pagamento.
	 * 
	 * @param preRequisicao EnvioPreAprovacao com os dados da assinatura e do cliente.
	 * @return {@link br.com.jopss.paypal.assinaturas.modelos.RespostaPreAprovacao} resposta com código do cliente e data, para iniciar fluxo de pagamento.
	 * @throws br.com.jopss.paypal.assinaturas.exception.ProblemaGenericoAPIException
	 * @throws br.com.jopss.paypal.assinaturas.exception.ErrosRemotosPayPalException
	 * @throws br.com.jopss.paypal.assinaturas.exception.ConfiguracaoInvalidaException
	 * @throws br.com.jopss.paypal.assinaturas.exception.AutorizacaoInvalidaException
	 */
	public RespostaPreAprovacao preAprovacao(EnvioPreAprovacao preRequisicao) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
		return new AcessoPayPal().acessoPOST( APIConfigSingleton.get().getUrlPreAprovacao(), RespostaPreAprovacao.class, preRequisicao );
	}
        
        public Resposta ativarAprovacao(RespostaPreAprovacao respostaPreAprovacao) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
		return new AcessoPayPal().acessoPATCH(APIConfigSingleton.get().getUrlAtivamento(respostaPreAprovacao.getId()), Resposta.class, new EnvioAtivacao[]{new EnvioAtivacao(SituacaoFaturamento.ACTIVE)} );
	}
        
        public RespostaContrato contrato(EnvioContrato envioContrato) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
		return new AcessoPayPal().acessoPOST( APIConfigSingleton.get().getUrlContrato(), RespostaContrato.class, envioContrato );
	}
        
        public void redirecionarURLPagamento(HttpServletResponse response, RespostaContrato respostaContrato) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
                try {
			String urlWithSessionID = response.encodeRedirectURL(respostaContrato.getLinkRedirecionamento());
			response.sendRedirect( urlWithSessionID );
		} catch (IOException ex) {
			throw new ProblemaGenericoAPIException(ex);
		}
	}
        
        public RespostaFaturamento faturamento(String token) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
		return new AcessoPayPal().acessoPOST( APIConfigSingleton.get().getUrlFaturamento(token), RespostaFaturamento.class );
	}
        
        public Resposta cancelar(String idFaturamento, String descricao) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
		return new AcessoPayPal().acessoPOSTSemParseJSon( APIConfigSingleton.get().getUrlCancelamento(idFaturamento), Resposta.class, "{\"note\":\""+descricao+"\"}" );
	}
        
        public RetornoVerificacaoNotificacao verificacaoNotificacao(Map<String,String> valoresRequisicao) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
		return RetornoVerificacaoNotificacao.valueOf( new AcessoPayPal().acessoPOSTComRequisicao( APIConfigSingleton.get().getUrlVerificacaoNotificacao(), valoresRequisicao ) );
	}
        
}
