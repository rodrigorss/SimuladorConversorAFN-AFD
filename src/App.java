import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.StatePair;
import dk.brics.automaton.Transition;

public class App {

	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("entrada.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String linha[] = sc.nextLine().split("=");
		String nomeAutomato = linha[0];
		String linhaSplit[] = linha[1].substring(1, linha[1].length() - 1).split(",");
		System.out.println(Arrays.toString(linhaSplit));
		List<String> listaEstados = new ArrayList<>();
		String sEstados = linhaSplit[0];
		System.out.println(linha);
		for (String estado : sEstados.substring(0, sEstados.length() - 1).split(","))
			listaEstados.add(estado);
		List<String> estadosFinais = new ArrayList<>();
		String sEstadosFinais = linhaSplit[3];
		for (String estado : sEstadosFinais.substring(1, sEstadosFinais.length()).split(","))
			estadosFinais.add(estado);
		String estadoInicial = linhaSplit[2];
		List<String> alfabeto = new ArrayList<>();
		String sAlfabeto = linhaSplit[1];
		for (String letra : sAlfabeto.substring(1, sAlfabeto.length() - 1).split(","))
			alfabeto.add(String.valueOf(letra.charAt(0)));
		Automaton aut = new Automaton();
		Estado inicial = new Estado(estadoInicial, estadosFinais.contains(estadoInicial));
		aut.setInitialState(inicial);
		HashMap<String, Estado> estados = new HashMap<>();
		listaEstados.forEach(e -> estados.put(e, new Estado(e, estadosFinais.contains(e))));
		sc.nextLine(); // Pula "Prog"
		while (sc.hasNext()) {
			String l = sc.nextLine();
			String estadoOrigem = Pattern.compile("\\((.*),").matcher(l).group(1);
			String simbolo = Pattern.compile("\\(.*,(.*)\\)").matcher(l).group(1);
			String estadoDestino = Pattern.compile("=(.*)").matcher(l).group(1);
			Transition transicao = new Transition(Character.MIN_VALUE, Character.MAX_VALUE, estados.get(estadoDestino));
			estados.get(estadoOrigem).addTransition(transicao);
		}
		ArrayList<StatePair> pares = new ArrayList<>();
		for (Estado e : estados.values()) {
			Set<Transition> trans = e.getTransitions();

			int[] count = new int[trans.size()];
			for (int i = 0; i < count.length; i++)
				if (count[i] > 1) {
					Transition t = (Transition) (trans.toArray()[i]);
					pares.add(new StatePair(e, t.getDest()));
				}
		}
		aut.addEpsilons(pares);
		aut.setDeterministic(false);
		System.out.println(aut.toDot());
	}

}
