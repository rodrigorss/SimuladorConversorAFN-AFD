

public class ConjuntoTransicao {
	
	private ConjuntoEstados conjEstadosOrigem;
	private String simboloLido;
	private ConjuntoEstados conjEstadosResult;
	
	public ConjuntoTransicao(ConjuntoEstados conjEstadosOrigem, String simbolo, ConjuntoEstados conjEstadosResult) {
		this.conjEstadosOrigem = conjEstadosOrigem;
		this.simboloLido = simbolo;
		this.conjEstadosResult = conjEstadosResult;
	}
	
	public ConjuntoEstados getConjEstadosOrigem() {
		return conjEstadosOrigem;
	}
	
	public String getSimboloLido() {
		return simboloLido;
	}
	
	public ConjuntoEstados getConjEstadosResult() {
		return conjEstadosResult;
	}
	
	public String toString() {
		//return conjEstadosOrigem.getNome();
		return "("+conjEstadosOrigem+","+simboloLido+") = "+conjEstadosResult;
	}
	
	@Override
	public boolean equals(Object bagulho) {
		ConjuntoTransicao conj = ((ConjuntoTransicao)bagulho);
		return conj.getConjEstadosOrigem()==this.getConjEstadosOrigem() && conj.getSimboloLido()==this.getSimboloLido() 
				&& conj.getConjEstadosResult() == this.getConjEstadosResult();
	}

	/*@Override
	public int hashCode() {
		return getNome().hashCode();
	}*/
}
