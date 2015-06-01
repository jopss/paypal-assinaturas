package br.com.jopss.paypal.assinaturas.modelos.suporte;

import br.com.jopss.paypal.assinaturas.modelos.enums.MetodoPagamento;
import br.com.jopss.paypal.assinaturas.modelos.enums.SituacaoPagamento;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plano {
	
	@JsonProperty("id")
	private String id;
	
        public Plano() {
        }

        public Plano(String id) {
                this.id = id;
        }

        public String getId() {
                return id;
        }
        
}
