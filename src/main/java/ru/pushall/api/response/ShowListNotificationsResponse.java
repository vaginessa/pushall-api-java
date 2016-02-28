package ru.pushall.api.response;

import ru.pushall.api.request.NotificationHideType;

import java.util.List;
import java.util.Objects;

public class ShowListNotificationsResponse
{
	private final List<NotificationInfo> notifications;

	public ShowListNotificationsResponse(List<NotificationInfo> notifications)
	{
		this.notifications = Objects.requireNonNull(notifications);
	}

	/**
	 * @return list of the returned notifications
	 */
	public List<NotificationInfo> getNotifications()
	{
		return notifications;
	}

	/**
	 * If notification list size equals to one, return this one notification. Else return null
	 *
	 * @return one notification from list, or null if list size != 1
	 */
	public NotificationInfo getOneNotificationNullable()
	{
		if(notifications.size() != 1)
			return null;
		return notifications.get(0);
	}

	@Override
	public String toString()
	{
		return "ShowListNotificationsResponse{" +
				"notifications=" + notifications +
				'}';
	}

	public static class NotificationInfo
	{
		private final long lid;
		private final String title;
		private final String text;
		private final String url;
		private final String icon;
		private final NotificationHideType hidden;
		private final int success;
		private final int unfilt;
		private final int all;
		private final PostStat poststat;

		public NotificationInfo(long lid, String title, String text, String url, String icon, NotificationHideType hidden, int success, int unfilt, int all, PostStat poststat)
		{
			this.lid = lid;
			this.title = title;
			this.text = text;
			this.url = url;
			this.icon = icon;
			this.hidden = hidden;
			this.success = success;
			this.unfilt = unfilt;
			this.all = all;
			this.poststat = poststat;
		}

		/**
		 * @return unique id ('lid') of this notification, used in ShowList API
		 */
		public long getLogId()
		{
			return lid;
		}

		/**
		 * @return the title of the notification
		 */
		public String getTitle()
		{
			return title;
		}

		/**
		 * @return the text of the notification
		 */
		public String getText()
		{
			return text;
		}

		/**
		 * @return the link URL of the notification
		 */
		public String getUrl()
		{
			return url;
		}

		/**
		 * @return the icon URL of the notification
		 */
		public String getIcon()
		{
			return icon;
		}

		/**
		 * @return extended stats of the notification delivery
		 */
		public NotificationHideType getHidden()
		{
			return hidden;
		}

		/**
		 * @return '<code>success<code/>' response field: the number of <b>devices</b> that notification
		 * will be sent (the sum of all enabled '<code>unfilt<code/>' user devices)
		 */
		public int getDevicesToSend()
		{
			return success;
		}

		/**
		 * @return '<code>unfilt<code/>' response field: the number of <b>users</b> that notification
		 * will be sent (notification has not been filtered by custom filters)
		 */
		public int getUsersToSend()
		{
			return unfilt;
		}

		/**
		 * @return '<code>all<code/>' response field: the number of <b>users</b> who can receive
		 * notifications (subscribed to the channel and have at least one device)
		 */
		public int getAllUsers()
		{
			return all;
		}

		public PostStat getPostStat()
		{
			return poststat;
		}

		@Override
		public String toString()
		{
			return "NotificationInfo{" +
					"lid=" + lid +
					", title='" + title + '\'' +
					", text='" + text + '\'' +
					", url='" + url + '\'' +
					", icon='" + icon + '\'' +
					", hidden=" + hidden +
					", success=" + success +
					", unfilt=" + unfilt +
					", all=" + all +
					", poststat=" + poststat +
					'}';
		}

		public static class PostStat
		{
			private final int waiting;
			private final int waitingu;
			private final int accepted;
			private final int acceptedu;
			private final int opened;
			private final int openedu;
			private final int deleted;
			private final int deletedu;

			public PostStat(int waiting, int waitingu, int accepted, int acceptedu, int opened, int openedu, int deleted, int deletedu)
			{
				this.waiting = waiting;
				this.waitingu = waitingu;
				this.accepted = accepted;
				this.acceptedu = acceptedu;
				this.opened = opened;
				this.openedu = openedu;
				this.deleted = deleted;
				this.deletedu = deletedu;
			}

			/**
			 * @return 'waiting' response field: the number of <b>devices</b> that have
			 * <b>not</b> yet confirmed receipt of the notification.
			 */
			public int getWaitingDevices()
			{
				return waiting;
			}

			/**
			 * @return 'waitingu' response field: the number of <b>users</b> that have
			 * <b>not</b> yet confirmed receipt of the notification.
			 */
			public int getWaitingUsers()
			{
				return waitingu;
			}

			/**
			 * @return 'accepted' response field: the number of <b>devices</b> that have
			 * confirmed receipt of the notification.
			 */
			public int getAcceptedDevices()
			{
				return accepted;
			}

			/**
			 * @return 'acceptedu' response field: the number of <b>users</b> that have
			 * confirmed receipt of the notification.
			 */
			public int getAcceptedUsers()
			{
				return acceptedu;
			}

			/**
			 * A notification becomes 'opened' if user clicks on its link. Also, notification on one device
			 * can not have both 'opened' and 'deleted' status (acquiring 'opened' resets 'deleted' status)
			 *
			 * @return 'opened' response field: number of <b>devices</b> where the notification acquired
			 * 'opened' status
			 */
			public int getOpenedDevices()
			{
				return opened;
			}

			/**
			 * A notification becomes 'opened' if user clicks on its link. Also, notification on one device
			 * can not have both 'opened' and 'deleted' status (acquiring 'opened' resets 'deleted' status)
			 *
			 * @return 'openedu' response field: number of <b>users</b> where the notification acquired
			 * 'opened' status
			 */
			public int getOpenedUsers()
			{
				return openedu;
			}

			/**
			 * A notification becomes 'deleted' if user closes it while it is still displayed on screen.
			 * A notification will not become 'deleted' by removing from user history. Also, 'deleted'
			 * status will be reset when user opens the notification (at any time).
			 *
			 * @return 'deleted' response field: number of <b>devices</b> where the notification acquired
			 * 'deleted' status
			 */
			public int getDeletedDevices()
			{
				return deleted;
			}

			/**
			 * A notification becomes 'deleted' if user closes it while it is still displayed on screen.
			 * A notification will not become 'deleted' by removing from user history. Also, 'deleted'
			 * status will be reset when user opens the notification (at any time).
			 *
			 * @return 'deletedu' response field: number of <b>users</b> where the notification acquired
			 * 'deleted' status
			 */
			public int getDeletedUsers()
			{
				return deletedu;
			}

			@Override
			public String toString()
			{
				return "PostStat{" +
						"waiting=" + waiting +
						", waitingu=" + waitingu +
						", accepted=" + accepted +
						", acceptedu=" + acceptedu +
						", opened=" + opened +
						", openedu=" + openedu +
						", deleted=" + deleted +
						", deletedu=" + deletedu +
						'}';
			}
		}
	}
}
