package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class FilledCircleEditor.
 */
public class FilledCircleEditor extends CircleEditor {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The filled circle. */
	private FilledCircle filledCircle;
	
	/** The bg color. */
	private JTextField bgColor;

	/**
	 * Instantiates a new filled circle editor.
	 *
	 * @param filledCircle the filled circle
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		super(filledCircle);
		this.filledCircle = filledCircle;
		
		initializeFilledCircle();
		setUpFilledCircle();
	}
	
	/**
	 * Initialize filled circle.
	 */
	protected void initializeFilledCircle() {
		bgColor = new JTextField(colorToHexString(filledCircle.getBgColor()));
	}
	
	/**
	 * Sets the up filled circle.
	 */
	protected void setUpFilledCircle() {
		JPanel bgColorPanel = new JPanel();
		bgColorPanel.setBorder(BorderFactory.createTitledBorder("Background"));

		bgColorPanel.add(new JLabel("color:"));
		bgColorPanel.add(bgColor);
		
		add(bgColorPanel);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.CircleEditor#checkEditing()
	 */
	@Override
	public void checkEditing() {
		super.checkEditing();
		
		validateColorValue(bgColor.getText());
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.CircleEditor#acceptEditing()
	 */
	@Override
	public void acceptEditing() {
		filledCircle.setBgColor(Color.decode(bgColor.getText()));
		
		super.acceptEditing();
	}

}
