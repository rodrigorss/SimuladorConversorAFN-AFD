
public class Transicao {
	private Estado estadoOrigem;
	private String simboloLido;
	private Estado estadoResultante;
	
	public Transicao(Estado estadoOri,String simbolo,Estado estadoResult) {
		this.estadoOrigem = estadoOri;
		this.simboloLido = simbolo;
		this.estadoResultante = estadoResult;
	}
	
	public Estado getEstadoOrigem() { return estadoOrigem; }
	public String getSimboloLido() { return simboloLido; }
	public Estado getEstadoResultante() { return estadoResultante; }
}
