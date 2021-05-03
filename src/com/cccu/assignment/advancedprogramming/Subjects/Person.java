package com.cccu.assignment.advancedprogramming.Subjects;
import java.util.ArrayList;

import com.cccu.assignment.advancedprogramming.Observers.PersonObserver;

public class Person extends Thread implements Subject{
	
	private ArrayList<PersonObserver> mObservers = new ArrayList<PersonObserver>();
	private String mName;
	private Floor mFloor;
	private Floor mDestinationFloor;
	private Lift mLift = Lift.GetInstance();
	private int mState;//state of the person subject depends on what notification to sent to observers
	
	public Person(String name, Floor currentFloor, Floor destinationFloor) {
		mName = name;
		mFloor = currentFloor;
		mFloor.AddPerson(this);
		mDestinationFloor = destinationFloor;
	}
	
	public void registerObserver(PersonObserver observer) {
		mObservers.add(observer);
	}
	
	public String Name() {
		return mName;
	}
	
	public Floor Floor() {
		return mFloor;
	}
	
	public void DoorOpened() {
		synchronized (this) {
		    this.notify();
		}
	}
	
	public void LiftArrived() {
		synchronized (this) {
		    this.notify();
		}
	}
	
	public void run() {
		try {
			mState = 0;
			NotifyObservers();
			//walk to elevator
			Thread.sleep(5000);
			//push button
			synchronized(mFloor) {
				//if the lift button hasn't already been pressed, press it
				if(!mFloor.ButtonPressed()) {
					mFloor.PressButton();
				}	
			}
			synchronized (this) {
				mState = 1;
				NotifyObservers();
				mFloor.PersonWaiting(this);
				//wait outside lift for lift to arrive and doors to open
			    this.wait();
			}
			
			//go in elevator
			mLift.PersonEntered(this);
			mFloor.RemovePerson(this);
			mState = 2;
			NotifyObservers();
			
			//call floor in elevator
			if(!mLift.ButtonState(mDestinationFloor)) {
				mLift.PressButton(mDestinationFloor);
			}
			
			Thread.sleep(1000);
			synchronized(mFloor) {
				mFloor.notify();
				//let the floor know that you have entered the lift
			}
			
			//wait for elevator
			while(mLift.CurrentFloor() != this.mDestinationFloor) {
				synchronized (this) {
					//wait in lift for the lift to arrive at the floor
				    this.wait();
				}
			}
			
			//exit elevator
			mFloor = mLift.CurrentFloor();
			mState = 3;
			NotifyObservers();
			
			Thread.sleep(1000);
			synchronized(mLift){
				mLift.notify();
				//let the lift know that person has exited
			}
			
			//walk to end of hallway
			Thread.sleep(5000);
			mState = 4;
			NotifyObservers();
			mFloor.RemovePerson(this);
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void NotifyObservers() {
		if(mState == 0) {//person has been created
			for(PersonObserver o : mObservers) {
				o.PersonCreated(this);
			}
		}else if(mState == 1) {//person is waiting for lift
			for(PersonObserver o : mObservers) {
				o.PersonWaitingForLift(this);
			}
		}else if(mState == 2) {//person has entered lift
			for(PersonObserver o : mObservers) {
				o.PersonEnteredLift(this);
			}
		}else if(mState == 3){//person has exited lift
			for(PersonObserver o : mObservers) {
				o.PersonExitedLift(this);
			}
		}else {//person has walked to end of hallway
			for(PersonObserver o : mObservers) {
				o.PersonEnded(this);
			}
		}
	}

	@Override
	public void AddObserver(Object o) {
		mObservers.add((PersonObserver)o);
	}

	@Override
	public void RemoverObservers(Object o) {
		mObservers.remove((PersonObserver)o);
	}
}
