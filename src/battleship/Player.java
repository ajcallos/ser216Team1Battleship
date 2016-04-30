package battleship;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ship.Ship;
import ship.ShipDirection;
import ship.ShipFactory;
import ship.ShipType;

public class Player {
	
	// Variables----------------------------------------------------------------------------
	// Values
	
	private final static ShipType[] SHIP_TYPES = {
			ShipType.PATROL_BOAT, ShipType.SUBMARINE, ShipType.DESTROYER,
			ShipType.BATTLESHIP, ShipType.AIRCRAFT_CARRIER
	};
	
	private int hits = 0;
	@SuppressWarnings("unused")
	private static int misses = 0;
	private int[][] board = new int[10][10];
	private int[][] gameBoard = new int[10][10];
	private boolean[] shipPlace = new boolean[5];
	private boolean turn = true;

	private Ship currentShip;
	
	private Player opponent;
	
	// private Player currentPlayer;
	String currentPlayer1 = "";
	private static Component sunkFrame;
	
	private Ship myPatrolBoat;
	private Ship mySubmarine;
	private Ship myDestroyer;
	private Ship myBattleship;
	private Ship myAircraftCarrier;
	
	// Deprecated
	private String cpuShipNames[] = new String[] { "Aircraft Carrier",
			"Battleship", "Destroyer", "Submarine", "Patrol Boat" };
	
	private Ship playerShips[];
	private Ship cpuShips[];
	
	private final boolean[] cpuCompass = new boolean[4];
	private final Random random = new Random();
	private int orientation = 0;
	private boolean hunting = false;
	private String lastShip = "";
	private int nextShip = 0;
	private int cpuStartHit_x;
	private int cpuStartHit_y;
	private int cpuLastHit_x;
	private int cpuLastHit_y;

	// Files
	final static File file_1L = new File("src/res/img/lg_head.jpg");
	final static File file_g = new File("src/res/img/green.gif");
	final static File file_blu = new File("src/res/img/blue.gif");
	final static File file_r = new File("src/res/img/red.gif");
	final static File file_gry = new File("src/res/img/grey.gif");
	final static File file_blk = new File("src/res/img/black.gif");
	public Image icon = Toolkit.getDefaultToolkit()
			.getImage("src/res/img/icon.gif");

	// Main window
	private JFrame placeFrame = new JFrame("BattleShip");
	final static int size_xL = 1010;
	final static int size_yL = 700;

	// Left board components
	private JPanel shipPanel = new JPanel();
	private JPanel boxPanel = new JPanel();
	private JPanel cpuPanel = new JPanel();
	private JPanel cpuSunkPanel = new JPanel();
	MouseListener boardListener = new setupMouseListener();

	// Right board components
	private JPanel fireBoxPanel = new JPanel();
	private JPanel sunkBoxPanel = new JPanel();
	MouseListener gameListener = new gameMouseListener();

	private static final ShipDirection DEFAULT_DIRECTION = ShipDirection.HORIZONTAL;
	
	// Ship selection components
	private ShipDirection shipDirection = DEFAULT_DIRECTION;
	private JPanel boardPanel = new JPanel();
	
	// TODO: figure out why this is organized like it is; it seems like going overboard
	// For now, switched to ShipType enum from String.
	private ShipType shipsListArray[] = { ShipType.AIRCRAFT_CARRIER, ShipType.BATTLESHIP,
			ShipType.DESTROYER, ShipType.SUBMARINE, ShipType.PATROL_BOAT
			};

	private JList<ShipType> shipList = new JList<ShipType>(shipsListArray);
	private ShipDirection shipDirections[] = { ShipDirection.HORIZONTAL, ShipDirection.VERTICAL };
	private JList<ShipDirection> directionList = new JList<ShipDirection>(shipDirections);
	private JButton doneButton;

	// The main class
	// ---------------------------------------------------------------
	
	/**
	 * Constructor; enforces only one finite set of ships per player via ShipFactory 
	 * @param currentPlayer
	 * @throws IOException
	 */
	public Player(String currentPlayer) throws IOException {
		this.currentPlayer1 = currentPlayer;
		ShipFactory myShipFactory = new ShipFactory();
		myPatrolBoat = myShipFactory.getPatrolBoat();
		mySubmarine = myShipFactory.getSubmarine();
		myDestroyer = myShipFactory.getDestroyer();
		myBattleship = myShipFactory.getBattleship();
		myAircraftCarrier = myShipFactory.getAircraftCarrier();
		playerShips = myShipFactory.getShipArray();
		
	}
	
	private JList<ShipType> getShipList() {
		return shipList;
	}

	// Figures out if the ship is where you fired
	public boolean isRange(Ship ship, int x, int y) {
		if (ship.getDirectionOfShip() == ShipDirection.VERTICAL) {
			if (ship.getX() == x) {
				if (ship.getY() <= y && y <= (ship.getY() + ship.getSize())) {
					return true;
				}
			}
		}
		if (ship.getDirectionOfShip() == ShipDirection.HORIZONTAL) {
			if (ship.getY() == y) {
				if (ship.getX() <= x && x <= (ship.getX() + ship.getSize())) {
					return true;
				}
			}
		}
		return false;
	}

