package strategy;

import java.util.List;

import ludo.AbstractAction;
import ludo.Player;
import ludo.Token;


public class BasherStrategy extends ChooserCollection{
	int bashCounter = 0;
	int homeCounter = 0;
	int playerCount = 0;
	int leadingCounter = 0;
	int noChoice = 0;
	int shortCounter = 0;
	@Override
	public int chooseAction(List<Token> tokens, int turn, int die,
			List<AbstractAction> actions) {
		if(playerCount == 0){
			playerCount = tokens.size() / 4;
		}
		if(actions.size() == 1){
			noChoice++;
			return 0;
		}
		int ret = -9999;
		
		ret = chooseHomeComing(actions);
		if(ret != -9999){
			homeCounter ++;
			return ret;
		}
		ret = chooseShortcutBash(tokens, actions, die);
		if(ret != -9999){
			shortCounter ++;
			return ret;
		}
		
		ret = chooseBash(tokens, actions);
		if(ret != -9999){
			bashCounter++;
			return ret;
		}else{
			leadingCounter++;
			return chooseLeading(actions);
		}
	}
	
	/*public void onRoundOver(Player winner, int roundCount){
		System.out.println("Geschlagene Spieler: " + bashCounter);
		System.out.println("HomeComing: " + homeCounter);
		System.out.println("Leading: " + leadingCounter);
		System.out.println("Abgekürzt: " + shortCounter);
		System.out.println("Keine Wahl: " + noChoice);
		System.out.println("SpielZüge: " + roundCount);
		System.out.println("Gewinner: " + winner.name() + "\n------------------\n");
		bashCounter = 0;
		homeCounter = 0;
		leadingCounter = 0;
		noChoice = 0;
		shortCounter = 0;
	}*/
	
}
