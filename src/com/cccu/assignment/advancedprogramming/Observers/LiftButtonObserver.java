package com.cccu.assignment.advancedprogramming.Observers;

import com.cccu.assignment.advancedprogramming.Subjects.Floor;

public interface LiftButtonObserver {
	public void ButtonPressOnLift(Floor f);
	public void ButtonReleaseOnLift(Floor f);
}
