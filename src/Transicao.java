
public class Transicao {
	private Estado estadoOrigem;
	private String simboloLido;
	private Estado estadoResultante;

	public Transicao(Estado estadoOri, String simbolo, Estado estadoResult) {
		this.estadoOrigem = estadoOri;
		this.simboloLido = simbolo;
		this.estadoResultante = estadoResult;
	}

	public Estado getEstadoOrigem() {
		return estadoOrigem;
	}

	public String getSimboloLido() {
		return simboloLido;
	}

	public Estado getEstadoResultante() {
		return estadoResultante;
	}
	
	@Override
	public String toString() {
		return "("+getEstadoOrigem()+","+getSimboloLido()+") = "+getEstadoResultante();
	}
	@Override
	public boolean equals(Object bagulho) {
		Transicao tt = (Transicao)bagulho;
		return tt.getEstadoOrigem().equals(this.estadoOrigem) && tt.getSimboloLido().equals(this.simboloLido)&&tt.getEstadoResultante().equals(this.estadoResultante);
	}

	
	/*@Override
	public int compareTo(Transicao op) {
		if(op.getEstadoOrigem() == estadoOrigem && op.getSimboloLido().equals(simboloLido) && op.getEstadoResultante() == estadoResultante) {
			return 0;
		}
		return -1;
	}*/
}
