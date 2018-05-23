package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.AvailableActionValidator;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseTabAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CreateNewDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitApplicationAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.LineSortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SetLanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ShowStatsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

import static java.awt.event.KeyEvent.getExtendedKeyCodeForChar;

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
	private ChangeCaseAction toUpperCaseAction;
	private ChangeCaseAction toLowerCaseAction;
	private ChangeCaseAction invertCaseAction;
	private LineSortAction sortAscendingAction;
	private LineSortAction sortDescendingAction;
	private LineSortAction uniqueAction;
	
	private FormLocalizationProvider flp;

	private DefaultMultipleDocumentModel model;
	private JPanel panel;

	private JStatusPanel statusPanel;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu helpMenu;
	private JMenu languageMenu;
	private JMenu toolsMenu;
	private JMenu changeCaseMenu;
	private JMenu sortMenu;

	private int editorLength;
	
	private Map<AbstractAction, String> actionMappings;
	private Map<JMenu, String> menuMappings;
	private Map<JLabel, String> labelMappings;

	public JNotepadPP() {
		setSize(600, 600);
		setLocation(50, 50);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		actionMappings = new HashMap<>();
		menuMappings = new HashMap<>();
		labelMappings = new HashMap<>();
		initGui();

		addLocalizationListener();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitApplicationAction.actionPerformed(null);
			}
		});
		
		flp.fire();
	}

	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		panel = new JPanel(new BorderLayout());
		statusPanel = new JStatusPanel(flp, new GridLayout(1, 3));

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
				model.getCurrentDocument().addSingleDocumentListener(toUpperCaseAction);
				model.getCurrentDocument().addSingleDocumentListener(toLowerCaseAction);
				model.getCurrentDocument().addSingleDocumentListener(invertCaseAction);
				model.getCurrentDocument().addSingleDocumentListener(sortAscendingAction);
				model.getCurrentDocument().addSingleDocumentListener(sortDescendingAction);
				model.getCurrentDocument().addSingleDocumentListener(uniqueAction);
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
		initializeMenuMappings();
		initializeLabelMappings();
		
		availableActionValidator.actionPerformed(null);
	}

	private void addLocalizationListener() {
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				
				for(Map.Entry<AbstractAction, String> entry : actionMappings.entrySet()) {
					AbstractAction action = entry.getKey();
					String name = entry.getValue();
					
					action.putValue(Action.NAME, flp.getString(name));
					
					action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(flp.getString(name + "Accelerator")));
					
					char c = flp.getString(name + "Mnemonic").charAt(0);
					action.putValue(Action.MNEMONIC_KEY, getExtendedKeyCodeForChar(c));
					
					action.putValue(Action.SHORT_DESCRIPTION, flp.getString(name + "Desc"));
				}
				
				for(Map.Entry<JMenu, String> entry : menuMappings.entrySet()) {
					JMenu menu = entry.getKey();
					String name = entry.getValue();
					
					menu.setText(flp.getString(name));
				}
				
				for(Map.Entry<JLabel, String> entry : labelMappings.entrySet()) {
					JLabel label = entry.getKey();
					String name = entry.getValue();
					
					label.setText(flp.getString(name));
				}
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
		actionMappings.put(toUpperCaseAction, "menuItemUppercase");
		actionMappings.put(toLowerCaseAction, "menuItemLowercase");
		actionMappings.put(invertCaseAction, "menuItemInvertCase");
		actionMappings.put(sortAscendingAction, "menuItemAscending");
		actionMappings.put(sortDescendingAction, "menuItemDescending");
		actionMappings.put(uniqueAction, "menuItemUnique");
	}
	
	private void initializeMenuMappings() {
		menuMappings.put(fileMenu, "menuFile");
		menuMappings.put(editMenu, "menuEdit");
		menuMappings.put(helpMenu, "menuHelp");
		menuMappings.put(fileMenu, "menuFile");
		menuMappings.put(languageMenu, "menuLanguage");
		menuMappings.put(toolsMenu, "menuTools");
		menuMappings.put(changeCaseMenu, "menuChangeCase");
		menuMappings.put(sortMenu, "menuSort");
	}
	
	private void initializeLabelMappings() {
		labelMappings.put(statusPanel.getLengthLabel(), "lengthLabel");
		labelMappings.put(statusPanel.getLnLabel(), "lnLabel");
		labelMappings.put(statusPanel.getColLabel(), "colLabel");
		labelMappings.put(statusPanel.getSelLabel(), "selLabel");
	}
		
	private void setUpActions() {
		createNewDocumentAction.putValue(Action.SMALL_ICON, acquireIcon("new.png"));
		openDocumentAction.putValue(Action.SMALL_ICON, acquireIcon("open.png"));
		saveDocumentAction.putValue(Action.SMALL_ICON, acquireIcon("save.png"));
		saveAsDocumentAction.putValue(Action.SMALL_ICON, acquireIcon("saveas.png"));
		closeTabAction.putValue(Action.SMALL_ICON, acquireIcon("close.png"));
		exitApplicationAction.putValue(Action.SMALL_ICON, acquireIcon("exit.png"));
		copyTextAction.putValue(Action.SMALL_ICON, acquireIcon("copy.png"));
		pasteTextAction.putValue(Action.SMALL_ICON, acquireIcon("paste.png"));
		cutTextAction.putValue(Action.SMALL_ICON, acquireIcon("cut.png"));
		showStatsAction.putValue(Action.SMALL_ICON, acquireIcon("stats.png"));
		setCroatian.putValue(Action.SMALL_ICON, acquireIcon("croatian.png"));
		setEnglish.putValue(Action.SMALL_ICON, acquireIcon("english.png"));
		setGerman.putValue(Action.SMALL_ICON, acquireIcon("deutsch.png"));
		toUpperCaseAction.putValue(Action.SMALL_ICON, acquireIcon("uppercase.png"));
		toLowerCaseAction.putValue(Action.SMALL_ICON, acquireIcon("lowercase.png"));
		invertCaseAction.putValue(Action.SMALL_ICON, acquireIcon("invertcase.png"));
		sortAscendingAction.putValue(Action.SMALL_ICON, acquireIcon("ascending.png"));
		sortDescendingAction.putValue(Action.SMALL_ICON, acquireIcon("descending.png"));
		uniqueAction.putValue(Action.SMALL_ICON, acquireIcon("unique.png"));
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
	
	public ChangeCaseAction getToUpperCase() {
		return toUpperCaseAction;
	}

	public ChangeCaseAction getToLowerCase() {
		return toLowerCaseAction;
	}

	public ChangeCaseAction getInvertCase() {
		return invertCaseAction;
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
		showStatsAction = new ShowStatsAction(this, model, flp);
		availableActionValidator = new AvailableActionValidator(this, model);
		setCroatian = new SetLanguageAction("hr");
		setEnglish = new SetLanguageAction("en");
		setGerman = new SetLanguageAction("de");
		toUpperCaseAction = new ChangeCaseAction(this, model, c -> Character.toUpperCase(c));
		toLowerCaseAction = new ChangeCaseAction(this, model, c -> Character.toLowerCase(c));
		invertCaseAction = new ChangeCaseAction(this, model, c ->  {
			if (Character.isUpperCase(c)){
				return Character.toLowerCase(c);
			} else {
				return Character.toUpperCase(c);
			}
		});
		sortAscendingAction = new LineSortAction(flp, model, "ascending");
		sortDescendingAction = new LineSortAction(flp, model, "descending");
		uniqueAction = new LineSortAction(flp, model, "unique");
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
		toUpperCaseAction.putValue(Action.NAME, "To Upper Case");
		toLowerCaseAction.putValue(Action.NAME, "To Lower Case");
		invertCaseAction.putValue(Action.NAME, "Invert Case");
		
		toUpperCaseAction.setEnabled(false);
		toLowerCaseAction.setEnabled(false);
		invertCaseAction.setEnabled(false);
		
		sortAscendingAction.putValue(Action.NAME, "Ascending");
		sortDescendingAction.putValue(Action.NAME, "Descending");
		uniqueAction.putValue(Action.NAME, "Unique");
		
		sortAscendingAction.setEnabled(false);
		sortDescendingAction.setEnabled(false);
		uniqueAction.setEnabled(false);
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
		
		languageMenu = new JMenu("Languages");
		menuBar.add(languageMenu);
		languageMenu.add(setCroatian);
		languageMenu.add(setEnglish);
		languageMenu.add(setGerman);
		
		toolsMenu = new JMenu("Tools");
		menuBar.add(toolsMenu);
		changeCaseMenu = new JMenu("Change Case");
		toolsMenu.add(changeCaseMenu);
		changeCaseMenu.add(toUpperCaseAction);
		changeCaseMenu.add(toLowerCaseAction);
		changeCaseMenu.add(invertCaseAction);
		
		sortMenu = new JMenu("Sort");
		toolsMenu.add(sortMenu);
		sortMenu.add(sortAscendingAction);
		sortMenu.add(sortDescendingAction);
		toolsMenu.add(uniqueAction);
		
		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		helpMenu.add(showStatsAction);

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
		toolBar.add(toUpperCaseAction);
		toolBar.add(toLowerCaseAction);
		toolBar.add(invertCaseAction);
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
