package ru.pushall.api.executor;

import ru.pushall.api.request.PushAllRequest;
import ru.pushall.api.request.RequestType;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class AsyncRequestExecutor implements RequestExecutor
{
	private static final long BROADCAST_INTERVAL = 30_000_000_000L;
	private final ScheduledExecutorService executor;
	private final RequestExecutor delegate;
	private final AtomicLong nextBroadcast = new AtomicLong();

	public AsyncRequestExecutor(ScheduledExecutorService executor, RequestExecutor delegate)
	{
		this.executor = Objects.requireNonNull(executor);
		this.delegate = Objects.requireNonNull(delegate);
	}

	@Override
	public <R> CompletableFuture<R> executeRequest(PushAllRequest<R> request)
	{
		CompletableFuture<R> future = new CompletableFuture<>();
		QueuedRequest<R> qreq = new QueuedRequest<>(request, future);
		if(request.getType() == RequestType.BROADCAST)
		{
			long time = System.nanoTime();
			executor.schedule(qreq, Math.max(0, nextBroadcast.getAndUpdate(last -> Math.max(last, time) + BROADCAST_INTERVAL) - time), TimeUnit.NANOSECONDS);
		}
		else
		{
			executor.execute(qreq);
		}
		return future;
	}

	private class QueuedRequest<RESULT> implements Runnable
	{
		public final PushAllRequest<RESULT> request;
		public final CompletableFuture<RESULT> future;

		public QueuedRequest(PushAllRequest<RESULT> request, CompletableFuture<RESULT> future)
		{
			this.request = request;
			this.future = future;
		}

		@Override
		public void run()
		{
			delegate.executeRequest(request).whenComplete((r, t) -> {
				if(t != null)
					future.completeExceptionally(t);
				else
					future.complete(r);
			});
		}
	}
}
