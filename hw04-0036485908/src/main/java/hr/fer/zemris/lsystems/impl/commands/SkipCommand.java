package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

public class SkipCommand implements Command{
	
	public double step;

	public SkipCommand(double step) {
		this.step=step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		Color transparent = new Color(0, 0, 0, 0);
		ColorCommand colorCommand = new ColorCommand(transparent);
		colorCommand.execute(ctx, painter);
		
		DrawCommand drawCommand = new DrawCommand(step);
		drawCommand.execute(ctx, painter);
	}
	
	

}
