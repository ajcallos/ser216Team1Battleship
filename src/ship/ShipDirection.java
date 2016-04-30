package ship;

public enum ShipDirection {
	HORIZONTAL("Horizontal"),
	VERTICAL("Vertical");
	
	private String name;
	
	ShipDirection(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
