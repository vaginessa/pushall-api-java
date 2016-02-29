package ru.pushall.api.request;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import ru.pushall.api.response.ShowListUsersResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.pushall.api.request.JsonUtil.asString;

public class ShowListUsersRequest extends PushAllRequest<ShowListUsersResponse>
{
	private final long uid;

	public ShowListUsersRequest(long id, String key)
	{
		super(RequestType.SHOW_LIST, id, key);
		this.uid = -1;
	}

	public ShowListUsersRequest(long id, String key, long uid)
	{
		super(RequestType.SHOW_LIST, id, key);
		this.uid = uid;
	}

	@Override
	protected void appendQueryString(StringBuilder sb)
	{
		super.appendQueryString(sb);
		sb.append("&subtype=users");
		if(uid >= 0)
		{
			sb.append("&uid=");
			sb.append(uid);
		}
	}

	@Override
	public ShowListUsersResponse makeResult(JsonObject obj)
	{
		JsonValue dataVal = obj.get("data");
		if(dataVal == null || dataVal.isNull())
			return new ShowListUsersResponse(Collections.emptyList());
		JsonObject data = dataVal.asObject();
		List<ShowListUsersResponse.UserInfo> list = new ArrayList<>(data.size());
		for(JsonObject.Member mem : data)
		{
			JsonObject val = mem.getValue().asObject();
			list.add(new ShowListUsersResponse.UserInfo(
					Long.parseLong(mem.getName()),
					asString(val.get("name")),
					asString(val.get("link")),
					asString(val.get("pic"))
			));
		}
		return new ShowListUsersResponse(list);
	}
}
