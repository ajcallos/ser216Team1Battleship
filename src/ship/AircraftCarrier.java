package ship;

class AircraftCarrier extends Ship {
	
	public final static int AIRCRAFT_CARRIER_SIZE = 5;
	
	public AircraftCarrier() {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.size = AIRCRAFT_CARRIER_SIZE;
		super.myType = ShipType.AIRCRAFT_CARRIER;
		super.x = 0;
		super.y = 0;
	}
}
