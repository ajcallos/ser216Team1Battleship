package battleship;

public class Ship {
	String name, directionOfShip;
	int x, y, size, hits;

	public Ship() {
		
	}
	
	public Ship(String name, String directionOfShip, int size, int x, int y) {
		this.name = name;
		this.directionOfShip = directionOfShip;
		this.size = size;
		this.x = x;
		this.y = y;
		this.hits = 0;
	}

	public void shipReset() {
		x = 0;
		y = 0;
		directionOfShip = "";
	}

	public void printShips() {
		System.out.println(name);
		System.out.println(directionOfShip);
		System.out.println(x);
		System.out.println(y);
		System.out.println(size);
		System.out.println(hits);
	}

	public boolean sunk() {
		if (this.hits == this.size) {
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirectionOfShip() {
		return directionOfShip;
	}

	public void setDirectionOfShip(String directionOfShip) {
		this.directionOfShip = directionOfShip;
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
	
