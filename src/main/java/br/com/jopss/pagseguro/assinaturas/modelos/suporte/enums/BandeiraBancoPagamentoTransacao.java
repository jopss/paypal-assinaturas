package br.com.jopss.pagseguro.assinaturas.modelos.suporte.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * @author João Paulo Sossoloti.
 */
@XmlType
@XmlEnum(Integer.class)
public enum BandeiraBancoPagamentoTransacao {
	
	@XmlEnumValue("101")
	CARTAO_CREDITO_VISA,
	
	@XmlEnumValue("102")
	CARTAO_CREDITO_MASTERCARD,
	
	@XmlEnumValue("103")
	CARTAO_CREDITO_AMERICAN_EXPRESS,
	
	@XmlEnumValue("104")
	CARTAO_CREDITO_DINERS,
	
	@XmlEnumValue("105")
	CARTAO_CREDITO_HIPERCARD,
	
	@XmlEnumValue("106")
	CARTAO_CREDITO_AURA,
	
	@XmlEnumValue("107")
	CARTAO_CREDITO_ELO,
	
	@XmlEnumValue("108")
	CARTAO_CREDITO_PLENOCARD,
	
	@XmlEnumValue("109")
	CARTAO_CREDITO_PERSONALCARD,
	
	@XmlEnumValue("110")
	CARTAO_CREDITO_JCB,
	
	@XmlEnumValue("111")
	CARTAO_CREDITO_DISCOVER,
	
	@XmlEnumValue("112")
	CARTAO_CREDITO_BRASILCARD,
	
	@XmlEnumValue("113")
	CARTAO_CREDITO_FORTBRASIL,
	
	@XmlEnumValue("114")
	CARTAO_CREDITO_CARDBAN,
	
	@XmlEnumValue("115")
	CARTAO_CREDITO_VALECARD,
	
	@XmlEnumValue("116")
	CARTAO_CREDITO_CABAL,
	
	@XmlEnumValue("117")
	CARTAO_CREDITO_MAIS,
	
	@XmlEnumValue("118")
	CARTAO_CREDITO_AVISTA,
	
	@XmlEnumValue("119")
	CARTAO_CREDITO_GRANDCARD,
	
	@XmlEnumValue("120")
	CARTAO_CREDITO_SOROCRED,
	
	@XmlEnumValue("201")
	BOLETO_BRADESCO,
	
	@XmlEnumValue("202")
	BOLETO_SANTANDER,
	
	@XmlEnumValue("301")
	DEBITO_ONLINE_BRADESCO,
	
	@XmlEnumValue("302")
	DEBITO_ONLINE_ITAU,
	
	@XmlEnumValue("303")
	DEBITO_ONLINE_UNIBANCO,
	
	@XmlEnumValue("304")
	DEBITO_ONLINE_BANCO_DO_BRASIL,
	
	@XmlEnumValue("305")
	DEBITO_ONLINE_BANCO_REAL,
	
	@XmlEnumValue("306")
	DEBITO_ONLINE_BANRISUL,
	
	@XmlEnumValue("307")
	DEBITO_ONLINE_HSBC,
	
	@XmlEnumValue("401")
	SALDO_PAGSEGURO,
	
	@XmlEnumValue("501")
	OI_PAGGO,
	
	@XmlEnumValue("701")
	DEPOSITO_CONTA_BANCO_DO_BRASIL,
	
	@XmlEnumValue("702")
	DEPOSITO_CONTA_HSBC;
}
