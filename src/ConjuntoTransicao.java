
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

	@Override
	public String toString() {
		// return conjEstadosOrigem.getNome();
		return "(" + conjEstadosOrigem + "," + simboloLido + ") = " + conjEstadosResult;
	}

	@Override
	public boolean equals(Object bagulho) {
		ConjuntoTransicao conj = (ConjuntoTransicao) bagulho;
		return conj.getConjEstadosOrigem().equals(this.getConjEstadosOrigem())
				&& conj.getSimboloLido().equals(this.getSimboloLido())
				&& conj.getConjEstadosResult().equals(this.getConjEstadosResult());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
