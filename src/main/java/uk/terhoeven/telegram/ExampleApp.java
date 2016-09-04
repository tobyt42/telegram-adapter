package uk.terhoeven.telegram;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import rx.Observable;
import uk.terhoeven.telegram.type.Chat;
import uk.terhoeven.telegram.type.Message;
import uk.terhoeven.telegram.type.User;

public class ExampleApp
{
	private final TelegramBot.TelegramBotFactory botFactory;

	@Inject
	public ExampleApp(final TelegramBot.TelegramBotFactory botFactory)
	{
		this.botFactory = botFactory;
	}

	public static void main(final String[] args)
	{
		final Injector injector = Guice.createInjector(new TelegramModule());
		final ExampleApp app = injector.getInstance(ExampleApp.class);

		app.start(args);
	}

	private void start(final String[] args)
	{
		final String botToken = args[0];
		final TelegramBot bot = botFactory.create(botToken);
		System.out.println(bot.getMe());

		final Observable<Message> all = bot.getObservable();

		final User toby = new User(Integer.parseInt(args[1]), args[2], args[3], args[4]);
		final Observable<Message> tobysMessages = all.filter(message -> toby.equals(message.getSender()));

		all.subscribe(System.out::println);
		all.subscribe(message -> sendMessage(bot, message.getChat(), "You wrote: " + message.getText()));
		all.filter(message -> message.getText().toLowerCase().equals("ping"))
				.subscribe(message -> sendMessage(bot, message.getChat(), message.getText().replace('i', 'o').replace('I', 'O')));
		tobysMessages.subscribe(message -> sendMessage(bot, message.getChat(), "Message from Toby: " + message.getText()));
	}

	private void sendMessage(final TelegramBot bot, final Chat chat, final String text)
	{
		final Message message = bot.sendMessage(chat, text);
		System.out.println("Sent: " + message);
	}
}
