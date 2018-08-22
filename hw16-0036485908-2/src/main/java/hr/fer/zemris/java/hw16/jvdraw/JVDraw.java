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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;
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

// TODO: Auto-generated Javadoc
/**
 * The Class JVDraw.
 */
public class JVDraw extends JFrame {
	
	/** The Constant FILE_MENU. */
	private static final String FILE_MENU = "File";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TITLE. */
	private static final String TITLE = "JVDraw";
	
	/** The Constant DEFAULT_FOREGROUND_COLOR. */
	private static final Color DEFAULT_FOREGROUND_COLOR = Color.RED;
	
	/** The Constant DEFAULT_BACKGROUND_COLOR. */
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLUE;
	
	/** The Constant FOREGROUND_TOOLTIP. */
	private static final String FOREGROUND_TOOLTIP = "Set foreground color";
	
	/** The Constant BACKGROUND_TOOLTIP. */
	private static final String BACKGROUND_TOOLTIP = "Set background color";
	
	/** The Constant LINE_TOOL. */
	private static final String LINE_TOOL = "line";
	
	/** The Constant CIRCLE_TOOL. */
	private static final String CIRCLE_TOOL = "circle";
	
	/** The Constant FILLED_CIRCLE_TOOL. */
	private static final String FILLED_CIRCLE_TOOL = "filledCircle";
	
	/** The Constant OPEN_EDITOR. */
	private static final int OPEN_EDITOR = 2;
	
	/** The Constant DIALOG_MESSAGE. */
	private static final String DIALOG_MESSAGE = "Do you want to edit properties?";
	
	/** The Constant SHIFT_UP. */
	public static final int SHIFT_UP = 1;
	
	/** The Constant SHIFT_DOWN. */
	public static final int SHIFT_DOWN = -1;

	/** The panel. */
	private JPanel panel;
	
	/** The canvas panel. */
	private JPanel canvasPanel;
	
	/** The color area label panel. */
	private JPanel colorAreaLabelPanel;
	
	/** The fg color area. */
	private JColorArea fgColorArea;
	
	/** The bg color area. */
	private JColorArea bgColorArea;
	
	/** The color area label. */
	private JColorAreaLabel colorAreaLabel;
	
	/** The tool bar. */
	private JToolBar toolBar;
	
	/** The btn line. */
	private JToggleButton btnLine;
	
	/** The btn circle. */
	private JToggleButton btnCircle;
	
	/** The btn filled circle. */
	private JToggleButton btnFilledCircle;
	
	/** The current tool. */
	private Tool currentTool;
	
	/** The drawing canvas. */
	private JDrawingCanvas drawingCanvas;
	
	/** The document model. */
	private DocumentModel documentModel;
	
	/** The tools. */
	private Map<String, Tool> tools;
	
	/** The j list. */
	private JList<GeometricalObject> jList;
	
	/** The j list model. */
	private DrawingObjectListModel jListModel;
	
	/** The j menu bar. */
	private JMenuBar jMenuBar;
	
	/** The open action. */
	private OpenAction openAction;
	
	/** The save action. */
	private SaveAction saveAction;
	
	/** The save as action. */
	private SaveAsAction saveAsAction;
	
	/** The export action. */
	private ExportAction exportAction;
	
	/** The exit action. */
	private ExitAction exitAction;
	
	/** The image path. */
	private Path imagePath;

	/**
	 * Instantiates a new JV draw.
	 */
	public JVDraw() {
		setSize(1000, 600);
		setLocation(50, 50);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		
		initGui();
	}

	/**
	 * Inits the gui.
	 */
	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		panel = new JPanel(new BorderLayout());
		cp.add(panel, BorderLayout.CENTER);
		
		setTitle(TITLE);

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
		
