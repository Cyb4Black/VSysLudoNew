package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.Token;

/**
 * Class containing chooseAction with the order of chooseMethods
 * 
 * @author Hex-3-En
 * @version 1.0
 */
public class DangerPointStrategy extends ChooserCollection {

	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		if (actions.size() == 1) {
			noChoice++;
			return 0;
		}

		int ret = -9999;
		ret = chooseHomeComing(actions);
		if (ret != -9999) {
			homeCounter++;
			return ret;
		}
		ret = chooseShortcutBash(tokens, actions, die);
		if (ret != -9999) {
			shortCounter++;
			return ret;
		}

		ret = chooseEndangered(tokens, die, actions);
		if (ret != -9999) {
			dangerCounter++;
			return ret;
		}
		leadingCounter++;
		return chooseLeading(actions);

	}

}
