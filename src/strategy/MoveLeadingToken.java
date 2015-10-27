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
		}
		int ret = chooseHomeComing(actions);
		if(ret != -9999){
			return ret;
		}else{
			return chooseLeading(actions);
		}
	}

}
