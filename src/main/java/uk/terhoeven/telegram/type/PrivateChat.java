package uk.terhoeven.telegram.type;

import com.google.common.base.MoreObjects;

public class PrivateChat extends Chat
{
	private final String firstName;
	private final String lastName;
	private final String userName;

	public PrivateChat(final int id, final String userName, final String firstName, final String lastName)
	{
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).add("firstName", firstName).add("lastName", lastName).add("userName", userName).add("id", getId()).toString();
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getUserName()
	{
		return userName;
	}
}
