package uk.terhoeven.telegram.type;

import com.google.common.base.MoreObjects;

public class Message
{
	private final int date;
	private final Chat chat;
	private final int messageId;
	private final User from;
	private final String text;

	public Message(final int date, final Chat chat, final int messageId, final User from, final String text)
	{
		this.date = date;
		this.chat = chat;
		this.messageId = messageId;
		this.from = from;
		this.text = text;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).add("date", date).add("chat", chat).add("messageId", messageId).add("from", from).add("text", text).toString();
	}

	public int getDate()
	{
		return date;
	}

	public Chat getChat()
	{
		return chat;
	}

	public int getMessageId()
	{
		return messageId;
	}

	public User getSender()
	{
		return from;
	}

	public String getText()
	{
		return text;
	}
}
