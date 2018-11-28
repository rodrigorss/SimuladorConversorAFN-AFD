
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

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public boolean equals(Object bagulho) {
		return ((Estado) bagulho).getNome().equals(this.nome);
	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}
}
