import java.util.List;

public class ConjuntoEstados extends Estado {
	
	private List<Estado> listaEstados;
	
	public ConjuntoEstados(List<Estado> listaEstados, boolean estadoFinal) {
		super(makeNome(listaEstados), estadoFinal);
	}
	private static String makeNome(List<Estado> listaEstados) {
		String nome="";
		for(Estado state : listaEstados) {nome+= state.getNome();}
		return nome;
	}
}
