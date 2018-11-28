import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AutomatoAFND {
	// LISTA TODOS OS ESTADOS DO AUTOMATO
	private List<Estado> listaEstadosAutomato;
	// LISTA DE SIMBOLOS ACEITOS
	private List<String> listaSimbolosAutomato;
	// ESTADO INICIAL
	private Estado estadoInicial;
	// LISTA ESTADOS FINAIS
	private List<Estado> listaEstadosFinal;
	// LISTA DE PROG, ( Q0 , a ) = Q9 por exemplo
	private List<Transicao> listaProducoes;

	public AutomatoAFND(List<Estado> listaEstados, List<String> listaSimbolos, Estado estadoInit,
			List<Estado> listEstadosFim, List<Transicao> listaProd) {
		this.listaEstadosAutomato = new LinkedList<>(listaEstados);
		this.listaSimbolosAutomato = new LinkedList<>(listaSimbolos);
		this.estadoInicial = estadoInit;
		this.listaEstadosFinal = new LinkedList<>(listEstadosFim);
		this.listaProducoes = new LinkedList<>(listaProd);
	}

	public List<String> getListaSimbolos(){ return listaSimbolosAutomato;}
	public String verificaSeAceitaPalavra(String palavra) {
		List<Estado> listaEstadosCorrentes = new LinkedList<>();
		listaEstadosCorrentes.add(estadoInicial);
		char[] listaSimbolos = palavra.toCharArray(); // lista com cada simbolo da palavra 1 a 1 separado
		// Para cada simbolo v� como o automato se comporta nos estados correntes
		for (char c : listaSimbolos) {
			boolean executa = temProxEstados(c, listaEstadosCorrentes);// Se executou o simbolo e chegou em algum lugar
																		// v�lido
			System.out.println("Simbolo lido: " + c);
			if (executa = true)
				// automato eceitou este simbolo de entrada e obteve, a partir dos estados
				// correntes antigos e o simbolo de entrada,
				// novos estados correntes.
				// OK, PROSSEGUIR
				System.out.println("Executou para o simbolo :" + c);
			else
				// automato n�o encontrou nenhuma producao que a partir de um estado corrente e
				// o simbolo de entrada pedido resultasse
				// em algum novo estado corrente.
				// ERRO, AUTOMATO N�O ACEITA ESTA PALAVRA
				return "O automato nao aceita a palavra :" + palavra; // break
		}
		// Agora basta verificar se no final da execu��o eu parei em algum estado final
		boolean contemEstadoFinal = false;
		for (Estado state : listaEstadosCorrentes)
			for (Estado stateFinal : listaEstadosFinal)
				if (state == stateFinal)
					contemEstadoFinal = true;
		if (contemEstadoFinal == false) {
			System.out.println("Deu ruim no final");
			return "O aut�mato n�o aceita a palavra :" + palavra;
		} else {
			System.out.println("Deu bom no final");
			return "O aut�mato aceita a palavra :" + palavra;
		}
	}

	// METODO QUE DADO UM SIMBOLO DE ENTRADA ME DIZ SE ACHOU ALGUM ESTADO NOVO A
	// PARTIR DE ALGUM ESTADO CORRENTE E O SIMBOLO DE ENTRADA
	// CASO TENHA ENCONTRADO J� TROCA O ESTADO CORRENTE PELO NOVO ESTADO GERADO A
	// PARTIR DO ANTIGO CORRENTE E O SIMBOLO DE ENTRADA
	private boolean temProxEstados(char simbolo, List<Estado> estadosCorrentes) {
		boolean achouAlgumEstadoNovo = false;
		List<Estado> RemoveCurrents = new LinkedList<>();
		List<Estado> AddCurrents = new LinkedList<>();
		// para cada estado corrente checar quais s�o os novos estados com esse simbolo
		for (Estado state : estadosCorrentes) {
			// PEGA A LISTA COM TODAS AS PROD QUE TIVEREM ESTE 'STATE' COMO ORIGEM + O
			// SIMBOLO DE ENTRADA PEDIDO
			List<Transicao> listProd = listaProducoes.stream()
					.filter(op -> op.getEstadoOrigem() == state && op.getSimboloLido().equals(String.valueOf(simbolo)))
					.collect(Collectors.toList());
			// Se essa lista tiver vazia, quer dizer que n�o existem produ��es com esse
			// s�mbolo a partir deste estado corrente
			// Remove o estado corrente, pois ele n�o levou a lugar algum
			// N�o faz nada???
			if (listProd.size() == 0) {
				RemoveCurrents.add(state);
				continue; // SE PRA ESSE CORRENTE ATUAL N�O ACHOU PROD ENT�O PRA ESSE CORRENTE ATUAL VC
							// REMOVE E VERIFICA OS OUTROS
				// PULA ESTA ITERA��O ATUAL
				// achouAlgumEstadoNovo = false; OU SEJA, N�O ACHOU
			}
			// Se n�o tiver vazia, nesta lista estar� as produ��es com os estados que podem
			// ser gerados a partir do corrente e o simbolo
			RemoveCurrents.add(state); // REMOVE O CORRENTE ANTIGO DA LISTA
			for (Transicao op : listProd)
				AddCurrents.add(op.getEstadoResultante());
			achouAlgumEstadoNovo = true;
		}
		for (Estado state : RemoveCurrents)
			estadosCorrentes.remove(state);
		for (Estado state : AddCurrents)
			estadosCorrentes.add(state);
		return achouAlgumEstadoNovo;
	}

	public HashSet<ConjuntoEstados> testaConversor() {
		return converteAFNDtoAFD(listaProducoes);
	}

	public HashSet<ConjuntoEstados> converteAFNDtoAFD(List<Transicao> listTransicoes) {
		List<Transicao> listAux = listTransicoes;
		HashSet<ConjuntoEstados> estadosNovos = new HashSet<>();// Evitar que tenha coisas iguais
		Estado origem;
		Estado result;
		String simbolo;
		for (Transicao op1 : listAux) {
			origem = op1.getEstadoOrigem();
			simbolo = op1.getSimboloLido();
			result = op1.getEstadoResultante();
			HashSet<Estado> listaEstadosResult = new HashSet<>(); // Quero ordem e não repetir
			for (Transicao op2 : listAux)
				// System.out.println("Op2 "+op2);
				// ACHAR OUTRA OP COM MESMA ORIGEM MESMO SIMBOLO MAS RESULTADO DIFERENTE,pra n
				// aceitar "ela mesma"
				tentaAcharOpIgual(listaEstadosResult, op2, origem, simbolo, result);
			// Se eu tiver achado pelo menos 2 prods com mesma origem e mesmo simbolo com
			// result diferente
			if (listaEstadosResult.size() > 0) {
				List<Estado> list = listaEstadosResult.stream().collect(Collectors.toList());
				ConjuntoEstados conjEstadoNovo = new ConjuntoEstados(list, false);
				if (!estadosNovos.contains(conjEstadoNovo))
					estadosNovos.add(conjEstadoNovo);
			}
		}
		return estadosNovos;
	}
	
	public HashSet<ConjuntoTransicao> testaParte2(){
		return converteParte2(converteAFNDtoAFD(listaProducoes));
	}
	
	public HashSet<ConjuntoTransicao> converteParte2(HashSet<ConjuntoEstados> listConjuntoEstados) {
		HashSet<ConjuntoTransicao> conjTransicao = new HashSet<>();
		HashSet<Estado> listaEstados = new HashSet<>();
		for(ConjuntoEstados conjOrigem : listConjuntoEstados) {
			HashSet<ConjuntoEstados> listConjResult = new HashSet<>();
			for(String str : listaSimbolosAutomato) {
				for(Estado state :conjOrigem.getListaEstados()) {// estado origem
					listaEstados = new HashSet<>();
					List<Transicao> listOp = listaProducoes.stream()
							.filter(op -> op.getEstadoOrigem().equals(state))
							.filter(op -> op.getSimboloLido().equals(str))
							.collect(Collectors.toList());
					System.out.println(listOp);
					for(Transicao prod : listOp) {
						listaEstados.add(prod.getEstadoResultante());
					}
				}
				List<Estado> list = listaEstados.stream().collect(Collectors.toList());
				ConjuntoEstados conjuntResult = new ConjuntoEstados(list,false);
				System.out.println(conjuntResult);
				listConjResult.add(conjuntResult);
				ConjuntoTransicao prod = new ConjuntoTransicao(conjOrigem,str,conjuntResult);
				conjTransicao.add(prod);
			}
		}
		return conjTransicao;
	}

	private void tentaAcharOpIgual(HashSet<Estado> listaEstadosResult, Transicao op, Estado origem, String simbolo,
			Estado result) {
		if (op.getEstadoOrigem() == origem && op.getSimboloLido().equals(simbolo)
				&& op.getEstadoResultante() != result) {
			listaEstadosResult.add(result); // result op1
			listaEstadosResult.add(op.getEstadoResultante());
		}
	}

}
