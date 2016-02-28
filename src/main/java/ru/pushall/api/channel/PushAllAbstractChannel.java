package ru.pushall.api.channel;

import ru.pushall.api.PushAllDefaults;
import ru.pushall.api.executor.AsyncRequestExecutor;
import ru.pushall.api.executor.RequestExecutor;

public abstract class PushAllAbstractChannel
{
	protected final long id;
	protected final String key;
	protected RequestExecutor executor = new AsyncRequestExecutor(PushAllDefaults.getDefaultExecutor(), PushAllDefaults.getDefaultLastRequestExecutor());

	PushAllAbstractChannel(long id, String key)
	{
		this.id = id;
		this.key = key;
	}

	public void setRequestExecutor(RequestExecutor executor)
	{
		this.executor = executor;
	}
}
