package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.MoveAction;
import ludo.AbstractStrategy;
import ludo.Token;


public class CarefullyMoveLeadingTokenNew extends AbstractStrategy{

	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		if(actions.size() == 1) return 0;
		int ret = -1;
		ret = chooseEndangered(tokens, die, actions);
		if(ret != -1){
			return ret;
		}
		
		
		return ret;
	}
	
	public int chooseEndangered(List<Token> tokens, int die, List<AbstractAction> actions){
		int ret = -1;
		for(AbstractAction ac : actions){
			for(int i : ((MoveAction)ac).token().field().)
		}
		return 0;
	}
	
	public int chooseLeading(List<AbstractAction> actions){
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
