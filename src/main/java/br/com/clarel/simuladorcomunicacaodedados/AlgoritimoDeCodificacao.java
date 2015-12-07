package br.com.clarel.simuladorcomunicacaodedados;

public interface AlgoritimoDeCodificacao {

	public StringBuilder encode(String asciiMessage);
	
	public String decode(StringBuilder codifiedMessage);
	
	public String verificaErro(StringBuilder codifiedMessage);
	
	public StringBuilder corrigeErro(StringBuilder codifiedMessage);
	
}
