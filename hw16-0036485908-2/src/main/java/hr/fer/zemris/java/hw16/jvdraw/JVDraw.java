package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorAreaLabel;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class JVDraw extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "JVDraw";
	private static final Color DEFAULT_FOREGROUND_COLOR = Color.RED;
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLUE;
	private static final String FOREGROUND_TOOLTIP="Set foreground color";
	private static final String BACKGROUND_TOOLTIP="Set background color";
	private static final String LINE_TOOL = "line";
	private static final String CIRCLE_TOOL = "circle";
	private static final String FILLED_CIRCLE_TOOL = "filledCircle";
	
	private JPanel panel;
	private JPanel colorAreaLabelPanel;
	private JColorArea fgColorArea;
	private JColorArea bgColorArea;
	private JColorAreaLabel colorAreaLabel;
	private JToolBar toolBar;
	private JToggleButton btnLine;
	private JToggleButton btnCircle;
	private JToggleButton btnFilledCircle;
	private Tool currentTool;
	private JDrawingCanvas drawingCanvas;
	private DocumentModel documentModel;
	private Map<String, Tool> tools;
	
	public JVDraw() {
		setSize(500, 300);
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
		initializeDocumentModel();
		
		initializeTools();
		setDefaultTool();
		initializeButtonListeners();
		
		setUpColorAreaLabelPanel();
		cp.add(colorAreaLabelPanel, BorderLayout.SOUTH);
		
		setUpToolbar();
		panel.add(toolBar, BorderLayout.NORTH);
		
		
		setUpCanvas();
		panel.add(drawingCanvas, BorderLayout.CENTER);
		
		
		setTitle(TITLE);
	}
	

	private void setDefaultTool() {
		btnLine.doClick();
		currentTool = tools.get(LINE_TOOL);
	}

	public Map<String, Tool> getTools() {
		return tools;
	}
	
	private void initializeTools() {
		tools = new HashMap<>();
		
		tools.put(LINE_TOOL, new Line(documentModel, fgColorArea));
		tools.put(CIRCLE_TOOL, new Circle(documentModel, fgColorArea));
		tools.put(FILLED_CIRCLE_TOOL, new FilledCircle(documentModel, fgColorArea, bgColorArea));
	}

	private void initializeDocumentModel() {
		documentModel = new DocumentModel();
	}

	private void setUpCanvas() {
		drawingCanvas = new JDrawingCanvas(this, documentModel);
	}

	private void initializeButtonListeners() {
		btnLine.addActionListener(createActionListener(LINE_TOOL));
		btnCircle.addActionListener(createActionListener(CIRCLE_TOOL));
		btnFilledCircle.addActionListener(createActionListener(FILLED_CIRCLE_TOOL));
	}

	private ActionListener createActionListener(String newTool) {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentTool(getTools().get(newTool));
			}
		};
	}

	public Tool getCurrentTool() {
		return currentTool;
	}

	public void setCurrentTool(Tool currentTool) {
		this.currentTool = currentTool;
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
		toolBar.add(fgColorArea);
		toolBar.addSeparator();
		toolBar.add(bgColorArea);
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
