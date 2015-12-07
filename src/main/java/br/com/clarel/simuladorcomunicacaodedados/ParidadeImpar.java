package br.com.clarel.simuladorcomunicacaodedados;

public class ParidadeImpar implements AlgoritimoDeCodificacao {

	@Override
	public StringBuilder encode(String asciiMessage) {
		StringBuilder binaryMessage = Utilidades.converteAsciiParaBinario(asciiMessage);

		if (Utilidades.contaUmEmArrayBinario(binaryMessage) % 2 == 0) {
			binaryMessage.append("1");
		} else {
			binaryMessage.append("0");
		}
		return binaryMessage;
	}

	@Override
	public String decode(StringBuilder binaryMessage) {
		StringBuilder decodedMessage = new StringBuilder(binaryMessage.substring(0, binaryMessage.toString().length()-1));

		return Utilidades.converteBinarioParaAscii(decodedMessage);
	}
	
	@Override
	public String verificaErro(StringBuilder codifiedMessage) {
		if (Utilidades.contaUmEmArrayBinario(codifiedMessage) % 2 == 0) {
			return "Mensagem transmitida com erro";
		} else {
			return "Mensagem transmitida sem erro";
		}

	}
	
	@Override
	public StringBuilder corrigeErro(StringBuilder codifiedMessage) {
		return null;
	}

}
