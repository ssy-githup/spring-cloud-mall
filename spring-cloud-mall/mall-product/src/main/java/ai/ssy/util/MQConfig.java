package ai.ssy.util;

import ai.ssy.common.MQProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
* @Description: 队列 交换机  绑定配置到我们专门的配置类中
* @Author: Administrator
* @Date: 2020/3/21
**/
@Configuration
public class MQConfig {


	//定义一个队列 测试使用
	@Bean
	public Queue testMessage(){
		return new Queue("testMessage");
	}
	//定义一个交换器 测试使用
	@Bean
	public DirectExchange testExchange(){
		return new DirectExchange("testExchange");
	}
	//定义一个交换器 测试使用
	@Bean
	public Binding bindingTestMessage(
			@Qualifier("testMessage") Queue queueMessage,
			@Qualifier("testExchange") DirectExchange exchange){
		return BindingBuilder.bind(
				queueMessage).to(exchange).with("testRollingKey");

	}

//--------------------------定义的队列--------------------------------------------
	//事务处理成功队列
	@Bean
	public Queue queueMessageTxReturnSuccess(){
		return new Queue(MQProperties.QUEUE_NAME_TX_RETURN_SUCCESS);
	}
	//定义分布式事务队列
	@Bean
	public Queue queueMessageTx(){
		return new Queue(MQProperties.QUEUE_NAME_TX);
	}

	//定义事务回滚队列
	@Bean
	public Queue queueMessageTxReturnFailure(){
		return new Queue(MQProperties.QUEUE_NAME_TX_RETURN_FAILURE);
	}

//-------------------------------------------------------------------------------------------------


	//定义分布式事务交换器
	@Bean
	public DirectExchange exchangeTx(){
		return new DirectExchange(MQProperties.EXCHANGE_NAME_TX);
	}
	//定义事务交回滚换器
	@Bean
	public DirectExchange exchangeTxReturn(){
		return new DirectExchange(MQProperties.EXCHANGE_NAME_TX_RETURN);
	}



	//绑定分布式事务队列与交换器
	@Bean
	public Binding bindingExchangeMessageTx(
			@Qualifier("queueMessageTx") Queue queueMessage,
			@Qualifier("exchangeTx") DirectExchange exchange){
		return BindingBuilder.bind(queueMessage).to(exchange).with(MQProperties.ROUTE_KEY_TX);
	}
	//绑定分布式事务队列与交换器
	@Bean
	public Binding bindingExchangeMessageTxReturnSuccess(
			@Qualifier("queueMessageTxReturnSuccess") Queue queueMessage,
			@Qualifier("exchangeTxReturn") DirectExchange exchange){
		return BindingBuilder.bind(queueMessage).to(exchange).with(MQProperties.ROUTE_KEY_TX_RETURN_SUCCESS);
	}
	//绑定分布式事务队列与交换器
	@Bean
	public Binding bindingExchangeMessageTxReturnFailure(
			@Qualifier("queueMessageTxReturnFailure") Queue queueMessage,
			@Qualifier("exchangeTxReturn") DirectExchange exchange){
		return BindingBuilder.bind(queueMessage).to(exchange).with(MQProperties.ROUTE_KEY_TX_RETURN_FAILURE);
	}
}
