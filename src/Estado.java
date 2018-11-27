
public class Estado {

	private String nome;
	private boolean estadoFinal;

	public Estado(String nome, boolean estadoFinal) {
		this.nome = nome;
		this.estadoFinal = estadoFinal;
	}

	public boolean isFinal() {
		return estadoFinal;
	}

	public String getNome() {
		return nome;
	}
}
