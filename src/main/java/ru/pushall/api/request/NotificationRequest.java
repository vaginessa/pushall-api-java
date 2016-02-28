package ru.pushall.api.request;

public abstract class NotificationRequest<R> extends PushAllRequest<R>
{
	protected final String title;
	protected final String text;
	protected final String icon;
	protected final String url;
	protected final NotificationHideType hidden;
	protected final NotificationPriority priority;
	protected final int ttl;
	protected final boolean background;

	protected NotificationRequest(RequestType type, long id, String key, String title, String text, String icon,
								  String url, NotificationHideType hidden, NotificationPriority priority, int ttl,
								  boolean background)
	{
		super(type, id, key);
		this.title = title;
		this.text = text;
		this.icon = icon;
		this.url = url;
		this.hidden = hidden;
		this.priority = priority;
		this.ttl = ttl;
		this.background = background;
	}

	protected void appendQueryString(StringBuilder sb)
	{
		super.appendQueryString(sb);
		if(title != null)
		{
			sb.append("&title=");
			sb.append(title);
		}
		if(text != null)
		{
			sb.append("&text=");
			sb.append(text);
		}
		if(icon != null)
		{
			sb.append("&icon=");
			sb.append(icon);
		}
		if(url != null)
		{
			sb.append("&url=");
			sb.append(url);
		}
		if(hidden != NotificationHideType.NOT_HIDE)
		{
			sb.append("&hidden=");
			sb.append(hidden.getValue());
		}
		if(priority != NotificationPriority.DEFAULT)
		{
			sb.append("&priority=");
			sb.append(priority.getValue());
		}
		if(ttl != Integer.MIN_VALUE)
		{
			sb.append("&ttl=");
			sb.append(ttl);
		}
		if(background)
		{
			sb.append("&background=1");
		}
	}


}
