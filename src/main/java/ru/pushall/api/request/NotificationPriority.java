package ru.pushall.api.request;


/**
 * Notification priority type. Priority affects the notification effects on some clients (devises).
 * For example, sound notification on android.
 */
public enum NotificationPriority
{
	HIGH(1),
	DEFAULT(0),
	LOW(-1);

	private final int value;

	NotificationPriority(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
}
