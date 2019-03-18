package inferencia.practica;

import jeops.engine.KnowledgeBase;

public class Main {
	public static void main(java.lang.String[] args) {
		// generacion del motor de inferencia, se le introduce la dirección a las reglas y se selecciona una seleccion de reglas pro prioridad (no modificar esto ultimo)
		KnowledgeBase kb = new KnowledgeBase("src/inferencia/practica/Code_of_rules.rules", new jeops.engine.PriorityRuleSorter()); 
		
		/**
		 * Instanciar los diversos objetos
		 */
		
		Robot robot1 = new Robot(1);
		Book_shelf PIC = new Book_shelf("PIC","PIC",true);
		
		// Bookshelf ART
		Book_shelf BS_ADE_ART = new Book_shelf ("ADE","ART",true);
		Book_shelf BS_INF_ART = new Book_shelf ("INF","ART",true);
		Book_shelf BS_TUR_ART = new Book_shelf ("TUR","ART",true);
		
		// Bookshelf VOL
		Book_shelf BS_ADE_VOL = new Book_shelf ("ADE","VOL",true);
		Book_shelf BS_INF_VOL = new Book_shelf ("INF","VOL",true);
		Book_shelf BS_TUR_VOL = new Book_shelf ("TUR","VOL",true);
		
		// Bookshelf COL
		Book_shelf BS_ADE_COL = new Book_shelf ("ADE","COL",true);
		Book_shelf BS_INF_COL = new Book_shelf ("INF","COL",true);
		Book_shelf BS_TUR_COL = new Book_shelf ("TUR","COL",true);
		
		
		// Books ART
		Book B_ADE_ART1 = new Book("PIC","ADE","ART",1);
		Book B_ADE_ART2 = new Book("PIC","ADE","ART",1);
		Book B_INF_ART1 = new Book("PIC","INF","ART",1);
		Book B_INF_ART2 = new Book("PIC","INF","ART",1);
		Book B_TUR_ART1 = new Book("PIC","TUR","ART",1);
		Book B_TUR_ART2 = new Book("PIC","TUR","ART",1);
		
		// Books VOL
		Book B_ADE_VOL1 = new Book("PIC","ADE","VOL",3);
		Book B_ADE_VOL2 = new Book("PIC","ADE","VOL",3);
		Book B_INF_VOL1 = new Book("PIC","INF","VOL",3);
		Book B_INF_VOL2 = new Book("PIC","INF","VOL",3);
		Book B_TUR_VOL1 = new Book("PIC","TUR","VOL",3);
		Book B_TUR_VOL2 = new Book("PIC","TUR","VOL",3);
		
		// Books COL
		Book B_ADE_COL1 = new Book("PIC","ADE","COL",7);
		Book B_ADE_COL2 = new Book("PIC","ADE","COL",7);
		Book B_INF_COL1 = new Book("PIC","INF","COL",7);
		Book B_INF_COL2 = new Book("PIC","INF","COL",7);
		Book B_TUR_COL1 = new Book("PIC","TUR","COL",7);
		Book B_TUR_COL2 = new Book("PIC","TUR","COL",7);
		
		
		kb.join(robot1); // introduce el objeto en la base de conocimiento
		
		kb.join(PIC);
		
		
		kb.join(BS_ADE_ART);
		kb.join(BS_INF_ART);
		kb.join(BS_TUR_ART);
		
		kb.join(BS_ADE_VOL);
		kb.join(BS_INF_VOL);
		kb.join(BS_TUR_VOL);
		
		kb.join(BS_ADE_COL);
		kb.join(BS_INF_COL);
		kb.join(BS_TUR_COL);
		
		kb.join(B_ADE_ART1);
		kb.join(B_INF_ART1);
		kb.join(B_TUR_ART1);
		
		kb.join(B_ADE_VOL1);
		kb.join(B_INF_VOL1);
		kb.join(B_TUR_VOL1);
		
		kb.join(B_ADE_COL1);
		kb.join(B_INF_COL1);
		kb.join(B_TUR_COL1);
		
		
		
		
		/**
		 * Modificar para mostrar resultados 
		 */
		//System.out.println("Inicio: "+ejemplo.getVariable1()+" > "+ejemplo.getVariable2());
		kb.run();		// ejecucion del motor de inferencia
		//System.out.println("Resultado: "+ejemplo.getVariable1()+" > "+ejemplo.getVariable2());
		
	}
}
