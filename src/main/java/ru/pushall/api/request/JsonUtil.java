package ru.pushall.api.request;

import com.eclipsesource.json.JsonValue;

class JsonUtil
{
	static String asString(JsonValue val)
	{
		if(val == null)
			return null;
		if(val.isString())
			return val.asString();
		return val.toString();
	}
}
