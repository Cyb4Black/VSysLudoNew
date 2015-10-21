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
		int homeFlag = -1;
		if(actions.size() > 1){
			for(AbstractAction ac : actions){
				if(((MoveAction)ac).token().field().position() >= ((MoveAction)actions.get(ret)).token().field().position()){
					ret = actions.indexOf(ac);
					if(((MoveAction)ac).token().field().inHomeArea()){//wenn Token in HomeArea
						//targetFlag = actions.indexOf(ac);
						if(homeFlag > -1){//wenn HomeFlag gesetzt
							if(((MoveAction)ac).token().field().position() >= ((MoveAction)actions.get(ret)).token().field().position()){
								//wenn aktueller Token weiter als bisheriger in HomeArea
								homeFlag = actions.indexOf(ac);
							}
						}else{//wenn HomeFlag nicht gesetzt
							homeFlag = actions.indexOf(ac);
						}
					}
				}
			}
		}
		if(homeFlag >= 0){
			return homeFlag;
		}else{
			return ret;
		}
	}

}
