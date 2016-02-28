package ru.pushall.api.response;

public class AbstractLoggedNotificationResponse extends AbstractNotificationResponse
{
	protected final int lid;

	AbstractLoggedNotificationResponse(int success, int lid)
	{
		super(success);
		this.lid = lid;
	}

	/**
	 * @return 'lid' response field: unique id of this notification, used in ShowList API
	 */
	public int getLogId()
	{
		return lid;
	}
}
