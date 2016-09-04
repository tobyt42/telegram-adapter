package uk.terhoeven.telegram.type;

import com.google.common.base.MoreObjects;

public class User extends Actor
{
	private final String firstName;
	private final String lastName;
	private final String username;

	public User(final int id, final String firstName, final String lastName, final String username)
	{
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
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
		return MoreObjects.toStringHelper(this).add("id", getId()).add("firstName", firstName).add("lastName", lastName).add("username", username).toString();
	}
}
