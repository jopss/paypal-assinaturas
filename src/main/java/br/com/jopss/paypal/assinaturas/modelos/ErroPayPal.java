package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.interfaces.RespostaPayPal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta de erros do PagSeguro.
 *
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class ErroPayPal implements RespostaPayPal {

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
	private Set<DetalhesErros> detalhes = new HashSet<>();

        @JsonProperty("debug_id")
	private String debugId;
        
        @Override
        public boolean isValido() {
                return true;
        }

        public String getMensagemCompleta() {
                String txt = "Retorno da interligação de pagamento: "+getMensagem()+" ("+getNome()+").";
                if(debugId!=null){
                       txt = txt+" ID Erro: '"+debugId+"'.";
                }
                txt = txt+" Por favor, tente novamente ou procure suporte.";
                if(detalhes!=null && !detalhes.isEmpty()){
                       txt = txt+" Detalhes Técnicos: ";
                       
                       //TODO: melhor este retorno de dados. Talvez escrever HTML aqui eh uma boa?
                       for(DetalhesErros e : detalhes){
                               txt = txt+" Campo: '"+ e.campo +"', ";
                               txt = txt+" Erro: '"+ e.erro +"'.";
                       }
                }
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

        public Set<DetalhesErros> getDetalhes() {
                return detalhes;
        }
        
        @Override
        public String toString() {
                return "Erro{" + "nome=" + (nome == null ? erro : nome) + ", mensagem=" + (mensagem == null ? descricaoErro : mensagem) + '}';
        }

        @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
        public static class DetalhesErros{
                
                @JsonProperty("field")
                private String campo;

                @JsonProperty("issue")
                private String erro;

                public String getCampo() {
                        return campo;
                }

                public String getErro() {
                        return erro;
                }
        
        }
        
}
