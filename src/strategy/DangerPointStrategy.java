package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.Field;
import ludo.MoveAction;
import ludo.AbstractStrategy;
import ludo.Token;


public class DangerPointStrategy extends AbstractStrategy{

	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		if(actions.size() == 1) return 0;
		int ret = -9999;
		ret = chooseEndangered(tokens, die, actions);
		if(ret != -9999){
			return ret;
		}else{
			return chooseLeading(actions);
		}
	}
	
	public int chooseEndangered(List<Token> tokens, int die, List<AbstractAction> actions){
		int ret = -9999;
		for(AbstractAction ac : actions){
			MoveAction mAc = (MoveAction)ac;
			int dScoreSrc = getDangerScore(tokens, mAc.token().field(), mAc);
			int dScoreTarget = getDangerScore(tokens, mAc.destination(), mAc);
			
			if(dScoreSrc == -1 && dScoreTarget == -1){
				ret = -9999;
			}else if(dScoreTarget <= dScoreSrc && dScoreTarget <= ret){
				ret = dScoreTarget;
			}else if(dScoreTarget > dScoreSrc && dScoreTarget <= ret){
				ret = dScoreSrc;
			}
		}
		return ret;
	}
	
	private int getDangerScore(List<Token> tokens, Field f, MoveAction action){
		final int enemy = 10;
		final int home = -100;
		final int position = action.token().field().position();
		final int playersCount = tokens.size() / 4;
		final int fieldsCount = playersCount * 12;
		boolean noEnemy = true;
		
		int score = 0;
		int i;
		
		score += position;
		
		if(movesHome(action))score += home;
		
		if(position - 5 < 0){
			i = fieldsCount - 5 + position;
		}else{
			i = position - 5;
		}
		for(Token t : tokens){
			if(t.index() != action.token().index() && t.field().inTrackArea() && t.field().position() >= i && t.field().position() <= (position + 5) && !(f.position() == t.field().position())){
				score += enemy;
				noEnemy = false;
			}
		}
		if(noEnemy){
			return -1;
		}else{
			return score;
		}
	}
	
	private boolean movesHome(MoveAction action){
		return action.destination().inHomeArea();
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
