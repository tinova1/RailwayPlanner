package views;

import utils.Side;

class GUIBackend {
		
	TurnoutTypes turnoutType= TurnoutTypes.STRAIGHT;
	Side handiness = Side.RIGHT;
	double gauge = 16.5;
	
	/*
	 * Only for Straight Turnout
	 */
	double radius = 200;
	double angle = 15.;
	double length = 200.;
	double strLength = 50.;

	/*
	 * Only for Curved Turnout
	 */
	double grRadius = 500;
	double smRadius = 200;
	double grRadiusStrLen = 0;
	double smRadiusStrLen=0;
	double offset = 0;
	
	enum TurnoutTypes {
		STRAIGHT(0),CURVED(1),WYE(2);
		final int index;
		TurnoutTypes(int index){
			this.index=index;
		}
	}
}
