package ship;

public enum ShipType {
	AIRCRAFT_CARRIER(5),
	BATTLESHIP(4),
	DESTROYER(3),
	SUBMARINE(3),
	PATROL_BOAT(2);
	
	private int length;
	
	ShipType(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
}
