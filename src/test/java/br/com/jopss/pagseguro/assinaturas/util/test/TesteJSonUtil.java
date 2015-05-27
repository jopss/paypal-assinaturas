package br.com.jopss.pagseguro.assinaturas.util.test;

import br.com.jopss.paypal.assinaturas.exception.ProblemaGenericoAPIException;
import br.com.jopss.paypal.assinaturas.modelos.EnvioPreAprovacao;
import br.com.jopss.paypal.assinaturas.modelos.RespostaPreAprovacao;
import br.com.jopss.paypal.assinaturas.modelos.enums.Moeda;
import br.com.jopss.paypal.assinaturas.modelos.enums.Periodo;
import br.com.jopss.paypal.assinaturas.modelos.enums.TipoPreRequisicao;
import br.com.jopss.paypal.assinaturas.modelos.suporte.DefinicaoPagamento;
import br.com.jopss.paypal.assinaturas.modelos.suporte.PreferenciasComerciais;
import br.com.jopss.paypal.assinaturas.util.JSonUtil;
import static org.junit.Assert.*;
import org.junit.Test;

public class TesteJSonUtil {

	@Test
	public void objetoEnvioParaJSon() throws ProblemaGenericoAPIException{
                DefinicaoPagamento definicao = new DefinicaoPagamento(1998.11, Moeda.BRL);
                definicao.setCiclos(12);
                definicao.setFrequencia(Periodo.MONTH);
                definicao.setIntervaloFrequencia(1);
                definicao.setNome("Regular Payments");
                definicao.setIndicaPeriodoDeTeste(false);
                
                PreferenciasComerciais preferencias = new PreferenciasComerciais();
                preferencias.setContinuarAcaoAoFalharPagamentoInicial(false);
                preferencias.setQtdMaximaTentativas(0);
                preferencias.setUrlCancelar("http://www.cancel.com");
                preferencias.setUrlNotificacao("http://www.notification.com");
                preferencias.setUrlRetorno("http://www.return.com");
                preferencias.setValorInicialDiferenciado(123.66, Moeda.BRL);
                
                EnvioPreAprovacao preAprovacao = new EnvioPreAprovacao(definicao, preferencias);
                preAprovacao.setDescricao("Template creation.");
                preAprovacao.setNome("T-Shirt of the Month Club Plan");
                preAprovacao.setTipo(TipoPreRequisicao.FIXED);
                
		String json = JSonUtil.getJSon(preAprovacao);
		assertNotNull(json);
	}
	
	@Test
	public void jsonRespostaParaObjeto() throws ProblemaGenericoAPIException{
		String json = "{\"id\":\"P-94458432VR012762KRWBZEUA\",\"state\":\"CREATED\",\"name\":\"T-Shirt of the Month Club Plan\",\"description\":\"Template creation.\",\"type\":\"FIXED\",\"payment_definitions\":[{\"id\":\"PD-50606817NF8063316RWBZEUA\",\"name\":\"Regular Payments\",\"type\":\"REGULAR\",\"frequency\":\"Month\",\"amount\":{\"currency\":\"USD\",\"value\":\"100\"},\"cycles\":\"12\",\"frequency_interval\":\"2\"}],\"merchant_preferences\":{\"setup_fee\":{\"currency\":\"USD\",\"value\":\"1\"},\"max_fail_attempts\":\"0\",\"return_url\":\"http://www.return.com\",\"cancel_url\":\"http://www.cancel.com\",\"auto_bill_amount\":\"YES\",\"initial_fail_amount_action\":\"CONTINUE\"},\"create_time\":\"2014-07-31T17:41:55.920Z\",\"update_time\":\"2014-07-31T17:41:55.920Z\",\"links\":[{\"href\":\"https://api.sandbox.paypal.com/v1/payments/billing-plans/P-94458432VR012762KRWBZEUA\",\"rel\":\"self\",\"method\":\"GET\"}]}";
		RespostaPreAprovacao resposta = JSonUtil.getObject(json, RespostaPreAprovacao.class);
		assertNotNull(resposta);
	}
}
