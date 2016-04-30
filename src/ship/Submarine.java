package ship;

class Submarine extends Ship {
	
	public Submarine () {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.myType = ShipType.SUBMARINE;
		super.x = 0;
		super.y = 0;
	}
}
