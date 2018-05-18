package com.miget.hxb.config;

import com.miget.hxb.util.MQConstants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * <p><b>Description:</b> RabbitMQ交换机、队列的配置类.定义交换机、key、queue并做好绑定。
 * 同时定义每个队列的ttl，队列最大长度，Qos等等
 * 这里只绑定了死信队列。建议每个队列定义自己的QueueConfig
 * <p><b>Company:</b> 
 *
 * @author created by fw at 21:54 on 2017-12-23
 * @version V0.1
 */
@Configuration
@ComponentScan
public class DeadQueueConfig {
	
	@Component
	public class DeadLetterMessageListener implements ChannelAwareMessageListener {
		private Logger logger = LoggerFactory.getLogger(DeadLetterMessageListener.class);

		@Autowired
		private RedisTemplate<String, Object> redisTemplate;

		/*@Autowired
		private DeadLetterMessageMapper deadLetterMessageMapper;*/

		/**
		 * Callback for processing a received Rabbit message.
		 * <p>Implementors are supposed to process the given Message,
		 * typically sending reply messages through the given Session.
		 * @param message the received AMQP message (never <code>null</code>)
		 * @param channel the underlying Rabbit Channel (never <code>null</code>)
		 * @throws Exception Any.
		 */
		@Override
		public void onMessage(Message message, Channel channel) throws Exception {
			MessageProperties messageProperties = message.getMessageProperties();
	        // 消息体
			String messageBody = new String(message.getBody());

			logger.warn("dead letter message：{} | tag：{}", messageBody, message.getMessageProperties().getDeliveryTag());
			// 入库
			//insertRecord(logKey, message);

			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

			redisTemplate.opsForHash().delete(MQConstants.MQ_CONSUMER_RETRY_COUNT_KEY, messageProperties.getMessageId());
		}

		/**
		 * 入库
		 */
		/*private void insertRecord(String logKey, Message message) {
			try {
				MessageProperties msgProp = message.getMessageProperties();
				DeadLetterMessageVo entity = new DeadLetterMessageVo();
				entity.setId(Identity.asyncUUID());
				entity.setMsgId(msgProp.getMessageId());
				entity.setMsgBody(new String(message.getBody()));
				entity.setDeliverTag("" + msgProp.getDeliveryTag());
				// PRODUCER为生产,CONSUMER为消费
				entity.setType(StringUtil.isNotBlank(msgProp.getType()) ? msgProp.getType() : Constants.MQ_CONSUMER);
				deadLetterMessageMapper.insert(entity);
				logger.info("{}|死信入库, {}", logKey, entity.toString());
			} catch (Exception e) {
				logger.error("{}|死信入库发生异常,{}", logKey, e.getMessage());
			}
		}*/

	}

    /**
     * 死信交换机
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(MQConstants.DLX_EXCHANGE);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue(MQConstants.DLX_QUEUE,true,false,false);
    }

    /**
     * 通过死信路由key绑定死信交换机和死信队列
     */
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(MQConstants.DLX_ROUTING_KEY);
    }
    
    /**
     * 死信队列的监听
     * @param connectionFactory RabbitMQ连接工厂
     * @param deadLetterMessageListener 死信的监听者
     * @return 监听容器对象
     */
    @Bean
    public SimpleMessageListenerContainer deadLetterListenerContainer(ConnectionFactory connectionFactory, DeadLetterMessageListener deadLetterMessageListener) {
    	
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(dlxQueue());
        container.setExposeListenerChannel(true);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(deadLetterMessageListener);
        /** 设置消费者能处理消息的最大个数 */
        container.setPrefetchCount(100);
        return container;
    }
      
}
