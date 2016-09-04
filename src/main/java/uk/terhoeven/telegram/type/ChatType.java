package uk.terhoeven.telegram.type;

public enum ChatType
{
	PRIVATE,
	GROUP,
	SUPERGROUP,
	CHANNEL,
	UNKNOWN;

	public static ChatType parse(final String type)
	{
		final String upperCaseType = type.toUpperCase();

		for(final ChatType chatType : ChatType.values())
		{
			if (chatType.name().equals(upperCaseType))
			{
				return chatType;
			}
		}
		return UNKNOWN;
	}
}
