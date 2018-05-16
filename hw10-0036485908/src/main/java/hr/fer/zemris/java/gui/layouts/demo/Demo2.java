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

/**
 * Demonstration class used for the purpose of testing the CalcLayout class.
 * @author Damjan Vučina
 */
@SuppressWarnings("unused")
public class Demo2 extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new Demonstration window.
	 */
	public Demo2() {
		setLocation(20, 50);
		setSize(600, 600);
		setTitle("CalcLayout Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();

	}


	/**
	 * Initializes the GUI of this demonstration class and defines RCPositions via their String representations.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("x"), "1,1");
		p.add(new JLabel("y"), "2,3");
		p.add(new JLabel("z"), "2,7");
		p.add(new JLabel("w"), "4,2");
		p.add(new JLabel("a"), "4,5");
		p.add(new JLabel("b"), "4,7");
		
		for(Component component : p.getComponents()) {
			JLabel currentLabel = (JLabel) component;
			currentLabel.setHorizontalAlignment(SwingConstants.CENTER);
			currentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			currentLabel.setOpaque(true);
			currentLabel.setBackground(Color.CYAN);
		}

		cp.add(p);
	}


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Demo1();
			frame.setVisible(true);
		});
	}
}
