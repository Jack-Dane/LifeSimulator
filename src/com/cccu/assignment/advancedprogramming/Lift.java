package com.cccu.assignment.advancedprogramming;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lift extends Thread implements Subject{
	
	private ArrayList<LiftObserverView> mObservers = new ArrayList<LiftObserverView>();
	private Floor mCurrentFloor;
	private static Lift mLiftInstant = new Lift();
	private ArrayList<Floor> mLiftFloors = new ArrayList<Floor>();//floors in queue
	private ArrayList<Floor> mAvaliableFloors = new ArrayList<Floor>();//floors lift can travel to
	private ArrayList<Person> mPeopleInLift = new ArrayList<Person>();
	private Map<Floor, LiftButton> mButtons = new HashMap<Floor, LiftButton>();
	private int mState;
	
	private Lift() {}
	
	public static Lift GetInstance() {
		return mLiftInstant;
	}
	
	public void registerFloor(Floor f) {
		mAvaliableFloors.add(f);
	}
	
	public void registerButton(LiftButton l) {
		mButtons.put(l.GetFloor(), l);
	}
	
	public void SetCurrentFloor(Floor f) {
		for(LiftObserverView o : mObservers) {
			o.ArrivedAtNewFloor(f);
		}
		mCurrentFloor = f;
	}
	
	public void PressButton(Floor f) {
		mButtons.get((Floor)f).Pressed();//press the button that the person wants to go to
		Call(f);
	}
	
	private void ReleaseButton(Floor f) {
		if(mButtons.get(f).GetState()) {
			mButtons.get(f).Released();
		}
	}
	
	public boolean ButtonState(Floor f) {
		return mButtons.get(f).GetState();
	}
	
	public void Call(Floor floor) {
		//if the floor isn't already in the queue
		if(!mLiftFloors.contains(floor)) {
			mLiftFloors.add(floor);
			synchronized (this) {
				this.notify();
			}
		}
	}
	
	public Floor CurrentFloor() {
		return mCurrentFloor;
	}
	
	private void travelToNewFloor() {
		try {
			//if the lift is already on the current floor, just open the doors
			if(mCurrentFloor == ((Floor)mLiftFloors.get(0))) {
				mCurrentFloor.OpenDoors();
				mCurrentFloor.Arrived();
				
			}else {
				//Travel to new floor
				mCurrentFloor.Departed();//let floor know that the lift is leaving
				
				mState = 0;
				NotifyObservers();
				Thread.sleep(5000);//5 seconds travel
				
				mCurrentFloor = (Floor)mLiftFloors.get(0);//change the current floor
				
				mState = 1;
				NotifyObservers();
				
				ReleaseButton(mCurrentFloor);//release lift button
				
				//open doors
				mCurrentFloor.OpenDoors();
				
				//notify people in lift to get out
				for(Person p: mPeopleInLift) {
					p.LiftArrived();//notify people on lift that the lift has arrived
					synchronized (this) {
						//lift is notified by person when they have exited lift
						this.wait();
					}
				}
				
				mPeopleInLift.clear();
				
				//let people waiting inside
				mCurrentFloor.Arrived();
			}
			
			//Arrived at new floor			
			mLiftFloors.remove(0);//remove the floor from the queue
			
			//Look for next floor
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void PersonEntered(Person p) {
		this.mPeopleInLift.add(p);
	}
	
	public void run() {
		while(this.isAlive()) {//when the thread is still alive
			while(mLiftFloors.size() <= 0) {
				//if no floors in queue wait until a floor added to the queue
				CurrentFloor().CloseDoor();//when lift not in use, close doors
				synchronized (this) {
				    try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			//once floor added to queue travel to the new floor
			this.travelToNewFloor();
		}
	}

	@Override
	public void NotifyObservers() {
		if(mState == 0) {//lift travelling to new floor
			for(LiftObserverView v: mObservers) {
				v.LiftGoingToFloor((Floor)mLiftFloors.get(0));//notify views that lift is travelling to new floor
			}
		}else {//lift arrived at new floor
			for(LiftObserverView v: mObservers) {
				v.ArrivedAtNewFloor(mCurrentFloor);//notify views that lift has arrived at a new floor
			}
		}
	}

	@Override
	public void AddObserver(Object o) {
		mObservers.add((LiftObserverView)o);
	}

	@Override
	public void RemoverObservers(Object o) {
		mObservers.remove((LiftObserverView)o);
	}
}
