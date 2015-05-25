package br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * @author João Paulo Sossoloti.
 */
@XmlType
@XmlEnum(Integer.class)
public enum SituacaoTransacao {
	
	@XmlEnumValue("1")
	AGUARDANDO_PAGAMENTO,
	
	@XmlEnumValue("2")
	EM_ANALISE,
	
	@XmlEnumValue("3")
	PAGA,
	
	@XmlEnumValue("4")
	DISPONIVEL,
	
	@XmlEnumValue("5")
	EM_DISPUTA,
	
	@XmlEnumValue("6")
	DEVOLVIDA,
	
	@XmlEnumValue("7")
	CANCELADA;
}
