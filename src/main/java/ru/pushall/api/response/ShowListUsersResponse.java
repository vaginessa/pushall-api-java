package ru.pushall.api.response;

import java.util.List;
import java.util.Objects;

public class ShowListUsersResponse
{
	private final List<UserInfo> users;

	public ShowListUsersResponse(List<UserInfo> users)
	{
		this.users = Objects.requireNonNull(users);
	}

	/**
	 * @return list of the returned users
	 */
	public List<UserInfo> getUsers()
	{
		return users;
	}

	/**
	 * If user list size equals to one, return this one user. Else return null
	 *
	 * @return one user from list, or null if list size != 1
	 */
	public UserInfo getOneUserNullable()
	{
		if(users.size() != 1)
			return null;
		return users.get(0);
	}

	@Override
	public String toString()
	{
		return "ShowListUsersResponse{" +
				"users=" + users +
				'}';
	}

	public static class UserInfo
	{
		private final long uid;
		private final String name;
		private final String link;
		private final String pic;

		public UserInfo(long uid, String name, String link, String pic)
		{
			this.uid = uid;
			this.name = name;
			this.link = link;
			this.pic = pic;
		}

		/**
		 * @return unique id od the user
		 */
		public long getUserId()
		{
			return uid;
		}

		/**
		 * @return name of the user (username)
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * @return URL to google+ page of the user
		 */
		public String getLink()
		{
			return link;
		}

		/**
		 * @return URL to avatar image of the user (from google+)
		 */
		public String getPic()
		{
			return pic;
		}

		@Override
		public String toString()
		{
			return "UserInfo{" +
					"uid=" + uid +
					", name='" + name + '\'' +
					", link='" + link + '\'' +
					", pic='" + pic + '\'' +
					'}';
		}
	}
}
