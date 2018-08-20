package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorAreaLabel;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.model.ObjectModelException;

public class JVDraw extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final String TITLE = "JVDraw";
	private static final Color DEFAULT_FOREGROUND_COLOR = Color.RED;
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLUE;
	private static final String FOREGROUND_TOOLTIP = "Set foreground color";
	private static final String BACKGROUND_TOOLTIP = "Set background color";
	private static final String LINE_TOOL = "line";
	private static final String CIRCLE_TOOL = "circle";
	private static final String FILLED_CIRCLE_TOOL = "filledCircle";
	private static final int OPEN_EDITOR = 2;
	private static final String DIALOG_MESSAGE = "Do you want to edit properties?";

	private JPanel panel;
	private JPanel canvasPanel;
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
	private JList<GeometricalObject> jList;
	private DrawingObjectListModel jListModel;

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
		initializeDocumentModel();
		initializeCanvas();
		initializeTools();

		setDefaultTool();
		initializeButtonListeners();

		setUpColorAreaLabelPanel();
		cp.add(colorAreaLabelPanel, BorderLayout.SOUTH);

		setUpToolbar();
		panel.add(toolBar, BorderLayout.NORTH);

		setUpJList();
		canvasPanel = new JPanel(new BorderLayout());
		panel.add(canvasPanel);
		canvasPanel.add(drawingCanvas, BorderLayout.CENTER);
		canvasPanel.add(new JScrollPane(jList), BorderLayout.EAST);

		setTitle(TITLE);
	}

	public JDrawingCanvas getDrawingCanvas() {
		return drawingCanvas;
	}

	public DocumentModel getDocumentModel() {
		return documentModel;
	}

	private void setUpJList() {
		jListModel = new DrawingObjectListModel(documentModel);
		jList = new JList<>(jListModel);

		jList.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				JList<GeometricalObject> jList = (JList<GeometricalObject>) e.getSource();
				int numOfClicks = e.getClickCount();

				if (numOfClicks == OPEN_EDITOR) {
					Rectangle r = jList.getCellBounds(0, jList.getLastVisibleIndex());

					if (r != null && r.contains(e.getPoint())) {
						int index = jList.locationToIndex(e.getPoint());

						GeometricalObject clickedObject = jList.getModel().getElementAt(index);
						GeometricalObjectEditor editor = clickedObject.createGeometricalObjectEditor();

						//@formatter:off
						if(JOptionPane.showConfirmDialog(getDrawingCanvas(),
														 editor,
														 DIALOG_MESSAGE,
														 JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						//@formatter:off
								try {
									editor.checkEditing();
									editor.acceptEditing();
								} catch (ObjectModelException exc) {
									JOptionPane.showMessageDialog(getDrawingCanvas(), exc.getMessage(),"Warning", JOptionPane.WARNING_MESSAGE);
									jList.clearSelection();
									return;
								}
								
								
						}
						jList.clearSelection();
						
					}
				} 
			}
		});
		
		jList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				@SuppressWarnings("unchecked")
				JList<GeometricalObject> jList = (JList<GeometricalObject>) e.getSource();
				
				if(jList.isFocusOwner()) {
					int selectedIndex = jList.getSelectedIndex();
					GeometricalObject object = documentModel.getObject(selectedIndex);
					
					switch (e.getKeyCode()) {
					case KeyEvent.VK_DELETE:
						getDocumentModel().remove(object);
						jList.clearSelection();
						break;
						
					case KeyEvent.VK_PLUS:
						System.out.println("Pritisnut je plus");
						break;
						
					case KeyEvent.VK_MINUS:
						System.out.println("Pritisnut je minus");
						break;

					default:
						break;
					}
				}

			}
		});
	}

	private void setDefaultTool() {
		btnLine.doClick();
		currentTool = tools.get(LINE_TOOL);
	}

	public Map<String, Tool> getTools() {
		return tools;
	}

	public DrawingObjectListModel getjListModel() {
		return jListModel;
	}

	private void initializeTools() {
		tools = new HashMap<>();

		tools.put(LINE_TOOL, new Line(documentModel, fgColorArea, drawingCanvas));
		tools.put(CIRCLE_TOOL, new Circle(documentModel, fgColorArea, drawingCanvas));
		tools.put(FILLED_CIRCLE_TOOL, new FilledCircle(documentModel, fgColorArea, bgColorArea, drawingCanvas));
	}

	private void initializeDocumentModel() {
		documentModel = new DocumentModel();
	}

	private void initializeCanvas() {
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