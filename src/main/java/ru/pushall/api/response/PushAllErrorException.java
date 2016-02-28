package ru.pushall.api.response;

/**
 * This exception thrown if PushAll service return 'error' response.
 * Known error messages:
 * <ul>
 *     <li>'wrong key' - if given key does not match real channel key</li>
 *     <li>
 *         'not so fast' - if sending notifications too frequently (completely
 *         leveled by {@link ru.pushall.api.executor.AsyncRequestExecutor})
 *     </li>
 *     <li>
 *         'duplicate in 10min' - if two notifications with the same title and text
 *         were sent to the same channel at intervals of less than 10 minutes
 *     </li>
 * <ul/>
 */
public class PushAllErrorException extends RuntimeException
{
	private final String pushAllErrorMessage;

	public PushAllErrorException(String pushAllErrorMessage)
	{
		super("PushAll service returned error message: " + pushAllErrorMessage);
		this.pushAllErrorMessage = pushAllErrorMessage;
	}

	/**
	 * @return PushAll error message
	 */
	public String getPushAllErrorMessage()
	{
		return pushAllErrorMessage;
	}
}
