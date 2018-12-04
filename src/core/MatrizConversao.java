package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MatrizConversao {
	private ArrayList<ArrayList<Set<Estado>>> linhas;

	public MatrizConversao(int qtdSimbolos) {
		linhas = new ArrayList<>();
	}

	public void set(int linha, int coluna, Set<Estado> estados) {
		ArrayList<Set<Estado>> colunas = null;
		while (coluna + 1 > linhas.size()) {
			linhas.add(linhas.size(), new ArrayList<>());
			colunas = linhas.get(coluna);
			while (linha + 1 > colunas.size())
				colunas.add(colunas.size(), new HashSet<Estado>());
		}
		while (linha + 1 > linhas.get(coluna).size())
			linhas.get(coluna).add(linhas.get(coluna).size(), new HashSet<Estado>());
		linhas.get(coluna).set(linha, estados);
	}

	public Set<Estado> get(int linha, int coluna) {
		return linhas.get(coluna).get(linha);
	}

	public int numLinhas() {
		return linhas.size();
	}

	public int numColunas() {
		return linhas.get(0).size();
	}

	@Override
	public String toString() {
		return linhas.toString();
	}
}
