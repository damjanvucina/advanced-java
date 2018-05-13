package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalcLayoutTest {
	public static final int GAP = 2;

	CalcLayout layout;
	Component testComponent;

	@Before
	public void setUp() {
		layout = new CalcLayout(GAP);
		testComponent = new Component() {
			private static final long serialVersionUID = 1L;
		};
	}

	@Test(expected = CalcLayoutException.class)
	public void testAddingComponentRowTooSmall() {
		layout.addLayoutComponent(testComponent, new RCPosition(0, 1));
	}

	@Test(expected = CalcLayoutException.class)
	public void testAddingComponentRowTooBig() {
		layout.addLayoutComponent(testComponent, new RCPosition(6, 1));
	}

	@Test(expected = CalcLayoutException.class)
	public void testAddingComponentColTooSmall() {
		layout.addLayoutComponent(testComponent, new RCPosition(1, 0));
	}

	@Test(expected = CalcLayoutException.class)
	public void testAddingComponentColTooBig() {
		layout.addLayoutComponent(testComponent, new RCPosition(1, 8));
	}

	@Test
	public void testAddingComponentToCalcScreen1() {
		layout.addLayoutComponent(testComponent, new RCPosition(1, 1));
	}

	@Test(expected = CalcLayoutException.class)
	public void testAddingComponentToCalcScreen2() {
		layout.addLayoutComponent(testComponent, new RCPosition(1, 2));
	}

	@Test(expected = CalcLayoutException.class)
	public void testAddingComponentToCalcScreen3() {
		layout.addLayoutComponent(testComponent, new RCPosition(1, 3));
	}

	@Test(expected = CalcLayoutException.class)
	public void testAddingComponentToCalcScreen4() {
		layout.addLayoutComponent(testComponent, new RCPosition(1, 4));
	}

	@Test(expected = CalcLayoutException.class)
	public void testAddingComponentToCalcScreen5() {
		layout.addLayoutComponent(testComponent, new RCPosition(1, 5));
	}

	@Test
	public void testAddingComponentToCalcScreen6() {
		layout.addLayoutComponent(testComponent, new RCPosition(1, 6));
	}

	@Test(expected = CalcLayoutException.class)
	public void testOverlappingComponents() {
		layout.addLayoutComponent(testComponent, new RCPosition(2, 6));
		layout.addLayoutComponent(testComponent, new RCPosition(2, 6));
	}

	@Test
	public void templateTest1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		Assert.assertEquals(152, dim.width);
		Assert.assertEquals(158, dim.height);
	}

	@Test
	public void templateTest2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		Assert.assertEquals(768, dim.width);
		Assert.assertEquals(158, dim.height);
	}

}
