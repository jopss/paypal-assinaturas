package br.com.jopss.pagseguro.assinaturas.modelos;

import br.com.jopss.pagseguro.assinaturas.modelos.interfaces.RespostaPayPal;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de resposta de erros do PagSeguro.
 * 
 * @author João Paulo Sossoloti.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "errors")
public class ErrosPagSeguro implements RespostaPayPal {
	
	@XmlElement(name = "error")
	private Set<Erro> erros = new HashSet<>();

	public Set<Erro> getErros() {
		return erros;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement(name = "error")
	public static class Erro{
		
		@XmlElement(name = "code")
		private Integer codigo;
		
		@XmlElement(name = "message")
		private String mensagem;
		
		public String getCodigoEMensagem(){
			return codigo+" - "+mensagem;
		}

		public Integer getCodigo() {
			return codigo;
		}

		public String getMensagem() {
			return mensagem;
		}

		@Override
		public String toString() {
			return "Erro{" + "codigo=" + codigo + ", mensagem=" + mensagem + '}';
		}
	}

	@Override
	public String toString() {
		return "ErrosPagSeguro{" + "erros=" + erros + '}';
	}
	
}
