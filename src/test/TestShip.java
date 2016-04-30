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
	
	
	@Before
	public void setUp() throws Exception {
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

	}
	
	@Test
	public void testSunk() {

	}
	
	@Test
	public void testGetName() {
		
	}
	
	@Test
	public void testSetName() {
		
	}
	
	@Test
	public void testGetDirectionOfShip() {
		
	}
	
	@Test
	public void testSetDirectionOfShip() {
		
	}
}
