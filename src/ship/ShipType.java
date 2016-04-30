package ship;

public enum ShipType {
	
	
	AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
	BATTLESHIP(4, "Battleship"),
	DESTROYER(3, "Destroyer"),
	SUBMARINE(3, "Submarine"),
	PATROL_BOAT(2, "Patrol Boat");
	
	private int length;
	private String name;
	
	ShipType(int length, String name) {
		this.length = length;
		this.name = name;
	}
	
	public int getLength() {
		return length;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
