package br.com.jopss.pagseguro.assinaturas.modelos.suporte;

import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.PeriodoPreAprovacao;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.TipoAssinatura;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Classe modelo de suporte com dados da aprovação de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "preApproval")
public class PreAprovacao {
	
        @XmlTransient
	private final DecimalFormat fmt = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.US));
	
	@XmlElement(name = "charge")
	private TipoAssinatura tipo;
	
	@XmlElement(name = "name")
	private String nome;
	
	@XmlElement(name = "details")
	private String descricao;
	
	@XmlElement(name = "amountPerPayment")
	private String valorCobrancaPeriodica;
	
	@XmlElement(name = "maxAmountPerPayment")
	private String valorMaximoCobrancaPeriodica;
	
	@XmlElement(name = "period")
	private PeriodoPreAprovacao periodo;
	
	@XmlElement(name = "maxPaymentsPerPeriod")
	private Integer limiteCobrancasPorPeriodo;
	
	@XmlElement(name = "maxAmountPerPeriod")
	private String valorLimiteMensal;
	
	@XmlElement(name = "initialDate")
	private Date dataInicioCobranca;
	
	@XmlElement(name = "finalDate")
	private Date dataFinalCobranca;
	
	@XmlElement(name = "maxTotalAmount")
	private String limiteValorAssinatura;

	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public PreAprovacao() {
	}
	
	/**
	 * 
	 * @param nome String. Nome/Identificador da assinatura. Formato: 100 caracteres.
	 * @param periodo PeriodoPreAprovacao. Periodicidade da cobrança.
	 * @param valorMensalidade Double.
	 * @param dataInicioCobranca Date. Início da vigência da assinatura. Assume valores maiores que a data atual e menores ou iguais a data atual + 2 anos. 
	 * @param dataFinalCobranca Date. Fim da vigência da assinatura. Assume valores maiores que a data atual ou maiores que o valor definido em 'initialDate', não podendo ter uma diferença superior a 2 anos da data de início.
	 * @param limiteValorAssinatura Double. Valor máximo que pode ser cobrado durante a vigência da assinatura. 
	 */
	public PreAprovacao(String nome, PeriodoPreAprovacao periodo, Double valorMensalidade, Date dataInicioCobranca, Date dataFinalCobranca, Double limiteValorAssinatura) {
		this.periodo = periodo;
		this.dataInicioCobranca = dataInicioCobranca;
		this.dataFinalCobranca = dataFinalCobranca;
		this.limiteValorAssinatura = fmt.format(limiteValorAssinatura);
		this.nome = nome;
		this.valorCobrancaPeriodica= fmt.format(valorMensalidade);
                this.valorLimiteMensal = fmt.format(valorMensalidade);
	}

        public PreAprovacao(String nome, PeriodoPreAprovacao periodo, Double valorMensalidade, Date dataFinalCobranca, Double limiteValorAssinatura) {
		this.periodo = periodo;
		this.dataFinalCobranca = dataFinalCobranca;
		this.nome = nome;
		this.valorCobrancaPeriodica= fmt.format(valorMensalidade);
		this.limiteValorAssinatura = fmt.format(limiteValorAssinatura);
	}
        
	/**
	 * Valor máximo que pode ser cobrado por mês de vigência da assinatura, independente de sua periodicidade. 
	 * <ul>
	 *	<li>Obrigatória quando a assinatura é gerenciada pelo vendedor (charge = manual).</li>
	 * </ul>
	 * 
	 * @param valorLimiteMensal Double
	 */
	public void setValorLimiteMensal(Double valorLimiteMensal) {
		this.valorLimiteMensal = fmt.format(valorLimiteMensal);
	}
	
	/**
	 * Indica se a assinatura será gerenciada pelo PagSeguro (auto) ou pelo 
	 * Vendedor (manual). Neste caso usaremos o valor "auto". 
	 * <ul>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param tipo TipoAssinatura
	 */
	public void setTipo(TipoAssinatura tipo) {
		this.tipo = tipo;
	}

	/**
	 * Detalhes/Descrição da assinatura. 
	 * <ul>
	 *	<li>Formato: Inteiro, maior ou igual a 1 e menor ou igual a 1000000.</li>
	 *	<li>Obrigatório para o modelo automatico.</li>
	 * </ul>
	 * 
	 * @param descricao String
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Valor exato de cada cobrança. 
	 * Não pode ser utilizado em conjunto com 'maxAmountPerPayment.'
	 * <ul>
	 *	<li>Formato: Decimal, com duas casas decimais separadas por ponto. Deve ser maior ou igual a 1.00 e menor ou igual a 1000000.00.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param valorMaximoCobrancaPeriodica Double
	 */
	public void setValorMaximoCobrancaPeriodica(Double valorMaximoCobrancaPeriodica) {
		this.valorMaximoCobrancaPeriodica = fmt.format(valorMaximoCobrancaPeriodica);
	}

	/**
	 * Número máximo de cobranças que podem ser realizadas por período. 
	 * <ul>
	 *	<li>Formato: Decimal, com duas casas decimais separadas por ponto. Deve ser maior ou igual a 1.00 e menor ou igual a 1000000.00.</li>
	 *	<li>Opcional, podendo ser utilizado apenas quando a assinatura é gerenciada pelo vendedor (charge = manual).</li>
	 * </ul>
	 * 
	 * @param limiteCobrancasPorPeriodo Integer
	 */
	public void setLimiteCobrancasPorPeriodo(Integer limiteCobrancasPorPeriodo) {
		this.limiteCobrancasPorPeriodo = limiteCobrancasPorPeriodo;
	}
	
}
