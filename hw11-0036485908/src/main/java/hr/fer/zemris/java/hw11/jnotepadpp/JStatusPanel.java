package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JStatusPanel extends JPanel implements SingleDocumentListener{
	public static final String LENGTH_LABEL_DEFAULT = "length: ";
	public static final String LN_LABEL_DEFAULT = "Ln: ";
	public static final String COL_LABEL_DEFAULT = "Col: ";
	public static final String SEL_LABEL_DEFAULT = "Sel: ";

	private JLabel lengthLabel;
	private JLabel lnLabel;
	private JLabel colLabel;
	private JLabel selLabel;
	private JPanel editorInfoPanel;
	private Clock clock;

	public JStatusPanel(LayoutManager layout) {
		setLayout(layout);

		lnLabel = new JLabel();
		colLabel = new JLabel();
		selLabel = new JLabel();
		editorInfoPanel = new JPanel();
	}

	private static final long serialVersionUID = 1L;
	
	public void turnOffClock() {
		clock.stop();
	}

	public void setUp() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));

		lengthLabel = new JLabel(LENGTH_LABEL_DEFAULT + "0");
		add(lengthLabel, LEFT_ALIGNMENT);

		editorInfoPanel = new JPanel();
		lnLabel = new JLabel(LN_LABEL_DEFAULT + "0");
		colLabel = new JLabel(COL_LABEL_DEFAULT + "0");
		selLabel = new JLabel(SEL_LABEL_DEFAULT + "0");
		editorInfoPanel.add(lnLabel);
		editorInfoPanel.add(colLabel);
		editorInfoPanel.add(selLabel);

		add(editorInfoPanel, CENTER_ALIGNMENT);

		clock = new Clock();
		add(clock, RIGHT_ALIGNMENT);
	}
	
	static class Clock extends JComponent {

		private static final long serialVersionUID = 1L;

		volatile String currentTime;
		volatile boolean stopRequested;// kad proces ugasis, ovo ide u true, pa se ugase demonske drtve koje prestanu
										// radit iako je prozor ugasen, pa ni edt vise nema posla
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
		int currentLength = ((DefaultSingleDocumentModel) model).getCurrentLength();
		lengthLabel.setText(LENGTH_LABEL_DEFAULT + String.valueOf(currentLength));
		repaint();
	}

	@Override
	public void documentFilePathUpdated(SingleDocumentModel model) {
	}
}
