package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.enums.SituacaoFaturamento;
import br.com.jopss.paypal.assinaturas.modelos.interfaces.EnvioPayPal;
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
public class EnvioAtivacao implements EnvioPayPal {
	
        @JsonProperty("")
	private Set<Ativacao> ativacoes = new HashSet<>();
        
	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public EnvioAtivacao() {
	}
        
        public EnvioAtivacao(SituacaoFaturamento situacaoFaturamento) {
                ativacoes.add(new Ativacao(situacaoFaturamento));
	}

        public Set<Ativacao> getAtivacoes() {
                return ativacoes;
        }
        
        public static class Ativacao{
                
                @JsonProperty("path")
                private String path;
                
                @JsonProperty("value")
                private ValorAtivacao valorAtivacao;
                
                @JsonProperty("op")
                private String operacao;
                
                public Ativacao() {
                }

                public Ativacao(SituacaoFaturamento situacaoFaturamento) {
                        this.path = "/";
                        this.operacao = "replace";
                        this.valorAtivacao = new ValorAtivacao(situacaoFaturamento);
                }

                public String getPath() {
                        return path;
                }

                public ValorAtivacao getValorAtivacao() {
                        return valorAtivacao;
                }

                public String getOperacao() {
                        return operacao;
                }
                
        }

        public static class ValorAtivacao{
                
                @JsonProperty("state")
                private String situacao;
                
                public ValorAtivacao() {
                }

                public ValorAtivacao(SituacaoFaturamento situacaoFaturamento) {
                        this.situacao = situacaoFaturamento.name();
                }

                public String getSituacao() {
                        return situacao;
                }
                
        }
}
