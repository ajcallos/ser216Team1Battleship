package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import battleship.Player;
import battleship.Ship;

public class TestPlayer {
	Player player1;
	Player cpu;
	
	public static final String HORIZONTAL_DIRECTION = "Horizontal";
	public static final String VERTICAL_DIRECTION = "Vertical";
	
	public static final String AIRCRAFT_CARRIER = "Aircraft Carrier";
	public static final String BATTLESHIP = "Battleship";
	public static final String SUBMARINE = "Submarine";
	public static final String DESTROYER = "Destroyer";
	public static final String PATROL_BOAT = "Patrol Boat";
	
	@Before
	public void setUp() throws Exception {
		player1 = new Player("player1");
		cpu = new Player("cpu");
		
		player1.setOpponent(cpu);
		cpu.setOpponent(player1);
		
		cpu.cpuShips();
		cpu.setCompass();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsRange() {
		Ship testShipOne = new Ship("testShipOne", HORIZONTAL_DIRECTION, 5, 0, 0);
		
		// Test valid horizontal positions
		assertTrue(player1.isRange(testShipOne, 0, 0));
		assertTrue(player1.isRange(testShipOne, 1, 0));
		assertTrue(player1.isRange(testShipOne, 2, 0));
		assertTrue(player1.isRange(testShipOne, 3, 0));
		assertTrue(player1.isRange(testShipOne, 4, 0));
		
		
		// Test invalid horizontal positions
		assertTrue(!(player1.isRange(testShipOne, 0, 1)));
		assertTrue(!(player1.isRange(testShipOne, 1, 1)));
		assertTrue(!(player1.isRange(testShipOne, 2, 1)));
		assertTrue(!(player1.isRange(testShipOne, 3, 1)));
		assertTrue(!(player1.isRange(testShipOne, 4, 1)));
		assertTrue(!(player1.isRange(testShipOne, 5, 1)));
		assertTrue(!(player1.isRange(testShipOne, 0, -1)));
		assertTrue(!(player1.isRange(testShipOne, 1, -1)));
		assertTrue(!(player1.isRange(testShipOne, 2, -1)));
		assertTrue(!(player1.isRange(testShipOne, 3, -1)));
		assertTrue(!(player1.isRange(testShipOne, 4, -1)));
		assertTrue(!(player1.isRange(testShipOne, 5, -1)));
		
		// Test horizontal edge cases
		assertTrue(!(player1.isRange(testShipOne, -1, 0)));
		assertTrue(!(player1.isRange(testShipOne, 0, -1)));
		assertTrue(!(player1.isRange(testShipOne, -1, -1)));
		
		Ship testShipTwo = new Ship("testShipTwo", VERTICAL_DIRECTION, 5, 1, 0);
		
		// Test valid vertical positions
		assertTrue(player1.isRange(testShipTwo, 1, 0));
		assertTrue(player1.isRange(testShipTwo, 1, 1));
		assertTrue(player1.isRange(testShipTwo, 1, 2));
		assertTrue(player1.isRange(testShipTwo, 1, 3));
		assertTrue(player1.isRange(testShipTwo, 1, 4));
		
		// Test invalid vertical positions
		assertTrue(!(player1.isRange(testShipTwo, 0, 0)));
		assertTrue(!(player1.isRange(testShipTwo, 0, 1)));
		assertTrue(!(player1.isRange(testShipTwo, 0, 2)));
		assertTrue(!(player1.isRange(testShipTwo, 0, 3)));
		assertTrue(!(player1.isRange(testShipTwo, 0, 4)));
		assertTrue(!(player1.isRange(testShipTwo, 0, 5)));
		assertTrue(!(player1.isRange(testShipTwo, 2, 0)));
		assertTrue(!(player1.isRange(testShipTwo, 2, 1)));
		assertTrue(!(player1.isRange(testShipTwo, 2, 2)));
		assertTrue(!(player1.isRange(testShipTwo, 2, 3)));
		assertTrue(!(player1.isRange(testShipTwo, 2, 4)));
		assertTrue(!(player1.isRange(testShipTwo, 2, 5)));
		
		// Test vertical edge cases
		assertTrue(!(player1.isRange(testShipTwo, 1, -1)));
	}
	
	@Test
	public void testValidPlace() {
		
	}

}
