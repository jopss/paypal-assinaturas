package br.com.jopss.pagseguro.assinaturas.modelos;

import br.com.jopss.pagseguro.assinaturas.modelos.interfaces.RespostaPayPal;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.SituacaoAssinatura;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.TipoAssinatura;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta sobre dados de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "preApproval")
public class RespostaNotificacaoAssinatura implements RespostaPayPal {
	
	@XmlElement(name = "name")
	private String nomeAssinatura;
	
	@XmlElement(name = "code")
	private String codigoAssinatura;
	
	@XmlElement(name = "date")
	private Date data;
	
	@XmlElement(name = "tracker")
	private String identificadorPublico;
	
	@XmlElement(name = "status")
	private SituacaoAssinatura situacao;
	
	@XmlElement(name = "reference")
	private String referenciaInterna;
	
	@XmlElement(name = "lastEventDate")
	private Date lastEventDate;
	
	@XmlElement(name = "charge")
	private TipoAssinatura tipo;

	/**
	 * Nome/Descrição da assinatura.
	 * <ul>
	 *	<li>Formato: Texto.</li>
	 * </ul>
	 * 
	 * @return String
	 */
	public String getNomeAssinatura() {
		return nomeAssinatura;
	}

	/**
	 * Código identificador da assinatura.
	 * <ul>
	 *	<li>Formato: Texto.</li>
	 * </ul>
	 * 
	 * @return String
	 */
	public String getCodigoAssinatura() {
		return codigoAssinatura;
	}

	/**
	 * Data de criação/requisição da assinatura.
	 * <ul>
	 *	<li>Formato: Data/Hora, YYYY-MM-DDThh:mm:ss.sTZD.</li>
	 * </ul>
	 * 
	 * @return Date
	 */
	public Date getData() {
		return data;
	}

	/**
	 * Código identificador público. Utilizado para facilitar a diferenciação de múltiplas assinaturas com o mesmo nome/descrição.
	 * <ul>
	 *	<li>Formato: Texto.</li>
	 * </ul>
	 * 
	 * @return String
	 */
	public String getIdentificadorPublico() {
		return identificadorPublico;
	}

	/**
	 * Status atual da assinatura.
	 * 
	 * @return SituacaoAssinatura
	 */
	public SituacaoAssinatura getSituacao() {
		return situacao;
	}

	/**
	 * Identificador que foi usado para fazer referência a assinatura no momento de sua requisição/cobrança.
	 * <ul>
	 *	<li>Formato: Texto.</li>
	 * </ul>
	 * 
	 * @return String
	 */
	public String getReferenciaInterna() {
		return referenciaInterna;
	}

	/**
	 * Data/hora em que ocorreu a última alteração no status da assinatura.
	 * <ul>
	 *	<li>Formato: Data/Hora, YYYY-MM-DDThh:mm:ss.sTZD.</li>
	 * </ul>
	 * 
	 * @return Date
	 */
	public Date getLastEventDate() {
		return lastEventDate;
	}

	/**
	 * Indica se a assinatura é gerenciada pelo vendedor (manual) ou pelo PagSeguro (auto).
	 * 
	 * @return TipoAssinatura
	 */
	public TipoAssinatura getTipo() {
		return tipo;
	}
	
}
