package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.interfaces.RespostaPayPal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta sobre a pré requisição, passo inicial ao cadastramento de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class RespostaToken implements RespostaPayPal {
	
        @JsonProperty("scope")
	private String escopo;
        
        @JsonProperty("access_token")
	private String token;
        
        @JsonProperty("token_type")
	private String tipo;

        @JsonProperty("app_id")
	private String identificacaoApp;
        
        @JsonProperty("expires_in")
	private String expiraEm;
        
	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public RespostaToken() {
	}

        public String getEscopo() {
                return escopo;
        }

        public String getToken() {
                return token;
        }

        public String getTipo() {
                return tipo;
        }

        public String getIdentificacaoApp() {
                return identificacaoApp;
        }

        public Long getExpiraEm() {
                if(expiraEm == null) return null;
                return Long.parseLong(expiraEm);
        }
        
        
}
