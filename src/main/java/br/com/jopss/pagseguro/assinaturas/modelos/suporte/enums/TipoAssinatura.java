package br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * @author João Paulo Sossoloti.
 */
@XmlType
@XmlEnum(String.class)
public enum TipoAssinatura {
	
	@XmlEnumValue("manual")
	MANUAL,
	
	@XmlEnumValue("auto")
	AUTOMATICO;
	
}
