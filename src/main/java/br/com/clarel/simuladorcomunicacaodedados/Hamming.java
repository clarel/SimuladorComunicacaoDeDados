package br.com.clarel.simuladorcomunicacaodedados;

public class Hamming implements AlgoritimoDeCodificacao {
	private int posicaoErro = 0;
	
	public StringBuilder encode(String asciiMessage) {
		StringBuilder mensagemRecebidaEmBinario = Utilidades.converteAsciiParaBinario(asciiMessage);
		int messageLenght = mensagemRecebidaEmBinario.toString().length();
		StringBuilder mensagemCodificada = new StringBuilder();
		int index = 0;
		while (index < messageLenght) {
			while (Utilidades.potenciaDeDois(mensagemCodificada.toString().length() + 1)) {
				//coloca 0 para evitar valores null, depois será cálculado o real valor
				mensagemCodificada.append("0");
			}
			mensagemCodificada.append(mensagemRecebidaEmBinario.charAt(index));
			index++;
		}
		messageLenght = mensagemCodificada.toString().length();
		index = 0;
		while (index < messageLenght) {
			if (Utilidades.potenciaDeDois(index + 1)) {
				StringBuilder bitsParaExecutarXor = new StringBuilder();
				int auxIndex = index;
				//usado para contar, usa 2 bits, pula, usa 2, assim por diante.. depende do index
				boolean indexIsProductOfSum = true;
				int totalAux = 0;
				while (auxIndex < messageLenght) {
					totalAux++;
					//para não permitir adicionar o valor na posição index na operação xor
					if (indexIsProductOfSum && auxIndex != index) {
						bitsParaExecutarXor.append(mensagemCodificada.charAt(auxIndex));
					}
					if (totalAux == index + 1) {
						indexIsProductOfSum = !indexIsProductOfSum;
						totalAux = 0;
					}
					auxIndex++;
				}
				mensagemCodificada.setCharAt(index, Utilidades.executaXorEmArrayBinario(bitsParaExecutarXor).charAt(0));
			}
			index++;
		}
		return mensagemCodificada;
	}

	public String decode(StringBuilder mensagemEmBinario) {
		int messageLenght = mensagemEmBinario.length();
		StringBuilder mensagemDecodificada = new StringBuilder();
		int index = 0;
		while (index < messageLenght) {
			if (!Utilidades.potenciaDeDois(index + 1)) {
				mensagemDecodificada.append(mensagemEmBinario.charAt(index));
			}
			index++;
		}
		return Utilidades.converteBinarioParaAscii(mensagemDecodificada);
	}

	public String verificaErro(StringBuilder mensagemCodificada) {
		int messageLenght = mensagemCodificada.toString().length();
		StringBuilder seguraBinariosVerificacao = new StringBuilder();
		int index = 0;
		while (index < messageLenght) {
			if (Utilidades.potenciaDeDois(index + 1)) {
				StringBuilder bitsParaExecutarXor = new StringBuilder();
				int auxIndex = index;
				//usado para contar, usa 2 bits, pula 2.. depende do valor do index.
				boolean indexProdutoDaSoma = true;
				int totalAux = 0;
				while (auxIndex < messageLenght) {
					totalAux++;
					if (indexProdutoDaSoma) {
						bitsParaExecutarXor.append(mensagemCodificada.charAt(auxIndex));
					}
					if (totalAux == index + 1) {
						indexProdutoDaSoma = !indexProdutoDaSoma;
						totalAux = 0;
					}
					auxIndex++;
				}
				seguraBinariosVerificacao.append(Utilidades.executaXorEmArrayBinario(bitsParaExecutarXor));
			}
			index++;
		}
		seguraBinariosVerificacao = seguraBinariosVerificacao.reverse();
		this.posicaoErro = Integer.parseInt(seguraBinariosVerificacao.toString(), 2);
		if (posicaoErro > 0) {
			return "Mensagem transmitida com erro na posição: " + this.posicaoErro;
		} else {
			return "Mensagem transmitida sem erro";
		}
	}

	public StringBuilder corrigeErro(StringBuilder mensagemCodificada) {
		return Utilidades.mudaUmValorDeterminadoNoArrayDeBits(mensagemCodificada, this.posicaoErro - 1);
	}

}
