package ru.pushall.api.request;

/**
 * Notification hide option type. This option can hide the notification from feed.
 */
public enum NotificationHideType
{
	NOT_HIDE(0),
	HIDE_FEED(1);
//	NOT_IMPLEMENT_FOR_NOW(2);

	private final int value;

	NotificationHideType(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	public static NotificationHideType forValue(int val)
	{
		switch(val)
		{
		case 0:
			return NOT_HIDE;
		default:
			return HIDE_FEED;
		}
	}
}
