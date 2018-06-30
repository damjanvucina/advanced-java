package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorAreaLabel;

public class JVDraw extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "JVDraw";
	private static final Color DEFAULT_FOREGROUND_COLOR = Color.RED;
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLUE;
	private static final String FOREGROUND_TOOLTIP="Set foreground color";
	private static final String BACKGROUND_TOOLTIP="Set background color";
	
	private JPanel panel;
	private JPanel colorAreaLabelPanel;
	private JColorArea fgColorArea;
	private JColorArea bgColorArea;
	private JColorAreaLabel colorAreaLabel;
	private JToolBar toolBar;
	private JToggleButton btnLine;
	private JToggleButton btnCircle;
	private JToggleButton btnFilledCircle;

	public JVDraw() {
		setSize(1000, 600);
		setLocation(50, 50);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGui();
	}

	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		panel = new JPanel(new BorderLayout());
		cp.add(panel, BorderLayout.CENTER);
		
		initializeColorAreas();
		initializeColorAreaLabel();
		initializeGeometricalObjectButtons();
		
		setUpColorAreaLabelPanel();
		cp.add(colorAreaLabelPanel, BorderLayout.SOUTH);
		
		setUpToolbar();
		panel.add(toolBar, BorderLayout.NORTH);
		
		setTitle(TITLE);
	}
	
	private void initializeGeometricalObjectButtons() {
		btnLine = new JToggleButton("Line");
		btnCircle = new JToggleButton("Circle");
		btnFilledCircle = new JToggleButton("Filled Circle");
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(btnLine);
		btnGroup.add(btnCircle);
		btnGroup.add(btnFilledCircle);
	}

	private void initializeColorAreaLabel() {
		colorAreaLabel = new JColorAreaLabel(fgColorArea, bgColorArea);
	}

	private void initializeColorAreas() {
		fgColorArea = new JColorArea(DEFAULT_FOREGROUND_COLOR, FOREGROUND_TOOLTIP);
		bgColorArea = new JColorArea(DEFAULT_BACKGROUND_COLOR, BACKGROUND_TOOLTIP);
	}

	private void setUpColorAreaLabelPanel() {
		colorAreaLabelPanel = new JPanel();
		colorAreaLabelPanel.add(colorAreaLabel);
	}

	private void setUpToolbar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(true);
		
		toolBar.addSeparator();
		toolBar.add(bgColorArea);
		toolBar.addSeparator();
		toolBar.add(fgColorArea);
		toolBar.addSeparator();
		
		toolBar.addSeparator();
		toolBar.add(btnLine);
		toolBar.addSeparator();
		toolBar.add(btnCircle);
		toolBar.addSeparator();
		toolBar.add(btnFilledCircle);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
	}
}
