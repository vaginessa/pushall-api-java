package ru.pushall.api;

import ru.pushall.api.executor.AsyncRequestExecutor;
import ru.pushall.api.executor.RequestExecutor;
import ru.pushall.api.executor.HttpRequestExecutor;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class PushAllDefaults
{
	private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(
			Integer.parseInt(AccessController.doPrivileged((PrivilegedAction<String>) () -> System.getProperty("ru.pushall.api.executor.threads", "2"))),
			new ThreadFactory()
			{
				private final AtomicInteger counter = new AtomicInteger();

				@Override
				public Thread newThread(Runnable r)
				{
					Thread thread = new Thread(r, "PushAll execution thread #" + counter.getAndIncrement());
					thread.setDaemon(true);
					return thread;
				}
			});
	private static RequestExecutor requestExecutor = new HttpRequestExecutor();

	static
	{
		if(Boolean.parseBoolean(AccessController.doPrivileged((PrivilegedAction<String>) () -> System.getProperty("ru.pushall.api.installStartComCrt", "true"))))
			StartComCrtInstaller.installCA();
	}

	private PushAllDefaults(){}

	/**
	 * This {@link ScheduledExecutorService} used in {@link ru.pushall.api.channel.PushAllAbstractChannel}
	 * to instantiate default {@link AsyncRequestExecutor} where used for
	 * async request execution
	 * @return the default {@link ScheduledExecutorService}
	 */
	public static ScheduledExecutorService getDefaultExecutor()
	{
		return executor;
	}

	/**
	 * Sets default {@link ScheduledExecutorService}, used in {@link ru.pushall.api.channel.PushAllAbstractChannel}
	 * to instantiate default {@link AsyncRequestExecutor} where used for
	 * async request execution
	 * @param executor new default {@link ScheduledExecutorService}
	 */
	public static void setDefaultExecutor(ScheduledExecutorService executor)
	{
		PushAllDefaults.executor = executor;
	}

	/**
	 * This {@link RequestExecutor} used in {@link ru.pushall.api.channel.PushAllAbstractChannel}
	 * to instantiate default {@link AsyncRequestExecutor} where used as
	 * "last" (or "final") executor, i.e. it's finally executing requests. You can change it, for example,
	 * to set proxy
	 * @return the default last {@link RequestExecutor}
	 */
	public static RequestExecutor getDefaultLastRequestExecutor()
	{
		return requestExecutor;
	}

	/**
	 * Sets default {@link RequestExecutor} used in {@link ru.pushall.api.channel.PushAllAbstractChannel}
	 * to instantiate default {@link AsyncRequestExecutor} where used as
	 * "last" (or "final") executor, i.e. it's finally executing requests. You can change it, for example,
	 * to set proxy
	 * @param requestExecutor new default last {@link RequestExecutor}
	 */
	public static void setDefaultLastRequestExecutor(RequestExecutor requestExecutor)
	{
		PushAllDefaults.requestExecutor = requestExecutor;
	}
}
