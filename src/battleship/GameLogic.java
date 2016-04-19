package battleship;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import battleship.Player.gameMouseListener;
import battleship.Player.setupMouseListener;

public class GameLogic {
	
	Player player1, cpu;
	
	private String shipName = "";
	
	// Main window
	private JFrame placeFrame = new JFrame("BattleShip");
	final static int size_xL = 1010;
	final static int size_yL = 700;

	// Left board components
	private JPanel shipPanel = new JPanel();
	private JPanel boxPanel = new JPanel();
	private JPanel cpuPanel = new JPanel();
	private JPanel cpuSunkPanel = new JPanel();
	MouseListener boardListener;

	// Right board components
	private JPanel fireBoxPanel = new JPanel();
	private JPanel sunkBoxPanel = new JPanel();
	MouseListener gameListener;

	// Ship selection components
	private String shipDirection = "";
	private JPanel boardPanel = new JPanel();
	private String shipsListArray[] = { "Aircraft Carrier", "Battleship",
			"Destroyer", "Submarine", "Patrol Boat" };
	private JList<String> shipList = new JList<String>(shipsListArray);
	private String shipDirections[] = { "Horizontal", "Vertical" };
	private JList<String> directionList = new JList<String>(shipDirections);
	private JButton doneButton;
	
	// Files
	final static File file_1L = new File("src/res/img/lg_head.jpg");
	final static File file_g = new File("src/res/img/green.gif");
	final static File file_blu = new File("src/res/img/blue.gif");
	final static File file_r = new File("src/res/img/red.gif");
	final static File file_gry = new File("src/res/img/grey.gif");
	final static File file_blk = new File("src/res/img/black.gif");
	public Image icon = Toolkit.getDefaultToolkit()
			.getImage("src/res/img/icon.gif");
	
	public GameLogic() {
		
	}
	
	public void startSinglePlayerGame() throws IOException {
		
		player1 = new Player("player1");
		cpu = new Player("cpu");
		
		player1.setOpponent(cpu);
		cpu.setOpponent(player1);
		
		cpu.cpuShips();
		cpu.setCompass();
		
		boardListener = player1.new setupMouseListener();
		gameListener = player1.new gameMouseListener();
		
		player1.mainGUI();
		
		//mainGUI();
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
					String stringValue = (String) shipList.getSelectedValue();
					if (stringValue != null)
						shipName = stringValue;
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
					String stringValue = (String) directionList
							.getSelectedValue();
					if (stringValue != null)
						shipDirection = stringValue;
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
}
