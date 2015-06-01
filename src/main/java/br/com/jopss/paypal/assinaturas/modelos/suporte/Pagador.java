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
public class Pagador {
	
	@JsonProperty("payment_method")
	private String metodoPagamento;
	
	@JsonProperty("status")
	private String situacao;
        
        public Pagador() {
                this.metodoPagamento = MetodoPagamento.PAYPAL.name().toLowerCase();
        }

        public MetodoPagamento getMetodoPagamento() {
                if(metodoPagamento == null) return null;
                return MetodoPagamento.valueOf(metodoPagamento.toUpperCase());
        }

        public SituacaoPagamento getSituacao() {
                if(situacao == null) return null;
                return SituacaoPagamento.valueOf(situacao.toUpperCase());
        }
        
}
