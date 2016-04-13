package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import battleship.Player;
import battleship.Player.Ship;

public class TestPlayer {
	Player player1;
	Player cpu;
	
	public static final String HORIZONTAL_DIRECTION = "Horizontal";
	public static final String VERTICAL_DIRECTION = "Vertical";
	
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
		Ship testShipOne = player1.new Ship("testShipOne", HORIZONTAL_DIRECTION, 5, 0, 0);
		
		assertTrue(player1.isRange(testShipOne, 0, 0));
		assertTrue(player1.isRange(testShipOne, 1, 0));
		assertTrue(player1.isRange(testShipOne, 2, 0));
		assertTrue(player1.isRange(testShipOne, 3, 0));
		assertTrue(player1.isRange(testShipOne, 4, 0));
		assertTrue(!(player1.isRange(testShipOne, 5, 0)));
		assertTrue(!(player1.isRange(testShipOne,6, 0)));
		
		assertTrue(!(player1.isRange(testShipOne, -1, 0)));
		
	}

}
