package ru.pushall.api.channel;

import ru.pushall.api.request.ShowListNotificationsRequest;
import ru.pushall.api.request.ShowListUsersRequest;
import ru.pushall.api.response.BroadcastResponse;
import ru.pushall.api.response.ShowListNotificationsResponse;
import ru.pushall.api.response.ShowListNotificationsResponse.NotificationInfo;
import ru.pushall.api.response.ShowListUsersResponse;
import ru.pushall.api.response.ShowListUsersResponse.UserInfo;
import ru.pushall.api.response.UnicastResponse;

import java.util.concurrent.CompletableFuture;

public class PushAllChannel extends PushAllAbstractChannel
{
	public PushAllChannel(long id, String key)
	{
		super(id, key);
	}

	/**
	 * Creates new <b>broadcast</b> notification builder. Broadcast notification
	 * sends to <b>all</b> users in channel.
	 * @return new {@link ExecutableNotificationBuilder} for <b>broadcast</b> notification
	 */
	public ExecutableNotificationBuilder<BroadcastResponse> newBroadcast()
	{
		return ExecutableNotificationBuilder.forBroadcast(executor, id, key);
	}

	/**
	 * Creates new <b>unicast</b> notification builder. Unicast notification
	 * sends to <b>one</b> users in channel.
	 * @param unicastUserId id of the user, the notification will be sent to
	 * @return new {@link ExecutableNotificationBuilder} for <b>unicast</b> notification
	 */
	public ExecutableNotificationBuilder<UnicastResponse> newUnicast(long unicastUserId)
	{
		return ExecutableNotificationBuilder.forUnicast(executor, id, key, unicastUserId);
	}

	/**
	 * Requests information about last notifications on this channel
	 * @param limit maximal amount of notifications in response. Can't be
	 *  greater than 200.
	 * @return {@link CompletableFuture} that provide server response
	 */
	public CompletableFuture<ShowListNotificationsResponse> showListNotifications(int limit)
	{
		return executor.executeRequest(new ShowListNotificationsRequest(id, key, limit));
	}

	/**
	 * Requests information about notification with specified log id (lid).
	 * @param logId id of the notification
	 * @return {@link CompletableFuture} that provide server response. Note that completed
	 * value is nullable (null if notification with specified log id not found)
	 */
	public CompletableFuture<NotificationInfo> showListNotification(long logId)
	{
		return executor.executeRequest(new ShowListNotificationsRequest(id, key, logId))
				.thenApply(ShowListNotificationsResponse::getOneNotificationNullable);
	}

	/**
	 * Requests information about channel subscribers
	 * @return {@link CompletableFuture} that provide server response: list of all
	 * subscribers of the channel
	 */
	public CompletableFuture<ShowListUsersResponse> showListUsers()
	{
		return executor.executeRequest(new ShowListUsersRequest(id, key));
	}

	/**
	 * Requests information about one channel subscriber with specified user id
	 * @param userId id of the user
	 * @return {@link CompletableFuture} that provide server response. Note that completed
	 * value is nullable (null if user with specified id not found)
	 */
	public CompletableFuture<UserInfo> showListUser(long userId)
	{
		return executor.executeRequest(new ShowListUsersRequest(id, key, userId))
				.thenApply(ShowListUsersResponse::getOneUserNullable);
	}
}
