package com.cccu.assignment.advancedprogramming.Subjects;

import java.util.ArrayList;

import com.cccu.assignment.advancedprogramming.Observers.LiftButtonObserver;

public class LiftButton extends Button implements Subject{
	
	private ArrayList<LiftButtonObserver> mObservers = new ArrayList<LiftButtonObserver>();
	
	public LiftButton(Floor destinationFloor) {
		super(destinationFloor);
		Lift.GetInstance().registerButton(this);
	}
	
	public void registerObserver(LiftButtonObserver o) {
		mObservers.add(o);
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
			for(LiftButtonObserver o : mObservers) {
				o.ButtonPressOnLift(mDestinationFloor);
			}
		}else {
			for(LiftButtonObserver o : mObservers) {
				o.ButtonReleaseOnLift(mDestinationFloor);
			}
		}
	}

	@Override
	public void AddObserver(Object o) {
		mObservers.add((LiftButtonObserver)o);
	}

	@Override
	public void RemoverObservers(Object o) {
		mObservers.remove((LiftButtonObserver)o);
	}
}
