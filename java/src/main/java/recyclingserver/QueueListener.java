package recyclingserver;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * This class listens to the rabbitMQ queue. 
 * Items are to be put into the queue as "userId, locationID, time, paper, plastic, glas, metal" encoded in UTF-8 
 * @author Jorrit
 *
 */
public class QueueListener implements Runnable{
	
	BatchManager batman;
	String queueHost;
	String queueName;
	
	public QueueListener(String queueHost, String queueName, String dbNode){
		this.queueHost = queueHost;
		this.queueName = queueName;
		batman = new BatchManager(dbNode, 1, 60);
	}

	@Override
	public void run() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection;
		try {
			connection = factory.newConnection();

			Channel channel = connection.createChannel();
			channel.queueDeclare(queueName, true, false, false, null);
			
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, consumer);
			String transaction;
			while (true) {
				System.out.println("Waiting next queue delivery.");
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				transaction = new String(delivery.getBody(),"UTF-8");
				System.out.println("Got something:" + transaction);
				batman.addTransaction(transaction);
				
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShutdownSignalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
