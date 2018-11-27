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

	// AUXILIARES
	List<Estado> listaEstadosCorrentes;

	public AutomatoAFND(List<Estado> listaEstados, List<String> listaSimbolos, Estado estadoInit,
			List<Estado> listEstadosFim, List<Transicao> listaProd) {
		this.listaEstadosAutomato = new LinkedList<>(listaEstados);
		this.listaSimbolosAutomato = new LinkedList<>(listaSimbolos);
		this.estadoInicial = estadoInit;
		this.listaEstadosFinal = new LinkedList<>(listEstadosFim);
		this.listaProducoes = new LinkedList<>(listaProd);

		this.listaEstadosCorrentes = new LinkedList<>();
		listaEstadosCorrentes.add(estadoInicial);
	}

	public String verificaSeAceitaPalavra(String palavra) {
		char[] listaSimbolos = palavra.toCharArray(); // lista com cada simbolo da palavra 1 a 1 separado

		// Para cada simbolo v� como o automato se comporta nos estados correntes
		for (char c : listaSimbolos) {
			boolean executa = temProxEstados(c);// Se executou o simbolo e chegou em algum lugar v�lido
			if (executa = true) {
				// automato eceitou este simbolo de entrada e obteve, a partir dos estados
				// correntes antigos e o simbolo de entrada,
				// novos estados correntes.
				// OK, PROSSEGUIR
			} else
				// automato n�o encontrou nenhuma producao que a partir de um estado corrente e
				// o simbolo de entrada pedido resultasse
				// em algum novo estado corrente.
				// ERRO, AUTOMATO N�O ACEITA ESTA PALAVRA
				return "O aut�mato n�o aceita a palavra :" + palavra; // break
		}
		// Agora basta verificar se no final da execu��o eu parei em algum estado final
		boolean contemEstadoFinal = false;
		for (Estado state : listaEstadosCorrentes)
			for (Estado stateFinal : listaEstadosFinal)
				if (state == stateFinal)
					contemEstadoFinal = true;
		if (contemEstadoFinal == false)
			return "O aut�mato n�o aceita a palavra :" + palavra;
		else
			return "O aut�mato n�o aceita a palavra :" + palavra;
	}

	// METODO QUE DADO UM SIMBOLO DE ENTRADA ME DIZ SE ACHOU ALGUM ESTADO NOVO A
	// PARTIR DE ALGUM ESTADO CORRENTE E O SIMBOLO DE ENTRADA
	// CASO TENHA ENCONTRADO J� TROCA O ESTADO CORRENTE PELO NOVO ESTADO GERADO A
	// PARTIR DO ANTIGO CORRENTE E O SIMBOLO DE ENTRADA
	public boolean temProxEstados(char simbolo) {
		boolean achouAlgumEstadoNovo = false;
		// para cada estado corrente checar quais s�o os novos estados com esse simbolo
		for (Estado state : listaEstadosCorrentes) {
			// PEGA A LISTA COM TODAS AS PROD QUE TIVEREM ESTE 'STATE' COMO ORIGEM + O
			// SIMBOLO DE ENTRADA PEDIDO
			List<Transicao> listProd = listaProducoes.stream()
					.filter(op -> op.getEstadoOrigem() == state && op.getSimboloLido() == String.valueOf(simbolo))
					.collect(Collectors.toList());
			// Se essa lista tiver vazia, quer dizer que n�o existem produ��es com esse
			// s�mbolo a partir deste estado corrente
			// Remove o estado corrente, pois ele n�o levou a lugar algum
			// N�o faz nada???
			if (listProd == null) {
				listaEstadosCorrentes.remove(state);
				continue; // SE PRA ESSE CORRENTE ATUAL N�O ACHOU PROD ENT�O PRA ESSE CORRENTE ATUAL VC
							// REMOVE E VERIFICA OS OUTROS
				// PULA ESTA ITERA��O ATUAL
				// achouAlgumEstadoNovo = false; OU SEJA, N�O ACHOU
			}
			// Se n�o tiver vazia, nesta lista estar� as produ��es com os estados que podem
			// ser gerados a partir do corrente e o simbolo
			listaEstadosCorrentes.remove(state); // REMOVE O CORRENTE ANTIGO DA LISTA
			for (Transicao op : listProd)
				listaEstadosCorrentes.add(op.getEstadoResultante());
			achouAlgumEstadoNovo = true;
		}
		return achouAlgumEstadoNovo;
	}
}
