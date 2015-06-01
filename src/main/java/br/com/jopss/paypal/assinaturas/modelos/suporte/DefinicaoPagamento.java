package br.com.jopss.paypal.assinaturas.modelos.suporte;

import br.com.jopss.paypal.assinaturas.modelos.enums.Moeda;
import br.com.jopss.paypal.assinaturas.modelos.enums.Periodo;
import br.com.jopss.paypal.assinaturas.modelos.enums.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author João Paulo Sossoloti.
 */
@XmlRootElement
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefinicaoPagamento {
	
        @JsonProperty("id")
	private String id;
        
	@JsonProperty("name")
	private String nome;
	
	@JsonProperty("type")
	private String tipo;
        
	@JsonProperty("frequency")
	private String frequencia;
        
	@JsonProperty("frequency_interval")
	private String intervaloFrequencia;
 
	@JsonProperty("cycles")
	private String ciclos;

        @JsonProperty("amount")
	private Valor valor;
        
        @JsonProperty("charge_models")
        private Set<String> modo = new HashSet<>();
        
        public DefinicaoPagamento() {}
        
        public DefinicaoPagamento(Double valor, Moeda moeda) {
                this.tipo = TipoPagamento.REGULAR.name();
                this.frequencia = Periodo.MONTH.name();
                this.ciclos = ""+0;
                this.valor = new Valor(valor, moeda);
        }
        
        public void setIndicaPeriodoDeTeste(Boolean teste) {
                if(teste){
                        this.tipo = TipoPagamento.TRIAL.name();
                }else{
                        this.tipo = TipoPagamento.REGULAR.name();
                }
        }
        
        public void setFrequencia(Periodo periodo) {
                this.frequencia = periodo.name();
        }
        
        public void setNome(String nome) {
                this.nome = nome;
        }


        public void setIntervaloFrequencia(Integer intervaloFrequencia) {
                this.intervaloFrequencia = ""+intervaloFrequencia;
        }

        public void setCiclos(Integer ciclos) {
                this.ciclos = ""+ciclos;
        }

        public String getId() {
                return id;
        }

        public String getNome() {
                return nome;
        }

        public TipoPagamento getTipo() {
                if(tipo == null) return null;
                return TipoPagamento.valueOf(tipo.toUpperCase());
        }

        public Periodo getFrequencia() {
                if(frequencia == null) return null;
                return Periodo.valueOf(frequencia.toUpperCase());
        }

        public Integer getIntervaloFrequencia() {
                if(intervaloFrequencia == null) return null;
                return Integer.parseInt(intervaloFrequencia);
        }

        public Integer getCiclos() {
                if(ciclos == null) return null;
                return Integer.parseInt(ciclos);
        }
        
        public BigDecimal getValor() {
                return new BigDecimal(valor.getValor());
        }
        
}
