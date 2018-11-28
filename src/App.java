import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		Matcher linhaMatcher = Pattern.compile("\\((\\{.*\\}),(\\{.*\\}),(\\w+),(\\{.*\\})\\)").matcher(linha[1]);
		linhaMatcher.matches();
		System.out.println("Nome: " + nomeAutomato);
		System.out.println("Infos: " + linhaMatcher.group());
		System.out.println("Estados: " + linhaMatcher.group(1));
		System.out.println("Estados Finais: " + linhaMatcher.group(4));
		System.out.println("Estado Inicial: " + linhaMatcher.group(3));
		System.out.println("Alfabeto: " + linhaMatcher.group(2));
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
		// Automaton aut = new Automaton();
		Estado inicial = new Estado(estadoInicial, estadosFinais.contains(estadoInicial));
		// aut.setInitialState(inicial);
		HashMap<String, Estado> estados = new HashMap<>();
		listaEstados.forEach(e -> estados.put(e, new Estado(e, estadosFinais.contains(e))));
		sc.nextLine(); // Pula "Prog"
		while (sc.hasNext()) {
			String l = sc.nextLine();
			System.out.println("Linha: " + l);
			String valores[] = l.replaceAll("\\(|\\)", "").replaceAll("=", ",").split(",");
			String estadoOrigem = valores[0];
			String simbolo = valores[1];
			String estadoDestino = valores[2];
			System.out.println("Estado Origem: " + estadoOrigem);
			System.out.println("Símbolo: " + simbolo);
			System.out.println("Estado Destino: " + estadoDestino);
			// Transition transicao = new Transition(Character.MIN_VALUE,
			// Character.MAX_VALUE, estados.get(estadoDestino));
			// estados.get(estadoOrigem).addTransition(transicao);
		}
	}

}
