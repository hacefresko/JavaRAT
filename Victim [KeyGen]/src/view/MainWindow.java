package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame{
	private JTextArea key;
	private JLabel icon;
	private String[] progList = {"Photoshop", "Lightroom Classic", "Illustrator", "InDesign", "Premiere Pro", "After Effects"};
	
	public MainWindow(){
		super("Key Generator for Adobe Suite");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel centerSuperior = new JPanel();
		centerSuperior.setBackground(Color.WHITE);
		centerPanel.add(centerSuperior);
		
		icon = new JLabel(new ImageIcon("resources/photoShop.png"));
		centerSuperior.add(icon);
		
		JComboBox<String> progSelect = new JComboBox<String>(progList);
		progSelect.addActionListener((e) -> {
			JComboBox<String> cb = (JComboBox<String>)e.getSource();
			String prog = (String)cb.getSelectedItem();
			if(prog.equals(progList[0])) {
				icon.setIcon(new ImageIcon("resources/photoShop.png"));
			}
			else if (prog.equals(progList[1])){
				icon.setIcon(new ImageIcon("resources/lightRoom.png"));
			}
			else if (prog.equals(progList[2])){
				icon.setIcon(new ImageIcon("resources/illustrator.png"));
			}
			else if (prog.equals(progList[3])){
				icon.setIcon(new ImageIcon("resources/inDesign.png"));
			}
			else if (prog.equals(progList[4])){
				icon.setIcon(new ImageIcon("resources/adobePremier.png"));
			}
			else if (prog.equals(progList[5])) {
				icon.setIcon(new ImageIcon("resources/afterEffects.png"));
			}
		});
		centerSuperior.add(progSelect);
		
		JPanel centerCenter = new JPanel();
		centerCenter.setBackground(Color.WHITE);
		centerPanel.add(centerCenter);
		
		JButton generateButton = new JButton("Generate key"); 
		generateButton.addActionListener((e) ->{
			key.setText(generateCode());
		});
		centerCenter.add(generateButton);
		
		JLabel keyLabel = new JLabel("Key: ");
		centerCenter.add(keyLabel);	
		
		key = new JTextArea();
		key.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		key.setEditable(false);
		key.setPreferredSize(new Dimension(200, 20));
		key.setMaximumSize(new Dimension(200, 20));
		key.setMinimumSize(new Dimension(200, 20));
		centerCenter.add(key);
		
		JPanel centerInferior = new JPanel(new BorderLayout());
		centerInferior.setBackground(Color.WHITE);
		centerPanel.add(centerInferior);
		
		JLabel howToUse = new JLabel("<html>In order to generate the key correctly, open the program you want to generate the key for, select it in the menu and press generate key. Otherwhise, the key won't work. Thanks for downloading and enjoy.</html>");
		howToUse.setFont(new Font(null, Font.PLAIN, 12));
		howToUse.setPreferredSize(new Dimension(360, 100));
		howToUse.setMaximumSize(new Dimension(360, 100));
		howToUse.setMinimumSize(new Dimension(360, 100));
		centerInferior.add(howToUse);
		
		JPanel versionPanel = new JPanel();
		versionPanel.setLayout(new BoxLayout(versionPanel, BoxLayout.X_AXIS));
		versionPanel.setBackground(Color.white);
		
		versionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		
		JLabel version = new JLabel("v 1.6");
		version.setFont(new Font(null, Font.PLAIN, 12));
		versionPanel.add(version);
		mainPanel.add(versionPanel, BorderLayout.PAGE_END);
		
		versionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setPreferredSize(new Dimension(20, 10));
		sep.setMaximumSize(new Dimension(20, 10));
		sep.setMinimumSize(new Dimension(20, 10));
		versionPanel.add(sep);
		
		JLabel info = new JLabel("More info at https://adobecrack.com");
		info.setFont(new Font(null, Font.PLAIN, 12));
		versionPanel.add(info);
		
		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setSize(500, 280);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	private String generateCode() {
		String code = "";
		Random rand = new Random();
		int next;
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 4; j++) {
				next = rand.nextInt(15) + 1;
				if(next == 10) {
					code += "A";
				}
				else if(next == 11){
					code += "B";
				}
				else if(next == 12){
					code += "C";			
				}
				else if(next == 13){
					code += "D";
				}
				else if(next == 14){
					code += "E";
				}
				else if(next == 15){
					code += "F";
				}
				else {
					code += String.valueOf(next);
				}
			}
			if(i < 5) {
				code += " ";
			}
		}
		
		return code;
	}
}
