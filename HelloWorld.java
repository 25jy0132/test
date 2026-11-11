
interface MessageProvider {
    String getMessage();
}


class HelloWorldMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "Hello, World!";
    }
}


interface MessageRenderer {
    void render();
    void setMessageProvider(MessageProvider provider);
}


class StandardOutMessageRenderer implements MessageRenderer {
    private MessageProvider messageProvider;

    @Override
    public void render() {
        if (messageProvider == null) {
            throw new RuntimeException(
                "MessageProviderが設定されていません: " + StandardOutMessageRenderer.class.getName()
            );
        }
        System.out.println(messageProvider.getMessage());
    }

    @Override
    public void setMessageProvider(MessageProvider provider) {
        this.messageProvider = provider;
    }
}


class MessageSupportFactory {
    private static MessageSupportFactory instance;
    private MessageRenderer renderer;
    private MessageProvider provider;

    private MessageSupportFactory() {

        this.renderer = new StandardOutMessageRenderer();
        this.provider = new HelloWorldMessageProvider();
        this.renderer.setMessageProvider(this.provider);
    }

    public static synchronized MessageSupportFactory getInstance() {
        if (instance == null) {
            instance = new MessageSupportFactory();
        }
        return instance;
    }

    public MessageRenderer getMessageRenderer() {
        return renderer;
    }
}

// アプリケーションのエントリポイント
public class EnterpriseHelloWorld {
    public static void main(String[] args) {
        MessageRenderer renderer = MessageSupportFactory.getInstance().getMessageRenderer();
        renderer.render();
    }
}
