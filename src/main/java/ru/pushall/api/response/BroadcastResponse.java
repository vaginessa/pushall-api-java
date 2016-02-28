package ru.pushall.api.response;

public class BroadcastResponse extends AbstractLoggedNotificationResponse
{
	private final int unfilt;
	private final int all;

	public BroadcastResponse(int success, int unfilt, int all, int lid)
	{
		super(success, lid);
		this.unfilt = unfilt;
		this.all = all;
	}

	/**
	 * @return 'all' response field: the number of <b>users</b> who can receive
	 * notifications (subscribed to the channel and have at least one device)
	 */
	public int getAllUsers()
	{
		return all;
	}

	/**
	 * @return 'unfilt' response field: the number of <b>users</b> that notification
	 * will be sent (notification has not been filtered by custom filters)
	 */
	public int getUsersToSend()
	{
		return unfilt;
	}

	@Override
	public String toString()
	{
		return "BroadcastResponse{" +
				"success=" + success +
				", unfilt=" + unfilt +
				", all=" + all +
				", lid=" + lid +
				'}';
	}
}
