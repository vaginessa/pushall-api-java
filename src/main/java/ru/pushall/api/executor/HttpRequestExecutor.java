package ru.pushall.api.executor;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import ru.pushall.api.request.PushAllRequest;
import ru.pushall.api.response.PushAllErrorException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;

public class HttpRequestExecutor implements RequestExecutor
{
	private static final String DEFAULT_API_URL = "https://pushall.ru/api.php";
	private static final Charset DEFAULT_CHARSET = Charset.forName("utf8");

	private final String apiUrl;
	private final Proxy proxy;

	public HttpRequestExecutor(String apiUrl, Proxy proxy)
	{
		this.apiUrl = apiUrl;
		this.proxy = proxy;
	}

	public HttpRequestExecutor(Proxy proxy)
	{
		this(DEFAULT_API_URL, proxy);
	}

	public HttpRequestExecutor(String apiUrl)
	{
		this(apiUrl, null);
	}

	public HttpRequestExecutor()
	{
		this(DEFAULT_API_URL, null);
	}

	@Override
	public <R> CompletableFuture<R> executeRequest(PushAllRequest<R> request)
	{
		try
		{
			byte[] postData = request.buildQueryString().getBytes(DEFAULT_CHARSET);
			URL url = new URL(apiUrl);
			HttpURLConnection http = (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Length", Integer.toString(postData.length));
			http.setUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);

			OutputStream out = http.getOutputStream();
			out.write(postData);
			out.flush();
			out.close();

			InputStreamReader reader = new InputStreamReader(http.getInputStream(), DEFAULT_CHARSET);
			int len = http.getContentLength();
			StringBuilder sb = new StringBuilder(len != -1 ? len : 128);
			char[] buffer = new char[512];
			int n;
			while((n = reader.read(buffer)) != -1)
				sb.append(buffer, 0, n);
			reader.close();

			String responseStr = sb.toString();
			JsonObject json = Json.parse(responseStr).asObject();
			String error = json.getString("error", null);
			if(error != null)
				throw new PushAllErrorException(error);
			else
				return CompletableFuture.completedFuture(request.makeResult(json));
		}
		catch (Exception e)
		{
			CompletableFuture<R> fut = new CompletableFuture<>();
			fut.completeExceptionally(e);
			return fut;
		}
	}
}
