package br.com.jopss.pagseguro.assinaturas.modelos.suporte;

import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.Pais;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums.UF;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe modelo de suporte com dados do endereço do cliente da assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "address")
public class Endereco {
	
	@XmlElement(name = "street")
	private String rua;
	
	@XmlElement(name = "number")
	private String numero;
	
	@XmlElement(name = "complement")
	private String complemento;
	
	@XmlElement(name = "district")
	private String bairro;
	
	@XmlElement(name = "postalCode")
	private Integer cep;
	
	@XmlElement(name = "city")
	private String cidade;
	
	@XmlElement(name = "state")
	private UF estado;
	
	/**
	 * País do endereço do comprador. Reconhece apenas o valor BRA. 
	 */
	@XmlElement(name = "country")
	private final Pais pais = Pais.BRA;

	/**
	 * Endereço do comprador.
	 * <ul>
	 *	<li>Formato: Texto, de 80 caracteres.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param rua String
	 */
	public void setRua(String rua) {
		this.rua = rua;
	}

	/**
	 * Número do endereço do comprador. 
	 * <ul>
	 *	<li>Formato: Texto, de 20 caracteres.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param numero String
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Complemento (bloco, apartamento, etc.) do endereço do comprador.
	 * <ul>
	 *	<li>Formato: Texto, de 40 caracteres.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param complemento String
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * Bairro do endereço do comprador.
	 * <ul>
	 *	<li>Formato: Texto, de 60 caracteres.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param bairro String
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * CEP do endereço do comprador.
	 * <ul>
	 *	<li>Formato: Número de 8 dígitos correspondente a um CEP válido.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param cep Integer
	 */
	public void setCep(Integer cep) {
		this.cep = cep;
	}

	/**
	 * Cidade do endereço do comprador.
	 * <ul>
	 *	<li>Formato: Texto, deve ser um nome válido de cidade do Brasil, com no mínimo 2 e no máximo 60 caracteres.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param cidade String
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * Unidade Federativa do endereço do comprador. 
	 * <ul>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param estado UF
	 */
	public void setEstado(UF estado) {
		this.estado = estado;
	}
	
}
