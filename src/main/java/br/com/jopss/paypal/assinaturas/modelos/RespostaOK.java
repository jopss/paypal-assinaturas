package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.interfaces.RespostaPayPal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta sobre a pré requisição, passo inicial ao cadastramento de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class RespostaOK  implements RespostaPayPal {
	
        @JsonIgnore
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    
	private String situacao = "OK";
        
	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public RespostaOK() {
	}
        
        @Override
        public boolean isValido() {
                return true;
        }

        public String getSituacao() {
                return situacao;
        }
        
}
