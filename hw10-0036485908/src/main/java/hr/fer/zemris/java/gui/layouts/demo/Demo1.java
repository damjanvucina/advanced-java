package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

@SuppressWarnings("unused")
public class Demo1 extends JFrame {
	private static final long serialVersionUID = 1L;

	public Demo1() {
		setLocation(20, 50);
		setSize(600, 600);
		setTitle("CalcLayout Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();

	}


	private void initGUI() {
		Container cp = getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("x"), new RCPosition(1,1));
		p.add(new JLabel("y"), new RCPosition(2,3));
		p.add(new JLabel("z"), new RCPosition(2,7));
		p.add(new JLabel("w"), new RCPosition(4,2));
		p.add(new JLabel("a"), new RCPosition(4,5));
		p.add(new JLabel("b"), new RCPosition(4,7));
		
		for(Component component : p.getComponents()) {
			JLabel currentLabel = (JLabel) component;
			currentLabel.setHorizontalAlignment(SwingConstants.CENTER);
			currentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			currentLabel.setOpaque(true);
			currentLabel.setBackground(Color.CYAN);
		}
		
		cp.add(p);
	}


	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Demo1();
			frame.setVisible(true);
		});
	}
}
