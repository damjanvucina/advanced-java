package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JTextArea;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path filePath;
	private String text;
	private JTextArea textComponent;
	private List<SingleDocumentListener> listeners;
	private boolean modified;

	public DefaultSingleDocumentModel(Path path, String text) {
		this.filePath = path;
		this.text = text;

		listeners = new ArrayList<>();

		textComponent = new JTextArea(text);
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
