package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseTabAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CreateNewDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitApplicationAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;

public class JNotepadPP extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private OpenDocumentAction openDocumentAction;
	private CreateNewDocumentAction createNewDocumentAction;
	private SaveDocumentAction saveDocumentAction;
	private SaveAsDocumentAction saveAsDocumentAction;
	private CloseTabAction closeTabAction;
	private ExitApplicationAction exitApplicationAction;
	private CopyTextAction copyTextAction;
	private PasteTextAction pasteTextAction;
	
	private DefaultMultipleDocumentModel model;

	public DefaultMultipleDocumentModel getModel() {
		return model;
	}

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
		
		createMenus(model);
		
		createActions();
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

		this.setJMenuBar(menuBar);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}
