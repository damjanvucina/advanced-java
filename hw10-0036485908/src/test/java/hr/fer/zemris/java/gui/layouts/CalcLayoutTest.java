package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;

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
	
	@Test
	public void testAddingComponentRowTooSmall() {
		layout.addLayoutComponent(testComponent, new RCPosition(0, 1));
	}
}
