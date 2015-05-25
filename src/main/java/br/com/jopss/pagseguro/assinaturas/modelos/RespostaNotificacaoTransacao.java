package br.com.jopss.pagseguro.assinaturas.modelos;

import br.com.jopss.pagseguro.assinaturas.modelos.interfaces.RespostaPayPal;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.MeioPagamento;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.OrigemCancelamentoTransacao;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.SituacaoTransacao;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.TipoTransacao;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta sobre dados de uma transação.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "transaction")
public class RespostaNotificacaoTransacao implements RespostaPayPal {
	
	@XmlElement(name = "date")
	private Date dataTransacao;
	
	@XmlElement(name = "code")
	private String codigoTransacao;

	@XmlElement(name = "reference")
	private String idReferenciaLocal;
	
	@XmlElement(name = "type")
	private TipoTransacao tipo;
	
	@XmlElement(name = "status")
	private SituacaoTransacao situacao;
	
	@XmlElement(name = "cancellationSource")
	private OrigemCancelamentoTransacao origemCancelamento;
	
	@XmlElement(name = "lasteventdate")
	private Date dataUltimaMudancaSituacao;
	
	@XmlElement(name = "escrowEndDate")
	private Date dataCreditoVendedor;
	
	@XmlElement(name = "grossamount")
	private Double valorBruto;
	
	@XmlElement(name = "discountamount")
	private Double desconto;
	
	@XmlElement(name = "feeamount")
	private Double taxasPagSeguro;
	
	@XmlElement(name = "netamount")
	private Double valorLiquido;
	
	@XmlElement(name = "extraamount")
	private Double valorExtra;
	
	@XmlElement(name = "installmentcount")
	private Integer parcelas;
	
	@XmlElement(name = "itemcount")
	private Integer numeroItens;
	
	@XmlElement(name = "paymentmethod")
	private MeioPagamento metodoPagamento;

	/**
	 * Informa o momento em que a transação foi criada.
	 * <ul>
	 *	<li>Formato: Data/Hora, YYYY-MM-DDThh:mm:ss.sTZD.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Date
	 */
	public Date getDataTransacao() {
		return dataTransacao;
	}

	/**
	 * Retorna o código que identifica a transação de forma única.
	 * <ul>
	 *	<li>Formato: Texto com 36 caracteres.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return String
	 */
	public String getCodigoTransacao() {
		return codigoTransacao;
	}

	/**
	 * Informa o código que foi usado para fazer referência ao pagamento. 
	 * Este código foi fornecido no momento do pagamento e é útil para vincular as transações do 
	 * PagSeguro às vendas registradas no seu sistema.
	 * <ul>
	 *	<li>Formato: Texto com 200 caracteres..</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @return String
	 */
	public String getIdReferenciaLocal() {
		return idReferenciaLocal;
	}

	/**
	 * Tipo da transação.
	 * <ul>
	 *	<li>Formato:
	 *		<ul>
	 *			<li>01 - Pagamento: a transação foi criada por um comprador fazendo um pagamento. Este é o tipo mais comum de transação que você irá receber.</li>
	 *			<li>11 - Assinatura: a transação foi criada a partir de uma cobrança de assinatura.</li>
	 *		</ul>
	 *	</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return TipoTransacao
	 */
	public TipoTransacao getTipo() {
		return tipo;
	}

	/**
	 * Informa o código representando o status da transação.
	 * <ul>
	 *	<li>Formato:
	 *		<ul>
	 *			<li>01 - Aguardando pagamento: o comprador iniciou a transação, mas até o momento o PagSeguro não recebeu nenhuma informação sobre o pagamento.</li>
	 *			<li>02 - Em análise: o comprador optou por pagar com um cartão de crédito e o PagSeguro está analisando o risco da transação.</li>
	 * 			<li>03 - Paga: a transação foi paga pelo comprador e o PagSeguro já recebeu uma confirmação da instituição financeira responsável pelo processamento.</li>
	 * 			<li>04 - Disponível: a transação foi paga e chegou ao final de seu prazo de liberação sem ter sido retornada e sem que haja nenhuma disputa aberta.</li>
	 * 			<li>05 - Em disputa: o comprador, dentro do prazo de liberação da transação, abriu uma disputa.</li>
	 * 			<li>06 - Devolvida: o valor da transação foi devolvido para o comprador.</li>
	 * 			<li>07 - Cancelada: a transação foi cancelada sem ter sido finalizada.</li>
	 *		</ul>
	 *	</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return SituacaoTransacao
	 */
	public SituacaoTransacao getSituacao() {
		return situacao;
	}

