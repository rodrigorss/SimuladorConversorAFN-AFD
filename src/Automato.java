import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	
	public String testaPalavra(String palavra) {
		List<Estado> listEstadosCorrentes = new LinkedList<>();
		listEstadosCorrentes.add(estadoInicial);
		char simbolos[] = palavra.toCharArray();
		
		//Para cada simbolo, verificar como ficam os estados correntes.
		for(char c : simbolos) {
			boolean temProxEstados = verificaProxEstado(listEstadosCorrentes,c);
			if(temProxEstados) {
				continue;// Ta okay
			}else {
				return "O automato nao aceita a palavra :"+palavra;
			}
		}
		boolean temEstadoFinal = false;
		for(Estado state : listEstadosCorrentes) {if(state.isEstadoFinal()) temEstadoFinal = true; break;}
		if(temEstadoFinal) {
			return "O automato aceita a palavra :"+palavra;
		}else {
			return "O automato nao aceita a palavra :"+palavra;
		}
	}
	private boolean verificaProxEstado(List<Estado> listEstadosCorrentes, char simbolo) {
		boolean achou = false;
		List<Estado> list = listEstadosCorrentes;
		//Para cada estado verifica pra onde vai
		for(Estado state : list) {
			//Pega para o estado corrente do laço as possibilidades com o simbolo recebido
			List<Estado> prods = state.getTransicoes().get(String.valueOf(simbolo));
			//Se o mapa estiver vazio então a partir do estado corrente não existem novos estados possiveis
			if(prods.isEmpty()){listEstadosCorrentes.remove(state);continue;}//pula o resto dos passos
			//Se não estiver vazia, então existe um estado novo valido a partir do simbolo recebido
			listEstadosCorrentes.remove(state);//Remove o estado antigo, de onde veio
			//Adiciona os novos estados atingidos na lista de estados correntes
			for(Estado atual : prods) {listEstadosCorrentes.add(atual);}
			achou = true;
		}
		return achou;
	}

}