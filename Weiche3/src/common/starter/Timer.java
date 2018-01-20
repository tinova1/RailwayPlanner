package common.starter;

public class Timer {
	long time; // current time in millis

	public Timer() {
		time = System.currentTimeMillis();
	}

	public double stopTime() {
		return (System.currentTimeMillis() - time) / 1000.;
	}
}
