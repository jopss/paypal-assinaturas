package br.com.jopss.pagseguro.assinaturas.modelos;

import br.com.jopss.pagseguro.assinaturas.modelos.interfaces.EnvioPayPal;
import br.com.jopss.pagseguro.assinaturas.modelos.suporte.Item;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de requisição para uma cobraça de mensalidade.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "payment")
public class EnvioCobranca implements EnvioPayPal {
	
	@XmlElement(name = "preApprovalCode")
	private String codigoAssinatura;
	
	@XmlElement(name = "reference")
	private String idReferenciaLocal;
        
        @XmlElementWrapper(name="items")
	@XmlElement(name = "item")
	private final Set<Item> itens = new HashSet<>();
	
	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public EnvioCobranca() {
	}
	
	/**
	 * Construtor que recebe como dependencia os dados da cobrança.
	 * 
	 * @param codigoAssinatura String. Código da assinatura, concedida previamente, que identifica a cobrança sendo realizada. Texto de 32 caracteres.
	 * @param idReferenciaLocal String.
	 * @param descricao String. Descreve o item a ser cobrado. Texto de 100 caracteres.
	 * @param valor Double. Representa o preço a cobrado.
	 */
	public EnvioCobranca(String codigoAssinatura, String idReferenciaLocal, String descricao, Double valor) {
		this.codigoAssinatura=codigoAssinatura;
		this.idReferenciaLocal=idReferenciaLocal;
                
		//o identificador e a quantidade esta fixa, pois para uma única mensalidade, não muda.
		Item itemUnico = new Item();
		itemUnico.setId(1);
		itemUnico.setQuantidade(1);
		itemUnico.setDescricao(descricao);
		itemUnico.setValor(valor);
	}
	
}
