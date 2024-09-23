public class Time_320 {

	private int hour = 0;
	private int minute = 0;
	private int second = 0;


	public Time_320() {

	}



	public void setTime(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		if(hour>= 0 && hour<24) {
			this.hour = hour;
		} else {
			throw new IllegalArgumentException("Stunde muss zwischen 0 und 23 sein.");
		}
	}


	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		if(minute>= 0 && minute<60) {
			this.minute = minute;
		} else {
			throw new IllegalArgumentException("Minute muss zwischen 0 und 59 sein.");
		}
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		if(second>= 0 && second<60) {
			this.second = second;
		} else {
			throw new IllegalArgumentException("Second muss zwischen 0 und 59 sein.");
		}
	}


	public void nextSecond() {
		this.second += 1;
		if(second >= 60) {
			second = 0;
			minute++;
			if(minute >= 60) {
				minute = 0;
				hour++;
				if(hour >= 24) {
					hour = 0;
				}
			}
		}
	}



	public static void main(String[] args) {

		Time_320 time = new Time_320();
		time.setTime(1,2,3);
		time.setHour(2);
		time.setMinute(59);
		time.setSecond(59);
		time.nextSecond();

		time.nextSecond();

		System.out.println(time.getHour());
		System.out.println(time.getMinute());
		System.out.println(time.getSecond());


	}


	@Override
	public String toString() {
		return "Time_320{" +
				"hour=" + hour +
				", minute=" + minute +
				", second=" + second +
				'}';
	}


}
