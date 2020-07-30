package com.cccu.assignment.advancedprogramming;
import java.util.ArrayList;
import java.util.Iterator;

public class Floor{
	
	private Door mDoor;
	private int mFloorNumber;
	private ArrayList<Person> mPeopleOnFloor = new ArrayList<Person>();
	private ArrayList<Person> mPeopleWaitingOnFloor = new ArrayList<Person>();
	private Lift mLift = Lift.GetInstance();
	private FloorButton mButton;
	
	public Floor(int n) {
		mLift.registerFloor(this);
		mFloorNumber = n;
	}
	
	public void registerButton(FloorButton f) {
		mButton = f;
	}
	
	public void registerDoor(Door d) {
		mDoor = d;
	}
	
	public void CloseDoor() {
		mDoor.CloseDoor();
	}
	
	public void OpenDoors() {
		mDoor.OpenDoor();
	}
	
	public boolean DoorOpen() {
		return mDoor.DoorStatus();
	}
	
	public void Departed() {
		if(mDoor.DoorStatus()) {
			mDoor.CloseDoor();
		}
	}
	
	public boolean ButtonPressed() {
		return mButton.GetState();
	}
	
	public void PressButton() {
		mButton.Pressed();
		mLift.Call(this);
	}
	
	public int FloorNumber() {
		return mFloorNumber;
	}
	
	public void AddPerson(Person p) {
		mPeopleOnFloor.add(p);
	}
	
	public void PersonWaiting(Person p) {
		synchronized(mPeopleWaitingOnFloor) {
			mPeopleWaitingOnFloor.add(p);
		}
	}
	
	public void RemovePerson(Person p) {
		mPeopleOnFloor.remove(p);
	}
	
	public void Arrived() {
		//let everyone waiting on the lift
		synchronized(mPeopleWaitingOnFloor) {
			for (Iterator<Person> iterator = mPeopleWaitingOnFloor.iterator(); iterator.hasNext(); ) {
			    Person person = iterator.next();
			    person.LiftArrived();
			    synchronized(this) {
			    	try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			    }
			    iterator.remove();
			}
		}
		
		if(mButton.GetState()) {//if the button has been pressed
			mButton.Released();
		}
	}

}
