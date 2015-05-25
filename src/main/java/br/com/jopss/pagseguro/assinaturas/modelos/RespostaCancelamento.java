package br.com.jopss.pagseguro.assinaturas.modelos;

import br.com.jopss.pagseguro.assinaturas.modelos.interfaces.RespostaPayPal;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.SituacaoAssinatura;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta sobre cancelamento de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class RespostaCancelamento implements RespostaPayPal {
	
	@XmlElement(name = "status")
	private String situacao;
	
	@XmlElement(name = "date")
	private Date data;

	/**
	 * Resposta ao pedido de cancelamento.
	 * <ul>
	 *	<li>Formato: Texto.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return SituacaoAssinatura
	 */
	public SituacaoAssinatura getSituacao() {
                if(situacao == null){
                        return null;
                }
		return SituacaoAssinatura.valueOf(situacao);
	}

	/**
	 * Data de solicitação do cancelamento.
	 * <ul>
	 *	<li>Formato: Data/Hora, YYYY-MM-DDThh:mm:ss.sTZD.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return Date
	 */
	public Date getData() {
		return data;
	}
	
}
