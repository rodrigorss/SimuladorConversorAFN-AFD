import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AutomatoAFND {
	//LISTA TODOS OS ESTADOS DO AUTOMATO
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
	
	public AutomatoAFND(List<Estado> listaEstados,List<String> listaSimbolos,Estado estadoInit,List<Estado> listEstadosFim,List<Transicao> listaProd) {
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
		
		// Para cada simbolo vê como o automato se comporta nos estados correntes
		for(char c : listaSimbolos) {
			boolean executa = temProxEstados(c);//Se executou o simbolo e chegou em algum lugar válido
			if(executa = true) {
				// 	automato eceitou este simbolo de entrada e obteve, a partir dos estados correntes antigos e o simbolo de entrada,
				// novos estados correntes.
				// OK, PROSSEGUIR
			}else {
				//	 automato não encontrou nenhuma producao que a partir de um estado corrente e o simbolo de entrada pedido resultasse
				// em algum novo estado corrente.
				// ERRO, AUTOMATO NÃO ACEITA ESTA PALAVRA
				return "O autômato não aceita a palavra :"+ palavra; // break
			}
		}
		// Agora basta verificar se no final da execução eu parei em algum estado final
		boolean contemEstadoFinal = false;
		for(Estado state : listaEstadosCorrentes) {
			for(Estado stateFinal : listaEstadosFinal) {
				if(state == stateFinal) {
					contemEstadoFinal = true;
				}
			}
		}
		if(contemEstadoFinal == false) {
			return "O autômato não aceita a palavra :"+ palavra;
		}else {
			return "O autômato não aceita a palavra :"+ palavra;
		}
	}
	
	//METODO QUE DADO UM SIMBOLO DE ENTRADA ME DIZ SE ACHOU ALGUM ESTADO NOVO A PARTIR DE ALGUM ESTADO CORRENTE E O SIMBOLO DE ENTRADA
	//CASO TENHA ENCONTRADO JÁ TROCA O ESTADO CORRENTE PELO NOVO ESTADO GERADO A PARTIR DO ANTIGO CORRENTE E O SIMBOLO DE ENTRADA
	public boolean temProxEstados(char simbolo) {
		boolean achouAlgumEstadoNovo = false;
		// para cada estado corrente checar quais são os novos estados com esse simbolo
		for(Estado state : listaEstadosCorrentes) {
			// PEGA A LISTA COM TODAS AS PROD QUE TIVEREM ESTE 'STATE' COMO ORIGEM + O SIMBOLO DE ENTRADA PEDIDO
			List<Transicao> listProd = listaProducoes.stream()
					.filter(op -> op.getEstadoOrigem() == state && op.getSimboloLido() == String.valueOf(simbolo))
					.collect(Collectors.toList());
			//Se essa lista tiver vazia, quer dizer que não existem produções com esse símbolo a partir deste estado corrente
			// Remove o estado corrente, pois ele não levou a lugar algum
			// Não faz nada???
			if(listProd == null) { 
				listaEstadosCorrentes.remove(state);
				continue; // SE PRA ESSE CORRENTE ATUAL NÃO ACHOU PROD ENTÃO PRA ESSE CORRENTE ATUAL VC REMOVE E VERIFICA OS OUTROS
				// PULA ESTA ITERAÇÃO ATUAL
				//achouAlgumEstadoNovo = false; OU SEJA, NÃO ACHOU
			}
			//Se não tiver vazia, nesta lista estará as produções com os estados que podem ser gerados a partir do corrente e o simbolo
			listaEstadosCorrentes.remove(state); // REMOVE O CORRENTE ANTIGO DA LISTA
			for(Transicao op : listProd) { // PRA CADA TRANSICAO NOVA ENCONTRADA, ADICIONAR O ESTADO NOVO RESULTANTE PARA SER O CORRENTE
				listaEstadosCorrentes.add(op.getEstadoResultante());
			}
			achouAlgumEstadoNovo = true;
		}
		return achouAlgumEstadoNovo;
	}
}
