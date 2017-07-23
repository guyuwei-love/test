package com.gyw.jms.test;

import java.util.Properties;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class MessageProducter {
	public static void main(String[] args) {
		String queueConnectionFactoryName = "myjmsconnectionfactory"; //JMS Connection Factory��JNDI
		String queueName = "myjmsqueue"; //JMS Queue����JMS Topic��JNDI
		boolean transacted = false;//transactionģʽ
		int acknowledgementMode = Session.AUTO_ACKNOWLEDGE;//acknowledgementģʽ
		String message="Message need to send";//ģ����Ҫ���͵���Ϣ 
		Properties properties = new Properties(); 
	    properties.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory"); 
	    properties.put(Context.PROVIDER_URL, "t3://localhost:7001");
	    try{
	    	Context context = new InitialContext(properties); 
	        Object obj = context.lookup(queueConnectionFactoryName); 
	        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) obj;//JMS Connection Factory�Ļ��
	        obj = context.lookup(queueName); 
	        Queue queue = (Queue) obj;//JMS Queue����JMS Topic�Ļ��  
	        QueueConnection queueConnection=queueConnectionFactory.createQueueConnection();//��������  
	        queueConnection.start(); 
	        QueueSession queueSession = queueConnection.createQueueSession(transacted, acknowledgementMode); 
	        TextMessage textMessage = queueSession.createTextMessage();
	        textMessage.clearBody(); 
	        textMessage.setText(message); 
	        QueueSender queueSender = queueSession.createSender(queue); 
	        queueSender.send(textMessage);
	        if (transacted) { 
	            queueSession.commit(); 
	          } 
	          if (queueSender != null) { 
	            queueSender.close(); 
	          } 
	          if (queueSession != null) { 
	            queueSession.close(); 
	          } 
	          if (queueConnection != null) { 
	            queueConnection.close(); 
	          } 
	    }catch (Exception ex) {
	    	ex.printStackTrace();
		}
	   
	   
	}
}
