package uk.terhoeven.telegram.type;

import com.google.common.base.MoreObjects;

public abstract class Actor
{
	private final int id;

	public Actor(final int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).add("id", id).toString();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Actor user = (Actor) o;

		return id == user.id;

	}

	@Override
	public int hashCode()
	{
		return id;
	}
}
