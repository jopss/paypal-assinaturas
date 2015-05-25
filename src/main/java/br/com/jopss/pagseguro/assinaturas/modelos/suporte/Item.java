package br.com.jopss.pagseguro.assinaturas.modelos.suporte;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Classe modelo de suporte com dados do item de produto e de um pagamento inicial associado a assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class Item {
	
        @XmlTransient
	private final DecimalFormat fmt = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.US));
        
	@XmlElement(name = "id")
	private Integer id = 1;
	
	@XmlElement(name = "description")
	private String descricao;

	@XmlElement(name = "amount")
	private String valor;
	
	@XmlElement(name = "quantity")
	private Integer quantidade = 1;
	
	@XmlElement(name = "weight")
	private Integer peso;
        
	/**
	 * Código do produto e do pagamento inicial.
	 * <ul>
	 *	<li>Formato: Integer.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @param nome Integer
	 */
        public void setId(Integer id) {
                this.id = id;
        }

	/**
	 * Descrição do produto e do pagamento inicial.
	 * <ul>
	 *	<li>Formato: String.</li>
	 *	<li>Obrigatorio.</li>
	 * </ul>
	 * 
	 * @param nome String
	 */
        public void setDescricao(String descricao) {
                this.descricao = descricao;
        }
	
	/**
	 * Valor do produto e do pagamento inicial.
	 * <ul>
	 *	<li>Obrigatória.</li>
	 * </ul>
	 * 
	 * @param valor Double
	 */
	public void setValor(Double valor) {
		this.valor = fmt.format(valor);
	}

	/**
	 * Quantidade do produto e do pagamento inicial.
	 * <ul>
	 *	<li>Formato: Integer.</li>
	 *	<li>Obrigatorio. Livre, com limite de 100 caracteres. O valor deve ser maior ou igual a 1 e menor ou igual a 999.</li>
	 * </ul>
	 * 
	 * @param nome Integer
	 */
        public void setQuantidade(Integer quantidade) {
                this.quantidade = quantidade;
        }

	/**
	 * Peso do produto.
	 * <ul>
	 *	<li>Formato: Integer.</li>
	 *	<li>Opcional.</li>
	 * </ul>
	 * 
	 * @param nome Integer
	 */
        public void setPeso(Integer peso) {
                this.peso = peso;
        }
        
}
