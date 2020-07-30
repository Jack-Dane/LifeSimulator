package com.cccu.assignment.advancedprogramming;

public abstract class Button{
	
	protected Floor mDestinationFloor;
	protected boolean mPressed = false;
	
	public Button(Floor destinationFloor) {
		mDestinationFloor = destinationFloor;
	}
	
	public void Pressed() {
		mPressed = true;
	}
	
	public void Released() {
		mPressed = false;
	}
	
	public boolean GetState() {
		return mPressed;
	}
	
	public Floor GetFloor() {
		return mDestinationFloor;
	}
}