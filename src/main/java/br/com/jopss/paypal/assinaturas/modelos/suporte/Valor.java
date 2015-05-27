package br.com.jopss.paypal.assinaturas.modelos.suporte;

import br.com.jopss.paypal.assinaturas.modelos.enums.Moeda;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Valor {
	
	@JsonProperty("value")
	private String valor;
	
	@JsonProperty("currency")
	private String moeda;
        
        public Valor() {
        }

        public Valor(Double valor, Moeda moeda) {
                this.valor = ""+valor;
                this.moeda = moeda.name();
        }

        public String getValor() {
                return valor;
        }

        public Moeda getMoeda() {
                if(moeda == null) return null;
                return Moeda.valueOf(moeda);
        }
        
}
