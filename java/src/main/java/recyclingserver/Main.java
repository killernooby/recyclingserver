package recyclingserver;

public class Main {

	
	
	public static void main(String[] args) {
		QueueListener ql = new QueueListener("localhost","recycling","127.0.0.1");
		ql.run();
	}
}
