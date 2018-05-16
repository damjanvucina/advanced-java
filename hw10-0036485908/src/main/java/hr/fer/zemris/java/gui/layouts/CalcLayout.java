package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
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

	public static final int MIN_ROW = 1;
	public static final int MIN_COL = 1;
	public static final int MAX_ROW = NUM_OF_ROWS;
	public static final int MAX_COL = NUM_OF_COLS;
	public static final int MAX_COMPONENTS = 31;

	public static final String LAYOUT_PREFFERED = "preffered";
	public static final String LAYOUT_MINIMUM = "minimum";
	public static final String LAYOUT_MAXIMUM = "maximum";
	public static final RCPosition CALC_SCREEEN = new RCPosition(1, 1);
	public static final int CALC_SCREEN_WIDTH_FACTOR = 5;
	public static final int CALC_GAPS = 4;

	private int gap;
	Map<Component, RCPosition> components;

	public CalcLayout(int gap) {
		if (gap < 0) {
			throw new CalcLayoutException("Gap cannot be set to a value less than zero, was: " + gap);
		}

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
	public void addLayoutComponent(Component component, Object obj) {
		if (component == null) {
			throw new CalcLayoutException("Provided component cannot be null.");
		}
		if (obj == null) {
			throw new CalcLayoutException("Provided constraint cannot be null.");
		}

		RCPosition currentPosition;
		if (obj instanceof RCPosition) {
			currentPosition = (RCPosition) obj;

		} else if (obj instanceof String) {
			currentPosition = RCPosition.extractRCPosition((String) obj);

		} else {
			throw new CalcLayoutException(
					"Invalid RCPosition constraint, must be of either String or RCPosition class.");
		}

		validateRCPositionAvailability(currentPosition);

		components.put(component, currentPosition);
	}

	private void validateRCPositionAvailability(RCPosition currentPosition) {
		int row = currentPosition.getRow();
		int col = currentPosition.getColumn();

		if (row < MIN_ROW || row > MAX_ROW || col < MIN_COL || col > MAX_COL) {
			throw new CalcLayoutException("Invalid RCPosition requested.");

		} else if (components.size() >= MAX_COMPONENTS) {
			throw new CalcLayoutException("Maximum number of components reached.");

		} else if (row == 1 && col >= 2 && col <= 5) {
			throw new CalcLayoutException("Cannot take up positions reserved for calculator's screen.");

		} else if (components.containsValue(currentPosition)) {
			throw new CalcLayoutException("Requested RCPosition has already been taken.");
		}
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
		if (component == null) {
			throw new CalcLayoutException("This layout does not store null components");
		}

		components.remove(component);
	}

	//@formatter:off
	@Override
	public void layoutContainer(Container container) {
		Insets insets = container.getInsets();

		int widthInsets = insets.left + insets.right;
		int heightInsets = insets.top + insets.bottom;

		int widthGaps = (NUM_OF_COL_GAPS + 2) * gap;
		int heightGaps = (NUM_OF_ROW_GAPS + 2) * gap;

		int buttonWidth = (container.getWidth() - widthInsets - widthGaps) / NUM_OF_COLS;
		int screenWidth = buttonWidth * CALC_SCREEN_WIDTH_FACTOR + gap * CALC_GAPS;
		int buttonHeight = (container.getHeight() - heightInsets - heightGaps) / NUM_OF_ROWS;

		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Component currentComponent = entry.getKey();
			RCPosition currentPosition = entry.getValue();

			int xCoordinate = insets.left +
							  currentPosition.getColumn() * gap + 
							  (currentPosition.getColumn() - 1) * buttonWidth;
			
			int yCoordinate = insets.top + 
							  currentPosition.getRow() * gap +
							  (currentPosition.getRow() - 1) * buttonHeight;

			if (!currentPosition.equals(CALC_SCREEEN)) {
				currentComponent.setBounds(xCoordinate, yCoordinate, buttonWidth, buttonHeight);

			} else {
				currentComponent.setBounds(xCoordinate, yCoordinate, screenWidth, buttonHeight);
			}
		}

	}
	//@formatter:on

	@Override
	public float getLayoutAlignmentX(Container container) {
		return container.getAlignmentX();
	}

	@Override
	public float getLayoutAlignmentY(Container container) {
		return container.getAlignmentY();
	}

	@Override
	public void invalidateLayout(Container container) {
	}
}
