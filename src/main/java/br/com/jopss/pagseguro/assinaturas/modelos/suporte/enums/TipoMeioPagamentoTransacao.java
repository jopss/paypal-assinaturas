package br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * @author João Paulo Sossoloti.
 */
@XmlType
@XmlEnum(Integer.class)
public enum TipoMeioPagamentoTransacao {
	
	@XmlEnumValue("1")
	CARTAO_CREDITO,
	
	@XmlEnumValue("2")
	BOLETO,
	
	@XmlEnumValue("3")
	DEBITO_ONLINE_TEF,
	
	@XmlEnumValue("4")
	SALDO_PAGSEGURO,
	
	@XmlEnumValue("5")
	OI_PAGGO,
	
	@XmlEnumValue("7")
	DEPOSITO_CONTA;
}
