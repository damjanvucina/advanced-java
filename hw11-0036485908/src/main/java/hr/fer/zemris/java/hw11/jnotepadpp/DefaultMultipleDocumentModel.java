package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

//@formatter:off
public class DefaultMultipleDocumentModel extends JTabbedPane 
										  implements MultipleDocumentModel,
										  			 Iterable<SingleDocumentModel> {
//@formatter:on

	public static final String UNTITLED = "Untitled";

	private static final long serialVersionUID = 1L;
	List<SingleDocumentModel> documents;
	List<MultipleDocumentListener> listeners;
	SingleDocumentModel currentDocument;

	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");

		documents.add(newDocument);
		notifyListeners(listener -> listener.documentAdded(newDocument));

		currentDocument = newDocument;
		notifyListeners(listener -> listener.currentDocumentChanged(documents.get(documents.size() - 2), newDocument));

		createNewTab(newDocument);

		return newDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Given path cannot be null");

		SingleDocumentModel oldDocument = currentDocument;
		SingleDocumentModel loadedDocument = acquireDocument(path);

		if (loadedDocument == null) {
			if (!Files.isReadable(path)) {
				JOptionPane.showMessageDialog(this, "Cannot read document" + path, "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}

			byte[] bytes;
			try {
				bytes = Files.readAllBytes(path);
			} catch (Exception r) {
				JOptionPane.showMessageDialog(this, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}

			loadedDocument = new DefaultSingleDocumentModel(path, new String(bytes, StandardCharsets.UTF_8));

			documents.add(loadedDocument);
			notifyListeners(listener -> listener.documentAdded(documents.get(documents.size() - 1)));

			createNewTab(loadedDocument);
		} else {
			setSelectedIndex(documents.indexOf(loadedDocument));
		}

		currentDocument = loadedDocument;
		notifyListeners(listener -> listener.currentDocumentChanged(oldDocument, currentDocument));
		return loadedDocument;
	}

	private void createNewTab(SingleDocumentModel document) {
		JTextArea tab = document.getTextComponent();
		
		String title = (document.getFilePath() == null) ? UNTITLED
				: String.valueOf(document.getFilePath().getFileName());

		JScrollPane scrollPane = new JScrollPane(tab);
		addTab(title, scrollPane);
		if(document.getFilePath() != null) {
			setToolTipTextAt(documents.indexOf(document), String.valueOf(document.getFilePath()));
		}
		
		setSelectedComponent(scrollPane);
	}

	private SingleDocumentModel acquireDocument(Path path) {
		for (SingleDocumentModel document : documents) {
			Path currentPath = document.getFilePath();
			if (currentPath != null && currentPath.equals(path)) {
				return document;
			}
		}

		return null;
	}

	private void notifyListeners(Consumer<MultipleDocumentListener> action) {
		for (MultipleDocumentListener listener : listeners) {
			action.accept(listener);
		}
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Objects.requireNonNull(model, "Given model cannot be null");

		if (acquireDocument(newPath) != null) {
			throw new IllegalArgumentException("Specified file is already opened.");
		}

		byte[] bytes = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		Path savePath = (newPath == null) ? model.getFilePath() : newPath;

		try {
			Files.write(savePath, bytes);
		} catch (Exception e) {
			System.out.println("Error saving file.");
		}

		if (newPath != null) {
			model.setFilePath(newPath);
			setToolTipTextAt(documents.indexOf(model), String.valueOf(model.getFilePath()));
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		Objects.requireNonNull(model, "Cannot close null document");

		int removedIndex = documents.indexOf(model);
		documents.remove(removedIndex);
		notifyListeners(listener -> listener.documentRemoved(model));

		removeTabAt(removedIndex);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l, "Multiple document listener cannot be null.");

		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l, "Multiple document listener cannot be null.");

		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new DocumentsIterator();
	}

	private class DocumentsIterator implements Iterator<SingleDocumentModel> {
		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < documents.size();
		}

		@Override
		public SingleDocumentModel next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more documents are available.");
			}

			return documents.get(index++);
		}
	}

}
