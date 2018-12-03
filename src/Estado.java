<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> origin/rodrigo
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estado implements Comparable<Estado> {
	private String nome;
	private Map<String, List<Estado>> transicoes;
	private boolean estadoFinal;

	public Estado(String nome, boolean estadoFinal) {
		this.nome = nome;
		this.estadoFinal = estadoFinal;
		transicoes = new HashMap<>();
	}

	public Map<String, List<Estado>> getTransicoes() {
		return Collections.unmodifiableMap(transicoes);
	}

	public void adicionarTransicao(String simbolo, Estado estado) {
		if (transicoes.containsKey(simbolo))
			transicoes.get(simbolo).add(estado);
		else {
			List<Estado> estados = new ArrayList<>();
			estados.add(estado);
			transicoes.put(simbolo, estados);
		}
	}

	public void adicionarTransicao(String simbolo, List<Estado> estados) {
		if (transicoes.containsKey(simbolo))
			estados.forEach(e -> transicoes.get(simbolo).add(e));
		else
			transicoes.put(simbolo, estados);
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
		transicoes = new HashMap<>();
	}

	public Map<String, List<Estado>> getTransicoes() {
		return Collections.unmodifiableMap(transicoes);
		//return transicoes;
	}

	public void adicionarTransicao(String simbolo, List<Estado> estados) {
		transicoes.put(simbolo, estados);
	}

	public boolean isEstadoFinal() {
		return estadoFinal;
	}

	public void setEstadoFinal(boolean estadoFinal) {
		this.estadoFinal = estadoFinal;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
<<<<<<< HEAD
		return ((Estado) obj).getNome().equals(nome);
=======
		return nome.equals(((Estado) obj).getNome());
>>>>>>> origin/rodrigo
	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}
<<<<<<< HEAD
}
=======

	@Override
	public int compareTo(Estado e) {
		return this.getNome().compareTo(e.getNome());
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
>>>>>>> origin/rodrigo
