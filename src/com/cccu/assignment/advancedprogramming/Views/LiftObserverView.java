package com.cccu.assignment.advancedprogramming.Views;

import com.cccu.assignment.advancedprogramming.Subjects.Floor;

public interface LiftObserverView {
	public void LiftGoingToFloor(Floor f);
	public void ArrivedAtNewFloor(Floor f);
}
