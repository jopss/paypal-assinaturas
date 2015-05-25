package br.com.jopss.pagseguro.assinaturas.modelos.suporte;

import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.BandeiraBancoPagamentoTransacao;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.TipoMeioPagamentoTransacao;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe modelo de suporte com dados do meio de pagamento de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "paymentmethod")
public class MeioPagamento {
	
	@XmlElement(name = "type")
	private TipoMeioPagamentoTransacao tipo;
	
	@XmlElement(name = "code")
	private BandeiraBancoPagamentoTransacao bandeiraOuBanco;

	/**
	 * Informa o tipo do meio de pagamento usado pelo comprador. 
	 * Este tipo agrupa diversos meios de pagamento e determina de forma geral o comportamento da transação.
	 * <ul>
	 *	<li>Formato:
	 *		<ul>
	 *			<li>1 - Cartão de crédito: O comprador pagou pela transação com um cartão de crédito. Neste caso, o pagamento é processado imediatamente ou no máximo em algumas horas, dependendo da sua classificação de risco.</li>
	 *			<li>2 - Boleto: O comprador optou por pagar com um boleto bancário. Ele terá que imprimir o boleto e pagá-lo na rede bancária. Este tipo de pagamento é confirmado em geral de um a dois dias após o pagamento do boleto. O prazo de vencimento do boleto é de 3 dias.</li>
	 *			<li>3 - Débito online (TEF): O comprador optou por pagar com débito online de algum dos bancos com os quais o PagSeguro está integrado. O PagSeguro irá abrir uma nova janela com o Internet Banking do banco escolhido, onde o comprador irá efetuar o pagamento. Este tipo de pagamento é confirmado normalmente em algumas horas.</li>
	 * 			<li>4 - Saldo PagSeguro: O comprador possuía saldo suficiente na sua conta PagSeguro e pagou integralmente pela transação usando seu saldo.</li>
	 * 			<li>5 - Oi Paggo: o comprador paga a transação através de seu celular Oi. A confirmação do pagamento acontece em até duas horas.</li>
	 * 			<li>7 - Depósito em conta: o comprador optou por fazer um depósito na conta corrente do PagSeguro. Ele precisará ir até uma agência bancária, fazer o depósito, guardar o comprovante e retornar ao PagSeguro para informar os dados do pagamento. A transação será confirmada somente após a finalização deste processo, que pode levar de 2 a 13 dias úteis.</li>
	 *		</ul>
	 *	</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return TipoMeioPagamentoTransacao
	 */
	public TipoMeioPagamentoTransacao getTipo() {
		return tipo;
	}

	/**
	 * Informa um código que identifica o meio de pagamento usado pelo comprador. 
	 * O meio de pagamento descreve a bandeira de cartão de crédito utilizada ou banco escolhido para um débito online.
	 * <ul>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return BandeiraBancoPagamentoTransacao
	 */
	public BandeiraBancoPagamentoTransacao getBandeiraOuBanco() {
		return bandeiraOuBanco;
	}

}
