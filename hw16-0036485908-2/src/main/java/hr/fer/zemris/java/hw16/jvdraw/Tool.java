package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

// TODO: Auto-generated Javadoc
/**
 * The Interface Tool.
 */
public interface Tool {
	
	/**
	 * Mouse pressed.
	 *
	 * @param e the e
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Mouse released.
	 *
	 * @param e the e
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Mouse clicked.
	 *
	 * @param e the e
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Mouse moved.
	 *
	 * @param e the e
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Mouse dragged.
	 *
	 * @param e the e
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paint.
	 *
	 * @param g2d the g 2 d
	 */
	public void paint(Graphics2D g2d);
}
