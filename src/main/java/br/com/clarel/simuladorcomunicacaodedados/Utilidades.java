package br.com.clarel.simuladorcomunicacaodedados;

import java.util.Random;

public class Utilidades {

	// ascii to binary
	public static StringBuilder converteAsciiParaBinario(String str) {
		byte[] bytes = str.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			binary.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}

		return binary;
	}

	// binary to ascii
	public static String converteBinarioParaAscii(StringBuilder arrayBinary) {
		String[] ss;
		if (arrayBinary.toString().length() < 8) {
			ss = fazSplitEmString(arrayBinary.toString(), arrayBinary.toString().length());
		} else {
			ss = fazSplitEmString(arrayBinary.toString(), 8);
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ss.length; i++) {
			sb.append((char) Integer.parseInt(ss[i], 2));
		}
		return sb.toString();
	}

	// 1s counter
	public static int contaUmEmArrayBinario(StringBuilder arrayBinary) {
		int index = 0;
		int messageLenght = arrayBinary.toString().length();
		int oneBinaryCounter = 0;

		while (index < messageLenght) {

			if (String.valueOf(arrayBinary.charAt(index)).equals("1")) {
				oneBinaryCounter++;
			}

			index++;
		}
		return oneBinaryCounter;
	}

	public static String executaXorEmArrayBinario(StringBuilder arrayBinary) {
		int messageLenght = arrayBinary.toString().length();
		// vai receber o valor do primeiro elemento do array
		int index = 1;
		boolean mustReturn = converteStringParaBit(String.valueOf(arrayBinary.charAt(0)));

		while (index < messageLenght) {
			mustReturn = mustReturn ^ converteStringParaBit(String.valueOf(arrayBinary.charAt(index)));
			index++;
		}
		return converteBitParaString(mustReturn);
	}

	// split the binary array in eight binary to allow to transform back to
	// ascii
	private static String[] fazSplitEmString(String s, int interval) {
		int arrayLength = (int) Math.ceil(((s.length() / (double) interval)));
		String[] result = new String[arrayLength];

		int j = 0;
		int lastIndex = result.length - 1;
		for (int i = 0; i < lastIndex; i++) {
			result[i] = s.substring(j, j + interval);
			j += interval;
		} // Add the last bit
		result[lastIndex] = s.substring(j);

		return result;
	}

	public static boolean potenciaDeDois(int number) {
		int index = 0;

		while (index <= number) {
			if (Math.pow(2, index) == number) {
				return true;
			}
			index++;
		}
		return false;

	}

	public static boolean converteStringParaBit(String bit) {
		if (bit.equals("1"))
			return true;
		else
			return false;
	}

	public static String converteBitParaString(boolean bit) {
		if (bit)
			return "1";
		else
			return "0";
	}

	public static StringBuilder mudaUmValorAleatorioNoArrayDeBits(StringBuilder originalMessage) {
		Random randomGenerator = new Random();
		// generate a random number, between zero and the last index from
		// message.
		int randomInt = randomGenerator.nextInt(originalMessage.toString().length());

		if (String.valueOf(originalMessage.charAt(randomInt)).equals("1")) {
			originalMessage.setCharAt(randomInt, "0".charAt(0));
		} else {
			originalMessage.setCharAt(randomInt, "1".charAt(0));
		}

		return originalMessage;

	}

	public static StringBuilder mudaUmValorDeterminadoNoArrayDeBits(StringBuilder message, int index) {
		if (String.valueOf(message.charAt(index)).equals("1")) {
			message.setCharAt(index, "0".charAt(0));
		} else {
			message.setCharAt(index, "1".charAt(0));
		}

		return message;

	}

	public static StringBuilder retornaRestoDivisaoBits(StringBuilder dividendo, StringBuilder divisor) {
		StringBuilder resto = new StringBuilder();
		boolean terminou = false;

		int quantidade = divisor.toString().length();
		// inicio o resto com as primeiros valores do dividendo
		resto = new StringBuilder(dividendo.substring(0, quantidade));
		// remove do dividendo o que já foi para o cálculo
		dividendo = removerBitsInicioArray(dividendo, quantidade);

		// percorre todo o dividendo, enquanto terminou=false
		while (!terminou) {
			int contadorIndexCalculoResto = 0;
			StringBuilder novoResto = new StringBuilder();

			// faz o xor
			while (contadorIndexCalculoResto < divisor.toString().length()) {

				novoResto.append(converteBitParaString(converteStringParaBit(String.valueOf(divisor.charAt(contadorIndexCalculoResto)))
								^ converteStringParaBit(String.valueOf(resto.charAt(contadorIndexCalculoResto)))));

				contadorIndexCalculoResto++;

			}
			
			int quantidadeDeZerosNaFrente = 0;
			boolean encontrouUm = false;
			int contador = 0;
			boolean percorreuTudo = false;

			// descobre quantos 0 foram encontrado na frente, para poder pegar
			// esta mesma quantidade do dividendo
			while (!encontrouUm && !percorreuTudo) {
				if (String.valueOf(novoResto.charAt(contador)).equals("0")) {
					quantidadeDeZerosNaFrente++;
				} else {
					encontrouUm = true;
				}

				contador++;

				if (contador == novoResto.length()) {
					percorreuTudo = true;
				}
			}

			if(!terminou) {
				if (quantidadeDeZerosNaFrente > dividendo.toString().length()) {
					quantidadeDeZerosNaFrente = dividendo.toString().length();
				}
				novoResto.append(dividendo.substring(0, quantidadeDeZerosNaFrente));
				dividendo = removerBitsInicioArray(dividendo, quantidadeDeZerosNaFrente);
				novoResto = removerBitsInicioArray(novoResto, quantidadeDeZerosNaFrente);
				
				if (dividendo.toString().equals("")) {
					terminou = true;
				}

				resto = novoResto;
			}
			
		
		}
		
		
		
		return resto;
	}

	public static StringBuilder removerBitsInicioArray(StringBuilder array, int quantidade) {
		array = array.reverse();
		array = new StringBuilder(array.substring(0, array.toString().length() - quantidade));
		array = array.reverse();
		return array;

	}

}
