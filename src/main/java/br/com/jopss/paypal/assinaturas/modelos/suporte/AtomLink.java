package br.com.jopss.paypal.assinaturas.modelos.suporte;

import br.com.jopss.paypal.assinaturas.modelos.enums.LinkRel;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe que provê as ações REST dos objetos de resposta. Assim o cliente pode decidir continuar ou mudar de fluxo.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement(namespace = "http://www.w3.org/2005/Atom")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
public class AtomLink {

	private String rel;
	private String href;
	private String method;

	/**
	 * Construtor padrão.
	 */
	public AtomLink() {
	}

	/**
	 * Retorna o Rel do link da ação, ou seja, a descrição da ação.
	 * @return String.
	 */
	public LinkRel getRel() {
                if(rel == null) return null;
		return LinkRel.valueOf(rel.toUpperCase());
	}

	/**
	 * Retorna o Href da ação, ou seja, o link real de acesso HTTP.
	 * @return String.
	 */
	public String getHref() {
		return href;
	}

        public String getMethod() {
                return method;
        }

}
