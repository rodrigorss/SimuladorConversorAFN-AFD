package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estado implements Comparable<Estado> {
	private String nome;
	private Map<String, List<String>> transicoes;
	private boolean estadoFinal;

	public Estado(String nome, boolean estadoFinal) {
		this.nome = nome;
		this.estadoFinal = estadoFinal;
		transicoes = new HashMap<>();
	}

	public Map<String, List<String>> getTransicoes() {
		return transicoes;
	}

	public void adicionarTransicao(String simbolo, String estado) {
		if (transicoes.containsKey(simbolo))
			transicoes.get(simbolo).add(estado);
		else {
			List<String> estados = new ArrayList<>();
			estados.add(estado);
			transicoes.put(simbolo, estados);
		}
	}

	public void adicionarTransicao(String simbolo, List<String> estados) {
		if (transicoes.containsKey(simbolo))
			estados.forEach(e -> transicoes.get(simbolo).add(e));
		else
			transicoes.put(simbolo, estados);
	}

	public void limparTransicoes() {
		this.transicoes = new HashMap<>();
	}

	public void setTransicoes(Map<String, List<String>> transicoes) {
		this.transicoes = transicoes;
	}

	public boolean isFinal() {
		return estadoFinal;
	}

	public void setEstadoFinal(boolean estadoFinal) {
		this.estadoFinal = estadoFinal;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		return nome.equals(((Estado) obj).getNome());
	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}

	@Override
	public int compareTo(Estado e) {
		return this.getNome().compareTo(e.getNome());
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
