import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class Automato {

	private boolean deterministico;
	private Estado estadoInicial;
	private String nome;
	private HashMap<String, Estado> estados;
	private List<String> simbolos;

	public Automato(String nome, Estado estadoInicial, HashMap<String, Estado> estados, List<String> simbolos) {
		this.nome = nome;
		this.estadoInicial = estadoInicial;
		this.simbolos = simbolos;
		this.estados = estados;
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
		System.out.println("gea - " + simbolo + " - " + estado);
		List<Estado> estados = estado.getTransicoes().get(simbolo);
		if (estados == null)
			return new ArrayList<>();
		else
			return estados;
	}

	public void converteAFNparaAFD() {
		if (deterministico)
			return;

		TreeSet<Estado> Q = new TreeSet<>();
		TreeSet<Estado> F = new TreeSet<>();
		ArrayList<TreeSet<Estado>> listaConjuntos = new ArrayList<>(); // Estados
		TreeSet<Estado> estadoInicial = new TreeSet<>(Collections.singleton(this.estadoInicial));
		listaConjuntos.add(estadoInicial);
		Q.add(this.estadoInicial);
		if (this.estadoInicial.isFinal())
			F.add(this.estadoInicial);
		MatrizConversao matriz = new MatrizConversao(simbolos.size());
		int linha = 0, coluna = 0;
		while (!listaConjuntos.isEmpty()) {
			System.out.println("LC = " + listaConjuntos);
			coluna = 0;
			TreeSet<Estado> estado = listaConjuntos.remove(0);
			StringBuilder sb = new StringBuilder();
			Iterator<Estado> sbit = estado.iterator();
			while (sbit.hasNext())
				sb.append(sbit.next().getNome());
			String nomeEstado = sb.toString();
			System.out.print(linha + " " + coluna + " - " + nomeEstado + " -> ");
			matriz.set(coluna, linha, estado);
			coluna++;
			System.out.println(matriz);
			for (String simbolo : simbolos) {
				TreeSet<Estado> novoEstado = new TreeSet<>();
				System.out.println("-> " + getEstadosAtingiveis(simbolo, estados.get(nomeEstado)));
				List<Estado> estadosAtingiveis = getEstadosAtingiveis(simbolo, estados.get(nomeEstado));
				estadosAtingiveis.forEach(e -> novoEstado.add(e));
				System.out.print(linha + " " + coluna + " -> ");
				matriz.set(coluna, linha, novoEstado);
				coluna++;
				if (novoEstado.isEmpty())
					continue;
				System.out.println(matriz);
				sb = new StringBuilder();
				sbit = novoEstado.iterator();
				while (sbit.hasNext())
					sb.append(sbit.next().getNome());
				String nomeNovoEstado = sb.toString();
				Estado eNovoEstado = new Estado(nomeNovoEstado, conjuntoContemEstadoFinal(novoEstado));
				adicionarTransicoes(novoEstado, eNovoEstado);
				TreeSet<Estado> conjuntoNovoEstado = new TreeSet<>();
				conjuntoNovoEstado.add(eNovoEstado);
				if (!Q.contains(eNovoEstado)) {
					listaConjuntos.add(conjuntoNovoEstado);
					Q.add(eNovoEstado);
					estadosAtingiveis.forEach(e -> {
						if (e.isFinal())
							F.add(eNovoEstado);
					});
				}
				estados.put(nomeNovoEstado, eNovoEstado);
				System.out.println("LC = " + listaConjuntos);
			}
			linha++;
		}
		System.out.println(matriz);
		System.out.println("Q=" + Q);
		System.out.println("F=" + F);
		System.out.println("Renomeando");
		int i = 0;
		Iterator<Estado> it = Q.iterator();
		while (it.hasNext())
			it.next().setNome("q" + i++);
		System.out.println("Q=" + Q);
		System.out.println("F=" + F);
		System.out.println("Inicial=" + this.estadoInicial);
		deterministico = true;
	}

	private void adicionarTransicoes(TreeSet<Estado> novoEstado, Estado eNovoEstado) {
		Iterator<Estado> it = novoEstado.iterator();
		while (it.hasNext()) {
			Estado e = it.next();
			System.out.println(e);
			for (String simbolo : simbolos) {
				List<Estado> estados = getEstadosAtingiveis(simbolo, e);
				if (estados.isEmpty())
					continue;
				eNovoEstado.adicionarTransicao(simbolo, estados);
			}
		}
	}

	private boolean conjuntoContemEstadoFinal(TreeSet<Estado> novoEstado) {
		return novoEstado.stream().anyMatch(e -> e.isFinal());
	}

}
