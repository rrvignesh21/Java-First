import java.util.Objects;

public class ClassFileDemo {
	public static void main(String[] args) {
		System.out.println(new MessageGenerator().genetateMessage("Vipul"));
	}
}

class MessageGenerator {
	public String genetateMessage(String username) {
		if (Objects.nonNull(username))
			return "Hello " + username + "!";
		else
			return "Hello world!";
	}
}