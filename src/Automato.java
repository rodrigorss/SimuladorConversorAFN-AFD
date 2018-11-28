import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Automato {

	private boolean deterministico;
	private Estado estadoInicial;
	private String nome;
	private List<Estado> estados;
	private List<String> simbolos;

	public Automato(String nome, Estado estadoInicial, List<Estado> estados, List<String> simbolos) {
		this.nome = nome;
		this.estadoInicial = estadoInicial;
		this.estados = estados;
		this.simbolos = simbolos;
	}

	public boolean isDeterministico() {
		return deterministico;
	}

	public String getNome() {
		return nome;
	}

	public Estado getEstadoInicial() {
		return estadoInicial;
	}

	public List<Estado> getEstadosAtingiveis(String simbolo, Estado estado) {
		return estado.getTransicoes().get(simbolo);
	}

	public void converteAFNparaAFD() {
		if (deterministico)
			return;

		Set<Set<Estado>> Q = new HashSet<>();
		// Adicionar em Q todas as possibilidades de conjuntos de estados
		Set<Set<Estado>> F = new HashSet<>();
		// Adicionar em F todos os conjuntos de estados que contêm um estado final

		deterministico = true;
	}

}
