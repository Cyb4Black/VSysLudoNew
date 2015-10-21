package application;

import strategy.*;
import ludo.LudoApp;
import ludo.AbstractStrategy;

public class MainNew {

	public static void main(String[] args) {
		AbstractStrategy red = new DangerPointStrategy(); // Replace with your implementation
		AbstractStrategy blue = new MyRandom(); // Replace with your implementation
		AbstractStrategy green = new MyRandom(); // Replace with your implementation
		AbstractStrategy orange = new MyRandom(); // Replace with your implementation
		AbstractStrategy brown = new MyRandom(); // Replace with your implementation
		AbstractStrategy violet = new MyRandom(); // Replace with your implementation

		//LudoApp.setStrategies(red, blue);
		//LudoApp.setStrategies(red, blue, green);
		//LudoApp.setStrategies(red, blue, green, orange);
		//LudoApp.setStrategies(red, blue, green, orange, brown);
		LudoApp.setStrategies(red, blue, green, orange, brown, violet);
		LudoApp.launch(LudoApp.class, args);
	}
}