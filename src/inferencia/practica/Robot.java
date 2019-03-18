package inferencia.practica;
import java.util.ArrayList;

public class Robot {

	public final int MAX_WEIGHT = 10;
	public int id;
	public String position; //{PIC,INF,ADE,TUR}
	public int weight;	//represents the ammount of KGs it carrys
	public int battery;	//represents the level of battery the robot has
	ArrayList <Book_shelf> route = new ArrayList <Book_shelf>(); // Create an ArrayList object which represents the area where it is located
	
	public Robot(int id) {
		this.battery = 10;
		this.weight = 0;
		this.route = null;
		this.position = "PIC";
		this.id = id;
	}
	
	public int getBattery() {
		return battery;
	}
	public void setBattery(int battery) {
		this.battery = battery;
	}
	public int getCarry() {
		return weight;
	}
	public void setCarry(int carry) {
		this.weight = carry;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public ArrayList<Book_shelf> getRoute() {
		return route;
	}

	public void setRoute(ArrayList<Book_shelf> route) {
		this.route = route;
	}

	public int getMAX_WEIGHT() {
		return MAX_WEIGHT;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public void decrementBattery () {
		this.battery = battery - 1; 
	}
	
	public void decrementWeight(Book book) {
		this.weight = weight - book.weight;
	}
	
	public boolean isLoaded (Book book) {
		return book.position.equals("ROB");
	}
	public void print() {
		System.out.println("	R" + id + "- Pos: "+position+" Weight: "+weight+" Bat: "+battery+" ");
	}
	
	/*public boolean isBattery () {
		if(battery>1)return;
		else()return;
	}*/

}
