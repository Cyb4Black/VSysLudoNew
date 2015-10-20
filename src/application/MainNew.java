package application;

import strategy.*;
import ludo.LudoApp;
import ludo.AbstractStrategy;

public class MainNew {

	public static void main(String[] args) {
		AbstractStrategy red = new MoveLeadingToken(); // Replace with your implementation
		AbstractStrategy blue = new MyRandom(); // Replace with your implementation
		AbstractStrategy green = new MyRandom(); // Replace with your implementation
		AbstractStrategy orange = new MyRandom(); // Replace with your implementation
		AbstractStrategy p5 = new MyRandom(); // Replace with your implementation
		AbstractStrategy p6 = new MyRandom(); // Replace with your implementation

		//LudoApp.setStrategies(red, blue);
		//LudoApp.setStrategies(red, blue, green);
		//LudoApp.setStrategies(red, blue, green, orange);
		//LudoApp.setStrategies(red, blue, green, orange, p5);
		LudoApp.setStrategies(red, blue, green, orange, p5, p6);
		LudoApp.launch(LudoApp.class, args);
	}
}