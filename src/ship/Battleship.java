package ship;

class Battleship extends Ship {
	
	public Battleship () {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.myType = ShipType.BATTLESHIP;
		super.x = 0;
		super.y = 0;
	}
}
