package com.dagong.mq.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

	public static final int SEND_NUMBER = 5;

	public static void main(String[] args) throws JMSException {
		// ConnectionFactory�����ӹ���,JMS��������������
		ConnectionFactory connectionFactory;
		// Connection:JMS�ͻ��˵�JMS Provider������
		Connection connection = null;
		// Session:һ�����ͻ������Ϣ���߳�
		Session session;
		// Destination:��Ϣ��Ŀ�ĵ�,��Ϣ���͸�˭
		Destination destination;
		// MessageProducer:��Ϣ�ķ�����
		MessageProducer producer;
		// TextMessage message;
		// ����ConnectionFactoryʵ��,�˴�����ActiveMQ��ʵ��
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		// ����ӹ����õ����Ӷ���
		connection = connectionFactory.createConnection();
		// ����
		connection.start();
		// ��ȡ��������
		session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		// ��ȡsessionע�����ֵ
		destination = session.createQueue("firstQueue");
		// �õ���Ϣ������[������]
		producer = session.createProducer(destination);
		// ���ó־û�
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// ������Ϣ,�˴�д��,��Ŀ���ǲ���,���߷�����ȡ
		sendMessage(session, producer);
		session.commit();

	}

	private static void sendMessage(Session session, MessageProducer producer) throws JMSException {
		for (int i = 0; i < SEND_NUMBER+1000000000; i++) {
			TextMessage message = session.createTextMessage("AQ������Ϣ..."+i);
			//������Ϣ��Ŀ�ĵ�
			System.out.println("������Ϣ��"+"ActiveMQ���͵���Ϣ��"+i);
			producer.send(message);
		}
	}
}
