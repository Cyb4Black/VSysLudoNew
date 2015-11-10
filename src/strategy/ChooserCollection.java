package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.AbstractStrategy;
import ludo.Field;
import ludo.MoveAction;
import ludo.PlayerStats;
import ludo.Token;

import java.util.Random;

/**
 * Abstract Class for delivering chooserMethods to Strategys
 * 
 * @author Hex-3-En
 * @version 1.0
 */
public abstract class ChooserCollection extends AbstractStrategy {
	int bashCounter = 0;
	int homeCounter = 0;
	int leadingCounter = 0;
	int noChoice = 0;
	int shortCounter = 0;
	int dangerCounter = 0;
	String myClass = this.getClass().getSimpleName();

	/**
	 * Method for choosing a random action from available actions
	 * 
	 * @param actions
	 *            list of available actions
	 * @return index of a random action
	 */
	public int chooseRandom(List<AbstractAction> actions) {
		Random rand = new Random();
		return rand.nextInt(actions.size());
	}

	/**
	 * Method for finding the actoin, wich moves the leading token
	 * 
	 * @param actions
	 *            list of available actions
	 * @return the index of the action, that moves leading token
	 */
	public int chooseLeading(List<AbstractAction> actions) {
		int ret = 0;
		int homeFlag = -1;
		for (AbstractAction ac : actions) {
			MoveAction mAc = (MoveAction) ac;
			if (mAc.token().field().inHomeArea()) {
				homeFlag = actions.indexOf(ac);
			} else {
				if (mAc.token().field().position() >= ((MoveAction) actions
						.get(ret)).token().field().position()) {
					ret = actions.indexOf(ac);
				}
			}
		}
		if (homeFlag >= 0) {
			return homeFlag;
		} else {
			return ret;
		}
	}

	/**
	 * Method for choosing an action, that move a token in or inside the
	 * HomeArea
	 * 
	 * @param actions
	 *            list of available actions
	 * @return if found: index of the action with the highest destination in
	 *         HomeArea, else: -9999
	 */
	public int chooseHomeComing(List<AbstractAction> actions) {
		int ret = -9999;
		int retPos = 0;

		for (AbstractAction ac : actions) {
			if (((MoveAction) ac).destination().inHomeArea()
					&& ((MoveAction) ac).token().field().position() >= retPos) {
				ret = actions.indexOf(ac);
				retPos = ((MoveAction) ac).token().field().position();
			}
		}

		return ret;
	}

	/**
	 * Method for finding an action that moves a Token using the shortcutGlitch
	 * 
	 * @param tokens
	 *            list of all tokens in game
	 * @param actions
	 *            list of available actions
	 * @param die
	 *            the given die-count as integer
	 * @return if found: index of an action that bashes an enemy using the
	 *         shortCut-Glitch, else: -9999
	 */
	public int chooseShortcutBash(List<Token> tokens,
			List<AbstractAction> actions, int die) {
		int ret = -9999;
		final int playersCount = tokens.size() / 4;

		for (AbstractAction ac : actions) {
			MoveAction mAc = (MoveAction) ac;
			int myPos = mAc.token().field().position();
			int targetPos = mAc.destination().position();
			if (myPos - die < 0 && targetPos > (playersCount * 12) - 7
					&& mAc.token().field().inTrackArea()) {
				ret = actions.indexOf(ac);
			}
		}

		return ret;
	}

	/**
	 * Method for finding an action that bashes an enemy token
	 * 
	 * @param tokens
	 *            list of all tokens in game
	 * @param actions
	 *            list of available actions
	 * @return if found: an action that bashes an enemy and with the highest
	 *         destination if multiple choices, else: -9999
	 */
	public int chooseBash(List<Token> tokens, List<AbstractAction> actions) {
		int ret = -9999;
		int targetPos = -10;
		for (AbstractAction ac : actions) {
			MoveAction mAc = (MoveAction) ac;
			for (Token t : tokens) {
				if (t.index() != mAc.token().index()
						&& t.field().position() == mAc.destination().position()
						&& mAc.destination().position() >= targetPos
						&& (mAc.destination().position() - mAc.token().field()
								.position()) >= -2) {
					ret = actions.indexOf(ac);
					targetPos = mAc.destination().position();
				}
			}
		}

		return ret;
	}

	/**
	 * Method for choosing the Action, that move its token to the field with the
	 * lowest DangerScore
	 * 
	 * @param tokens
	 *            list of all token in game
	 * @param die
	 *            the given die-count as integer
	 * @param actions
	 *            list of available actions
	 * @return index of action with lowest dangerScore at destination or -9999
	 *         if no token in danger
	 */
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
			} /*
			 * else if (dScoreTarget > dScoreSrc && retScore != 9999) { ret =
			 * -9999; }
			 */
		}
		return ret;
	}

	/**
	 * Sub-Method for calculating the dangerScore of a specific Field in
	 * TrackArea
	 * 
	 * @param tokens
	 *            list of all tokens in Game
	 * @param f
	 *            Fild to calculate DangerScore for
	 * @param action
	 *            MoveAction to be checked
	 * @return DangerScore of Field or -1 if Field has no Danger
	 */
	private int getDangerScore(List<Token> tokens, Field f, MoveAction action) {
		final int enemy = 10;
		final int position = action.token().field().position();
		final int playersCount = tokens.size() / 4;
		final int fieldsCount = playersCount * 12;
		boolean noEnemy = true;

		int score = 0;
		int i;

		score += position;

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

	/**
	 * Method for printing the counts to stdOut
	 */
	public void onGameOver(List<PlayerStats> stats, int roundCount) {
		if ((bashCounter + homeCounter + leadingCounter + shortCounter
				+ noChoice + dangerCounter) == 0)
			return;

		System.out.print(myClass + "; ");
		System.out.print(bashCounter + "; ");
		System.out.print(homeCounter + "; ");
		System.out.print(leadingCounter + "; ");
		System.out.print(shortCounter + "; ");
		System.out.print(dangerCounter + "; ");
		System.out.print(noChoice + "; ");
		System.out.println((bashCounter + homeCounter + leadingCounter
				+ shortCounter + noChoice + dangerCounter)
				+ "; ");

	}

}