package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.AbstractStrategy;
import ludo.Token;
import java.util.Random;

public class MyRandom extends AbstractStrategy{

	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		Random rand = new Random();
		return rand.nextInt(actions.size());
	}

}
