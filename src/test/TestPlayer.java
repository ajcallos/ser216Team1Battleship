package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import battleship.Player;
import ship.ShipFactory;

import ship.Ship;
import ship.ShipDirection;

public class TestPlayer {
	Player player1;
	Player cpu;
	
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
		Ship[] testShips = new ShipFactory().getShipArray();
				
		testShips[0].setX(0);
		testShips[0].setY(0);
		testShips[1].setX(1);
		testShips[1].setY(1);
		
		// Test valid horizontal positions
		for (Ship shipElement : testShips) {
			assertTrue(player1.isRange(shipElement, 0, 0));
			assertTrue(player1.isRange(shipElement, 1, 0));
			assertTrue(player1.isRange(shipElement, 2, 0));
			assertTrue(player1.isRange(shipElement, 3, 0));
			assertTrue(player1.isRange(shipElement, 4, 0));
			// Test invalid horizontal positions
			assertTrue(!(player1.isRange(shipElement, 0, 1)));
			assertTrue(!(player1.isRange(shipElement, 1, 1)));
			assertTrue(!(player1.isRange(shipElement, 2, 1)));
			assertTrue(!(player1.isRange(shipElement, 3, 1)));
			assertTrue(!(player1.isRange(shipElement, 4, 1)));
			assertTrue(!(player1.isRange(shipElement, 5, 1)));
			assertTrue(!(player1.isRange(shipElement, 0, -1)));
			assertTrue(!(player1.isRange(shipElement, 1, -1)));
			assertTrue(!(player1.isRange(shipElement, 2, -1)));
			assertTrue(!(player1.isRange(shipElement, 3, -1)));
			assertTrue(!(player1.isRange(shipElement, 4, -1)));
			assertTrue(!(player1.isRange(shipElement, 5, -1)));
			
			// Test horizontal edge cases
			assertTrue(!(player1.isRange(shipElement, -1, 0)));
			assertTrue(!(player1.isRange(shipElement, 0, -1)));
			assertTrue(!(player1.isRange(shipElement, -1, -1)));
		}
		
	}
	
	@Test
	public void testGetGrid() {
		int gridPosition = 1;
		
		// Test valid grid positions
		for(int i = 45; i < 495; i = i + 45) {
			assertEquals(player1.getGrid(i), gridPosition);
			gridPosition++;
		}
		
		// Test invalid positions above and below grid
		assertEquals(player1.getGrid(600), 0);
		
		assertEquals(player1.getGrid(0), 0);
	}
	
	@Test
	public void testValidPlace() throws Exception {
		Player placePlayer = new Player("player_test");
		Ship[] testShips = new ShipFactory().getShipArray();
		
		for (Ship ship : testShips) {
			placePlayer.setCurrentShip(ship);
			
			placePlayer.setShipDirection(ShipDirection.HORIZONTAL);
			assertEquals(placePlayer.validPlace(0, 0), false);
			assertEquals(placePlayer.validPlace(4, 4), true);
			assertEquals(placePlayer.validPlace(10, 10), false);
			
			placePlayer.setShipDirection(ShipDirection.VERTICAL);
			assertEquals(placePlayer.validPlace(0, 0), false);
			assertEquals(placePlayer.validPlace(4, 4), true);
			assertEquals(placePlayer.validPlace(10, 10), false);
		}
	}
}
