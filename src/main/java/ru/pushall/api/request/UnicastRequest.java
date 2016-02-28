package ru.pushall.api.request;

import com.eclipsesource.json.JsonObject;
import ru.pushall.api.response.UnicastResponse;

public class UnicastRequest extends NotificationRequest<UnicastResponse>
{
	protected final long unicastUserId;

	UnicastRequest(long id, String key, String title, String text, String icon, String url, NotificationHideType hidden, NotificationPriority priority, int ttl, boolean background, long unicastUserId)
	{
		super(RequestType.UNICAST, id, key, title, text, icon, url, hidden, priority, ttl, background);
		this.unicastUserId = unicastUserId;
	}

	protected void appendQueryString(StringBuilder sb)
	{
		super.appendQueryString(sb);
		sb.append("&uid=");
		sb.append(unicastUserId);
	}

	@Override
	public UnicastResponse makeResult(JsonObject obj)
	{
		return new UnicastResponse(obj.getInt("success", -1));
	}
}
