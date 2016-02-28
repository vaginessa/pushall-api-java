package ru.pushall.api.response;

public class SelfResponse extends AbstractLoggedNotificationResponse
{
	public SelfResponse(int success, int lid)
	{
		super(success, lid);
	}

	@Override
	public String toString()
	{
		return "SelfResponse{" +
				"success=" + success +
				", lid=" + lid +
				'}';
	}
}
