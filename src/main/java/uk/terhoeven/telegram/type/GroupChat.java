package uk.terhoeven.telegram.type;

import com.google.common.base.MoreObjects;

public class GroupChat extends Chat
{
	private final String title;

	public GroupChat(final int id, final String title)
	{
		super(id);
		this.title = title;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).add("id", getId()).add("title", title).toString();
	}
}
