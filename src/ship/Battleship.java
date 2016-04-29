package ship;

class Battleship extends Ship {
	
	public final static int BATTLESHIP_SIZE = 4;
	
	public Battleship () {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.size = BATTLESHIP_SIZE;
		super.myType = ShipType.BATTLESHIP;
		super.x = 0;
		super.y = 0;
	}
}
