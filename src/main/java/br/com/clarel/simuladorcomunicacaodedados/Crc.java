package br.com.clarel.simuladorcomunicacaodedados;

import org.apache.commons.lang3.StringUtils;

public class Crc implements AlgoritimoDeCodificacao {

	private final StringBuilder polinomio = new StringBuilder("10001");

	@Override
	public StringBuilder encode(String mensagemAscii) {
		StringBuilder mensagemEmBinario = Utilidades.converteAsciiParaBinario(mensagemAscii);
		String auxiliar = StringUtils.rightPad("", polinomio.length() - 1, "0");

		StringBuilder restoDivisao = Utilidades.retornaRestoDivisaoBits(new StringBuilder(mensagemEmBinario + auxiliar),
				polinomio);

		return mensagemEmBinario.append(restoDivisao.substring(1, polinomio.length()));
	}

	@Override
	public String decode(StringBuilder mensagemEmBinario) {
		StringBuilder array = new StringBuilder(
				mensagemEmBinario.subSequence(0, mensagemEmBinario.length() - (polinomio.length() - 1)));

		return Utilidades.converteBinarioParaAscii(array);

	}

	@Override
	public String verificaErro(StringBuilder mensagemCodificada) {
		StringBuilder restoDivisao = Utilidades.retornaRestoDivisaoBits(new StringBuilder(mensagemCodificada), polinomio);
		int resultadoRestoDivisao = Integer.valueOf(restoDivisao.toString());

		if (resultadoRestoDivisao != 0) {
			return "Mensagem transmitida com erro";
		} else {
			return "Mensagem transmitida sem erro";
		}

	}

	@Override
	public StringBuilder corrigeErro(StringBuilder mensagemCodificada) {
		return null;
	}

}
