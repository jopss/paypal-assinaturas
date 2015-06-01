package br.com.jopss.paypal.assinaturas.modelos;

import br.com.jopss.paypal.assinaturas.modelos.interfaces.EnvioPayPal;
import br.com.jopss.paypal.assinaturas.modelos.suporte.Pagador;
import br.com.jopss.paypal.assinaturas.modelos.suporte.Plano;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de requisição para a pré requisição, passo inicial ao cadastramento de uma assinatura.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
@JsonInclude(Include.NON_NULL)
public class EnvioContrato implements EnvioPayPal {
	
        @JsonIgnore
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        
        @JsonProperty("name")
	private String nome;
	
        @JsonProperty("description")
	private String descricao;
	
        @JsonProperty("start_date")
	private String dataInicioPagamento;
        
        @JsonProperty("payer")
        private Pagador pagador;
        
        @JsonProperty("plan")
        private Plano plano;
        
	/**
	 * Construtor vazio necessario para formatacao automatica.
	 */
	public EnvioContrato() {
	}

        public EnvioContrato(EnvioPreAprovacao preAprovacao, Date dataInicioPagamento, RespostaPreAprovacao respostaPreAprovacao) {
                this.nome = preAprovacao.getNome();
                this.descricao = preAprovacao.getDescricao();
                this.dataInicioPagamento = dateFormat.format(dataInicioPagamento);
                this.pagador = new Pagador();
                this.plano = new Plano(respostaPreAprovacao.getId());
        }

        public Date getDataInicioPagamento() {
                try {
                        return dateFormat.parse(dataInicioPagamento);
                } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                }
        }

        public String getNome() {
                return nome;
        }

        public String getDescricao() {
                return descricao;
        }
        
        public Pagador getPagador() {
                return pagador;
        }

        public Plano getPlano() {
                return plano;
        }

}
