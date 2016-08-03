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
		// ConnectionFactory：连接工厂,JMS用它来创建连接
		ConnectionFactory connectionFactory;
		// Connection:JMS客户端到JMS Provider的连接
		Connection connection = null;
		// Session:一个发送或接收消息的线程
		Session session;
		// Destination:消息的目的地,消息发送给谁
		Destination destination;
		// MessageProducer:消息的发送者
		MessageProducer producer;
		// TextMessage message;
		// 构造ConnectionFactory实例,此处采用ActiveMQ的实现
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		// 构造从工厂得到连接对象
		connection = connectionFactory.createConnection();
		// 启动
		connection.start();
		// 获取操作连接
		session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		// 获取session注意参数值
		destination = session.createQueue("firstQueue");
		// 得到消息生产者[发送者]
		producer = session.createProducer(destination);
		// 设置持久化
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// 构造消息,此处写死,项目就是参数,或者方法获取
		sendMessage(session, producer);
		session.commit();

	}

	private static void sendMessage(Session session, MessageProducer producer) throws JMSException {
		for (int i = 0; i < SEND_NUMBER+1000000000; i++) {
			TextMessage message = session.createTextMessage("AQ发送消息..."+i);
			//发送消息到目的地
			System.out.println("发送消息："+"ActiveMQ发送的消息："+i);
			producer.send(message);
		}
	}
}
