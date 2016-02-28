package ru.pushall.api.request;

import com.eclipsesource.json.JsonObject;

public abstract class PushAllRequest<R>
{
	protected final RequestType type;
	protected final long id;
	protected final String key;

	protected PushAllRequest(RequestType type, long id, String key)
	{
		this.type = type;
		this.id = id;
		this.key = key;
	}

	public RequestType getType()
	{
		return type;
	}

	public final String buildQueryString()
	{
		StringBuilder sb = new StringBuilder();
		appendQueryString(sb);
		return sb.toString();
	}

	protected void appendQueryString(StringBuilder sb)
	{
		sb.append("type=");
		sb.append(getType().getValue());
		sb.append("&id=");
		sb.append(id);
		sb.append("&key=");
		sb.append(key);
	}

	public abstract R makeResult(JsonObject obj);
}
