package ship;

class Destroyer extends Ship {
	
	public Destroyer() {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.myType = ShipType.DESTROYER;
		super.x = 0;
		super.y = 0;
	}
}
