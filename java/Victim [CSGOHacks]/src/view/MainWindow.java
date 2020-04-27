package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame{
	public MainWindow(){
		super("CSGO HACKS [FREE VERSION]");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);
		
		ImageIcon background = new ImageIcon("resources/background.jpg");
		Image temp = background.getImage();
		temp = temp.getScaledInstance(270, 342, Image.SCALE_SMOOTH);
		background = new ImageIcon(temp);
		JLabel backgroundLabel = new JLabel(background);
		mainPanel.add(backgroundLabel, BorderLayout.LINE_START);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		centerPanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.LINE_START);
		
		JPanel centerCenterPanel = new JPanel();
		centerCenterPanel.setLayout(new BoxLayout(centerCenterPanel, BoxLayout.Y_AXIS));
		centerCenterPanel.setBackground(Color.WHITE);
		centerPanel.add(centerCenterPanel, BorderLayout.CENTER);;
		
		ImageIcon titleImage = new ImageIcon("resources/title.png");
		temp = titleImage.getImage();
		temp = temp.getScaledInstance(223, 100, Image.SCALE_SMOOTH);
		titleImage = new ImageIcon(temp);
		JLabel titleLabel = new JLabel(titleImage);
		centerPanel.add(titleLabel, BorderLayout.PAGE_START);
		
		JLabel title = new JLabel("<html>Game hacks for Counter Strike Global Offensive [<font color='lime'>UNDETECTED</font>]</html>");
		title.setFont(new Font(null, Font.BOLD, 20));
		centerCenterPanel.add(title);
		
		centerCenterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JLabel howTo = new JLabel("Select the hacks you want before initializing the game:");
		centerCenterPanel.add(howTo);
		
		JCheckBox aimbot = new JCheckBox("Aimbot");
		JCheckBox wallhack = new JCheckBox("Wallhack");
		JCheckBox autoheadshot = new JCheckBox("Autoheadshot");
		aimbot.setBackground(Color.WHITE);
		wallhack.setBackground(Color.WHITE);
		autoheadshot.setBackground(Color.WHITE);
		aimbot.setAlignmentX(LEFT_ALIGNMENT);
		wallhack.setAlignmentX(LEFT_ALIGNMENT);
		autoheadshot.setAlignmentX(LEFT_ALIGNMENT);
		centerCenterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		centerCenterPanel.add(aimbot);
		centerCenterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		centerCenterPanel.add(wallhack);
		centerCenterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		centerCenterPanel.add(autoheadshot);
		
		this.setSize(620, 380);
		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
}
