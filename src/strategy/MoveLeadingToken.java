package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.MoveAction;
import ludo.AbstractStrategy;
import ludo.Token;


public class MoveLeadingToken extends AbstractStrategy{

	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		int ret = 0;
		int targetFlag = -1;
		if(actions.size() > 1){
			for(AbstractAction ac : actions){
				if(((MoveAction)ac).token().field().position() >= ((MoveAction)actions.get(ret)).token().field().position()){
					ret = actions.indexOf(ac);
					if(((MoveAction)ac).token().field().inHomeArea()){
						//targetFlag = actions.indexOf(ac);
						if(targetFlag > -1){
							if(((MoveAction)ac).token().field().position() >= ((MoveAction)actions.get(ret)).token().field().position()){
								
							}
						}else{
							targetFlag = actions.indexOf(ac);
						}
					}
				}
			}
		}
		if(targetFlag >= 0){
			return targetFlag;
		}else{
			return ret;
		}
	}

}
