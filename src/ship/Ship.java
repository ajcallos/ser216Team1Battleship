// I just realized this should have been an interface.
// I'd change it, but I realized it at deadline.

package ship;

public abstract class Ship {
	protected ShipType myType;
	protected ShipDirection myDirection;
	protected int x;
	protected int y;
	protected int size;
	protected int hits;
	
	public final static ShipDirection DEFAULT_DIRECTION = ShipDirection.HORIZONTAL; 
	public final static int INITIAL_HITS = 0;
	public final static int INITIAL_X = 0;
	public final static int INITIAL_Y = 0;
	
	public void shipReset() {
		x = 0;
		y = 0;
		myDirection = DEFAULT_DIRECTION;
	}

	@Override 
	public String toString() {
		return String.format("Type:%s\nDirection:%s\nPosition: %d, %d\n%d hits", 
				myType.toString(), myDirection.toString(), x, y, hits);
	}

	public boolean sunk() {
		return hits >= size;
	}

	public ShipType getType() {
		return myType;
	}

	public ShipDirection getDirectionOfShip() {
		return myDirection;
	}

	public void setDirectionOfShip(ShipDirection directionOfShip) {
		this.myDirection = directionOfShip;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void addHits() {
		this.hits++;
	}
}
	
