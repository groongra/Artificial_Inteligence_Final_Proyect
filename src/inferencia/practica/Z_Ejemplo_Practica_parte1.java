package inferencia.practica;

import jeops.engine.KnowledgeBase;

public class Z_Ejemplo_Practica_parte1 {
	public static void main(java.lang.String[] args) {
		// generacion del motor de inferencia, se le introduce la dirección a las reglas y se selecciona una seleccion de reglas pro prioridad (no modificar esto ultimo)
		KnowledgeBase kb = new KnowledgeBase("src/inferencia/practica/Practica_parte1.rules", new jeops.engine.PriorityRuleSorter()); 
		/**
		 * Instanciar los diversos objetos
		 */
		Z_Ejemplo_Objeto1 ejemplo = new Z_Ejemplo_Objeto1("ejemplo", "empezado");
		kb.join(ejemplo); // introduce el objeto en la base de conocimiento

		
		/**
		 * Modificar para mostrar resultados 
		 */
		System.out.println("Inicio: "+ejemplo.getVariable1()+" > "+ejemplo.getVariable2());
		kb.run();		// ejecucion del motor de inferencia
		System.out.println("Resultado: "+ejemplo.getVariable1()+" > "+ejemplo.getVariable2());
		
	}
}
