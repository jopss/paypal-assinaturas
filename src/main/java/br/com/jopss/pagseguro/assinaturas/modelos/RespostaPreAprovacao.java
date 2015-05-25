package br.com.jopss.pagseguro.assinaturas.modelos;

import br.com.jopss.pagseguro.assinaturas.modelos.interfaces.RespostaPayPal;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta sobre a pré requisição, passo inicial ao cadastramento de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "preApprovalRequest")
public class RespostaPreAprovacao implements RespostaPayPal {
	
	@XmlElement(name = "code")
	private String codigo;
	
	@XmlElement(name = "date")
	private Date data;

	/**
	 * Código de requisição criado. Este código deve ser usado para direcionar o comprador para o fluxo de aprovação. 
	 * <ul>
	 *	<li>Formato: Texto com 32 caracteres.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @return String
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Data da requisição.
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
