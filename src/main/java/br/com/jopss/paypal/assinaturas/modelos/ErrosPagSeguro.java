package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.interfaces.RespostaPayPal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta de erros do PagSeguro.
 *
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class ErrosPagSeguro implements RespostaPayPal {

        @JsonProperty("name")
        private String nome;

        @JsonProperty("error")
        private String erro;
        
        @JsonProperty("error_description")
        private String descricaoErro;
        
        @JsonProperty("message")
        private String mensagem;

        @JsonProperty("information_link")
        private String link;

        @JsonProperty("details")
	private String detalhes;

        @Override
        public boolean isValido() {
                return true;
        }

        public String getNomeCompleto() {
                String txt = "Retorno da interligação de pagamento: "+getNome() + ". "+getMensagem();
                if(detalhes!=null){
                       txt = txt+" - "+detalhes;
                }
                txt = txt+". Por favor, tente novamente ou procure suporte.";
                return txt;
        }
        
        public String getNome() {
                if(nome == null){
                        return erro;
                }
                return nome;
        }

        public String getMensagem() {
                if(mensagem == null){
                        return descricaoErro;
                }
                return mensagem;
        }

        public String getLink() {
                return link;
        }

        public String getDetalhes() {
                return detalhes;
        }

        @Override
        public String toString() {
                return "Erro{" + "nome=" + nome + ", mensagem=" + mensagem + '}';
        }

}
