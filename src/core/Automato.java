package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

	public List<String> getEstadosAtingiveis(String simbolo, Estado estado) {
		System.out.println("gea - " + simbolo + " - " + estado);
		List<String> estados = estado.getTransicoes().get(simbolo);
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
			Map<String, List<String>> novasTransicoes = new HashMap<>();
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
				List<String> estadosAtingiveis = getEstadosAtingiveis(simbolo, estados.get(nomeEstado));
				estadosAtingiveis.forEach(e -> novoEstado.add(estados.get(e)));
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
				Estado eNovoEstado = new Estado(nomeNovoEstado, colecaoContemEstadoFinal(novoEstado));
				adicionarTransicoes(novoEstado, eNovoEstado);
				novasTransicoes.put(simbolo, new ArrayList<>(Collections.singleton(nomeNovoEstado)));
				TreeSet<Estado> conjuntoNovoEstado = new TreeSet<>();
				conjuntoNovoEstado.add(eNovoEstado);
				if (!Q.contains(eNovoEstado)) {
					listaConjuntos.add(conjuntoNovoEstado);
					Q.add(eNovoEstado);
					estadosAtingiveis.forEach(e -> {
						if (estados.get(e).isFinal())
							F.add(eNovoEstado);
					});
				}
				estados.put(nomeNovoEstado, eNovoEstado);
				System.out.println("LC = " + listaConjuntos);
			}
			estado.forEach(e -> e.setTransicoes(novasTransicoes));
			linha++;
		}
		System.out.println(matriz);
		System.out.println("Q=" + Q);
		System.out.println("F=" + F);
		// Renomeando e salvando os nomes antigos com o novo nome
		System.out.println("Renomeando");
		int i = 0;
		HashMap<String, String> nomesAntigos = new HashMap<>();
		Iterator<Estado> it = Q.iterator();
		while (it.hasNext()) {
			Estado e = it.next();
			String novoNome = "q" + i++;
			nomesAntigos.put(e.getNome(), novoNome);
			e.setNome(novoNome);
		}
		System.out.println("Q=" + Q);
		System.out.println("F=" + F);
		System.out.println("Inicial=" + this.estadoInicial);

		// Definindo os novos estados no autômato e corrigindo nomes dos destinos
		HashMap<String, Estado> novosEstados = new HashMap<>();
		Q.forEach(e -> {
			e.getTransicoes().forEach((s, l) -> {
				for (int j = 0; j < l.size(); j++)
					if (nomesAntigos.containsKey(l.get(j)))
						l.set(j, nomesAntigos.get(l.get(j)));
			});
			novosEstados.put(e.getNome(), e);
		});
		this.estados = novosEstados;
		deterministico = true;
	}

	private void adicionarTransicoes(TreeSet<Estado> novoEstado, Estado eNovoEstado) {
		Iterator<Estado> it = novoEstado.iterator();
		while (it.hasNext()) {
			Estado e = it.next();
			System.out.println(e);
			for (String simbolo : simbolos) {
				List<String> estados = getEstadosAtingiveis(simbolo, e);
				if (estados.isEmpty())
					continue;
				eNovoEstado.adicionarTransicao(simbolo, estados);
			}
		}
	}

	private boolean colecaoContemEstadoFinal(Collection<Estado> colecao) {
		return colecao.stream().anyMatch(e -> e.isFinal());
	}

	public boolean testaPalavra(String palavra) {
		System.out.println("\nTestando a palavra " + palavra);
		List<Estado> listEstadosCorrentes = new LinkedList<>();
		listEstadosCorrentes.add(estadoInicial);
		char simbolos[] = palavra.toCharArray();

		//Para cada simbolo, verificar como ficam os estados correntes.
		for (char c : simbolos) {
			System.out.println("Estado sendo testado :" + listEstadosCorrentes + ",Simbolo testado atual :" + c);
			boolean temProxEstados = verificaProxEstado(listEstadosCorrentes, c);
			if (temProxEstados)
				continue;// Ta okay
			else
				return false;
		}
		boolean temEstadoFinal = colecaoContemEstadoFinal(listEstadosCorrentes);
		return temEstadoFinal;
	}

	private boolean verificaProxEstado(List<Estado> listEstadosCorrentes, char simbolo) {
		boolean achou = false;
		List<Estado> list = listEstadosCorrentes;
		List<Estado> remove = new LinkedList<>();/////AUX
		List<Estado> adiciona = new LinkedList<>();///AUX
		//Para cada estado verifica pra onde vai
		for (Estado state : list) {
			//Pega para o estado corrente do laço as possibilidades com o simbolo recebido
			List<String> prods = new LinkedList<>();
			List<String> aux = state.getTransicoes().get(String.valueOf(simbolo));
			if (aux != null)
				prods.addAll(aux);
			//Se o mapa estiver vazio então a partir do estado corrente não existem novos estados possiveis
			if (prods.isEmpty()) {
				remove.add(state);
				continue;//pula o resto dos passos
			}
			//Se não estiver vazia, então existe um estado novo valido a partir do simbolo recebido
			remove.add(state);
			//Adiciona os novos estados atingidos na lista de estados correntes
			for (String atual : prods)
				adiciona.add(estados.get(atual));
			achou = true;
		}
		for (Estado e : remove)
			listEstadosCorrentes.remove(e);
		for (Estado e : adiciona)
			listEstadosCorrentes.add(e);
		return achou;
	}

	private List<Estado> getEstadosFinais() {
		return estados.values().stream().filter(Estado::isFinal).collect(Collectors.toList());
	}

	public String toDot() {
		StringBuilder b = new StringBuilder("digraph " + nome + " {\n");
		b.append("  rankdir=LR;\n");
		b.append("  size=\"8,5\"\n");
		b.append("  node [ shape = doublecircle ]");
		getEstadosFinais().forEach(e -> b.append(" " + e.getNome()));
		b.append(";\n");
		b.append("  node [ shape = point ]; qi\n\n");
		b.append("  node [ shape = circle ];\n");
		b.append("  qi -> " + estadoInicial.getNome() + ";\n");
		estados.values().forEach(e -> {
			e.getTransicoes().forEach((s, l) -> {
				l.forEach(ed -> {
					b.append("  " + e.getNome() + " -> " + ed + " [ label = \"" + s + "\" ];\n");
				});
			});
		});
		return b.append("}\n").toString();
	}

	public static Automato lerAutomato(File arquivo) throws IOException {
		Scanner sc = new Scanner(arquivo);
		String linha[] = sc.nextLine().split("=");
		String nomeAutomato = linha[0];
		Matcher linhaMatcher = Pattern.compile("\\((\\{.*\\}),(\\{.*\\}),(\\w+),(\\{.*\\})\\)").matcher(linha[1]);
		linhaMatcher.matches();
		List<String> listaEstados = new ArrayList<>();
		String sEstados = linhaMatcher.group(1);
		for (String estado : sEstados.substring(1, sEstados.length() - 1).split(","))
			listaEstados.add(estado);
		List<String> estadosFinais = new ArrayList<>();
		String sEstadosFinais = linhaMatcher.group(4);
		for (String estado : sEstadosFinais.substring(1, sEstadosFinais.length() - 1).split(","))
			estadosFinais.add(estado);
		String estadoInicial = linhaMatcher.group(3);
		List<String> alfabeto = new ArrayList<>();
		String sAlfabeto = linhaMatcher.group(2);
		for (String letra : sAlfabeto.substring(1, sAlfabeto.length() - 1).split(","))
			alfabeto.add(String.valueOf(letra.charAt(0)));
		HashMap<String, Estado> estados = new HashMap<>();
		listaEstados.forEach(e -> estados.put(e, new Estado(e, estadosFinais.contains(e))));
		Estado eInicial = estados.get(estadoInicial);
		sc.nextLine(); // Pula "Prog"
		while (sc.hasNext()) {
			String l = sc.nextLine();
			String valores[] = l.replaceAll("\\(|\\)", "").replaceAll("=", ",").split(",");
			String estadoOrigem = valores[0];
			String simbolo = valores[1];
			String estadoDestino = valores[2];
			estados.get(estadoOrigem).adicionarTransicao(simbolo, estadoDestino);
		}
		sc.close();
		return new Automato(nomeAutomato, eInicial, estados, alfabeto);
	}
}
