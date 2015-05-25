package br.com.jopss.pagseguro.assinaturas.modelos.suporte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe modelo de suporte com dados do cliente da assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sender")
public class Cliente {
	
	@XmlElement(name = "name")
	private String nome;
	
	@XmlElement(name = "email")
	private String email;

	@XmlElement(name = "phone")
	private Telefone telefone;
	
	@XmlElement(name = "address")
	private Endereco endereco;
	
	/**
	 * Nome completo do comprador.
	 * <ul>
	 *	<li>Formato: Texto, com no mínimo duas sequências de strings, de 50 caracteres.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param nome String
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * E-mail do comprador.
	 * <ul>
	 *	<li>Formato: Texto, um e-mail válido, com limite de 60 caracteres. </li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param email String
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Telefone do comprador.
	 * <ul>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param telefone Telefone
	 */
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	/**
	 * Endereco do comprador.
	 * <ul>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param endereco Endereco
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
}
