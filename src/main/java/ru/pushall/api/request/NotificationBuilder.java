package ru.pushall.api.request;

import ru.pushall.api.response.BroadcastResponse;
import ru.pushall.api.response.SelfResponse;
import ru.pushall.api.response.UnicastResponse;

import java.util.Objects;

public class NotificationBuilder<R>
{
	private final RequestType type;
	private final long id;
	private final String key;
	private final long unicastUserId;
	private String title;
	private String text;
	private String icon;
	private String url;
	private NotificationHideType hidden = NotificationHideType.NOT_HIDE;
	private NotificationPriority priority = NotificationPriority.DEFAULT;
	private int ttl = Integer.MIN_VALUE;
	private boolean background;

	private NotificationBuilder(RequestType type, long id, String key, long unicastUserId)
	{
		this.type = type;
		this.id = id;
		this.key = key;
		this.unicastUserId = unicastUserId;
	}

	public NotificationBuilder<R> setTitle(String title)
	{
		this.title = title;
		return this;
	}

	public NotificationBuilder<R> setText(String text)
	{
		this.text = text;
		return this;
	}

	public NotificationBuilder<R> setIcon(String icon)
	{
		this.icon = icon;
		return this;
	}

	public NotificationBuilder<R> setUrl(String url)
	{
		this.url = url;
		return this;
	}

	public NotificationBuilder<R> setHidden(NotificationHideType hidden)
	{
		this.hidden = Objects.requireNonNull(hidden);
		return this;
	}

	public NotificationBuilder<R> setPriority(NotificationPriority priority)
	{
		this.priority = Objects.requireNonNull(priority);
		return this;
	}

	public NotificationBuilder<R> setTtl(int ttl)
	{
		this.ttl = ttl;
		return this;
	}

	public NotificationBuilder<R> setBackground(boolean background)
	{
		this.background = background;
		return this;
	}

	@SuppressWarnings("unchecked")
	public NotificationRequest<R> build()
	{
		if(type == RequestType.BROADCAST)
			return (NotificationRequest<R>) new BroadcastRequest(id, key, title, text, icon, url, hidden, priority, ttl, background);
		if(type == RequestType.SELF)
			return (NotificationRequest<R>) new SelfRequest(id, key, title, text, icon, url, hidden, priority, ttl, background);
		if(type == RequestType.UNICAST)
			return (NotificationRequest<R>) new UnicastRequest(id, key, title, text, icon, url, hidden, priority, ttl, background, unicastUserId);
		throw new UnsupportedOperationException();
	}

	public static NotificationBuilder<SelfResponse> forSelf(long selfId, String key)
	{
		return new NotificationBuilder<>(RequestType.SELF, selfId, key, 0);
	}

	public static NotificationBuilder<BroadcastResponse> forBroadcast(long channelId, String key)
	{
		return new NotificationBuilder<>(RequestType.BROADCAST, channelId, key, 0);
	}

	public static NotificationBuilder<UnicastResponse> forUnicast(long channelId, String key, long unicastUserId)
	{
		return new NotificationBuilder<>(RequestType.UNICAST, channelId, key, unicastUserId);
	}
}
