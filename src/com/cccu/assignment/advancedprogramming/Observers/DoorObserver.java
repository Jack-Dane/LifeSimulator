package com.cccu.assignment.advancedprogramming.Observers;

import com.cccu.assignment.advancedprogramming.Subjects.Floor;

public interface DoorObserver {
	public void DoorClosing(Floor f);
	public void DoorOpening(Floor f);
}
