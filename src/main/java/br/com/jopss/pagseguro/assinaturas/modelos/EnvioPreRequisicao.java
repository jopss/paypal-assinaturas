package br.com.jopss.pagseguro.assinaturas.modelos;

import br.com.jopss.pagseguro.assinaturas.modelos.suporte.Cliente;
import br.com.jopss.pagseguro.assinaturas.modelos.interfaces.EnvioPayPal;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.PreAprovacao;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.TipoAssinatura;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de requisição para a pré requisição, passo inicial ao cadastramento de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "preApprovalRequest")
public class EnvioPreRequisicao implements EnvioPayPal {
	
	@XmlElement(name = "redirectURL")
	private String urlRedirecionamentoAposConfirmacao;
	
	@XmlElement(name = "reviewURL")
	private String urlRevisao;
	
	@XmlElement(name = "reference")
	private String idReferenciaLocal;
	
	@XmlElement(name = "sender")
	private Cliente cliente;
	
	@XmlElement(name = "preApproval")
	private PreAprovacao preAprovacao;

	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public EnvioPreRequisicao() {
	}
	
	/**
	 * Construtor que recebe como dependencia os dados da assinatura.
	 * 
	 * @param preAprovacao PreAprovacao com os dados da assinatura.
	 */
	public EnvioPreRequisicao(PreAprovacao preAprovacao) {
                preAprovacao.setTipo(TipoAssinatura.AUTOMATICO);
		this.preAprovacao = preAprovacao;
	}

	/**
	 * URL para onde o comprador será redirecionado após a finalização do fluxo de assinatura. 
	 * <ul>
	 *	<li>Formato: Uma URL válida, com limite de 255 caracteres.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param urlRedirecionamentoAposConfirmacao String
	 */
	public void setUrlRedirecionamentoAposConfirmacao(String urlRedirecionamentoAposConfirmacao) {
		this.urlRedirecionamentoAposConfirmacao = urlRedirecionamentoAposConfirmacao;
	}

	/**
	 * URL para onde o comprador será redirecionado, durante o fluxo de aprovação, caso deseje alterar/revisar as regras da assinatura. 
	 * <ul>
	 *	<li>Formato: Uma URL válida, com limite de 255 caracteres.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param urlRevisao String
	 */
	public void setUrlRevisao(String urlRevisao) {
		this.urlRevisao = urlRevisao;
	}

	/**
	 * Código/Identificador para fazer referência a assinatura em seu sistema.
	 * <ul>
	 *	<li>Formato: Texto com limite de 200 caracteres.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param idReferenciaLocal String
	 */
	public void setIdReferenciaLocal(String idReferenciaLocal) {
		this.idReferenciaLocal = idReferenciaLocal;
	}

	/**
	 * Dados do cliente da assinatura.
	 * <ul>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param cliente Cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
