package ru.pushall.api.executor;

import ru.pushall.api.request.PushAllRequest;

import java.util.concurrent.CompletableFuture;

public interface RequestExecutor
{
	<T> CompletableFuture<T> executeRequest(PushAllRequest<T> request);
}
