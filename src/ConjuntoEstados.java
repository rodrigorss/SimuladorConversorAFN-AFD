import java.util.List;

public class ConjuntoEstados extends Estado {

	private List<Estado> listaEstados;

	public ConjuntoEstados(List<Estado> listaEstados, boolean estadoFinal) {
		super(makeNome(listaEstados), estadoFinal);
		this.listaEstados = listaEstados;
	}

	private static String makeNome(List<Estado> listaEstados) {
		String nome = "";
		if (listaEstados == null)
			return nome;
		for (Estado state : listaEstados)
			nome += state.getNome();
		return nome;
	}
	
	public List<Estado> getListaEstados(){ return listaEstados;}

	@Override
	public String getNome() {
		return ConjuntoEstados.makeNome(listaEstados);
	}

	@Override
	public boolean equals(Object bagulho) {
		ConjuntoEstados cj = (ConjuntoEstados) bagulho;
		return cj.getNome().equals(this.getNome());
	}

	@Override
	public int hashCode() {
		return getNome().hashCode();
	}
}
