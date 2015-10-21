package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.Token;


public class MoveLeadingToken extends ChooserCollection{

	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		if(actions.size() == 1){
			return 0;
		}else{
			return chooseLeading(actions);
		}
	}

}
