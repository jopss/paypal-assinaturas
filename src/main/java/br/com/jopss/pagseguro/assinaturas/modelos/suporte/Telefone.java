package br.com.jopss.pagseguro.assinaturas.modelos.suporte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe modelo de suporte com dados do telefone do cliente da assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "phone")
public class Telefone {
	
	@XmlElement(name = "areaCode")
	private Integer codigoArea;
	
	@XmlElement(name = "number")
	private Integer numero;

	/**
	 * Código de área (DDD) do comprador.
	 * <ul>
	 *	<li>Formato: Inteiro, 2 dígitos correspondente a um DDD válido.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param codigoArea Integer
	 */
	public void setCodigoArea(Integer codigoArea) {
		this.codigoArea = codigoArea;
	}

	/**
	 * Número de telefone do comprador.
	 * <ul>
	 *	<li>Formato: Inteiro, entre 7 e 9 dígitos.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param numero Integer
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
}
