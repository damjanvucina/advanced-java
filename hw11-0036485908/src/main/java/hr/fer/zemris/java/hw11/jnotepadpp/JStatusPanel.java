package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;

public class JStatusPanel extends JPanel implements SingleDocumentListener {
	
	public String lengthLabelDefault = "length: ";
	public String lnLabelDefault = "Ln: ";
	public String colLabelDefault = "Col: ";
	public String selLabelDefault = "Sel: ";

	private JLabel lengthLabel;
	private JLabel lnLabel;
	private JLabel colLabel;
	private JLabel selLabel;
	private JPanel editorInfoPanel;
	private Clock clock;
	private FormLocalizationProvider flp;

	public JStatusPanel(FormLocalizationProvider flp, LayoutManager layout) {
		setLayout(layout);

		this.flp = flp;
		lnLabel = new JLabel();
		colLabel = new JLabel();
		selLabel = new JLabel();
		editorInfoPanel = new JPanel();
	}

	
	public JLabel getLengthLabel() {
		return lengthLabel;
	}

	public JLabel getLnLabel() {
		return lnLabel;
	}

	public JLabel getColLabel() {
		return colLabel;
	}

	public JLabel getSelLabel() {
		return selLabel;
	}

	public void setLengthLabelDefault(String lengthLabelDefault) {
		this.lengthLabelDefault = lengthLabelDefault;
	}

	public void setLnLabelDefault(String lnLabelDefault) {
		this.lnLabelDefault = lnLabelDefault;
	}

	public void setColLabelDefault(String colLabelDefault) {
		this.colLabelDefault = colLabelDefault;
	}

	public void setSelLabelDefault(String selLabelDefault) {
		this.selLabelDefault = selLabelDefault;
	}

	private static final long serialVersionUID = 1L;

	public void turnOffClock() {
		clock.stop();
	}

	public void setUp() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));

		lengthLabel = new JLabel(lengthLabelDefault + "0");
		lengthLabel.setHorizontalAlignment(JLabel.LEFT);
		add(lengthLabel);

		editorInfoPanel = new JPanel();
		lnLabel = new JLabel(lnLabelDefault + "0");
		colLabel = new JLabel(colLabelDefault + "0");
		selLabel = new JLabel(selLabelDefault + "0");
		editorInfoPanel.add(lnLabel);
		editorInfoPanel.add(colLabel);
		editorInfoPanel.add(selLabel);
		add(editorInfoPanel);

		clock = new Clock();
		add(clock);
	}

	static class Clock extends JComponent {

		private static final long serialVersionUID = 1L;

		volatile String currentTime;
		volatile boolean stopRequested;
										
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		public Clock() {
			updateTime();

			Thread t = new Thread(() -> {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (Exception ex) {
					}
					if (stopRequested)
						break;
					SwingUtilities.invokeLater(() -> {
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}

		public void stop() {
			stopRequested = true;
		}

		private void updateTime() {
			currentTime = formatter.format(LocalDateTime.now());
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			Insets ins = getInsets();
			Dimension dim = getSize();
			Rectangle r = new Rectangle(ins.left, ins.top, dim.width - ins.left - ins.right,
					dim.height - ins.top - ins.bottom);
			if (isOpaque()) {
				g.setColor(getBackground());
				g.fillRect(r.x, r.y, r.width, r.height);
			}
			g.setColor(getForeground());

			FontMetrics fm = g.getFontMetrics();
			int w = fm.stringWidth(currentTime);
			int h = fm.getAscent();

			g.drawString(currentTime, r.x + (r.width - w) / 2, r.y + r.height - (r.height - h) / 2);
		}
	}

	@Override
	public void documentModifyStatusUpdated(SingleDocumentModel model) {
		DefaultSingleDocumentModel defaultModel = (DefaultSingleDocumentModel) model;

		updateCurrentLength(defaultModel);

		updateEditorInfo(defaultModel);

		repaint();
	}

	private void updateEditorInfo(DefaultSingleDocumentModel defaultModel) {
		if (defaultModel.getText() != null) {
			int lineNumber = defaultModel.getText().split("\n").length;
			lnLabel.setText(flp.getString("lnLabel") + String.valueOf(lineNumber));
			
			String caretLine = defaultModel.getText().substring(0, defaultModel.getDot());
			int caretLineNewLine = caretLine.lastIndexOf("\n");
			colLabel.setText(flp.getString("colLabel") + String.valueOf(defaultModel.getDot() - caretLineNewLine));

			int selectionLength = Math.abs(defaultModel.getDot() - defaultModel.getMark());
			selLabel.setText(flp.getString("selLabel") + String.valueOf(selectionLength));

		}
	}

	private void updateCurrentLength(DefaultSingleDocumentModel defaultModel) {
		int currentLength = defaultModel.getCurrentLength();
		lengthLabel.setText(flp.getString("lengthLabel") + String.valueOf(currentLength));
	}

	@Override
	public void documentFilePathUpdated(SingleDocumentModel model) {
	}
}
