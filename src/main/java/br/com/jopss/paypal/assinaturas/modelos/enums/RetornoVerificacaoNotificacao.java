package br.com.jopss.paypal.assinaturas.modelos.enums;

public enum RetornoVerificacaoNotificacao {
        VERIFIED, INVALID;
        
        public boolean isVerificado(){
                return this.equals(VERIFIED);
        }
}
