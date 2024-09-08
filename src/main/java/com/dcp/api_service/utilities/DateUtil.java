package com.dcp.api_service.utilities;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class DateUtil {
	public static boolean isOneDayLater(Timestamp problemSentAt) {
		long oneDayMillis = TimeUnit.DAYS.toMillis(1);
		long timeElapsed = System.currentTimeMillis() - problemSentAt.getTime();
		return timeElapsed >= oneDayMillis;
	}
}
