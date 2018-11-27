import dk.brics.automaton.State;

public class Estado extends State {

	private static final long serialVersionUID = 4877533431426859368L;
	private String nome;

	public Estado(String nome) {
		super();
		this.nome = nome;
	}

	public Estado(String nome, boolean estadoFinal) {
		super();
		this.nome = nome;
		super.setAccept(estadoFinal);
	}

	public String getNome() {
		return nome;
	}

}
