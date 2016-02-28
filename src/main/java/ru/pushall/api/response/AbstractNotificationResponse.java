package ru.pushall.api.response;

public abstract class AbstractNotificationResponse
{
	protected final int success;

	AbstractNotificationResponse(int success)
	{
		this.success = success;
	}

	/**
	 * @return 'success' response field: the number of <b>devices</b> that notification
	 * will be sent (the sum of all enabled 'unfilt' user devices)
	 */
	public int getDevicesToSend()
	{
		return success;
	}
}
