package uk.terhoeven.telegram.type;

import com.google.common.base.MoreObjects;

public class Message
{
	private final int date;
	private final Chat chat;
	private final int messageId;
	private final Actor from;
	private final String text;

	public Message(final int date, final Chat chat, final int messageId, final Actor from, final String text)
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

	public Actor getSender()
	{
		return from;
	}

	public String getText()
	{
		return text;
	}
}
