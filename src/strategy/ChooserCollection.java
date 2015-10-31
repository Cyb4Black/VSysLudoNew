package strategy;


import java.util.List;

import ludo.AbstractAction;
import ludo.AbstractStrategy;
import ludo.Field;
import ludo.MoveAction;
import ludo.Token;

import java.util.Random;

public abstract class ChooserCollection extends AbstractStrategy {

	public int chooseRandom(List<AbstractAction> actions) {
		Random rand = new Random();
		return rand.nextInt(actions.size());
	}

	public int chooseLeading(List<AbstractAction> actions) {
		int ret = 0;
		int homeFlag = -1;
		for (AbstractAction ac : actions) {
			MoveAction mAc = (MoveAction)ac;
			if(mAc.token().field().inHomeArea()){
				homeFlag = actions.indexOf(ac);
			}else{
				if(mAc.token().field().position() >= ((MoveAction)actions.get(ret)).token().field().position()){
					ret = actions.indexOf(ac);
				}
			}
		}
		if(homeFlag >= 0){
			return homeFlag;
		}else{
			return ret;
		}
	}

	public int chooseHomeComing(List<AbstractAction> actions) {
		int ret = -9999;

		for (AbstractAction ac : actions) {
			if (((MoveAction) ac).destination().inHomeArea()
					&& ((MoveAction) ac).token().field().position() >= ret) {
				//System.out.println("PositionID HomeComing: " + ((MoveAction)ac).token().field().position());
				ret = actions.indexOf(ac);
			}
		}

		return ret;
	}
	
	public int chooseShortcutBash(List<Token> tokens, List<AbstractAction> actions, int die){
		int ret = -9999;
		final int playersCount = tokens.size() / 4;
		
		for(AbstractAction ac : actions){
			MoveAction mAc = (MoveAction)ac;
			int myPos = mAc.token().field().position();
			int targetPos = mAc.destination().position();
			if(myPos < 6 && targetPos > (playersCount * 12)-die){
				ret = actions.indexOf(ac);
			}
		}
		
		return ret;
	}

	public int chooseBash(List<Token> tokens, List<AbstractAction> actions) {
		int ret = -9999;
		int targetPos = -10;
		for (AbstractAction ac : actions) {
			MoveAction mAc = (MoveAction) ac;
			for (Token t : tokens) {
				if (t.index() != mAc.token().index()
						&& t.field().position() == mAc.destination().position()
						&& mAc.destination().position() >= targetPos) {
					ret = actions.indexOf(ac);
				}
			}
		}

		return ret;
	}

	public int chooseEndangered(List<Token> tokens, int die,
			List<AbstractAction> actions) {
		int ret = -9999;
		int retScore = 9999;
		for (AbstractAction ac : actions) {
			MoveAction mAc = (MoveAction) ac;
			int dScoreSrc = getDangerScore(tokens, mAc.token().field(), mAc);
			int dScoreTarget = getDangerScore(tokens, mAc.destination(), mAc);
			if (dScoreSrc == -1 && dScoreTarget == -1) {
				ret = -9999;
			} else if (dScoreTarget <= dScoreSrc && dScoreTarget <= retScore) {
				ret = actions.indexOf(ac);
				retScore = dScoreTarget;
			} /*else if (dScoreTarget > dScoreSrc && retScore != 9999) {
				ret = -9999;
			}*/
		}
		return ret;
	}

	private int getDangerScore(List<Token> tokens, Field f, MoveAction action) {
		final int enemy = 10;
		//final int home = -1000;
		final int position = action.token().field().position();
		final int playersCount = tokens.size() / 4;
		final int fieldsCount = playersCount * 12;
		boolean noEnemy = true;

		int score = 0;
		int i;

		score += position;

		/*if (movesHome(action))
			score += home;*/

		if (position - 5 < 0) {
			i = fieldsCount - 5 + position;
		} else {
			i = position - 5;
		}
		for (Token t : tokens) {
			if (t.index() != action.token().index() && t.field().inTrackArea()
					&& t.field().position() >= i
					&& t.field().position() <= (position + 5)) {
				score += enemy;
				noEnemy = false;

				if (t.field().position() == f.position()) {
					score += -100;
				}
			}

		}
		if (noEnemy) {
			return -1;
		} else {
			return score;
		}
	}

	/*private boolean movesHome(MoveAction action) {
		return action.destination().inHomeArea();
	}*/

}