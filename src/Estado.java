import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estado {
	private String nome;
	private Map<String, List<Estado>> transicoes;
	private boolean estadoFinal;

	public Estado(String nome, boolean estadoFinal) {
		this.nome = nome;
		this.estadoFinal = estadoFinal;
		transicoes = new HashMap<>();
	}

	public Map<String, List<Estado>> getTransicoes() {
		//return Collections.unmodifiableMap(transicoes);
		return transicoes;
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
		return ((Estado) obj).getNome().equals(nome);
	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}

}