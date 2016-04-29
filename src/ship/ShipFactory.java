package ship;

/**
 * Fulfills the Factory and Singleton patterns by returning whatever ships
 * are requested of it.
 * 
 * Since a given player can only ever have one of each type of ship, it makes sense
 * to use a singleton pattern to enforce this.
 * 
 */
public class ShipFactory {
	private PatrolBoat myPatrolBoat;
	private Submarine mySubmarine;
	private Destroyer myDestroyer;
	private Battleship myBattleship;
	private AircraftCarrier myAircraftCarrier;
	
	
	public ShipFactory() {
		myPatrolBoat = new PatrolBoat();
		mySubmarine = new Submarine();
		myDestroyer = new Destroyer();
		myBattleship = new Battleship();
		myAircraftCarrier = new AircraftCarrier();
	}
	
	public Ship getShip(ShipType someShip) {
		if (someShip == ShipType.PATROL_BOAT) {
			return getPatrolBoat();
		} else if (someShip == ShipType.DESTROYER) {
			return getDestroyer();
		} else if (someShip == ShipType.SUBMARINE) {
			return getSubmarine();
		} else if (someShip == ShipType.BATTLESHIP) {
			return getBattleship();
		} else {
			return getAircraftCarrier();
		}
	}
	
	public Ship getPatrolBoat() {
		return myPatrolBoat;
	}
	
	public Ship getSubmarine() {
		return mySubmarine;
	}
	
	public Ship getDestroyer() {
		return myDestroyer;
	}
	
	public Ship getBattleship() {
		return myBattleship;
	}
	
	public Ship getAircraftCarrier() {
		return myAircraftCarrier;
	}
	
	/**
	 * Fetches all possible ships in an array
	 * @return all possible player ships
	 */
	public Ship[] getShipArray() {
		Ship[] allMyShips = {
				myPatrolBoat, mySubmarine, myDestroyer, myBattleship, myAircraftCarrier
		};
		
		return allMyShips;
	}
}
