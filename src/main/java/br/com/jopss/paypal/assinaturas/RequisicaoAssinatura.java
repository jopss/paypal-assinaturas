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
import br.com.jopss.paypal.assinaturas.modelos.RespostaOK;
import br.com.jopss.paypal.assinaturas.modelos.RespostaPreAprovacao;
import br.com.jopss.paypal.assinaturas.util.APIConfigSingleton;
import br.com.jopss.paypal.assinaturas.util.AcessoPayPal;

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
        
        public RespostaOK ativarAprovacao(EnvioAtivacao envioAtivacao, RespostaPreAprovacao respostaPreAprovacao) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
		return new AcessoPayPal().acessoPATCH( APIConfigSingleton.get().getUrlAtivamento(respostaPreAprovacao.getId()), RespostaOK.class, envioAtivacao );
	}
        
        public RespostaContrato contrato(EnvioContrato envioContrato) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
		return new AcessoPayPal().acessoPOST( APIConfigSingleton.get().getUrlContrato(), RespostaContrato.class, envioContrato );
	}
        
        public RespostaFaturamento faturamento(RespostaContrato respostaContrato) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
		return new AcessoPayPal().acessoPOST( APIConfigSingleton.get().getUrlFaturamento(respostaContrato.getId()), RespostaFaturamento.class );
	}
        
}
