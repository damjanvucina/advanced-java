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

/**
 * The class used for the purpose of providing a custom implementation of the
 * LayoutManager2 interface. Layout implemented here is used for building a
 * basic caluclator. This class defines a layout consisiting of 30 equal sized
 * elements, plus a single bigger element representing the calculator screen.
 * This layout makes implemented calculator smoothly resizable.
 * 
 * @author Damjan Vučina
 */
public class CalcLayout implements LayoutManager2 {

	/** The Constant NUM_OF_ROWS. */
	public static final int NUM_OF_ROWS = 5;

	/** The Constant NUM_OF_COLS. */
	public static final int NUM_OF_COLS = 7;

	/** The Constant NUM_OF_ROW_GAPS. */
	public static final int NUM_OF_ROW_GAPS = NUM_OF_ROWS - 1;

	/** The Constant NUM_OF_COL_GAPS. */
	public static final int NUM_OF_COL_GAPS = NUM_OF_COLS - 1;

	/** The Constant DEFAULT_GAP. */
	public static final int DEFAULT_GAP = 0;

	/** The Constant MIN_ROW. */
	public static final int MIN_ROW = 1;

	/** The Constant MIN_COL. */
	public static final int MIN_COL = 1;

	/** The Constant MAX_ROW. */
	public static final int MAX_ROW = NUM_OF_ROWS;

	/** The Constant MAX_COL. */
	public static final int MAX_COL = NUM_OF_COLS;

	/** The Constant MAX_COMPONENTS. */
	public static final int MAX_COMPONENTS = 31;

	/** The Constant LAYOUT_PREFFERED. */
	public static final String LAYOUT_PREFFERED = "preffered";

	/** The Constant LAYOUT_MINIMUM. */
	public static final String LAYOUT_MINIMUM = "minimum";

	/** The Constant LAYOUT_MAXIMUM. */
	public static final String LAYOUT_MAXIMUM = "maximum";

	/** The Constant CALC_SCREEEN. */
	public static final RCPosition CALC_SCREEEN = new RCPosition(1, 1);

	/** The Constant CALC_SCREEN_WIDTH_FACTOR. */
	public static final int CALC_SCREEN_WIDTH_FACTOR = 5;

	/** The Constant CALC_GAPS. */
	public static final int CALC_GAPS = 4;

	/** The gap between the buttons. */
	private int gap;

	/** The components of the layout. */
	Map<Component, RCPosition> components;

	/**
	 * Instantiates a new calc layout.
	 *
	 * @param gap
	 *            the gap between the elements of this calculator
	 */
	public CalcLayout(int gap) {
		if (gap < 0) {
			throw new CalcLayoutException("Gap cannot be set to a value less than zero, was: " + gap);
		}

		this.gap = gap;
		components = new HashMap<>();
	}

	/**
	 * Instantiates a new calc layout with gap set to the default value of 0.
	 */
	public CalcLayout() {
		this(DEFAULT_GAP);
	}

	/**
	 * If the layout manager uses a per-component string, adds the component comp to
	 * the layout, associating it with the string specified by name.
	 * 
	 * NOTICE: not supported by this implementation
	 */
	@Override
	public void addLayoutComponent(String s, Component component) {
	}

	/**
	 * Adds the specified component to the layout, using the specified constraint
	 * object. Object defines the position of the given element in this layout and
	 * as such is supposed to be either an instance of RCPosition class or String
	 * representation of RCPosition in form of rowNumber,columnNumber.
	 * 
	 * @throws CalcLayoutException
	 *             if the provided component is null or provided constraint is null
	 *             or Invalid RCPosition is given
	 */
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

	/**
	 * Validates RC position availability. RCPosition 1,1 is reserved for the
	 * calculator screen, which is stretched so it takes up five column spaces.
	 * Different components cannot be added to these five places. Each component
	 * must be in its own space.
	 *
	 * @param currentPosition
	 *            the current position of the given component
	 * @throws CalcLayoutException
	 *             if invalid RCPosition is requested or maximum number of
	 *             components is reached or if user tries to take up positions
	 *             reserved for calculator's screen or if requested RCPosition has
	 *             already been taken.
	 */
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

	/**
	 * Calculates the minimum size dimensions for the specified container, given the
	 * components it contains.
	 */
	@Override
	public Dimension minimumLayoutSize(Container container) {
		Dimension componentSize = calculateComponentSize((component) -> component.getMinimumSize());
		return calculateLayoutSize(container, componentSize);
	}

	/**
	 * Calculates the maximum size dimensions for the specified container, given the
	 * components it contains.
	 */
	@Override
	public Dimension maximumLayoutSize(Container container) {
		Dimension componentSize = calculateComponentSize((component) -> component.getMaximumSize());
		return calculateLayoutSize(container, componentSize);
	}

	/**
	 * Calculates the preferred size dimensions for the specified container, given
	 * the components it contains.
	 */
	@Override
	public Dimension preferredLayoutSize(Container container) {
		Dimension componentSize = calculateComponentSize((component) -> component.getPreferredSize());
		return calculateLayoutSize(container, componentSize);
	}

	/**
	 * Calculates layout size, by multiplying the dimensions of the custom component
	 * received via arguments by defined layout format (5 rows, 7 columns). Insets
	 * ang gaps are also taken into account.
	 *
	 * @param container
	 *            the container
	 * @param componentSize
	 *            the component size
	 * @return the dimension of the layout given the component
	 */
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

	/**
	 * Calculates component size, by iterating over all components of this layout so
	 * a Dimension that meets up all of the components' requirements can be set.
	 *
	 * @param action
	 *            the action
	 * @return the dimension
	 */
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

	/**
	 * Checks if the currently inspected component is in fact calculator's screen.
	 *
	 * @param position
	 *            the position
	 * @return true, if is calc screen
	 */
	private boolean isCalcScreen(RCPosition position) {
		return CALC_SCREEEN.equals(position);
	}

	/**
	 * Removes the specified component from the layout.
	 * 
	 * @throws CalcLayoutException
	 *             if null component is to be removed
	 */
	@Override
	public void removeLayoutComponent(Component component) {
		if (component == null) {
			throw new CalcLayoutException("This layout does not store null components");
		}

		components.remove(component);
	}

	/**
	 * Lays out the specified container by positioning all elements of this layout
	 * with respect to the defined insets, gaps and sizes.
	 */
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

	/**
	 * Returns the alignment along the x axis.
	 */
	@Override
	public float getLayoutAlignmentX(Container container) {
		return container.getAlignmentX();
	}

	/**
	 * Returns the alignment along the y axis.
	 */
	@Override
	public float getLayoutAlignmentY(Container container) {
		return container.getAlignmentY();
	}

	/**
	 * Invalidates the layout, indicating that if the layout manager has cached
	 * information it should be discarded. NOTICE: not supported by this
	 * implementation
	 */
	@Override
	public void invalidateLayout(Container container) {
	}
}
