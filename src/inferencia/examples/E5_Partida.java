package inferencia.examples;

public class E5_Partida {
	String [][] grid;
	int tam;
	String turno;
	String ganador="Vacio";

	public E5_Partida(int n) {
		this.grid = new String[n][n];
		this.turno="Blanco";
		this.tam=n;
		this.inicializar(n);
	}

	public String[][] getGrid() {
		return grid;
	}
	public String getGridValue(E5_posicion pos) {
		String value=this.grid[pos.getX()][pos.getY()];
		return value;
	}
	public void setGridValue(E5_posicion pos, String value) {
		this.grid[pos.getX()][pos.getY()]=value;
	}

	public void setGrid(String[][] grid) {
		this.grid = grid;
	}
	
	

	public String getGanador() {
		return ganador;
	}

	public void setGanador(String ganador) {
		this.ganador = ganador;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public void inicializar(int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.grid[i][j]="Vacio";
			}
		}
		this.grid[0][0]="Blanco";
		this.grid[0][size-1]="Negro";
		this.grid[size-1][0]="Negro";
		this.grid[size-1][size-1]="Blanco";

	}
	
	public int isAdyacentes(E5_posicion posicion1, E5_posicion posicion2) {
		int ady=0;
		if(posicion1.getX()==posicion2.getX()) {
			if(posicion1.getY()==posicion2.getY()+1 || posicion1.getY()==posicion2.getY()-1) ady=1;
			if(posicion1.getY()==posicion2.getY()+2 || posicion1.getY()==posicion2.getY()-2) ady=2;
		}
		else if(posicion1.getY()==posicion2.getY()) {
			if(posicion1.getX()==posicion2.getX()+1 || posicion1.getX()==posicion2.getX()-1) ady=1;
			if(posicion1.getX()==posicion2.getX()+2 || posicion1.getX()==posicion2.getX()-2) ady=2;
		}
		else if(posicion1.getX()==posicion2.getX()+1 || posicion1.getX()==posicion2.getX()-1) {
			if(posicion1.getY()==posicion2.getY()+1 || posicion1.getY()==posicion2.getY()-1) ady=1;
		}
		else if(posicion1.getY()==posicion2.getY()+1 || posicion1.getY()==posicion2.getY()-1) {
			if(posicion1.getX()==posicion2.getX()+1 || posicion1.getX()==posicion2.getX()-1) ady=1;
		}
		else if(posicion1.getX()==posicion2.getX()+2 || posicion1.getX()==posicion2.getX()-2) {
			if(posicion1.getY()==posicion2.getY()+1 || posicion1.getY()==posicion2.getY()-1) ady=2;
			if(posicion1.getY()==posicion2.getY()+2 || posicion1.getY()==posicion2.getY()-2) ady=2;
		}
		else if(posicion1.getY()==posicion2.getY()+2 || posicion1.getY()==posicion2.getY()-2) {
			if(posicion1.getX()==posicion2.getX()+1 || posicion1.getX()==posicion2.getX()-1) ady=1;
			if(posicion1.getX()==posicion2.getX()+2 || posicion1.getX()==posicion2.getX()-2) ady=2;
		}
		return ady;
	}
	
	public boolean finJuego() {
		boolean flag=true;
		for (int i = 0; i < this.tam; i++) {
			for (int j = 0; j < this.tam; j++) {
				if (this.grid[i][j].equals("Vacio")) {
					flag=false;
				}
			}
		}
		return flag;
	}
	
	public void ganador() {
		int cuentaB=0;
		int cuentaN=0;
		for (int i = 0; i < this.tam; i++) {
			for (int j = 0; j < this.tam; j++) {
				if (this.grid[i][j].equals("Blanco")) {
					cuentaB++;
				}
				if (this.grid[i][j].equals("Negro")) {
					cuentaN++;
				}
			}
		}
		if(cuentaB>cuentaN) {
			this.ganador="Blanco";
		}
		if(cuentaB<cuentaN) {
			this.ganador="Negro";
		}
		if(cuentaB==cuentaN) {
			this.ganador="Empate";
		}
	
	}

}
