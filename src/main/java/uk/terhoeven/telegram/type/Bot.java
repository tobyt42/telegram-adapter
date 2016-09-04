package uk.terhoeven.telegram.type;

import com.google.common.base.MoreObjects;

public class Bot extends Actor
{
	private final String name;
	private final String username;

	public Bot(final int id, final String name, final String username)
	{
		super(id);
		this.name = name;
		this.username = username;
	}

	public String getName()
	{
		return name;
	}

	public String getUsername()
	{
		return username;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).add("id", getId()).add("name", name).add("username", username).toString();
	}
}
