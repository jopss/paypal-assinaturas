package br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * @author João Paulo Sossoloti.
 */
@XmlType
@XmlEnum(String.class)
public enum SituacaoAssinatura {
	
	@XmlEnumValue("INITIATED")
	CRIADO,
	
	@XmlEnumValue("PENDING")
	AGUARDANDO_OPERADORA,
	
	@XmlEnumValue("ACTIVE")
	PAGA_ATIVA,
	
	@XmlEnumValue("CANCELLED")
	CANCELADA_PAGSEGURO_OU_OPERADORA,
	
	@XmlEnumValue("CANCELLED_BY_RECEIVER")
	CANCELADA_VENDEDOR,
	
	@XmlEnumValue("CANCELLED_BY_SENDER")
	CANCELADA_COMPRADOR,
	
	@XmlEnumValue("EXPIRED")
	EXPIRADA_TEMPO_LIMITE_VIGENCIA;
}
