package ru.pushall.api.channel;

import ru.pushall.api.executor.RequestExecutor;
import ru.pushall.api.request.NotificationBuilder;
import ru.pushall.api.request.NotificationHideType;
import ru.pushall.api.request.NotificationPriority;
import ru.pushall.api.response.BroadcastResponse;
import ru.pushall.api.response.SelfResponse;
import ru.pushall.api.response.UnicastResponse;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class ExecutableNotificationBuilder<R>
{
	private final RequestExecutor executor;
	private final NotificationBuilder<R> notificationBuilder;

	private ExecutableNotificationBuilder(RequestExecutor executor, NotificationBuilder<R> notificationBuilder)
	{
		this.executor = executor;
		this.notificationBuilder = notificationBuilder;
	}

	/**
	 * Sets title of the notification. If not set or null present, default title will be used
	 * @param title the title of the notification
	 * @return this object
	 */
	public ExecutableNotificationBuilder<R> setTitle(String title)
	{
		notificationBuilder.setTitle(title);
		return this;
	}

	/**
	 * Sets text of the notification. May be empty or null
	 * @param text the text of the notification
	 * @return this object
	 */
	public ExecutableNotificationBuilder<R> setText(String text)
	{
		notificationBuilder.setText(text);
		return this;
	}

	/**
	 * Sets icon URL of the notification. If not set or null present, default channel icon will be used
	 * @param iconUrl the icon URL of the notification
	 * @return this object
	 */
	public ExecutableNotificationBuilder<R> setIcon(String iconUrl)
	{
		notificationBuilder.setIcon(iconUrl);
		return this;
	}

	/**
	 * Sets link URL of the notification
	 * @param url the link URL of the notification
	 * @return this object
	 */
	public ExecutableNotificationBuilder<R> setUrl(String url)
	{
		notificationBuilder.setUrl(url);
		return this;
	}

	/**
	 * Sets hidden type of the notification. This option can hide the notification from feed.
	 * See {@link NotificationHideType}
	 * @param hidden the hidden type of the notification
	 * @return this object
	 * @see NotificationHideType
	 */
	public ExecutableNotificationBuilder<R> setHidden(NotificationHideType hidden)
	{
		notificationBuilder.setHidden(hidden);
		return this;
	}

	/**
	 * Sets priority of the notification. Priority affects the notification effects on some clients (devises).
	 * For example, sound notification on android. See {@link NotificationPriority}
	 * @param priority the priority of the notification
	 * @return this object
	 * @see NotificationPriority
	 */
	public ExecutableNotificationBuilder<R> setPriority(NotificationPriority priority)
	{
		notificationBuilder.setPriority(priority);
		return this;
	}

	/**
	 * Sets TTL (Time To Live) value of the notification. If the client does not receive the notification
	 * within the specified time, the notification will be treated as expired and the client does not get
	 * it ever. If -1 specified, only online clients will receive the notification
	 * @param ttl the TTL (Time To Live) value of the notification in <b>seconds</b>
	 * @return this object
	 */
	public ExecutableNotificationBuilder<R> setTtl(int ttl)
	{
		notificationBuilder.setTtl(ttl);
		return this;
	}

	/**
	 * Sets TTL (Time To Live) value of the notification. If the client does not receive the notification
	 * within the specified time, the notification will be treated as expired and the client does not get
	 * it ever
	 * @param ttl the TTL (Time To Live) value of the notification
	 * @return this object
	 */
	public ExecutableNotificationBuilder<R> setTtl(Duration ttl)
	{
		notificationBuilder.setTtl((int)ttl.getSeconds());
		return this;
	}

	/**
	 * Sets 'background' flag of the request. If 'true' given, notifications will be sent in background,
	 * <b>after</b> the request execution. This option accelerates request execution but may affect
	 * the request response (for example, returned number of devices that notification will be sent).
	 * Use it if you don't use request response or use only 'lid' (LogId) field from request response.
	 * @param background the 'background' flag of the request
	 * @return this object
	 */
	public ExecutableNotificationBuilder<R> setBackground(boolean background)
	{
		notificationBuilder.setBackground(background);
		return this;
	}

	/**
	 * Send the notification to PushAll service via {@link RequestExecutor}. Since it is asynchronous
	 * by default, this method return {@link CompletableFuture} that provide server response. Use
	 * {@link CompletableFuture#join} method if you want "synchronous" behavior. You can also change
	 * {@link RequestExecutor} of the channel by {@link PushAllAbstractChannel#setRequestExecutor}
	 * @return {@link CompletableFuture} that provide server response
	 */
	public CompletableFuture<R> send()
	{
		return executor.executeRequest(notificationBuilder.build());
	}

	static ExecutableNotificationBuilder<SelfResponse> forSelf(RequestExecutor executor, long selfId, String key)
	{
		return new ExecutableNotificationBuilder<>(executor, NotificationBuilder.forSelf(selfId, key));
	}

	static ExecutableNotificationBuilder<BroadcastResponse> forBroadcast(RequestExecutor executor, long channelId, String key)
	{
		return new ExecutableNotificationBuilder<>(executor, NotificationBuilder.forBroadcast(channelId, key));
	}

	static ExecutableNotificationBuilder<UnicastResponse> forUnicast(RequestExecutor executor, long channelId, String key, long unicastUserId)
	{
		return new ExecutableNotificationBuilder<>(executor, NotificationBuilder.forUnicast(channelId, key, unicastUserId));
	}
}
