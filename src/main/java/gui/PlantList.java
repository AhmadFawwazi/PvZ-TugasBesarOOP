package gui;

import javax.swing.*;

import java.awt.*;

public class PlantList extends JPanel {
    private Image backgroundImage;
    private JLabel detailLabel; // Displays the detail in the large rectangle
    private GameGUI gameGUI;

    public PlantList(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        initializeUI();
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/resources/images/description/bgplantdesc.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace(); // Handle potential errors gracefully
        }
        setPreferredSize(new Dimension(1280, 720)); // Preferred size for the JPanel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image to fit the panel size
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout(5, 5)); // Set the layout for JPanel

        // Create a panel for the top bar and back button
        JPanel topBarPanel = new JPanel();
        topBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Use FlowLayout to place items from left to right
        topBarPanel.setPreferredSize(new Dimension(1280, 130));
        topBarPanel.setOpaque(false);  // Make the panel transparent
    
        // Back Button
        ImageIcon backButtonIcon = new ImageIcon(getClass().getResource("/resources/images/description/backdescplant.png"));
        JButton backButton = new JButton(backButtonIcon);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> gameGUI.showMainMenu());
        
        // Top bar with image
        ImageIcon topBarIcon = new ImageIcon(getClass().getResource("/resources/images/description/headerplant.png"));
        JLabel topBarLabel = new JLabel(topBarIcon);
    
        // Add back button and top bar label to the top bar panel
        topBarPanel.add(backButton);
        topBarPanel.add(Box.createHorizontalStrut(100));
        topBarPanel.add(topBarLabel);
        add(topBarPanel, BorderLayout.NORTH);

        // Grid of clickable items
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        gridPanel.setOpaque(false);

        for (int i = 0; i < 10; i++) { // Assume you've corrected the count to match the grid layout
            JButton button = new JButton(new ImageIcon(getClass().getResource("/resources/images/description/plant" + i + ".png")));
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            final int index = i;
            button.addActionListener(e -> updateDetailDisplay("/resources/images/description/descplant" + index + ".png"));
            gridPanel.add(button);
        }

        // Large rectangle for details
        detailLabel = new JLabel("", JLabel.CENTER);

        // Adding components to the layout
        add(gridPanel, BorderLayout.WEST);
        add(detailLabel, BorderLayout.CENTER);
    }

    private void updateDetailDisplay(String detailText) {
        detailLabel.setIcon(new ImageIcon(getClass().getResource(detailText)));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PlantList");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        GameGUI gameGUI = new GameGUI();
        frame.add(new PlantList(gameGUI));
        frame.setVisible(true);
    }
}
