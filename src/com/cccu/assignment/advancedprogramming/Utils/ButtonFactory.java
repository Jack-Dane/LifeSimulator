package com.cccu.assignment.advancedprogramming.Utils;

import com.cccu.assignment.advancedprogramming.Subjects.Button;
import com.cccu.assignment.advancedprogramming.Subjects.Floor;
import com.cccu.assignment.advancedprogramming.Subjects.FloorButton;
import com.cccu.assignment.advancedprogramming.Subjects.LiftButton;

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
