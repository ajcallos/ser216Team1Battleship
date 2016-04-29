package ship;

class PatrolBoat extends Ship{
	
	public final static int PATROL_BOAT_SIZE = 2;
	
	public PatrolBoat () {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.size = PATROL_BOAT_SIZE;
		super.myType = ShipType.PATROL_BOAT;
		super.x = 0;
		super.y = 0;
	}
}
