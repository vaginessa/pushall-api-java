package ru.pushall.api;

import ru.pushall.api.channel.PushAllChannel;
import ru.pushall.api.channel.PushAllSelfChannel;

/**
 *
 */
public class PushAll
{
	private PushAll(){}

	/**
	 * Creates new PushAll channel for Self notifications
	 * @param id unique id of the user
	 * @param key API key of the user
	 * @return {@link PushAllSelfChannel} associated with given user id
	 */
	public static PushAllSelfChannel getSelfChannel(long id, String key)
	{
		return new PushAllSelfChannel(id, key);
	}

	/**
	 * Creates new PushAll channel for Broadcast and Unicast notifications
	 * @param id unique id of the channel
	 * @param key API key of the channel
	 * @return {@link PushAllChannel} associated with given channel id
	 */
	public static PushAllChannel getChannel(long id, String key)
	{
		return new PushAllChannel(id, key);
	}

}
