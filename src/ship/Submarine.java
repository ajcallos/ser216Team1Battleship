package ship;

class Submarine extends Ship {
	
	public final static int SUBMARINE_SIZE = 3;
	
	public Submarine () {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.size = SUBMARINE_SIZE;
		super.myType = ShipType.SUBMARINE;
		super.x = 0;
		super.y = 0;
	}
}
