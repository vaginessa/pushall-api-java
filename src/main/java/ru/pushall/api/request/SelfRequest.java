package ru.pushall.api.request;

import com.eclipsesource.json.JsonObject;
import ru.pushall.api.response.SelfResponse;

public class SelfRequest extends NotificationRequest<SelfResponse>
{
	SelfRequest(long id, String key, String title, String text, String icon, String url, NotificationHideType hidden, NotificationPriority priority, int ttl, boolean background)
	{
		super(RequestType.SELF, id, key, title, text, icon, url, hidden, priority, ttl, background);
	}

	@Override
	public SelfResponse makeResult(JsonObject obj)
	{
		return new SelfResponse(obj.getInt("success", -1), obj.getInt("lid", -1));
	}
}
