package com.cccu.assignment.advancedprogramming.Subjects;

import java.util.ArrayList;

import com.cccu.assignment.advancedprogramming.Observers.FloorButtonObserver;

public class FloorButton extends Button implements Subject{
	
	private ArrayList<FloorButtonObserver> mObservers = new ArrayList<FloorButtonObserver>();
	
	public FloorButton(Floor destinationFloor) {
		super(destinationFloor);
		destinationFloor.registerButton(this);
	}

	@Override
	public void Pressed() {
		super.Pressed();
		NotifyObservers();
	}
	
	@Override
	public void Released() {
		super.Released();
		NotifyObservers();
	}

	@Override
	public void NotifyObservers() {
		if(mPressed) {
			for(FloorButtonObserver o : mObservers) {
				o.ButtonPressOnFloor(mDestinationFloor);
			}
		}else {
			for(FloorButtonObserver o : mObservers) {
				o.ButtonReleaseOnFloor(mDestinationFloor);
			}
		}
	}

	@Override
	public void AddObserver(Object o) {
		mObservers.add((FloorButtonObserver)o);
	}

	@Override
	public void RemoverObservers(Object o) {
		mObservers.remove((FloorButtonObserver)o);
	}
}