	// For replacing the ships it removes the old ship from the array
	public void removeOldShip() {
		
		if (currentShip.getType() == ShipType.AIRCRAFT_CARRIER) {
			if (myAircraftCarrier.getDirectionOfShip() == ShipDirection.VERTICAL) {
				for (int i = 0; i < 5; i++) {
					board[myAircraftCarrier.getX()][i + myAircraftCarrier.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 5; i++) {
					board[i + myAircraftCarrier.getX()][myAircraftCarrier.getY()] = 0;
				}
			}
		}

		if (currentShip.getType() == ShipType.BATTLESHIP) {
			if (myBattleship.getDirectionOfShip() == ShipDirection.VERTICAL) {
				for (int i = 0; i < 4; i++) {
					board[myBattleship.getX()][i + myBattleship.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 4; i++) {
					board[i + myBattleship.getX()][myBattleship.getY()] = 0;
				}
			}
		}

		if (currentShip.getType() == ShipType.SUBMARINE) {
			if (mySubmarine.getDirectionOfShip() == ShipDirection.VERTICAL) {
				for (int i = 0; i < 3; i++) {
					board[mySubmarine.getX()][i + mySubmarine.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 3; i++) {
					board[i + mySubmarine.getX()][mySubmarine.getY()] = 0;
				}
			}
		}

		if (currentShip.getType() == ShipType.DESTROYER) {
			if (myDestroyer.getDirectionOfShip() == ShipDirection.VERTICAL) {
				for (int i = 0; i < 3; i++) {
					board[myDestroyer.getX()][i + myDestroyer.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 3; i++) {
					board[i + myDestroyer.getX()][myDestroyer.getY()] = 0;
				}
			}
		}

		if (currentShip.getType() == ShipType.PATROL_BOAT) {
			if (myPatrolBoat.getDirectionOfShip() == ShipDirection.VERTICAL) {
				for (int i = 0; i < 2; i++) {
					board[myPatrolBoat.getX()][i + myPatrolBoat.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 2; i++) {
					board[i + myPatrolBoat.getX()][myPatrolBoat.getY()] = 0;
				}
			}
		}

	}

	// find if the ship will be off the board 
	public boolean validPlace(int x, int y) {
		if (currentShip.getType() == ShipType.AIRCRAFT_CARRIER) {
			if (shipDirection.equals("Vertical") && y <= 6) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 6) {
				return true;
			}
		}

		if (currentShip.getType() == ShipType.BATTLESHIP) {
			if (shipDirection.equals("Vertical") && y <= 7) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 7) {
				return true;
			}
		}

		if (currentShip.getType() == ShipType.SUBMARINE) {
			if (shipDirection.equals("Vertical") && y <= 8) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 8) {
				return true;
			}
		}

		if (currentShip.getType() == ShipType.DESTROYER) {
			if (shipDirection.equals("Vertical") && y <= 8) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 8) {
				return true;
			}
		}

		if (currentShip.getType() == ShipType.PATROL_BOAT) {
			if (shipDirection.equals("Vertical") && y <= 9) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 9) {
				return true;
			}
		}

		return false;

	}

