package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ship.Ship;
import ship.ShipDirection;
import ship.ShipType;
import ship.ShipFactory;

public class TestShip {
	
	private Ship[] testShips = null;
	private ShipFactory testFactory = null;
	
	private final int[] arrayTestCases = {
			-1, 0, 5, 11, 10, 9, 5
	};
	
	private final ShipDirection[] shipDirections = {
			ShipDirection.HORIZONTAL, ShipDirection.VERTICAL
	};
	
	@Before
	public void setUp() throws Exception {
		testFactory = new ShipFactory();
		testShips = testFactory.getShipArray();
	}

	@After
	public void tearDown() throws Exception {
		testShips = null;
	}

	@Test
	public void testSetX() {
		for (int i = 0; i < arrayTestCases.length; i++){
			int x = arrayTestCases[i];
			for (Ship shipElement : testShips) {
				shipElement.setX(x);
				assertTrue(shipElement.getX() == x);
			}
		}
	}
	
	@Test
	public void testSetY() {
		for (int i = 0; i < arrayTestCases.length; i++) {
			int y = arrayTestCases[i];
			for (Ship shipElement : testShips) {
				shipElement.setY(y);
				assertTrue(shipElement.getY() == y);
			}
		}
	}
	
	
	@Test
	public void testShipReset() {
		final int TESTX = 5;
		final int TESTY = 10;
		
		testShips[0].setX(TESTX);
		testShips[0].setY(TESTY);
		testShips[0].setDirectionOfShip(ShipDirection.VERTICAL);
		
		testShips[0].shipReset();
		
		assertTrue(testShips[0].getX() == 0);
		assertTrue(testShips[0].getY() == 0);
		assertTrue(testShips[0].getDirectionOfShip() == ShipDirection.HORIZONTAL);
	}
	
	@Test
	public void testSunk() {
		final int MAX_HITS = 5;
		for (int i = 0; i <= MAX_HITS; i++) {
			for (Ship shipElement : testShips) {
				shipElement.setHits(i);
				if (i < shipElement.getType().getLength()) {
					assertTrue(!shipElement.sunk());
				} else {
					assertTrue(shipElement.sunk());
				}
			}
			
		}
	}
	
	@Test
	public void testGetDirectionOfShip() {
		for (ShipDirection direction : shipDirections) {
			for (Ship shipElement : testShips) {
				shipElement.setDirectionOfShip(direction);
				assertTrue(shipElement.getDirectionOfShip() == direction);
			}
		}
	}
}
