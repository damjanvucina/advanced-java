package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.AvailableActionValidator;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseTabAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CreateNewDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitApplicationAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ShowStatsAction;

public class JNotepadPP extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_TITLE = "JNotepad++";
	public static final String UNTITLED = "Untitled";
	public static final String TITLE_SEPARATOR = " - ";

	private OpenDocumentAction openDocumentAction;
	private CreateNewDocumentAction createNewDocumentAction;
	private SaveDocumentAction saveDocumentAction;
	private SaveAsDocumentAction saveAsDocumentAction;
	private CloseTabAction closeTabAction;
	private ExitApplicationAction exitApplicationAction;
	private CopyTextAction copyTextAction;
	private PasteTextAction pasteTextAction;
	private CutTextAction cutTextAction;
	private ShowStatsAction showStatsAction;
	private AvailableActionValidator availableActionValidator;
	
	private DefaultMultipleDocumentModel model;

	public JNotepadPP() {
		setSize(600, 600);
		setLocation(50, 50);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		initGui();
	}

	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new BorderLayout());

		cp.add(panel, BorderLayout.CENTER);

		model = new DefaultMultipleDocumentModel();
		panel.add(model, BorderLayout.CENTER);

		initializeActions();
		setUpActions();

		createMenus(model);

		createActions();
		
		availableActionValidator.actionPerformed(null);
	}
	
	private void setUpActions() {
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Opens existing file.");
		
		createNewDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createNewDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Creates a new blank file.");
		
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Saves existing file.");
		
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Saves file to new location.");
		
		closeTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeTabAction.putValue(Action.SHORT_DESCRIPTION, "Closes current tab.");
		
		exitApplicationAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitApplicationAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitApplicationAction.putValue(Action.SHORT_DESCRIPTION, "Closes application.");
		
		copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyTextAction.putValue(Action.SHORT_DESCRIPTION, "Copies current selection.");
		
		pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteTextAction.putValue(Action.SHORT_DESCRIPTION, "Pastes from clipboard.");
		
		cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		cutTextAction.putValue(Action.SHORT_DESCRIPTION, "Cuts current selection.");
		
		showStatsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		showStatsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		showStatsAction.putValue(Action.SHORT_DESCRIPTION, "Shows current document statistics.");
	}

	public DefaultMultipleDocumentModel getModel() {
		return model;
	}

	public AvailableActionValidator getAvailableActionValidator() {
		return availableActionValidator;
	}
	
	public OpenDocumentAction getOpenDocumentAction() {
		return openDocumentAction;
	}

	public CreateNewDocumentAction getCreateNewDocumentAction() {
		return createNewDocumentAction;
	}

	public SaveDocumentAction getSaveDocumentAction() {
		return saveDocumentAction;
	}

	public SaveAsDocumentAction getSaveAsDocumentAction() {
		return saveAsDocumentAction;
	}

	public CloseTabAction getCloseTabAction() {
		return closeTabAction;
	}

	public ExitApplicationAction getExitApplicationAction() {
		return exitApplicationAction;
	}

	public PasteTextAction getPasteTextAction() {
		return pasteTextAction;
	}

	public CutTextAction getCutTextAction() {
		return cutTextAction;
	}

	public ShowStatsAction getShowStatsAction() {
		return showStatsAction;
	}

	public CopyTextAction getCopyTextAction() {
		return copyTextAction;
	}

	private void initializeActions() {
		openDocumentAction = new OpenDocumentAction(this, model);
		createNewDocumentAction = new CreateNewDocumentAction(this, model);
		saveDocumentAction = new SaveDocumentAction(this, model);
		saveAsDocumentAction = new SaveAsDocumentAction(this, model);
		closeTabAction = new CloseTabAction(this, model);
		exitApplicationAction = new ExitApplicationAction(this, model);
		copyTextAction = new CopyTextAction(this, model);
		pasteTextAction = new PasteTextAction(this, model);
		cutTextAction = new CutTextAction(this, model);
		showStatsAction = new ShowStatsAction(this, model);
		availableActionValidator = new AvailableActionValidator(this, model);
	}

	private void createActions() {
		createNewDocumentAction.putValue(Action.NAME, "New");
		openDocumentAction.putValue(Action.NAME, "Open");
		saveDocumentAction.putValue(Action.NAME, "Save");
		saveAsDocumentAction.putValue(Action.NAME, "Save As");
		closeTabAction.putValue(Action.NAME, "Close");
		exitApplicationAction.putValue(Action.NAME, "Exit");
		copyTextAction.putValue(Action.NAME, "Copy");
		pasteTextAction.putValue(Action.NAME, "Paste");
		cutTextAction.putValue(Action.NAME, "Cut");
		showStatsAction.putValue(Action.NAME, "Show Stats");
	}

	private void createMenus(MultipleDocumentModel model) {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(createNewDocumentAction);
		fileMenu.add(openDocumentAction);
		fileMenu.addSeparator();
		fileMenu.add(saveDocumentAction);
		fileMenu.add(saveAsDocumentAction);
		fileMenu.addSeparator();
		fileMenu.add(closeTabAction);
		fileMenu.add(exitApplicationAction);

		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		editMenu.add(copyTextAction);
		editMenu.add(pasteTextAction);
		editMenu.add(cutTextAction);

		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		helpMenu.add(showStatsAction);

		this.setJMenuBar(menuBar);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}
