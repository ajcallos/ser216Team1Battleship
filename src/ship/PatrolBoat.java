package ship;

class PatrolBoat extends Ship{
	
	public PatrolBoat () {
		super();
		super.myDirection = DEFAULT_DIRECTION;
		super.hits = INITIAL_HITS;
		super.myType = ShipType.PATROL_BOAT;
		super.x = 0;
		super.y = 0;
	}
}
