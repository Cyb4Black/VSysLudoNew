package strategy;


import java.util.List;

import ludo.AbstractAction;
import ludo.AbstractStrategy;
import ludo.Field;
import ludo.MoveAction;
import ludo.PlayerStats;
import ludo.Token;

import java.util.Random;

public abstract class ChooserCollection extends AbstractStrategy {
	int bashCounter = 0;
	int homeCounter = 0;
	int leadingCounter = 0;
	int noChoice = 0;
	int shortCounter = 0;
	int dangerCounter = 0;
	String myClass = this.getClass().getSimpleName();


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
		int retPos = 0;

		for (AbstractAction ac : actions) {
			if (((MoveAction) ac).destination().inHomeArea()
					&& ((MoveAction) ac).token().field().position() >= retPos) {
				//System.out.println("PositionID HomeComing: " + ((MoveAction)ac).token().field().position());
				ret = actions.indexOf(ac);
				retPos = ((MoveAction) ac).token().field().position();
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
			if(myPos - die < 0 
					&& targetPos > (playersCount * 12) - 7 
					&& mAc.token().field().inTrackArea()){
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
						&& mAc.destination().position() >= targetPos && (mAc.destination().position() - mAc.token().field().position()) >= -2) {
					ret = actions.indexOf(ac);
					targetPos = mAc.destination().position();
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
	
	public void onGameOver(List<PlayerStats> stats, int roundCount){
		if((bashCounter + homeCounter + leadingCounter + shortCounter + noChoice + dangerCounter) == 0)return;
		
		System.out.print(myClass+ "; ");
		System.out.print(bashCounter + "; ");
		System.out.print(homeCounter + "; ");
		System.out.print(leadingCounter + "; ");
		System.out.print(shortCounter + "; ");
		System.out.print(dangerCounter + "; ");
		System.out.print(noChoice + "; ");
		System.out.println((bashCounter + homeCounter + leadingCounter + shortCounter + noChoice + dangerCounter) + "; ");
		//System.out.println("\n------------------\n");
		
	}

	/*private boolean movesHome(MoveAction action) {
		return action.destination().inHomeArea();
	}*/

}