	/**
	 * Informa a origem do cancelamento da transação.
	 * <ul>
	 *	<li>Formato:
	 *		<ul>
	 *			<li>INTERNAL - PagSeguro</li>
	 *			<li>EXTERNAL - Instituições Financeiras</li>
	 *		</ul>
	 *	</li>
	 *	<li>Opcional, somente quando transactionStatus igual a 7.</li>
	 * </ul>
	 * 
	 * @return OrigemCancelamentoTransacao
	 */
	public OrigemCancelamentoTransacao getOrigemCancelamento() {
		return origemCancelamento;
	}

	/**
	 * Informa o momento em que ocorreu a última alteração no status da transação.
	 * <ul>
	 *	<li>Formato: Data/Hora, YYYY-MM-DDThh:mm:ss.sTZD.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Date
	 */
	public Date getDataUltimaMudancaSituacao() {
		return dataUltimaMudancaSituacao;
	}

	/**
	 * Data em que o valor da transação estará disponível na conta do vendedor.
	 * <ul>
	 *	<li>Formato: Data/Hora, YYYY-MM-DDThh:mm:ss.sTZD.</li>
	 *	<li>Opcional, somente quando o status da transação for um dos seguintes valores: Paga (3), Disponível (4), Em disputa (5) ou Devolvida (6).</li>
	 * </ul>
	 * 
	 * @return Date
	 */
	public Date getDataCreditoVendedor() {
		return dataCreditoVendedor;
	}

	/**
	 * Informa o valor bruto da transação (calculado pela soma dos preços de todos os itens presentes no pagamento).
	 * <ul>
	 *	<li>Formato: Decimal, com duas casas decimais separadas por ponto.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Double
	 */
	public Double getValorBruto() {
		return valorBruto;
	}

	/**
	 * Valor do desconto dado.
	 * <ul>
	 *	<li>Formato: Decimal, com duas casas decimais separadas por ponto.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Double
	 */
	public Double getDesconto() {
		return desconto;
	}

	/**
	 * Informa o valor total das taxas cobradas pelo PagSeguro nesta transação.
	 * <ul>
	 *	<li>Formato: Decimal, com duas casas decimais separadas por ponto.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Double
	 */
	public Double getTaxasPagSeguro() {
		return taxasPagSeguro;
	}

	/**
	 * Informa o valor líquido da transação, que corresponde ao valor bruto, menos o valor das taxas.
	 * Caso presente, o valor de extraAmount (que pode ser positivo ou negativo) também é considerado no cálculo.
	 * <ul>
	 *	<li>Formato: Decimal, com duas casas decimais separadas por ponto.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Double
	 */
	public Double getValorLiquido() {
		return valorLiquido;
	}

	/**
	 * Informa um valor extra que foi somado ou subtraído do valor pago pelo comprador. 
	 * Este valor é especificado por você no pagamento e pode representar um valor que 
	 * você quer cobrar separadamente do comprador ou um desconto que quer dar a ele.
	 * <ul>
	 *	<li>Formato: Decimal, com duas casas decimais separadas por ponto.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Double
	 */
	public Double getValorExtra() {
		return valorExtra;
	}

	/**
	 * Indica o número de parcelas que o comprador escolheu no pagamento com cartão de crédito.
	 * <ul>
	 *	<li>Formato: Inteiro.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Integer
	 */
	public Integer getParcelas() {
		return parcelas;
	}

	/**
	 * Aponta o número de itens contidos nesta transação.
	 * <ul>
	 *	<li>Formato: Inteiro.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Integer
	 */
	public Integer getNumeroItens() {
		return numeroItens;
	}

	/**
	 * Aponta o meio de pagamento da transação.
	 * <ul>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return MeioPagamento
	 */
	public MeioPagamento getMetodoPagamento() {
		return metodoPagamento;
	}
	
}