	// Looks to see if you can put the ship down
	public boolean freeBoard(int x, int y){
		if (currentShip.getType() == ShipType.AIRCRAFT_CARRIER) {
			if (shipDirection == ShipDirection.VERTICAL) {
				for (int i = 0; i < 5; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection == ShipDirection.HORIZONTAL) {
				for (int i = 0; i < 5; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}
		
		if (currentShip.getType() == ShipType.BATTLESHIP) {
			if (shipDirection == ShipDirection.VERTICAL) {
				for (int i = 0; i < 4; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection == ShipDirection.HORIZONTAL) {
				for (int i = 0; i < 4; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}

		if (currentShip.getType() == ShipType.SUBMARINE) {
			if (shipDirection == ShipDirection.VERTICAL) {
				for (int i = 0; i < 3; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection == ShipDirection.HORIZONTAL) {
				for (int i = 0; i < 3; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}

		if (currentShip.getType() == ShipType.DESTROYER) {
			if (shipDirection == ShipDirection.VERTICAL) {
				for (int i = 0; i < 3; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection == ShipDirection.HORIZONTAL) {
				for (int i = 0; i < 3; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}

		if (currentShip.getType() == ShipType.PATROL_BOAT) {
			if (shipDirection == ShipDirection.VERTICAL) {
				for (int i = 0; i < 2; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection == ShipDirection.HORIZONTAL) {
				for (int i = 0; i < 2; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}
		
		return true;
	}

	// Replaces the ships that were placed
	public void redrawShips() throws IOException {
		boxPanel.removeAll();

		if (shipPlace[0]) {
			for (int i = 0; i < 5; i++) {
				if (myAircraftCarrier.getDirectionOfShip() == ShipDirection.VERTICAL) {
					board[(myAircraftCarrier.getX())][i + myAircraftCarrier.getY()] = 1;
					addBoard(myAircraftCarrier.getX() + 1, myAircraftCarrier.getY() + i + 1);
				} else if (myAircraftCarrier.getDirectionOfShip() == ShipDirection.HORIZONTAL) {
					board[i + myAircraftCarrier.getX()][myAircraftCarrier.getY()] = 1;
					addBoard(myAircraftCarrier.getX() + i + 1, myAircraftCarrier.getY() + 1);
				}
			}

		}
		if (shipPlace[1]) {
			for (int i = 0; i < 4; i++) {
				if (myBattleship.getDirectionOfShip() == ShipDirection.VERTICAL) {
					board[(myBattleship.getX())][i + myBattleship.getY()] = 1;
					addBoard(myBattleship.getX() + 1, myBattleship.getY() + i + 1);
				} else if (myBattleship.getDirectionOfShip() == ShipDirection.HORIZONTAL) {
					board[i + myBattleship.getX()][myBattleship.getY()] = 1;
					addBoard(myBattleship.getX() + i + 1, myBattleship.getY() + 1);
				}
			}

		}
		if (shipPlace[2]) {
			for (int i = 0; i < 3; i++) {
				if (mySubmarine.getDirectionOfShip() == ShipDirection.VERTICAL) {
					board[(mySubmarine.getX())][i + mySubmarine.getY()] = 1;
					addBoard(mySubmarine.getX() + 1, mySubmarine.getY() + i + 1);
				} else if (mySubmarine.getDirectionOfShip() == ShipDirection.HORIZONTAL) {
					board[i + mySubmarine.getX()][mySubmarine.getY()] = 1;
					addBoard(mySubmarine.getX() + i + 1, mySubmarine.getY() + 1);
				}
			}

		}
		if (shipPlace[3]) {
			for (int i = 0; i < 3; i++) {
				if (myDestroyer.getDirectionOfShip() == ShipDirection.VERTICAL) {
					board[(myDestroyer.getX())][i + myDestroyer.getY()] = 1;
					addBoard(myDestroyer.getX() + 1, myDestroyer.getY() + i + 1);
				} else if (myDestroyer.getDirectionOfShip() == ShipDirection.HORIZONTAL) {
					board[i + myDestroyer.getX()][myDestroyer.getY()] = 1;
					addBoard(myDestroyer.getX() + i + 1, myDestroyer.getY() + 1);
				}
			}
		}
		if (shipPlace[4]) {
			for (int i = 0; i < 2; i++) {
				if (myPatrolBoat.getDirectionOfShip() == ShipDirection.VERTICAL) {
					board[(myPatrolBoat.getX())][i + myPatrolBoat.getY()] = 1;
					addBoard(myPatrolBoat.getX() + 1, myPatrolBoat.getY() + i + 1);
				} else if (myPatrolBoat.getDirectionOfShip() == ShipDirection.HORIZONTAL) {
					board[i + myPatrolBoat.getX()][myPatrolBoat.getY()] = 1;
					addBoard(myPatrolBoat.getX() + i + 1, myPatrolBoat.getY() + 1);
				}
			}
		}

	}

	// Puts the ship in a new spot in the board
	public void replaceShip(int x, int y) throws IOException {
		// Places the ship vertical
		if (shipDirection == ShipDirection.VERTICAL) {
			
			// For each ship it adds spots on the game board and in the array if
			// it hasn't been done before
			// TODO: this needs to be cleaned up; too many magic numbers.
			if (currentShip.getType() == ShipType.AIRCRAFT_CARRIER) {
				if (y <= 6) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					for (int i = 0; i < 5; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}

			if (currentShip.getType() == ShipType.BATTLESHIP) {
				if (y <= 7) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);					
					for (int i = 0; i < 4; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}

			if (currentShip.getType() == ShipType.SUBMARINE) {
				if (y <= 8) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					for (int i = 0; i < 3; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}

			if (currentShip.getType() == ShipType.DESTROYER) {
				if (y <= 8) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					for (int i = 0; i < 3; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}

			if (currentShip.getType() == ShipType.PATROL_BOAT) {
				if (y <= 9) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					for (int i = 0; i < 2; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}
		}

		// -----------------------------------------------------------

		if (shipDirection == ShipDirection.HORIZONTAL) {
			if (currentShip.getType() == ShipType.AIRCRAFT_CARRIER) {
				if (x <= 6) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					for (int i = 0; i < 5; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

			if (currentShip.getType() == ShipType.BATTLESHIP) {
				if (x <= 7) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					for (int i = 0; i < 4; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

			if (currentShip.getType() == ShipType.SUBMARINE) {
				if (x <= 8) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					for (int i = 0; i < 3; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

			if (currentShip.getType() == ShipType.DESTROYER) {
				if (x <= 8) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					for (int i = 0; i < 3; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

			if (currentShip.getType() == ShipType.PATROL_BOAT) {
				if (x <= 9) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					for (int i = 0; i < 2; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

		}
		return;

	}

	// Adds a square to the board grid at the point given
	public void addBoard(int x, int y) throws IOException {
		// For values given as pixels
		if (x >= 45 && y >= 45) {
			JLabel img_bg = (new JLabel(new ImageIcon(ImageIO.read(file_g))));
			img_bg.setBounds(x, y, 45, 45);
			boxPanel.add(img_bg);
			boxPanel.repaint(); // Updates the image so the boxes appear
		}
		// For values given as grid coordinates
		if (x <= 10 && y <= 10) {
			JLabel img_bg = (new JLabel(new ImageIcon(ImageIO.read(file_g))));
			img_bg.setBounds(x * 45, y * 45, 45, 45);
			boxPanel.add(img_bg);
			boxPanel.repaint(); // Updates the image so the boxes appear
		}
	}

	// Returns the value of the game coordinates given pixels of the click
	public int getGrid(int x) {
		if (x >= 45 && x < 90) {
			return 1;
		} else if (x >= 90 && x < 135) {
			return 2;
		} else if (x >= 135 && x < 180) {
			return 3;
		} else if (x >= 180 && x < 225) {
			return 4;
		} else if (x >= 225 && x < 270) {
			return 5;
		} else if (x >= 270 && x < 315) {
			return 6;
		} else if (x >= 315 && x < 360) {
			return 7;
		} else if (x >= 360 && x < 405) {
			return 8;
		} else if (x >= 405 && x < 450) {
			return 9;
		} else if (x >= 450 && x < 500) {
			return 10;
		} else
			return 0;
	}

	// Returns the value of the pixel coordinates given pixels of the click
	public int getGridPixel(int x) {
		if (x >= 45 && x < 90) {
			return 45;
		} else if (x >= 90 && x < 135) {
			return 90;
		} else if (x >= 135 && x < 180) {
			return 135;
		} else if (x >= 180 && x < 225) {
			return 180;
		} else if (x >= 225 && x < 270) {
			return 225;
		} else if (x >= 270 && x < 315) {
			return 270;
		} else if (x >= 315 && x < 360) {
			return 315;
		} else if (x >= 360 && x < 405) {
			return 360;
		} else if (x >= 405 && x < 450) {
			return 405;
		} else if (x >= 450 && x < 500) {
			return 450;
		} else
			return 0;
	}

	// Places the color on the board for the ship and adds values to the array
	public boolean putShipsInBoard(int x, int y) throws IOException {

		// Places the ship vertical
		if (shipDirection == ShipDirection.VERTICAL) {
			// For each ship it adds spots on the game board and in the array if
			// it hasn't been done before
			if (currentShip.getType() == ShipType.AIRCRAFT_CARRIER) {
				if (y <= 6 && shipPlace[0] != true) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					currentShip.setDirectionOfShip(ShipDirection.VERTICAL);
					for (int i = 0; i < 5; i++) {
						board[(x - 1)][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[0] = true;
					return true;
				}
			}

			if (currentShip.getType() == ShipType.BATTLESHIP) {
				if (y <= 7 && shipPlace[1] != true) {
					for (int i = 0; i < 4; i++) {
						currentShip.setX(x - 1);
						currentShip.setY(y - 1);
						currentShip.setDirectionOfShip(ShipDirection.VERTICAL);
						board[x - 1][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[1] = true;
					return true;
				}
			}

			if (currentShip.getType() == ShipType.SUBMARINE) {
				if (y <= 8 && shipPlace[2] != true) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					currentShip.setDirectionOfShip(ShipDirection.VERTICAL);
					for (int i = 0; i < 3; i++) {
						board[x - 1][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[2] = true;
					return true;
				}
			}

			if (currentShip.getType() == ShipType.DESTROYER) {
				if (y <= 8 && shipPlace[3] != true) {
					for (int i = 0; i < 3; i++) {
						currentShip.setX(x - 1);
						currentShip.setY(y - 1);
						currentShip.setDirectionOfShip(ShipDirection.VERTICAL);
						board[x - 1][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[3] = true;
					return true;
				}
			}

			if (currentShip.getType() == ShipType.PATROL_BOAT) {
				if (y <= 9 && shipPlace[4] != true) {
					for (int i = 0; i < 2; i++) {
						currentShip.setX(x - 1);
						currentShip.setY(y - 1);
						currentShip.setDirectionOfShip(ShipDirection.VERTICAL);
						board[x - 1][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[4] = true;
					return true;
				}
			}
		}

		// -----------------------------------------------------------

		if (shipDirection == ShipDirection.HORIZONTAL) {
			if (currentShip.getType() == ShipType.AIRCRAFT_CARRIER) {
				if (x <= 6 && shipPlace[0] != true) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					currentShip.setDirectionOfShip(ShipDirection.HORIZONTAL);
					for (int i = 0; i < 5; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[0] = true;
					return true;
				}
			}

			if (currentShip.getType() == ShipType.BATTLESHIP) {
				if (x <= 7 && shipPlace[1] != true) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					currentShip.setDirectionOfShip(ShipDirection.HORIZONTAL);
					for (int i = 0; i < 4; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[1] = true;
					return true;
				}
			}

			if (currentShip.getType() == ShipType.SUBMARINE) {
				if (x <= 8 && shipPlace[2] != true) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					currentShip.setDirectionOfShip(ShipDirection.HORIZONTAL);
					for (int i = 0; i < 3; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[2] = true;
					return true;
				}
			}

			if (currentShip.getType() == ShipType.DESTROYER) {
				if (x <= 8 && shipPlace[3] != true) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					currentShip.setDirectionOfShip(ShipDirection.HORIZONTAL);
					for (int i = 0; i < 3; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[3] = true;
					return true;
				}
			}

			if (currentShip.getType() == ShipType.PATROL_BOAT) {
				if (x <= 9 && shipPlace[4] != true) {
					currentShip.setX(x - 1);
					currentShip.setY(y - 1);
					currentShip.setDirectionOfShip(ShipDirection.HORIZONTAL);
					for (int i = 0; i < 2; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[4] = true;
					return true;
				}
			}
		}

		return false;

	}

	// Loads the GUI for the game
	public void mainGUI() throws IOException {
		// Loads the board image and sets size
		final JLabel board_img = (new JLabel(new ImageIcon(
				ImageIO.read(new File("src/res/img/board.gif")))));
		board_img.setBounds(0, 0, 500, 500);
		// Makes a second board
		final JLabel board_img1 = (new JLabel(new ImageIcon(
				ImageIO.read(new File("src/res/img/board.gif")))));
		board_img1.setBounds(0, 0, 500, 500);

		// Loads the board image and sets size
		JLabel img_1L = (new JLabel(new ImageIcon(ImageIO.read(file_1L))));
		img_1L.setBounds(0, 0, size_xL, 163);

		// Makes the CPU sunk ships appear
		cpuSunkPanel.setBounds(0, 0, 500, 500);
		cpuSunkPanel.setLayout(null);
		cpuSunkPanel.setOpaque(false);

		// Makes the CPU shots appear
		cpuPanel.setBounds(0, 0, 500, 500);
		cpuPanel.setLayout(null);
		cpuPanel.setOpaque(false);

		// Panel to make ships appear on top
		boxPanel.setBounds(0, 0, 500, 500);
		boxPanel.setLayout(null);
		boxPanel.setOpaque(false);

		// Ship board setup
		boardPanel.add(cpuSunkPanel);
		boardPanel.add(cpuPanel);
		boardPanel.add(boxPanel);
		boardPanel.add(board_img);
		boardPanel.setLayout(null);
		boardPanel.setOpaque(false);
		boardPanel.setBackground(Color.BLACK);
		boardPanel.setBounds(0, 173, 500, 500);
		boardPanel.addMouseListener(boardListener);

		// Directions text
		JTextArea directionsTxt = new JTextArea("Game directions\n\n");
		directionsTxt.append("1. Select a ship from the leftmost box below.\n");
		directionsTxt
				.append("2. Select a direction from the rightmost box below.\n");
		directionsTxt
				.append("3. Select a coordinate for the beginning position of your ship on the grid.\n");
		directionsTxt
				.append("4. Click \"Done\" once all 5 ships are placed.\n");
		directionsTxt.setBounds(10, 10, 475, 100);
		directionsTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		directionsTxt.setForeground(Color.WHITE);
		directionsTxt.setBackground(Color.DARK_GRAY);
		directionsTxt.setEditable(false);

		// Ship placement menu
		shipList.setFont(new Font("Tahoma", Font.PLAIN, 17));
		shipList.setForeground(Color.WHITE);
		shipList.setBackground(Color.DARK_GRAY);
		shipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		shipList.setBounds(93, 192, 115, 115);
		
		shipList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getSource() == shipList
						&& !event.getValueIsAdjusting()) {
					ShipType someType = shipList.getSelectedValue();
					if (someType != null)
						currentShip = selectShip(someType);
				}

			}

		});
		
		shipList.setListData(shipsListArray);
		shipList.setSelectedIndex(0);

		// Direction of the ship menu
		directionList.setFont(new Font("Tahoma", Font.PLAIN, 17));
		directionList.setForeground(Color.WHITE);
		directionList.setBackground(Color.DARK_GRAY);
		directionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		directionList.setBounds(292, 192, 115, 115);
		directionList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getSource() == directionList
						&& !event.getValueIsAdjusting()) {
					ShipDirection directionValue = directionList
							.getSelectedValue();
					if (directionValue != null)
						shipDirection = directionValue;
				}

			}

		});
		directionList.setListData(shipDirections);
		directionList.setSelectedIndex(0);

		// Done Button
		doneButton = new JButton("Done");
		doneButton.setBounds(200, 400, 100, 50);
		doneButton.setEnabled(false);
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sunkBoxPanel.setBounds(0, 0, 500, 500);
				sunkBoxPanel.setLayout(null);
				sunkBoxPanel.setOpaque(false);
				fireBoxPanel.setBounds(0, 0, 500, 500);
				fireBoxPanel.setLayout(null);
				fireBoxPanel.setOpaque(false);
				shipPanel.removeAll();
				shipPanel.add(sunkBoxPanel);
				shipPanel.add(fireBoxPanel);
				shipPanel.add(board_img1);
				shipPanel.addMouseListener(gameListener);
				shipPanel.repaint();
				boardPanel.removeMouseListener(boardListener);
			}

		});

		// Putting the ship menu next to the player ship board
		shipPanel.add(doneButton);
		shipPanel.add(directionsTxt);
		shipPanel.add(directionList);
		shipPanel.add(shipList);
		shipPanel.setLayout(null);
		shipPanel.setOpaque(false);
		shipPanel.setBackground(Color.BLACK);
		shipPanel.setBounds(505, 173, 500, 500);

		// Frame setup
		placeFrame.add(img_1L);
		placeFrame.add(boardPanel);
		placeFrame.add(shipPanel);
		placeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		placeFrame.setSize(size_xL, size_yL);
		placeFrame.getContentPane().setBackground(Color.BLACK);
		placeFrame.setLayout(null);
		placeFrame.setIconImage(icon);
		placeFrame.setResizable(false);
		placeFrame.setVisible(true);

	}

	// Adds shot to board
	public void placeShotLeft(String type, int x, int y) throws IOException {
		if (type.equals("miss")) {
			JLabel img_g = (new JLabel(new ImageIcon(ImageIO.read(file_gry))));
			img_g.setBounds(x * 45, y * 45, 45, 45);
			boxPanel.add(img_g);
			boxPanel.repaint(); // Updates the image so the boxes appear

		}
		if (type.equals("hit")) {
			JLabel img_r = (new JLabel(new ImageIcon(ImageIO.read(file_r))));
			img_r.setBounds(x * 45, y * 45, 45, 45);
			boxPanel.add(img_r);
			boxPanel.repaint(); // Updates the image so the boxes appear
		}

	}

	// fires shot at other player
	public boolean fire(int x, int y) throws IOException {
		if (gameBoard[x - 1][y - 1] == 0) {

			gameBoard[x - 1][y - 1] = 1;

			if (opponent.board[x - 1][y - 1] == 0) {
				// misses++;
				fireGUI("miss", x, y);
				return true;
			} else if (opponent.board[x - 1][y - 1] == 1) {
				hits++;
				fireGUI("hit", x, y);
				findShip(x - 1, y - 1);
				return true;
			}
		}
		// returning false if you already fired there
		return false;
	}

	
	/**
	 * Helper method to migrate old code of using strings to newer code using enums.
	 * Was created in mind to be eventually deprecated in favor of more elegant solutions.
	 * @param someShipType the type to test
	 * @return the player's ship which matches the type
	 */
	private Ship selectShip(ShipType someShipType) {
		
		Ship returnShip = null;
		
		for (Ship shipElement : playerShips) {
			if (shipElement.getType() == someShipType) {
				returnShip = shipElement;
			}
		}
		
		return returnShip;
	
	}
	
	// Finds what ship is being hit
	private void findShip(int x, int y) throws IOException {

		// TODO: needs to be cleaned up to incorporate enums
		if (isRange(opponent.cpuShips[0], x, y)) {
			opponent.cpuShips[0].addHits();
			if (opponent.cpuShips[0].sunk()) {
				fillSunk(opponent.cpuShips[0]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Aircraft Carrier");
			}
		} else if (isRange(opponent.cpuShips[1], x, y)) {
			opponent.cpuShips[1].addHits();
			if (opponent.cpuShips[1].sunk()) {
				fillSunk(opponent.cpuShips[1]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Battleship");
			}
		} else if (isRange(opponent.cpuShips[2], x, y)) {
			opponent.cpuShips[2].addHits();
			if (opponent.cpuShips[2].sunk()) {
				fillSunk(opponent.cpuShips[2]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Destroyer");
			}
		} else if (isRange(opponent.cpuShips[3], x, y)) {
			opponent.cpuShips[3].addHits();
			if (opponent.cpuShips[3].sunk()) {
				fillSunk(opponent.cpuShips[3]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Submarine");
			}
		} else if (isRange(opponent.cpuShips[4], x, y)) {
			opponent.cpuShips[4].addHits();
			if (opponent.cpuShips[4].sunk()) {
				fillSunk(opponent.cpuShips[4]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Patrol Boat");
			}
		}
	}

	// When a ship is sunk this adds the black over ship
	private void fillSunk(Ship ship) throws IOException {
		if (ship.getDirectionOfShip() == ShipDirection.VERTICAL) {
			for (int i = 0; i < ship.getSize(); i++) {
				fireGUI("sunk", (ship.getX() + 1), (ship.getY() + i + 1));
			}
		} else if (ship.getDirectionOfShip() == ShipDirection.HORIZONTAL) {
			for (int i = 0; i < ship.getSize(); i++) {
				fireGUI("sunk", (ship.getX() + i + 1), (ship.getY() + 1));
			}
		}
	}

	// Adds the color to the board 
	private void fireGUI(String type, int x, int y) throws IOException {
		if (type.equals("miss")) {
			JLabel img_g = (new JLabel(new ImageIcon(ImageIO.read(file_gry))));
			img_g.setBounds(x * 45, y * 45, 45, 45);
			fireBoxPanel.add(img_g);
			fireBoxPanel.repaint(); // Updates the image so the boxes appear

		}
		if (type.equals("hit")) {
			JLabel img_r = (new JLabel(new ImageIcon(ImageIO.read(file_r))));
			img_r.setBounds(x * 45, y * 45, 45, 45);
			fireBoxPanel.add(img_r);
			fireBoxPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("sunk")) {
			JLabel img_blk = (new JLabel(new ImageIcon(ImageIO.read(file_blk))));
			img_blk.setBounds(x * 45, y * 45, 45, 45);
			sunkBoxPanel.add(img_blk);
			sunkBoxPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("cpuShow")) {
			JLabel img_g = (new JLabel(new ImageIcon(ImageIO.read(file_g))));
			img_g.setBounds(x * 45, y * 45, 45, 45);
			sunkBoxPanel.add(img_g);
			sunkBoxPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("cpuHit")) {
			JLabel img_r = (new JLabel(new ImageIcon(ImageIO.read(file_r))));
			img_r.setBounds(x * 45, y * 45, 45, 45);
			cpuPanel.add(img_r);
			cpuPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("cpuMiss")) {
			JLabel img_g = (new JLabel(new ImageIcon(ImageIO.read(file_gry))));
			img_g.setBounds(x * 45, y * 45, 45, 45);
			cpuPanel.add(img_g);
			cpuPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("cpuSunk")) {
			JLabel img_blk = (new JLabel(new ImageIcon(ImageIO.read(file_blk))));
			img_blk.setBounds(x * 45, y * 45, 45, 45);
			cpuSunkPanel.add(img_blk);
			cpuSunkPanel.repaint(); // Updates the image so the boxes appear
		}
	}

	// Makes an instance of the other player to get their board
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	//Checks to see if a player has won the game
	public boolean checkWin() throws IOException {
		if (opponent.hits == 17 || hits == 17) {
			if (opponent.hits == 17) {
				showComputerShips();
				JOptionPane.showMessageDialog(sunkFrame, "You lost");
				placeFrame.dispose();
			}
			if (hits == 17) {
				JOptionPane.showMessageDialog(sunkFrame, "You Win!");
				placeFrame.dispose();

			}
			return true;
		}
		return false;

	}

	//If you lose the game the cpu ships are shown in green on the board
	private void showComputerShips() throws IOException {
		for (int j = 0; j < 5; j++) {
			if (opponent.cpuShips[j].getHits() != opponent.cpuShips[j]
					.getSize()) {
				if (opponent.cpuShips[j].getDirectionOfShip()
						.equals("Vertical")) {
					for (int i = 0; i < opponent.cpuShips[j].getSize(); i++) {
						fireGUI("cpuShow", (opponent.cpuShips[j].getX() + 1),
								(opponent.cpuShips[j].getY() + i + 1));
					}
				} else if (opponent.cpuShips[j].getDirectionOfShip().equals(
						"Horizontal")) {
					for (int i = 0; i < opponent.cpuShips[j].getSize(); i++) {
						fireGUI("cpuShow",
								(opponent.cpuShips[j].getX() + i + 1),
								(opponent.cpuShips[j].getY() + 1));
					}
				}
			}
		}
	}

	// Ship placement for CPU
	public void cpuShips() {
		ShipFactory cpuFactory = new ShipFactory();
		cpuShips = cpuFactory.getShipArray();
		
		for (ShipType shipTypes : SHIP_TYPES) {
			while (!computeraddShip(shipTypes)) {
				continue;
			}
		}
	}

	// Puts the cpu values back to 0 when it is done sinking a ship
	public void resetCpu() {
		orientation = 0;
		hunting = false;
		nextShip = 0;
		setCompass();
		lastShip = "";
		cpuLastHit_y = 0;
		cpuLastHit_x = 0;
		cpuStartHit_y = 0;
		cpuStartHit_x = 0;

	}

	// Adding ships to the board
	public boolean computeraddShip(ShipType someType) {

		Ship someShip = null;
		
		for (Ship shipElement : cpuShips) {
			if (shipElement.getType() == someType) {
				someShip = shipElement;
			}
		}
		
		//TODO: this is sloppy, change it to be more elegant
		if (someShip == null) {
			return false;
		}
		
		// Generate two random coordinates between 1 and 10, one limited by
		// battleship length
		int x = random.nextInt(10 - someType.getLength());
		int y = random.nextInt(10);
		int d = random.nextInt(2);
		// Randomly choose horizontal or vertical
		if (d == 1) {
			if (x + someType.getLength() < 10) {

				for (int i = 0; i < someType.getLength(); i++) {
					if (board[x + i][y] == 1) {
						return false;
					}
				}
				someShip.setDirectionOfShip(ShipDirection.HORIZONTAL);
				someShip.setX(x);
				someShip.setY(y);
				
				for (int i = 0; i < someType.getLength(); i++) {
					board[x + i][y] = 1;
				}
				return true;
			}
		}

		else if (d == 0) {
			if (y + someType.getLength() < 10) {
				for (int i = 0; i < someType.getLength(); i++) {
					if (board[x][y + i] == 1) {
						return false;
					}
				}
				someShip.setDirectionOfShip(ShipDirection.VERTICAL);
				someShip.setX(x);
				someShip.setY(y);
				
				for (int i = 0; i < someType.getLength(); i++) {
					board[x][y + i] = 1;
				}
				return true;
			}
		}
		return false;
	}

	// Makes a count of the number of hits each ship has
	private boolean sameShip(int x, int y) throws IOException {
		if (isRange(opponent.myAircraftCarrier, x, y)) {
			opponent.myAircraftCarrier.addHits();
			if (opponent.myAircraftCarrier.sunk()) {
				cpuFillSunk(opponent.myAircraftCarrier);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Aircraft Carrier was sunk");
			}
			if (lastShip == "ac") {
				return true;
			}
			lastShip = "ac";
			return false;
		} else if (isRange(opponent.myBattleship, x, y)) {
			opponent.myBattleship.addHits();
			if (opponent.myBattleship.sunk()) {
				cpuFillSunk(opponent.myBattleship);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Battleship was sunk");
			}
			if (lastShip == "bs") {
				return true;
			}
			lastShip = "bs";
			return false;
		} else if (isRange(opponent.myDestroyer, x, y)) {
			opponent.myDestroyer.addHits();
			if (opponent.myDestroyer.sunk()) {
				cpuFillSunk(opponent.myDestroyer);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Destroyer was sunk");
			}
			if (lastShip == "ds") {
				return true;
			}
			lastShip = "ds";
			return false;
		} else if (isRange(opponent.mySubmarine, x, y)) {
			opponent.mySubmarine.addHits();
			if (opponent.mySubmarine.sunk()) {
				cpuFillSunk(opponent.mySubmarine);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Submarine was sunk");
			}
			if (lastShip == "sb") {
				return true;
			}
			lastShip = "sb";
			return false;
		} else if (isRange(opponent.myPatrolBoat, x, y)) {
			opponent.myPatrolBoat.addHits();
			if (opponent.myPatrolBoat.sunk()) {
				cpuFillSunk(opponent.myPatrolBoat);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Patrol Boat was sunk");
			}
			if (lastShip == "pb") {
				return true;
			}
			lastShip = "pb";
			return false;
		}
		return false;
	}

	// Fills a ship with black when the cpu sinks it
	private void cpuFillSunk(Ship ship) throws IOException {
		if (ship.getDirectionOfShip() == ShipDirection.VERTICAL) {
			for (int i = 0; i < ship.getSize(); i++) {
				opponent.fireGUI("cpuSunk", (ship.getX() + 1),
						(ship.getY() + i + 1));
			}
		} else if (ship.getDirectionOfShip() == ShipDirection.HORIZONTAL) {
			for (int i = 0; i < ship.getSize(); i++) {
				opponent.fireGUI("cpuSunk", (ship.getX() + i + 1),
						(ship.getY() + 1));
			}
		}
	}

	// Sets the initial values of the direction array and resets
	public void setCompass() {

		for (int i = 0; i < cpuCompass.length; i++)
			cpuCompass[i] = false;
	}

	// Takes a shot for the cpu and adds the shot to the board 
	public boolean cpuTakeShot(int x, int y) throws IOException {
		gameBoard[x][y] = 1;
		if (opponent.board[x][y] == 0) {
			// Player.misses++;
			opponent.fireGUI("cpuMiss", x + 1, y + 1);
			opponent.turn = true;
			return false;
		} else if (opponent.board[x][y] == 1) {
			hits++;
			opponent.fireGUI("cpuHit", x + 1, y + 1);
			opponent.turn = true;
			return true;
		}
		return false;
	}

	// The logic for the cpu taking shots
	public boolean cpuFire() throws IOException {
		// Finds a random number in the range
		int x = random.nextInt(10);
		int y = random.nextInt(10);

		// If the last shot was a miss then the current shot is fired randomly 
		if (gameBoard[x][y] == 0 && !hunting && nextShip == 0) {
			// If the shot is a hit then it sets the next shot to be searching
			if (cpuTakeShot(x, y)) {
				cpuStartHit_x = x;
				cpuStartHit_y = y;
				sameShip(x, y);
				hunting = true;
			}
			return true;
		}

		else if (hunting) {
			// Finding the direction of the ship starting with up and going clockwise  
			if (orientation <= 0) {
				for (int i = 0; i < cpuCompass.length; i++) {
					if (!cpuCompass[i]) {
						cpuCompass[i] = true;

						if (i == 0) {
							if (cpuStartHit_y - 1 > -1
									&& gameBoard[cpuStartHit_x][cpuStartHit_y - 1] == 0) {
								//checks to see if the shot is on the board and not already shot
								if (cpuTakeShot(cpuStartHit_x,
										cpuStartHit_y - 1)) {
									// If the shot is on the same ship the direction is found
									if (sameShip(cpuStartHit_x,
											cpuStartHit_y - 1)) {
										orientation = 1;
										cpuLastHit_x = cpuStartHit_x;
										cpuLastHit_y = cpuStartHit_y - 1;
									}
								}
								return true;
							}

						} else if (i == 1) {
							if (cpuStartHit_x + 1 < 10
									&& gameBoard[cpuStartHit_x + 1][cpuStartHit_y] == 0) {
								if (cpuTakeShot(cpuStartHit_x + 1,
										cpuStartHit_y)) {
									if (sameShip(cpuStartHit_x + 1,
											cpuStartHit_y)) {
										orientation = 2;
										cpuLastHit_x = cpuStartHit_x + 1;
										cpuLastHit_y = cpuStartHit_y;
									}
								}
								return true;

							}
						} else if (i == 2) {
							if (cpuStartHit_y + 1 < 10
									&& gameBoard[cpuStartHit_x][cpuStartHit_y + 1] == 0) {
								if (cpuTakeShot(cpuStartHit_x,
										cpuStartHit_y + 1)) {
									if (sameShip(cpuStartHit_x,
											cpuStartHit_y + 1)) {
										orientation = 3;
										cpuLastHit_x = cpuStartHit_x;
										cpuLastHit_y = cpuStartHit_y + 1;
									}
								}
								return true;
							}

						} else if (i == 3) {
							if (cpuStartHit_x - 1 > -1
									&& gameBoard[cpuStartHit_x - 1][cpuStartHit_y] == 0) {
								if (cpuTakeShot(cpuStartHit_x - 1,
										cpuStartHit_y)) {
									if (sameShip(cpuStartHit_x - 1,
											cpuStartHit_y)) {
										orientation = 4;
										cpuLastHit_x = cpuStartHit_x - 1;
										cpuLastHit_y = cpuStartHit_y;
									}

								}
								return true;
							}
						}
					}
					
				}
			

			} else if (orientation > 0) {
				// When the direction of the ship is known The boat shot until it sinks
				if (orientation == 1) {
					if (cpuLastHit_y - 1 > -1
							&& gameBoard[cpuLastHit_x][cpuLastHit_y - 1] == 0) {
						if (cpuTakeShot(cpuLastHit_x, cpuLastHit_y - 1)) {
							if (sameShip(cpuLastHit_x, cpuLastHit_y - 1)) {
								cpuLastHit_y = cpuLastHit_y - 1;
								return true;
							}
						} else { // If the shot was a miss or off the board the direction is changed
							orientation = 3;
							cpuLastHit_y = cpuStartHit_y;
							return true;
						}
					} else {
						orientation = 3;
						cpuLastHit_y = cpuStartHit_y;
						return false;
					}
				} else if (orientation == 2) {
					if (cpuLastHit_x + 1 < 10
							&& gameBoard[cpuLastHit_x + 1][cpuLastHit_y] == 0) {
						if (cpuTakeShot(cpuLastHit_x + 1, cpuLastHit_y)) {
							if (sameShip(cpuLastHit_x + 1, cpuLastHit_y)) {
								cpuLastHit_x = cpuLastHit_x + 1;
								return true;
							}
						} else {
							orientation = 4;
							cpuLastHit_x = cpuStartHit_x;
							return true;
						}
					} else {
						orientation = 4;
						cpuLastHit_x = cpuStartHit_x;
						return false;
					}
				} else if (orientation == 3) {
					if (cpuLastHit_y + 1 < 10
							&& gameBoard[cpuLastHit_x][cpuLastHit_y + 1] == 0) {
						if (cpuTakeShot(cpuLastHit_x, cpuLastHit_y + 1)) {
							if (sameShip(cpuLastHit_x, cpuLastHit_y + 1)) {
								cpuLastHit_y = cpuLastHit_y + 1;
								return true;
							}
						} else {
							orientation = 1;
							cpuLastHit_y = cpuStartHit_y;
							return true;
						}
					} else {
						orientation = 1;
						cpuLastHit_y = cpuStartHit_y;
						return false;
					}
				} else if (orientation == 4) {
					if (cpuLastHit_x - 1 > -1
							&& gameBoard[cpuLastHit_x - 1][cpuLastHit_y] == 0) {
						if (cpuTakeShot(cpuLastHit_x - 1, cpuLastHit_y)) {
							if (sameShip(cpuLastHit_x - 1, cpuLastHit_y)) {
								cpuLastHit_x = cpuLastHit_x - 1;
								return true;
							}
						} else {
							orientation = 2;
							cpuLastHit_x = cpuStartHit_x;
							return true;
						}
					} else {
						orientation = 2;
						cpuLastHit_x = cpuStartHit_x;
						return false;
					}
				}
				return false;
			}

		}

		return false;

	}
	
	// Classes-----------------------------------------------------------------
	
	// Holds the values for the ships
	public class setupMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			int x = getGrid(e.getX());
			int y = getGrid(e.getY());
			try {
				if (x >= 1 && y >= 1 && freeBoard(x, y)) {
					if (!putShipsInBoard(x, y) && validPlace(x, y)) {
						removeOldShip();
						replaceShip(x, y);
						redrawShips();
					}
					if (shipPlace[0] == true && shipPlace[1] == true
							&& shipPlace[2] == true && shipPlace[3] == true
							&& shipPlace[4] == true) {
						doneButton.setEnabled(true);
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}
}

	// Takes values to pass to fire
	public class gameMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			int x = getGrid(e.getX());
			int y = getGrid(e.getY());
			if (x >= 1 && y >= 1 && turn) {
				try {
					if (fire(x, y)) {
						turn = false;
						opponent.turn = true;
						if (!checkWin()) {
							while (!opponent.cpuFire())
								;
							checkWin();
						}

					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}

	}

}