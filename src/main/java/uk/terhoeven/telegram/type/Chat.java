package uk.terhoeven.telegram.type;

public abstract class Chat
{
	private final int id;

	public Chat(final int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Chat chat = (Chat) o;

		return id == chat.id;

	}

	@Override
	public int hashCode()
	{
		return id;
	}
}