		jMenuBar = new JMenuBar();
		setJMenuBar(jMenuBar);
		setUpActions();
		setUpMenu();
	}
	
	/**
	 * Gets the save as action.
	 *
	 * @return the save as action
	 */
	public SaveAsAction getSaveAsAction() {
		return saveAsAction;
	}
	
	/**
	 * Gets the save action.
	 *
	 * @return the save action
	 */
	public SaveAction getSaveAction() {
		return saveAction;
	}

	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	public Path getImagePath() {
		return imagePath;
	}

	/**
	 * Sets the image path.
	 *
	 * @param imagePath the new image path
	 */
	public void setImagePath(Path imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * Sets the up menu.
	 */
	private void setUpMenu() {
		JMenu fileMenu = new JMenu(FILE_MENU);
		jMenuBar.add(fileMenu);
		
		fileMenu.add(openAction);
		fileMenu.addSeparator();
		fileMenu.add(saveAction);
		fileMenu.add(saveAsAction);
		fileMenu.addSeparator();
		fileMenu.add(exportAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);
		
	}

	/**
	 * Sets the up actions.
	 */
	private void setUpActions() {
		openAction = new OpenAction(this);
		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.SMALL_ICON, acquireIcon("open.png"));
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openAction.putValue(Action.SHORT_DESCRIPTION, "Opens existing file.");
		
		saveAction = new SaveAction(this);
		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.SMALL_ICON, acquireIcon("save.png"));
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAction.putValue(Action.SHORT_DESCRIPTION, "Saves current file.");
		
		saveAsAction = new SaveAsAction(this);
		saveAsAction.putValue(Action.NAME, "Save As");
		saveAsAction.putValue(Action.SMALL_ICON, acquireIcon("saveas.png"));
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Saves file to new location.");
		
		exportAction = new ExportAction(this);
		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.SMALL_ICON, acquireIcon("export.png"));
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exportAction.putValue(Action.SHORT_DESCRIPTION, "Exports file.");
		
		exitAction = new ExitAction(this);
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.SMALL_ICON, acquireIcon("open.png"));
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Closes current file.");
	}
	
	/**
	 * Acquire icon.
	 *
	 * @param iconName the icon name
	 * @return the image icon
	 */
	public ImageIcon acquireIcon(String iconName) {
		StringBuilder sb = new StringBuilder("icons");
		sb.append("/").append(iconName);

		InputStream is = this.getClass().getResourceAsStream(sb.toString());

		if (is == null) {
			throw new IllegalArgumentException("Cannot access icon " + sb.toString());
		}

		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ImageIcon(bytes);
	}

	/**
	 * Gets the drawing canvas.
	 *
	 * @return the drawing canvas
	 */
	public JDrawingCanvas getDrawingCanvas() {
		return drawingCanvas;
	}

	/**
	 * Gets the document model.
	 *
	 * @return the document model
	 */
	public DocumentModel getDocumentModel() {
		return documentModel;
	}

	/**
	 * Sets the up J list.
	 */
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
				
				if(jList.isFocusOwner() && !jList.isSelectionEmpty()) {
					int selectedIndex = jList.getSelectedIndex();
					GeometricalObject object = documentModel.getObject(selectedIndex);
					
					switch (e.getKeyCode()) {
					case KeyEvent.VK_DELETE:
						getDocumentModel().remove(object);
						jList.clearSelection();
						break;
						
					case KeyEvent.VK_PLUS:
						getDocumentModel().changeOrder(object, SHIFT_UP);
						jList.clearSelection();
						break;
						
					case KeyEvent.VK_MINUS:
						getDocumentModel().changeOrder(object, SHIFT_DOWN);
						jList.clearSelection();
						break;

					default:
						break;
					}
				}

			}
		});
	}

	/**
	 * Sets the default tool.
	 */
	private void setDefaultTool() {
		btnLine.doClick();
		currentTool = tools.get(LINE_TOOL);
	}

	/**
	 * Gets the tools.
	 *
	 * @return the tools
	 */
	public Map<String, Tool> getTools() {
		return tools;
	}

	/**
	 * Gets the j list model.
	 *
	 * @return the j list model
	 */
	public DrawingObjectListModel getjListModel() {
		return jListModel;
	}

	/**
	 * Initialize tools.
	 */
	private void initializeTools() {
		tools = new HashMap<>();

		tools.put(LINE_TOOL, new Line(documentModel, fgColorArea, drawingCanvas));
		tools.put(CIRCLE_TOOL, new Circle(documentModel, fgColorArea, drawingCanvas));
		tools.put(FILLED_CIRCLE_TOOL, new FilledCircle(documentModel, fgColorArea, bgColorArea, drawingCanvas));
	}

	/**
	 * Initialize document model.
	 */
	private void initializeDocumentModel() {
		documentModel = new DocumentModel();
	}

	/**
	 * Initialize canvas.
	 */
	private void initializeCanvas() {
		drawingCanvas = new JDrawingCanvas(this, documentModel);
	}

	/**
	 * Initialize button listeners.
	 */
	private void initializeButtonListeners() {
		btnLine.addActionListener(createActionListener(LINE_TOOL));
		btnCircle.addActionListener(createActionListener(CIRCLE_TOOL));
		btnFilledCircle.addActionListener(createActionListener(FILLED_CIRCLE_TOOL));
	}

	/**
	 * Creates the action listener.
	 *
	 * @param newTool the new tool
	 * @return the action listener
	 */
	private ActionListener createActionListener(String newTool) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentTool(getTools().get(newTool));
			}
		};
	}

	/**
	 * Gets the current tool.
	 *
	 * @return the current tool
	 */
	public Tool getCurrentTool() {
		return currentTool;
	}

	/**
	 * Sets the current tool.
	 *
	 * @param currentTool the new current tool
	 */
	public void setCurrentTool(Tool currentTool) {
		this.currentTool = currentTool;
	}

	/**
	 * Initialize geometrical object buttons.
	 */
	private void initializeGeometricalObjectButtons() {
		btnLine = new JToggleButton("Line");
		btnCircle = new JToggleButton("Circle");
		btnFilledCircle = new JToggleButton("Filled Circle");

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(btnLine);
		btnGroup.add(btnCircle);
		btnGroup.add(btnFilledCircle);
	}

	/**
	 * Initialize color area label.
	 */
	private void initializeColorAreaLabel() {
		colorAreaLabel = new JColorAreaLabel(fgColorArea, bgColorArea);
	}

	/**
	 * Initialize color areas.
	 */
	private void initializeColorAreas() {
		fgColorArea = new JColorArea(DEFAULT_FOREGROUND_COLOR, FOREGROUND_TOOLTIP);
		bgColorArea = new JColorArea(DEFAULT_BACKGROUND_COLOR, BACKGROUND_TOOLTIP);
	}

	/**
	 * Sets the up color area label panel.
	 */
	private void setUpColorAreaLabelPanel() {
		colorAreaLabelPanel = new JPanel();
		colorAreaLabelPanel.add(colorAreaLabel);
	}

	/**
	 * Sets the up toolbar.
	 */
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

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
	}
}