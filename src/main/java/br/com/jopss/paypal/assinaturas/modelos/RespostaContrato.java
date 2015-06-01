package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.interfaces.RespostaPayPal;
import br.com.jopss.paypal.assinaturas.modelos.suporte.AtomLink;
import br.com.jopss.paypal.assinaturas.modelos.suporte.Plano;
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
public class RespostaContrato implements RespostaPayPal {
	
        @JsonIgnore
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    
        @JsonProperty("id")
	private String id;
        
        @JsonProperty("name")
	private String nome;
	
        @JsonProperty("description")
	private String descricao;

        @JsonProperty("start_date")
	private String dataInicioPagamento;
        
        @JsonProperty("plan")
        private Plano plano;
        
        @JsonProperty("links")
	private final Set<AtomLink> acoes = new HashSet<>();
        
	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public RespostaContrato() {
	}
        
        @Override
        public boolean isValido() {
                return this.plano!=null && this.plano.getId()!=null && !this.plano.getId().trim().equalsIgnoreCase("");
        }
        
        public Date getDataInicioPagamento() {
                try {
                        return dateFormat.parse(dataInicioPagamento);
                } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                }
        }

        public String getId() {
                return id;
        }

        public String getNome() {
                return nome;
        }

        public String getDescricao() {
                return descricao;
        }

        public Plano getPlano() {
                return plano;
        }
        
}
