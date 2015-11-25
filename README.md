paypal-assinaturas
=====================

API em Java que implementa as ações de assinaturas do Paypal, por meio do WS.

Licença
-------

O projeto foi concebido na licença Mozilla 2.0, ou seja, pode-se utilizar o jar em qualquer projeto, comercial ou não. Mas alterações na API devem ser obrigatoriamente disponibilizada na mesma licença.

É OpenSource, e como tal, qualquer um pode melhorar o código, corrigindo ou adicionando novas funcionalidades. Basta dar um fork neste repo.

Maven
-----

Em fase de publicacao... :/
	
Uso
===

O PayPal exige passos para efetuar um pagamento recorrente. Primeiro deve-se cadastrar e configurar a sua conta no PayPal para receber pagamentos. O site deles é bem intuitivo.

Cadastrando um Pagamento Recorrente
-----------------------------------

	public void enviarAssinaturaPayPal(HttpServletResponse response) throws ProblemaGenericoAPIException, ErrosRemotosPayPalException, ConfiguracaoInvalidaException, AutorizacaoInvalidaException {
	
		//lib JodaTime.
		Date dataInicial = new DateTime().plusMonths(1).toDate(); //daqui a 30 dias.
	                
		DefinicaoPagamento definicao = new DefinicaoPagamento(100.0, Moeda.BRL);
	        definicao.setCiclos(12);
	        definicao.setFrequencia(Periodo.MONTH);
	        definicao.setIntervaloFrequencia(1);
	        definicao.setNome("Assinatura Gold");
	        definicao.setIndicaPeriodoDeTeste(false);
	
	        PreferenciasComerciais preferencias = new PreferenciasComerciais();
	        preferencias.setContinuarAcaoAoFalharPagamentoInicial(false);
	        preferencias.setQtdMaximaTentativas(0);
	        preferencias.setUrlCancelar("http://meusistema/pagamento/cancelar");
	        preferencias.setUrlRetorno("http://meusistema/pagamento/callback");
	        preferencias.setValorInicialDiferenciado(20.0, Moeda.BRL);
	
	        StringBuilder strDescricao = new StringBuilder();
	        strDescricao.append("Assinatura Gold com ");
		strDescricao.append("pagamento inicial (à vista): ");
		strDescricao.append("1x de R$ 20,00. ");
	        strDescricao.append("Mensalidade: ");
	        strDescricao.append("12x de R$ 100,00. ");
	                
	        EnvioPreAprovacao preAprovacao = new EnvioPreAprovacao(definicao, preferencias);
	        preAprovacao.setDescricao(strDescricao.toString());
	        preAprovacao.setNome("Assinatura Gold");
	        preAprovacao.setTipo(TipoPreRequisicao.FIXED);
	
		//pode-se configurar proxy de rede.
	        PayPalAPI.instance().config().setId("seu-token").setSecret("sua-senha").indicaAmbienteReal();
	        PayPalAPI.instance().seguranca().gerarToken();
	        
	        RespostaPreAprovacao respostaPreAprovacao = PayPalAPI.instance().assinatura().preAprovacao(preAprovacao);
	        PayPalAPI.instance().assinatura().ativarAprovacao(respostaPreAprovacao);
	        RespostaContrato respostaContrato = PayPalAPI.instance().assinatura().contrato(new EnvioContrato(preAprovacao, dataInicial, respostaPreAprovacao));
	
	        PayPalAPI.instance().assinatura().redirecionarURLPagamento(response, respostaContrato);
	}
	
	
Callback de Sucesso do Pagamento Recorrente
-------------------------------------------

Utilizando Spring MVC. Esta URL foi passada no passo anterior, no método 'setUrlRetorno'.

	@RequestMapping(value = "/pagamento/callback", method = RequestMethod.GET)
	public String paypalCallback(Model model, @RequestParam("token") String codigoFaturamento) {
                //...

                try {
                        PayPalAPI.instance().config().setId("seu-token").setSecret("sua-senha").indicaAmbienteReal();
	        	PayPalAPI.instance().seguranca().gerarToken();
	        	
                        RespostaFaturamento respostaFaturamento = PayPalAPI.instance().assinatura().faturamento(codigoFaturamento);
                        String code = respostaFaturamento.getId();
			//pode-se trabalhar com o retorno do faturamento para exibir em tela a confirmacao.
                        
                } catch (ErrosRemotosPayPalException ex) {
                        //pode-se tratar erros especificos gerados pela API.
                } 

                //...
        }


Status
------

Finalizado primeira release. Em manutenção.
