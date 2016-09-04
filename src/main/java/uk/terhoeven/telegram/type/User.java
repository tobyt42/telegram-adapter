package uk.terhoeven.telegram.type;

import com.google.common.base.MoreObjects;

public class User
{
	private final int id;
	private final String firstName;
	private final String lastName;
	private final String username;

	public User(final int id, final String firstName, final String lastName, final String username)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}

	public int getId()
	{
		return id;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getUsername()
	{
		return username;
	}

	public String getLastName()
	{
		return lastName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).add("id", id).add("firstName", firstName).add("lastName", lastName).add("username", username).toString();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final User user = (User) o;

		return id == user.id;

	}

	@Override
	public int hashCode()
	{
		return id;
	}
}
