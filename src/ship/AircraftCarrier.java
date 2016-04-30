package ship;

class AircraftCarrier extends Ship {
	
	public AircraftCarrier() {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.myType = ShipType.AIRCRAFT_CARRIER;
		super.x = 0;
		super.y = 0;
	}
}
