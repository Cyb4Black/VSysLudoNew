package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.Token;


public class DangerPointStrategy extends ChooserCollection{

	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		if(actions.size() == 1) return 0;
		
		int ret = -9999;
		ret = chooseHomeComing(actions);
		if(ret != -9999){
			return ret;
		}
		ret = chooseShortcutBash(tokens, actions, die);
		if(ret != -9999){
			return ret;
		}
		
		ret = chooseBash(tokens, actions);
		if(ret != -9999){
			return ret;
		}
		ret = chooseEndangered(tokens, die, actions);
		if(ret != -9999){
			return ret;
		}
		else{
			return chooseLeading(actions);
		}
	}

}
