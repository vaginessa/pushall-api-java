package ru.pushall.api.response;

public class UnicastResponse extends AbstractNotificationResponse
{
	public UnicastResponse(int success)
	{
		super(success);
	}

	@Override
	public String toString()
	{
		return "UnicastResponse{" +
				"success=" + success +
				'}';
	}
}
