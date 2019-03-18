package inferencia.examples;

import jeops.engine.KnowledgeBase;

public class Prog_E5 {
	public static void main(java.lang.String[] args) {
		// Insira aqui o codigo para iniciar a aplicacion.
		int tam=4;
		E5_Partida P = new E5_Partida(tam);
		
		KnowledgeBase kb = new KnowledgeBase("src/inferencia/examples/Ejercicio5.rules", new jeops.engine.PriorityRuleSorter());
		kb.join(P);
		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < tam; j++) {
				E5_posicion pos=new E5_posicion(i, j);
				kb.join(pos);
			}
		}
		E5_jugador Jugador1 = new E5_jugador("Blanco", "Negro");
		kb.join(Jugador1);
		E5_jugador Jugador2 = new E5_jugador("Negro", "Blanco");
		kb.join(Jugador2);
		
		System.out.println("INICIO");

		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < tam; j++) {
				System.out.print(" "+P.getGrid()[i][j]);
			}
			System.out.println();
		}
		
		kb.run();
		System.out.println("EJECUTADO, ganador: "+P.getGanador());
		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < tam; j++) {
				System.out.print(" "+P.getGrid()[i][j]);
			}
			System.out.println();
		}
	}
}
