package inferencia.practica;

import java.util.ArrayList;

public class Book_shelf {
	public final String area; //{INF,ADE,TUR,PIC}
	public final String type; //{ART,VOL,COL,PIC}
	public boolean free;
	ArrayList <Book> books_included = new ArrayList <Book>();
	
	public Book_shelf(String area, String type, boolean isFree) {
		this.area=area;
		this.type=type;
		this.free = isFree;
	}
	
	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public String getArea() {
		return area;
	}

	public String getType() {
		return type;
	}
	//funcion que ayude a comoarar un libro y una estanteria
	
	public boolean belongingBook(Book book) {
		return(book.area.equals(area)&&book.type.equals(type));
	}

}
