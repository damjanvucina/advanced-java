package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SetLanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ShowStatsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

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
	private SetLanguageAction setCroatian;
	private SetLanguageAction setEnglish;
	private SetLanguageAction setGerman;

	private FormLocalizationProvider flp;

	private DefaultMultipleDocumentModel model;
	private JPanel panel;

	private JStatusPanel statusPanel;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu helpMenu;
	private JMenu languageMenu;

	private int editorLength;
	private Map<AbstractAction, String> actionMappings;

	public JNotepadPP() {
		setSize(600, 600);
		setLocation(50, 50);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initGui();

		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		addLocalizationListener();
		
		actionMappings = new HashMap<>();
		

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitApplicationAction.actionPerformed(null);
			}
		});
	}

	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		panel = new JPanel(new BorderLayout());
		statusPanel = new JStatusPanel(new GridLayout(1, 3));

		cp.add(panel, BorderLayout.CENTER);
		cp.add(statusPanel, BorderLayout.SOUTH);

		model = new DefaultMultipleDocumentModel();
		panel.add(model, BorderLayout.CENTER);

		initializeActions();
		setUpActions();

		createMenus();
		createActions();
		createToolbars();
		statusPanel.setUp();

		model.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int indexOfSelectedTab = ((DefaultMultipleDocumentModel) e.getSource()).getSelectedIndex();
				model.getCurrentDocument().addSingleDocumentListener(statusPanel);
				String title = null;
				if (((DefaultMultipleDocumentModel) model).getNumberOfDocuments() > 0) {
					Path filePath = model.getDocument(indexOfSelectedTab).getFilePath();
					title = (filePath == null) ? UNTITLED : String.valueOf(filePath);

				} else {
					title = UNTITLED;
					editorLength = 0;
				}

				setTitle(title + TITLE_SEPARATOR + DEFAULT_TITLE);

				if (model.getNumberOfDocuments() > 0) {
					statusPanel.documentModifyStatusUpdated(model.getDocument(indexOfSelectedTab));
				}
			}
		});

		initializeActionMappings();
		availableActionValidator.actionPerformed(null);
	}



	private void addLocalizationListener() {
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				createNewDocumentAction.putValue(Action.NAME, flp.getString("menuItemNew"));
				openDocumentAction.putValue(Action.NAME, flp.getString("menuItemOpen"));
				saveDocumentAction.putValue(Action.NAME, flp.getString("menuItemSave"));
				saveAsDocumentAction.putValue(Action.NAME, flp.getString("menuItemSaveAs"));
				closeTabAction.putValue(Action.NAME, flp.getString("menuItemClose"));
				exitApplicationAction.putValue(Action.NAME, flp.getString("menuItemExit"));
				copyTextAction.putValue(Action.NAME, flp.getString("menuItemCopy"));
				pasteTextAction.putValue(Action.NAME, flp.getString("menuItemPaste"));
				cutTextAction.putValue(Action.NAME, flp.getString("menuItemCut"));
				showStatsAction.putValue(Action.NAME, flp.getString("menuItemStats"));
				setCroatian.putValue(Action.NAME, flp.getString("menuItemCroatian"));
				setEnglish.putValue(Action.NAME, flp.getString("menuItemEnglish"));
				setGerman.putValue(Action.NAME, flp.getString("menuItemGerman"));

				fileMenu.setText(flp.getString("menuFile"));
			}
		});
	}
			
	private void initializeActionMappings() {
		actionMappings.put(createNewDocumentAction, "menuItemNew");
		actionMappings.put(openDocumentAction, "menuItemOpen");
		actionMappings.put(saveDocumentAction, "menuItemSave");
		actionMappings.put(saveAsDocumentAction, "menuItemSaveAs");
		actionMappings.put(closeTabAction, "menuItemClose");
		actionMappings.put(exitApplicationAction, "menuItemExit");
		actionMappings.put(copyTextAction, "menuItemCopy");
		actionMappings.put(pasteTextAction, "menuItemPaste");
		actionMappings.put(cutTextAction, "menuItemCut");
		actionMappings.put(showStatsAction, "menuItemStats");
		actionMappings.put(setCroatian, "menuItemCroatian");
		actionMappings.put(setEnglish, "menuItemEnglish");
		actionMappings.put(setGerman, "menuItemGerman");
	}
	
	private void setUpActions() {
		createNewDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createNewDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Creates a new blank file.");
		createNewDocumentAction.putValue(Action.SMALL_ICON, acquireIcon("new.png"));
		
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Opens existing file.");
		openDocumentAction.putValue(Action.SMALL_ICON, acquireIcon("open.png"));


		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Saves existing file.");
		saveDocumentAction.putValue(Action.SMALL_ICON, acquireIcon("save.png"));

		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Saves file to new location.");
		saveAsDocumentAction.putValue(Action.SMALL_ICON, acquireIcon("saveas.png"));

		closeTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeTabAction.putValue(Action.SHORT_DESCRIPTION, "Closes current tab.");
		closeTabAction.putValue(Action.SMALL_ICON, acquireIcon("close.png"));

		exitApplicationAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitApplicationAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitApplicationAction.putValue(Action.SHORT_DESCRIPTION, "Closes application.");
		exitApplicationAction.putValue(Action.SMALL_ICON, acquireIcon("exit.png"));

		copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyTextAction.putValue(Action.SHORT_DESCRIPTION, "Copies current selection.");
		copyTextAction.putValue(Action.SMALL_ICON, acquireIcon("copy.png"));

		pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteTextAction.putValue(Action.SHORT_DESCRIPTION, "Pastes from clipboard.");
		pasteTextAction.putValue(Action.SMALL_ICON, acquireIcon("paste.png"));

		cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		cutTextAction.putValue(Action.SHORT_DESCRIPTION, "Cuts current selection.");
		cutTextAction.putValue(Action.SMALL_ICON, acquireIcon("cut.png"));

		showStatsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		showStatsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		showStatsAction.putValue(Action.SHORT_DESCRIPTION, "Shows current document statistics.");
		showStatsAction.putValue(Action.SMALL_ICON, acquireIcon("stats.png"));

		setCroatian.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt C"));
		setCroatian.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		setCroatian.putValue(Action.SHORT_DESCRIPTION, "Sets croatian language");
		setCroatian.putValue(Action.SMALL_ICON, acquireIcon("croatian.png"));

		setEnglish.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt E"));
		setEnglish.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		setEnglish.putValue(Action.SHORT_DESCRIPTION, "Sets english language");
		setEnglish.putValue(Action.SMALL_ICON, acquireIcon("english.png"));

		setGerman.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt G"));
		setGerman.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		setGerman.putValue(Action.SHORT_DESCRIPTION, "Sets german language");
		setGerman.putValue(Action.SMALL_ICON, acquireIcon("deutsch.png"));
		
	}

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

	public int getEditorLength() {
		return editorLength;
	}

	public void setEditorLength(int editorLength) {
		this.editorLength = editorLength;
	}

	public JStatusPanel getStatusPanel() {
		return statusPanel;
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
		setCroatian = new SetLanguageAction("hr");
		setEnglish = new SetLanguageAction("en");
		setGerman = new SetLanguageAction("de");
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
		setCroatian.putValue(Action.NAME, "Croatian");
		setEnglish.putValue(Action.NAME, "English");
		setGerman.putValue(Action.NAME, "German");
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(createNewDocumentAction);
		fileMenu.add(openDocumentAction);
		fileMenu.addSeparator();
		fileMenu.add(saveDocumentAction);
		fileMenu.add(saveAsDocumentAction);
		fileMenu.addSeparator();
		fileMenu.add(closeTabAction);
		fileMenu.add(exitApplicationAction);

		editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		editMenu.add(copyTextAction);
		editMenu.add(pasteTextAction);
		editMenu.add(cutTextAction);

		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		helpMenu.add(showStatsAction);

		languageMenu = new JMenu("Languages");
		menuBar.add(languageMenu);
		languageMenu.add(setCroatian);
		languageMenu.add(setEnglish);
		languageMenu.add(setGerman);

		this.setJMenuBar(menuBar);
	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);

		toolBar.add(createNewDocumentAction);
		toolBar.add(openDocumentAction);
		toolBar.addSeparator();
		toolBar.add(saveDocumentAction);
		toolBar.add(saveAsDocumentAction);
		toolBar.addSeparator();
		toolBar.add(closeTabAction);
		toolBar.add(exitApplicationAction);
		toolBar.addSeparator();
		toolBar.add(copyTextAction);
		toolBar.add(pasteTextAction);
		toolBar.add(cutTextAction);
		toolBar.addSeparator();
		toolBar.add(showStatsAction);
		panel.add(toolBar, BorderLayout.NORTH);
	}

	public static class ActionLanguagePack {
		String name;
		String keyStroke;
		String keyEvent;
		String description;
		
		public ActionLanguagePack(String name, String keyStroke, String keyEvent, String description) {
			this.name = name;
			this.keyStroke = keyStroke;
			this.keyEvent = keyEvent;
			this.description = description;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}
