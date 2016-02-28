package ru.pushall.api.request;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import ru.pushall.api.response.ShowListNotificationsResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowListNotificationsRequest extends PushAllRequest<ShowListNotificationsResponse>
{
	private final long lid;
	private final int limit;

	public ShowListNotificationsRequest(long id, String key)
	{
		super(RequestType.SHOW_LIST, id, key);
		this.lid = -1;
		this.limit = -1;
	}

	public ShowListNotificationsRequest(long id, String key, long lid)
	{
		super(RequestType.SHOW_LIST, id, key);
		this.lid = lid;
		this.limit = -1;
	}

	public ShowListNotificationsRequest(long id, String key, int limit)
	{
		super(RequestType.SHOW_LIST, id, key);
		this.lid = -1;
		this.limit = limit;
	}

	@Override
	protected void appendQueryString(StringBuilder sb)
	{
		super.appendQueryString(sb);
		if(lid >= 0)
		{
			sb.append("&lid=");
			sb.append(lid);
		}
		else if(limit > 0)
		{
			sb.append("&limit=");
			sb.append(limit);
		}
	}

	@Override
	public ShowListNotificationsResponse makeResult(JsonObject obj)
	{
		JsonValue dataVal = obj.get("data");
		if(dataVal == null || dataVal.isNull())
			return new ShowListNotificationsResponse(Collections.emptyList());
		JsonObject data = dataVal.asObject();
		List<ShowListNotificationsResponse.NotificationInfo> list = new ArrayList<>(data.size());
		for(JsonObject.Member mem : data)
		{
			JsonObject val = mem.getValue().asObject();
			JsonObject postStat = val.get("poststat").asObject();
			list.add(new ShowListNotificationsResponse.NotificationInfo(
					Long.parseLong(mem.getName()),
					val.getString("title", null),
					val.getString("text", null),
					val.getString("url", null),
					val.getString("icon", null),
					NotificationHideType.forValue(val.getInt("hidden", 0)),
					val.getInt("success", 0),
					val.getInt("unfilt", 0),
					val.getInt("all", 0),
					new ShowListNotificationsResponse.NotificationInfo.PostStat(
							postStat.getInt("waiting", 0),
							postStat.getInt("waitingu", 0),
							postStat.getInt("accepted", 0),
							postStat.getInt("acceptedu", 0),
							postStat.getInt("opened", 0),
							postStat.getInt("openedu", 0),
							postStat.getInt("deleted", 0),
							postStat.getInt("deletedu", 0)
					)
			));
		}
		return new ShowListNotificationsResponse(list);
	}
}
