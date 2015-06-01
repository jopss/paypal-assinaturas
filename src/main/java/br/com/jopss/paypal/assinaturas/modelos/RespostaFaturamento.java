package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.enums.SituacaoFaturamento;
import br.com.jopss.paypal.assinaturas.modelos.interfaces.RespostaPayPal;
import br.com.jopss.paypal.assinaturas.modelos.suporte.AtomLink;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RespostaFaturamento implements RespostaPayPal {
	
        @JsonProperty("id")
	private String id;
        
        @JsonProperty("name")
	private String nome;
	
        @JsonProperty("description")
	private String descricao;
        
	@JsonProperty("state")
	private String situacao;
        
        @JsonProperty("links")
	private final Set<AtomLink> acoes = new HashSet<>();
        
	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public RespostaFaturamento() {
	}
        
        @Override
        public boolean isValido() {
                return this.id!=null && !this.id.trim().equalsIgnoreCase("");
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

        public SituacaoFaturamento getSituacao() {
                if(situacao == null) return null;
                return SituacaoFaturamento.valueOf(situacao.toUpperCase());
        }
        
}
