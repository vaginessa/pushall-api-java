package ru.pushall.api.channel;

import ru.pushall.api.response.SelfResponse;

public class PushAllSelfChannel extends PushAllAbstractChannel
{
	public PushAllSelfChannel(long selfId, String key)
	{
		super(selfId, key);
	}

	public ExecutableNotificationBuilder<SelfResponse> newSelfRequest()
	{
		return ExecutableNotificationBuilder.forSelf(executor, id, key);
	}
}
