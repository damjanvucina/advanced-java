package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

public class DrawCommand implements Command {

	private double step;

	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();

		Vector2D oldPosition = currentState.getCurrentPosition();
		Vector2D direction = currentState.getDirection();
		Vector2D newPosition = oldPosition.translated(direction.scaled(step * currentState.getStep()));

		painter.drawLine(oldPosition.getX(), oldPosition.getY(), newPosition.getX(), newPosition.getY(),
				currentState.getCurrentColor(), 1);

		currentState.setCurrentPosition(newPosition);
	}
}
