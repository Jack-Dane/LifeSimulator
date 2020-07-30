package com.cccu.assignment.advancedprogramming;

public class ButtonFactory {
	
	public static Button getButton(String type, Floor destinationFloor) {
		if(type == "Lift") {
			return new LiftButton(destinationFloor);
		}else if(type == "Floor"){
			return new FloorButton(destinationFloor);
		}else {
			return null;
		}
	}
}
