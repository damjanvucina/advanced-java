package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

public class RotateCommand implements Command {

	private double angle;

	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D newDirection = ctx.getCurrentState().getDirection().rotated(angle);
		ctx.getCurrentState().setDirection(newDirection);
	}

}
