package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstration class used for previewing the constructed window printing prime
 * numbers.
 * 
 * @author Damjan Vučina
 */
public class PrimDemo extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new prim demo.
	 */
	public PrimDemo() {
		setLocation(50, 50);
		setSize(500, 500);
		setTitle("Prim Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Initializes the GUI.
	 */
	public void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JPanel listPanel = new JPanel(new GridLayout(1, 0));
		listPanel.add(new JScrollPane(list1));
		listPanel.add(new JScrollPane(list2));

		JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
		JButton addButton = new JButton("Sljedeći");
		addButton.addActionListener(l -> {
			model.next();

		});
		buttonPanel.add(addButton);

		cp.add(listPanel, BorderLayout.CENTER);
		cp.add(buttonPanel, BorderLayout.PAGE_END);
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.setVisible(true);
		});
	}
}
