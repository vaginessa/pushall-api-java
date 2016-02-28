package ru.pushall.api.request;

import com.eclipsesource.json.JsonObject;
import ru.pushall.api.response.BroadcastResponse;

public class BroadcastRequest extends NotificationRequest<BroadcastResponse>
{
	BroadcastRequest(long id, String key, String title, String text, String icon, String url, NotificationHideType hidden, NotificationPriority priority, int ttl, boolean background)
	{
		super(RequestType.BROADCAST, id, key, title, text, icon, url, hidden, priority, ttl, background);
	}

	@Override
	public BroadcastResponse makeResult(JsonObject obj)
	{
		return new BroadcastResponse(obj.getInt("success", -1), obj.getInt("unfilt", -1), obj.getInt("all", -1), obj.getInt("lid", -1));
	}
}
