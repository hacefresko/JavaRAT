package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow extends JFrame{
	public MainWindow(){
		super("Youtube to MP3 downloader [FREE VERSION]");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel imagePanel = new JPanel();
		imagePanel.setBackground(Color.WHITE);
		imagePanel.add(new JLabel(new ImageIcon("resources/internal/binaries/images/youtubeMP3.png")));
		mainPanel.add(imagePanel, BorderLayout.PAGE_START);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		centerPanel.setBackground(Color.WHITE);
		
		centerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		
		JLabel urlLabel = new JLabel("URL: ");
		centerPanel.add(urlLabel);
		
		JTextField url = new JTextField(50);
		url.setPreferredSize(new Dimension(500, 25));
		url.setMaximumSize(new Dimension(500, 25));
		url.setMinimumSize(new Dimension(500, 25));
		centerPanel.add(url);
		
		JButton urlButton = new JButton("Download as mp3");
		centerPanel.add(urlButton);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setSize(700, 220);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
}
