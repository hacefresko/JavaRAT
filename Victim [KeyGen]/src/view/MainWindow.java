package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow extends JFrame{
	private JTextArea key;
	
	public MainWindow(){
		super("Key Generator for Adobe Suite");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		String[] progList = {"Photoshop", "Lightroom Classic", "Illustrator", "InDesign", "Adobe Premiere Pro", "After Effects"};
		JComboBox<String> progSelect = new JComboBox<String>(progList);
		centerPanel.add(progSelect);
		
		JButton generateButton = new JButton("Generate key"); 
		generateButton.addActionListener((e) ->{
			key.setText(generateCode());
		});
		centerPanel.add(generateButton);
		
		JLabel keyLabel = new JLabel("Key: ");
		centerPanel.add(keyLabel);	
		
		key = new JTextArea();
		key.setEditable(false);
		key.setPreferredSize(new Dimension(400, 15));
		key.setMaximumSize(new Dimension(400, 15));
		key.setMinimumSize(new Dimension(400, 15));
		centerPanel.add(key);
		
		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setSize(600, 400);
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
