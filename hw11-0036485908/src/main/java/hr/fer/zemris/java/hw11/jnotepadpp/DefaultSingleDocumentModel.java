package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path filePath;
	private String text;
	private JTextArea textComponent;
	private List<SingleDocumentListener> listeners;
	private boolean modified;
	private int currentLength;

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
				notifyListeners(listener -> listener.documentModifyStatusUpdated(DefaultSingleDocumentModel.this));
			}
		});
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
