package uk.terhoeven.telegram.mapper;

import com.google.inject.Singleton;
import org.json.JSONObject;
import uk.terhoeven.telegram.type.*;

@Singleton
public class TypeMapper
{
	public Message mapMessage(final JSONObject json)
	{
		final int date = json.getInt("date");
		final Chat chat = mapChat(json.getJSONObject("chat"));
		final int messageId = json.getInt("message_id");
		final Actor from = mapActor(json.getJSONObject("from"));
		final String text = json.has("text") ? json.getString("text") : "ERROR: only text messages supported at the moment";

		return new Message(date, chat, messageId, from, text);
	}

	private Actor mapActor(final JSONObject json)
	{
		final String firstName = json.getString("first_name");
		final int id = json.getInt("id");
		final String userName = json.getString("username");

		if (json.has("last_name"))
		{
			final String lastName = json.getString("last_name");
			return new User(id, firstName, lastName, userName);
		}
		else
		{
			return new Bot(id, firstName, userName);
		}
	}

	private Chat mapChat(final JSONObject json)
	{
		final ChatType type = ChatType.parse(json.getString("type"));
		final int id = json.getInt("id");
		if(type == ChatType.GROUP)
		{
			final String title = json.getString("title");
			return new GroupChat(id, title);
		}
		else
		{
			final String firstName = json.getString("first_name");
			final String lastName = json.getString("last_name");
			final String userName = json.getString("username");
			return new PrivateChat(id, userName, firstName, lastName);
		}
	}
}
