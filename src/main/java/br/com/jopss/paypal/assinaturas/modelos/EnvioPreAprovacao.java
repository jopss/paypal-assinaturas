package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.enums.TipoPreRequisicao;
import br.com.jopss.paypal.assinaturas.modelos.interfaces.EnvioPayPal;
import br.com.jopss.paypal.assinaturas.modelos.suporte.DefinicaoPagamento;
import br.com.jopss.paypal.assinaturas.modelos.suporte.PreferenciasComerciais;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de requisição para a pré requisição, passo inicial ao cadastramento de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
@JsonInclude(Include.NON_NULL)
public class EnvioPreAprovacao implements EnvioPayPal {
	
        @JsonProperty("name")
	private String nome;
	
        @JsonProperty("description")
	private String descricao;
	
        @JsonProperty("type")
	private String tipo;
        
        @JsonProperty("payment_definitions")
	private Set<DefinicaoPagamento> definicoesPagamento = new HashSet<>();
        
        @JsonProperty("merchant_preferences")
	private PreferenciasComerciais preferenciasComerciais;
        
	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public EnvioPreAprovacao() {
	}
	
	/**
	 * Construtor que recebe como dependencia os dados da assinatura.
	 */
	public EnvioPreAprovacao(DefinicaoPagamento definicaoPagamento, PreferenciasComerciais preferenciasComerciais) {
		this.tipo = TipoPreRequisicao.FIXED.name().toLowerCase();
                this.definicoesPagamento.add(definicaoPagamento);
                this.preferenciasComerciais = preferenciasComerciais;
	}
        
        public void setTipo(TipoPreRequisicao tipo) {
                this.tipo = tipo.name().toLowerCase();
        }
        
        public void setNome(String nome) {
                this.nome = nome;
        }

        public void setDescricao(String descricao) {
                this.descricao = descricao;
        }

        public DefinicaoPagamento getDefinicaoPagamento() {
                return definicoesPagamento.iterator().next();
        }

        public PreferenciasComerciais getPreferenciasComerciais() {
                return preferenciasComerciais;
        }

        public String getNome() {
                return nome;
        }

        public String getDescricao() {
                return descricao;
        }

}
