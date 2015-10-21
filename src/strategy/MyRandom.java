package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.Token;

public class MyRandom extends ChooserCollection{

	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		return chooseRandom(actions);
	}

}
