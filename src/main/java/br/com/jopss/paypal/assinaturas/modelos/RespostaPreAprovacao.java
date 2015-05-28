package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.interfaces.RespostaPayPal;
import br.com.jopss.paypal.assinaturas.modelos.suporte.AtomLink;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta sobre a pré requisição, passo inicial ao cadastramento de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class RespostaPreAprovacao extends EnvioPreAprovacao implements RespostaPayPal {
	
        @JsonIgnore
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    
        @JsonProperty("id")
	private String id;
        
        @JsonProperty("create_time")
	private String dataCriacao;
        
        @JsonProperty("update_time")
	private String dataAtualizacao;

        @JsonProperty("links")
	private final Set<AtomLink> acoes = new HashSet<>();
        
	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public RespostaPreAprovacao() {
	}
        
        @Override
        public boolean isValido() {
                return this.id!=null && !this.id.trim().equalsIgnoreCase("");
        }
        
        public String getId() {
                return id;
        }

        public Date getDataCriacao() {
                try {
                        return dateFormat.parse(dataCriacao);
                } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                }
        }

        public Date getDataAtualizacao() {
                try {
                        return dateFormat.parse(dataAtualizacao);
                } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                }
        }
        
}
