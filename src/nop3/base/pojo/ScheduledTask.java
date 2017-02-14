package base.pojo;

import java.util.Date;

import base.interfaces.Scheduled;

public class ScheduledTask implements Scheduled {
	private Date startDateTime;
	private Date endDateTime;

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	@Override
	public String toString() {
		return "startDateTime:" + startDateTime.toString() + ";"
				+ "endDateTime:" + endDateTime.toString();
	}
}
