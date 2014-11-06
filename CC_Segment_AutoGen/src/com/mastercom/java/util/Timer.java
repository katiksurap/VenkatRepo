package com.mastercom.java.util;

import java.util.Calendar;


public class Timer {

	  private long startTime = 0;
	  private long endTime   = 0;

	  public void start(){
	    this.startTime = System.currentTimeMillis();
	  }

	  public void end() {
	    this.endTime   = System.currentTimeMillis();  
	  }

	  public long getStartTime() {
	    return this.startTime;
	  }

	  public long getEndTime() {
	    return this.endTime;
	  }

	  public String getTotalTime() {
	    long millis=this.endTime - this.startTime;
	    Calendar c=Calendar.getInstance();
	    c.setTimeInMillis(millis);
	    int hours=c.get(Calendar.HOUR);
	    int minutes=c.get(Calendar.MINUTE);
	    //long MinutesHours=(hours*60)+minutes;
	    return ""+minutes+" Minutes";
	  }
	}
