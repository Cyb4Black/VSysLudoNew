package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.Field;
import ludo.MoveAction;
import ludo.AbstractStrategy;
import ludo.Token;


public class BasherStrategy extends AbstractStrategy{

	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		if(actions.size() == 1) return 0;
		int ret = -9999;
		
		ret = chooseHomeComing(actions);
		if(ret != -9999){
			return ret;
		}
		
		ret = chooseBash(tokens, actions);
		if(ret != -9999){
			return ret;
		}else{
			return chooseLeading(actions);
		}
	}
	
	private int chooseBash(List<Token> tokens,List<AbstractAction> actions){
		int ret = -9999;
		int targetPos = -10;
		for(AbstractAction ac : actions){
			MoveAction mAc = (MoveAction)ac;
			for(Token t : tokens){
				if(t.index() != mAc.token().index() && t.field().position() == mAc.destination().position() && mAc.destination().position() >= targetPos){
					ret = actions.indexOf(ac);
				}
			}
		}
		
		return ret;
	}
	
	private int chooseHomeComing(List<AbstractAction> actions){
		int ret = -9999;
		
		for(AbstractAction ac : actions){
			if(((MoveAction)ac).destination().inHomeArea() && ((MoveAction)ac).token().field().position() >= ret){
				ret = actions.indexOf(ac);
			}
		}
		
		return ret;
	}
	
	
	public int chooseLeading(List<AbstractAction> actions){
		int ret = 0;
		int homeFlag = -1;
		if(actions.size() > 1){
			for(AbstractAction ac : actions){
				if(((MoveAction)ac).token().field().position() >= ((MoveAction)actions.get(ret)).token().field().position()){
					ret = actions.indexOf(ac);
					if(((MoveAction)ac).token().field().inHomeArea()){
						//targetFlag = actions.indexOf(ac);
						if(homeFlag > -1){
							if(((MoveAction)ac).token().field().position() >= ((MoveAction)actions.get(ret)).token().field().position()){
								homeFlag = actions.indexOf(ac);
							}
						}else{
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
