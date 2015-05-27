package br.com.jopss.paypal.assinaturas.modelos.suporte;

import br.com.jopss.paypal.assinaturas.modelos.enums.Moeda;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class PreferenciasComerciais {
	
	@JsonProperty("return_url")
	private String urlRetorno;
	
	@JsonProperty("cancel_url")
	private String urlCancelar;
        
	@JsonProperty("notify_url")
	private String urlNotificacao;
        
	@JsonProperty("auto_bill_amount")
	private String pagamentoRecorrente;
        
	@JsonProperty("initial_fail_amount_action")
	private String acaoAoFalharPagamentoInicial;
 
	@JsonProperty("max_fail_attempts")
	private String qtdMaximaTentativas;

        @JsonProperty("setup_fee")
	private Valor valor;
        
        public PreferenciasComerciais() {
                this.pagamentoRecorrente = "YES";
                this.acaoAoFalharPagamentoInicial = "CANCEL";
                this.qtdMaximaTentativas = ""+0;
        }

        public void setValorInicialDiferenciado(Double valor, Moeda moeda) {
                this.valor = new Valor(valor, moeda);
        }
        
        public void setUrlRetorno(String urlRetorno) {
                this.urlRetorno = urlRetorno;
        }

        public void setUrlCancelar(String urlCancelar) {
                this.urlCancelar = urlCancelar;
        }

        public void setUrlNotificacao(String urlNotificacao) {
                this.urlNotificacao = urlNotificacao;
        }

        public void setQtdMaximaTentativas(Integer qtdMaximaTentativas) {
                this.qtdMaximaTentativas = ""+qtdMaximaTentativas;
        }

        public void setContinuarAcaoAoFalharPagamentoInicial(Boolean continuar) {
                if(continuar){
                        this.acaoAoFalharPagamentoInicial = "CONTINUE";
                }else{
                        this.acaoAoFalharPagamentoInicial = "CANCEL";
                }
        }
        
        public BigDecimal getValor() {
                return new BigDecimal(valor.getValor());
        }
        
}
