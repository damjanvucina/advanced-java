package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import static java.lang.Math.max;

public class CalcLayout implements LayoutManager2 {
	public static final int NUM_OF_ROWS = 5;
	public static final int NUM_OF_COLS = 7;
	public static final int NUM_OF_ROW_GAPS = NUM_OF_ROWS - 1;
	public static final int NUM_OF_COL_GAPS = NUM_OF_COLS - 1;
	public static final int DEFAULT_GAP = 0;
	public static final String LAYOUT_PREFFERED = "preffered";
	public static final String LAYOUT_MINIMUM = "minimum";
	public static final String LAYOUT_MAXIMUM = "maximum";
	public static final RCPosition CALC_SCREEEN = new RCPosition(1, 1);

	private int gap;
	Map<Component, RCPosition> components;

	public CalcLayout(int gap) {
		this.gap = gap;
		components = new HashMap<>();
	}

	public CalcLayout() {
		this(DEFAULT_GAP);
	}

	@Override
	public void addLayoutComponent(String s, Component component) {
	}

	@Override
	public void layoutContainer(Container container) {
	}

	@Override
	public Dimension minimumLayoutSize(Container container) {
		Dimension componentSize = calculateComponentSize((component) -> component.getMinimumSize());
		return calculateLayoutSize(container, componentSize);
	}
	

	@Override
	public Dimension maximumLayoutSize(Container container) {
		Dimension componentSize = calculateComponentSize((component) -> component.getMaximumSize());
		return calculateLayoutSize(container, componentSize);
	}
	
	@Override
	public Dimension preferredLayoutSize(Container container) {
		Dimension componentSize = calculateComponentSize((component) -> component.getPreferredSize());
		return calculateLayoutSize(container, componentSize);
	}

	private Dimension calculateLayoutSize(Container container, Dimension componentSize) {
		int componentWidth = componentSize.width;
		int componentHeight = componentSize.height;
		int insetsWidth = container.getInsets().left + container.getInsets().right;
		int insetsHeight = container.getInsets().top + container.getInsets().bottom;

		//@formatter:off
		int layoutWidth = NUM_OF_COLS * componentWidth +
					  NUM_OF_COL_GAPS * gap + 
					  insetsWidth;
		
		int layoutHeight = NUM_OF_ROWS * componentHeight + 
					   NUM_OF_ROW_GAPS * gap +
					   insetsHeight;
		//@formatter:on
		
		return new Dimension(layoutWidth, layoutHeight);
	}

	private Dimension calculateComponentSize(Function<Component, Dimension> action) {
		int currentWidth = 0;
		int currentHeight = 0;
		Dimension currentSize;

		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			currentSize = action.apply(entry.getKey());

			if (currentSize == null) {
				continue;
			}

			currentHeight = max(currentHeight, currentSize.height);
			currentWidth = max(currentWidth, (isCalcScreen(entry.getValue())) ? currentWidth : currentSize.width);
		}

		return new Dimension(currentWidth, currentHeight);
	}

	private boolean isCalcScreen(RCPosition position) {
		return CALC_SCREEEN.equals(position);
	}



	@Override
	public void removeLayoutComponent(Component component) {
	}

	@Override
	public void addLayoutComponent(Component component, Object obj) {
	}

	@Override
	public float getLayoutAlignmentX(Container container) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container container) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container container) {
	}


}
