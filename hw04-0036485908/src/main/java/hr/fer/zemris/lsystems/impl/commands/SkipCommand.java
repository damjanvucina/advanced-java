package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

// TODO: Auto-generated Javadoc
/**
 * The Class SkipCommand.
 */
public class SkipCommand implements Command{
	
	/** The step. */
	public double step;

	/**
	 * Instantiates a new skip command.
	 *
	 * @param step the step
	 */
	public SkipCommand(double step) {
		this.step=step;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.lsystems.impl.Command#execute(hr.fer.zemris.lsystems.impl.Context, hr.fer.zemris.lsystems.Painter)
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Color transparent = new Color(0, 0, 0, 0);
		ColorCommand colorCommand = new ColorCommand(transparent);
		colorCommand.execute(ctx, painter);
		
		DrawCommand drawCommand = new DrawCommand(step);
		drawCommand.execute(ctx, painter);
			
	}
	
	

}
