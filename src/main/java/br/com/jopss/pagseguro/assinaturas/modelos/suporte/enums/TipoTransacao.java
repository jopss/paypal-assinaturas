package br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * @author João Paulo Sossoloti.
 */
@XmlType
@XmlEnum(Integer.class)
public enum TipoTransacao {
	
	@XmlEnumValue("1")
	PAGAMENTO,
	
	@XmlEnumValue("11")
	ASSINATURA;
	
}
