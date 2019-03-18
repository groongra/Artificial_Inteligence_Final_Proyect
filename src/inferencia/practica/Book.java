package inferencia.practica;

public class Book {

	public String position; 	// {PIC,INF,ADE,TUR,ROB} Shows the current location of the book
	public final String area; 	// {INF,ADE,TUR} Shows the area where the book belongs
	public final String type; 	// {ART,VOL,COL} Shows the the destination of the book
	public final int weight;	// {ART=1,VOL=3,COL=7} Shows the the weight of the book

	public Book(String position, String area, String type, int weight) {
		this.position = position;
		this.area = area;
		this.type = type;
		if(weight==1||weight==3||weight==7) this.weight=weight;
		else {
			System.out.println("Not a valid book weight");
			this.weight = -1;
		}
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		if(position.equals("PIC")||position.equals("INF")||position.equals("ADE")||position.equals("TUR")||position.equals("ROB"))
		this.position = position;
		else System.out.println("Not a valid position");
	}

	public String getArea() {
		return area;
	}

	public String getType() {
		return type;
	}

	public int getWeight() {
		return weight;
	}
	
	public void print() {
		System.out.println("	B - Pos: "+position+" Area: "+area+" Type: "+type+" ");
	}
}