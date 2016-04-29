package ship;

class Destroyer extends Ship {
	
	public final static int DESTROYER_SIZE = 3;
	
	public Destroyer() {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.size = DESTROYER_SIZE;
		super.myType = ShipType.DESTROYER;
		super.x = 0;
		super.y = 0;
	}
}
