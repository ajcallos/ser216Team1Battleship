package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import battleship.Ship;

public class TestShip {

	public static final String HORIZONTAL_DIRECTION = "Horizontal";
	public static final String VERTICAL_DIRECTION = "Vertical";
	
	public static final String AIRCRAFT_CARRIER = "Aircraft Carrier";
	public static final String BATTLESHIP = "Battleship";
	public static final String SUBMARINE = "Submarine";
	public static final String DESTROYER = "Destroyer";
	public static final String PATROL_BOAT = "Patrol Boat";
	
	private Ship testShip = null;
	
	@Before
	public void setUp() throws Exception {
		testShip = new Ship(AIRCRAFT_CARRIER, HORIZONTAL_DIRECTION, 0, 0, 0);
	}

	@After
	public void tearDown() throws Exception {
		testShip = null;
	}

	@Test
	public void testShip() {
		new Ship(AIRCRAFT_CARRIER, HORIZONTAL_DIRECTION, 0, 0, 0);
		new Ship(BATTLESHIP, HORIZONTAL_DIRECTION, 0, 0, 0);
		new Ship();
	}
	
	@Test
	public void testShipReset() {
		testShip.shipReset();
	}
	
	@Test
	public void testPrintShips() {
		testShip.printShips();
	}
	
	@Test
	public void testSunk() {
		
		Ship testHitsShip = new Ship(SUBMARINE, HORIZONTAL_DIRECTION, 1, 0, 0);
		
		assertTrue(testShip.sunk());
		assertTrue(!testHitsShip.sunk());
	}
	
	@Test
	public void testGetName() {
		Ship testGetNameShip = new Ship(SUBMARINE, HORIZONTAL_DIRECTION, 1, 0, 0);
		
		assertTrue(testGetNameShip.getName().equals(SUBMARINE));
		assertTrue(testShip.getName().equals(AIRCRAFT_CARRIER));
		assertTrue(new Ship().getName().equals(null)); // TODO: fix Ship.java
	}
	
	@Test
	public void testSetName() {
		Ship testSetNameShip = new Ship();
		
		testSetNameShip.setName(SUBMARINE);
		assertTrue(testSetNameShip.getName().equals(SUBMARINE));
		testSetNameShip.setName(null);
		assertTrue(testSetNameShip.getName().equals(null));
	}
	
	@Test
	public void testGetDirectionOfShip() {
		assertTrue(testShip.getDirectionOfShip().equals(HORIZONTAL_DIRECTION));
	}
	
	@Test
	public void testSetDirectionOfShip() {
		testShip.setDirectionOfShip(VERTICAL_DIRECTION);
		assertTrue(testShip.getDirectionOfShip().equals(VERTICAL_DIRECTION));
	}
}
