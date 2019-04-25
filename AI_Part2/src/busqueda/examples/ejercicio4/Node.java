package busqueda.examples.ejercicio4;

/**
 * Clase creada como ejemplo de la parte 2 de la práctica 2018-2019 de IA, UC3M Colmenarejo
 *
 * 
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class Node {
	private double coste;							// Valor del coste de llegada al nodo
	private double heuristica;						// Valor de la heuristica del nodo
	private double evaluacion;						// Valor de la evaluacion
	private Node parent;							// Nodo padre del arbol A*
	private Node nextNodeList = null;				// Para la gestion de la lista Slist
	
	private boolean randomStart=false;
	// Variables especificas al problema
	/**
	 * MODIFICAR para ajustarse a las necesidades del problema
	 */
	private boolean luces[][]= new boolean [4][4];

	//Constructor para introducir un nuevo nodo en el algoritmo A estrella
	/**
	 * MODIFICAR para ajustarse a las necesidades del problema
	 */
	public Node(Node parentNode, boolean initial, boolean goal, int celda[]) {
		super();
		this.parent = parentNode;// padre en el árbol A*
		
		if(goal){				// Inicializa con todas las luces encendidas
			for (int i = 0; i < this.luces[0].length; i++) {
				for (int j = 0; j < this.luces[0].length; j++) {
					this.luces[i][j]=true;
					
				}
			}
		}
		else if(initial){		// Se genera un tablero para inicializar
			
			if(randomStart) {	// true. Se inicializa de manera aleatoria como indica el enunciado 
				for (int i = 0; i < this.luces[0].length; i++) {
					for (int j = 0; j < this.luces[0].length; j++) {
						this.luces[i][j]=false;
						if(Math.random()<0.5)this.luces[i][j]=true;
						
					}
				}
			}
			else {				// false. Se inicializa de manera concreta para llegar a una solución 
				for (int i = 0; i < luces[0].length; i++) {
					for (int j = 0; j < luces[0].length; j++) {
						luces[i][j]=true;
						
					}
				}
				luces[3][3]=false;
				luces[2][2]=false;
				luces[2][1]=false;
				luces[1][2]=false;
			}	
			
		}
		else{					// Genera un nuevo tablero a partir del tablero del nodo padre y la acción realizada
			for (int i = 0; i < this.luces[0].length; i++) {
				for (int j = 0; j < this.luces[0].length; j++) {
					this.luces[i][j]=parentNode.luces[i][j];
					
				}
			}
			if(this.luces[celda[0]][celda[1]]){this.luces[celda[0]][celda[1]]=false;}
			else{ this.luces[celda[0]][celda[1]]=true;}
			
			if(celda[0]>0 && this.luces[celda[0]-1][celda[1]]){this.luces[celda[0]-1][celda[1]]=false;}
			else if(celda[0]>0){ this.luces[celda[0]-1][celda[1]]=true;}
			
			if(celda[1]>0 && this.luces[celda[0]][celda[1]-1]){this.luces[celda[0]][celda[1]-1]=false;}
			else if(celda[1]>0){ this.luces[celda[0]][celda[1]-1]=true;}
			
			if(celda[0]<3 && this.luces[celda[0]+1][celda[1]]){this.luces[celda[0]+1][celda[1]]=false;}
			else if(celda[0]<3){ this.luces[celda[0]+1][celda[1]]=true;}
			
			if(celda[1]<3 && this.luces[celda[0]][celda[1]+1]){this.luces[celda[0]][celda[1]+1]=false;}
			else if(celda[1]<3){ this.luces[celda[0]][celda[1]+1]=true;}
		}
		
	}

	// Constructor auxiliar para la implementación del algoritmo. Genera una copia de un nodo para introducirla en la OpenList
	public Node(Node original) {

		// Incluir todas las variables del nodo
		this.coste = original.coste;
		this.heuristica = original.heuristica;
		this.evaluacion = original.evaluacion;
		this.parent = original.parent;
		this.nextNodeList = original.nextNodeList;

		/**
		 * MODIFICAR para ajustarse a las necesidades del problema
		 */
		this.luces = original.luces;
	}
	
	// Constructor auxiliar para generar el primer nodo de la lista abierta
	public Node() {	}

	// Calcula el valor de la heuristica del problema para el nodo 
	/**
	 * MODIFICAR para ajustarse a las necesidades del problema
	 */
	public void calculaHeuristica(Node finalNode) {
		int aux=0;
		for (int i = 0; i < luces[0].length; i++) {	// La heurística es el número de casillas diferentes respecto al nodo meta
			for (int j = 0; j < luces[0].length; j++) {
				if(luces[i][j]!=finalNode.luces[i][j])aux++;
			}
		}
		this.heuristica = aux; 
	}

	// Comprobación de que la informacion de un nodo es equivalente a la de otro nodo
	/**
	 * MODIFICAR la condición para ajustarse a las necesidades del problema
	 */
	public boolean equals(Object arg0) {
		Node other = (Node) arg0;
		boolean check=true;  
		for (int i = 0; i < luces[0].length; i++) {
			for (int j = 0; j < luces[0].length; j++) {
				if(luces[i][j]!=other.luces[i][j])check=false;
			}
		}
		return check;
	}

	// Impresión de la informacion del nodo
	/**
	 * MODIFICAR para imprimir las comprobaciones que se consideren para explicar los resultados
	 */
	public void printNodeData() {
		System.out.println("mi coste es "+this.coste+" mi heurística es "+this.heuristica+" y mi matriz es: ");
		for (int i = 0; i < luces[0].length; i++) {
			for (int j = 0; j < luces[0].length; j++) {
				if(luces[i][j])	System.out.print(1);
				else System.out.print(0);
			}
			System.out.println("");
		}
	}

	// Actualiza el coste del nodo. Introducir el coste
	public void calculaCoste( double costeAccion ) {
		this.coste = costeAccion; 
	}

	// Ejecuta la funcion de evaluación del problema para el nodo. IMPORTANTE: ejecutar antes el cálculo del coste y heurística
	public void calculaEvaluacion() {
		this.evaluacion = this.coste+this.heuristica; 
	}

	
	/**** Getters y Setters ****/
	/**
	 * MODIFICAR si se considera necesario. No es imprescindible, solo si consideras que puede ayudar a tu implementación
	 */
	public double getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(double evaluacion) {
		this.evaluacion = evaluacion;
	}
	public double getCoste() {
		return coste;
	}

	public void setCoste(double coste) {
		this.coste = coste;
	}

	public double getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(double heuristica) {
		this.heuristica = heuristica;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean[][] getLuces() {
		return luces;
	}

	public void setLuces(boolean[][] luces) {
		this.luces = luces;
	}

	public Node getNextNode() {
		return nextNodeList;
	}

	public void setNextNode(Node nextNode) {
		this.nextNodeList = nextNode;
	}



}
