package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path filePath;
	private String text;
	private JTextArea textComponent;
	private List<SingleDocumentListener> listeners;
	private boolean modified;
	
	private int currentLength;
	private int dot;
	private int mark;
	private int selectionLength;
	private int offset;

	public DefaultSingleDocumentModel(Path path, String text) {
		this.filePath = path;
		this.text = text;
		this.currentLength = (text == null) ? 0 : text.length();

		listeners = new ArrayList<>();

		textComponent = new JTextArea(text);
		addDocumentAndCaretListeners(textComponent);

	}

	private void addDocumentAndCaretListeners(JTextArea textComponent) {
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateEditorLength(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateEditorLength(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateEditorLength(e);
			}

			private void updateEditorLength(DocumentEvent e) {
				currentLength = e.getDocument().getLength();
				try {
					text = e.getDocument().getText(0, currentLength);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				notifyListeners(listener -> listener.documentModifyStatusUpdated(DefaultSingleDocumentModel.this));
			}
		});
		
		textComponent.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				dot = e.getDot();
				mark = e.getMark();
				selectionLength = Math.abs(e.getDot() - e.getMark());
				offset = Math.min(e.getDot(), e.getMark());
				
				notifyListeners(listener -> listener.documentModifyStatusUpdated(DefaultSingleDocumentModel.this));
			}
		});
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getDot() {
		return dot;
	}

	public int getMark() {
		return mark;
	}

	public int getSelectionLength() {
		return selectionLength;
	}

	public void setCurrentLength(int currentLength) {
		this.currentLength = currentLength;
	}

	public void setDot(int dot) {
		this.dot = dot;
	}

	public void setSelectionLength(int selectionLength) {
		this.selectionLength = selectionLength;
	}

	public String getText() {
		return text;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path filePath) {
		Objects.requireNonNull(filePath, "Path cannot be set to null");
		this.filePath = filePath;

		notifyListeners(listener -> listener.documentFilePathUpdated(this));
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public boolean isModified() {
		return modified == true;
	}

	public int getCurrentLength() {
		return currentLength;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;

		notifyListeners(listener -> listener.documentModifyStatusUpdated(this));
	}

	private void notifyListeners(Consumer<SingleDocumentListener> action) {
		for (SingleDocumentListener listener : listeners) {
			action.accept(listener);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l, "Cannot add null listener to the collection.");

		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener to the collection.");

		listeners.remove(l);
	}

}
