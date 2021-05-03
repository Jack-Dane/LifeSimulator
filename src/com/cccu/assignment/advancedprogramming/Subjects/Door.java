package com.cccu.assignment.advancedprogramming.Subjects;

import java.util.ArrayList;

import com.cccu.assignment.advancedprogramming.Observers.DoorObserver;

public class Door implements Subject{
	
	//true = open, false = closed
	private boolean mDoorOpen;
	private Floor mFloor;
	private ArrayList<DoorObserver> mObservers = new ArrayList<DoorObserver>();
	
	public Door(Floor f) {
		mDoorOpen = false;
		mFloor = f;
		mFloor.registerDoor(this);
	}
	
	public boolean DoorStatus() {
		return mDoorOpen;
	}
	
	public void CloseDoor() {
		try {
			mDoorOpen = false;
			NotifyObservers();
			Thread.sleep(500);//takes .5 seconds to close the doors
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void OpenDoor() {
		try {
			mDoorOpen = true;
			NotifyObservers();
			Thread.sleep(500);//takes .5 seconds to open the doors
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void NotifyObservers() {
		if(mDoorOpen == true) {
			for(DoorObserver o : mObservers) {
				o.DoorOpening(mFloor);
			}
		}else {
			for(DoorObserver o : mObservers) {
				o.DoorClosing(mFloor);
			}
		}
	}

	@Override
	public void AddObserver(Object o) {
		mObservers.add((DoorObserver)o);
	}

	@Override
	public void RemoverObservers(Object o) {
		mObservers.add((DoorObserver)o);
	}
}
