package ru.pushall.api.request;

public enum RequestType
{
	SELF("self"),
	UNICAST("unicast"),
	BROADCAST("broadcast"),
	SHOW_LIST("showlist");

	private final String value;

	RequestType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
