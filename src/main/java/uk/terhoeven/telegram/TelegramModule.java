package uk.terhoeven.telegram;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class TelegramModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		install(new FactoryModuleBuilder()
		.implement(TelegramBot.class, TelegramBot.class)
		.build(TelegramBot.TelegramBotFactory.class));
	}
}
