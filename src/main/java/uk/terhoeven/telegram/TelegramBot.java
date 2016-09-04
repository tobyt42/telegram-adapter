package uk.terhoeven.telegram;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import rx.AsyncEmitter;
import rx.Observable;
import uk.terhoeven.telegram.mapper.TypeMapper;
import uk.terhoeven.telegram.type.Chat;
import uk.terhoeven.telegram.type.Message;
import uk.terhoeven.telegram.type.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TelegramBot
{
	public static final String API_URL = "https://api.telegram.org/bot";
	private final TypeMapper typeMapper;
	private final String token;
	private CloseableHttpClient httpClient;
	private int nextUpdate;
	private Observable<Message> observable;

	@Inject
	public TelegramBot(final TypeMapper typeMapper, @Assisted final String token)
	{
		this.typeMapper = typeMapper;
		this.token = token;
		init();
	}

	private void init()
	{
		httpClient = HttpClients.createDefault();
		observable = Observable.<Message>fromAsync(emitter -> {
			final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
			executor.scheduleAtFixedRate(() -> getUpdates(emitter), 0, 1, TimeUnit.SECONDS);
			emitter.setCancellation(executor::shutdown);
		}, AsyncEmitter.BackpressureMode.BUFFER);
	}

	private JSONObject request(final String method)
	{
		final String request = API_URL + token + "/" + method;
		try
		{
			return new JSONObject(IOUtils.toString(new URL(request), Charset.forName("UTF-8")));
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			return new JSONObject("{\"ok\":false}");
		}
	}

	private JSONObject request(final String method, final List<NameValuePair> parameters)
	{
		try
		{
			final HttpPost request = new HttpPost(API_URL + token + "/" + method);
			request.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			final HttpResponse response = httpClient.execute(request);
			final HttpEntity entity = response.getEntity();
			if (entity != null)
			{
				final InputStream stream = entity.getContent();
				final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				String line;
				final StringBuilder builder = new StringBuilder();
				while((line = reader.readLine()) != null)
				{
					builder.append(line);
				}
				stream.close();
				return new JSONObject(builder.toString());
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public User getMe()
	{
		final JSONObject json = request("getMe");
		if (json.getBoolean("ok"))
		{
			final JSONObject result = json.getJSONObject("result");
			return new User(result.getInt("id"), result.getString("first_name"), null, result.getString("username"));
		}

		return null;
	}

	public Message sendMessage(final Chat chat, final String text)
	{
		final List<NameValuePair> parameters = new ArrayList<>(2);
		parameters.add(new BasicNameValuePair("chat_id", Integer.toString(chat.getId())));
		parameters.add(new BasicNameValuePair("text", text));

		final JSONObject json = request("sendMessage", parameters);
		if (json != null && json.getBoolean("ok"))
		{
			return typeMapper.mapMessage(json.getJSONObject("result"));
		}
		return null;
	}

	public Observable<Message> getObservable()
	{
		return observable;
	}

	private void getUpdates(final AsyncEmitter<Message> emitter)
	{
		final JSONObject response = nextUpdate > 0
			? request("getUpdates", Collections.singletonList(new BasicNameValuePair("offset", Integer.toString(nextUpdate))))
			: request("getUpdates");
		if (response != null && response.getBoolean("ok")) {
			final JSONArray updates = response.getJSONArray("result");
			updates.forEach(o ->
			{
				final JSONObject node = (JSONObject) o;
				final int updateId = node.getInt("update_id");
				if (updateId >= nextUpdate)
				{
					nextUpdate = updateId + 1;
				}

				final Message message = typeMapper.mapMessage(node.getJSONObject("message"));
				emitter.onNext(message);
			});
		}
	}

	public interface TelegramBotFactory
	{
		TelegramBot create(final String token);
	}
}
