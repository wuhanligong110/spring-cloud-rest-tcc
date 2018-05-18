package com.miget.hxb;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhao Junjian
 */
@RestController
public class RabbitController {
    /*@Autowired
    private RabbitTemplate amqpTemplate;
    @Autowired
    private EventDrivenPublisher eventBus;
    @Autowired
    private EventDrivenSubscriber subscriber;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitController.class);

    //@PostConstruct
    public void init() {
        amqpTemplate.setConfirmCallback((correlationData, ack, cause) -> LOGGER.info("ACK:: correlationData: {}, ack: {}, cause: {}", correlationData, ack, cause));
        amqpTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> LOGGER.info("RETURN:: message: {}, replyCode: {}, replyText: {}, exchange: {}, routingKey: {}", new String(message.getBody(), Charsets.UTF_8), replyCode, replyText, exchange, routingKey));
    }

    @RequestMapping(value = "/queue-exists", method = RequestMethod.GET)
    public Map<String, ?> queueExist() {
        final Foo unit = new Foo();
        unit.setId(RANDOM.nextInt());
        unit.setPayload(UUID.randomUUID().toString());
        amqpTemplate.convertAndSend(RabbitConfiguration.DEFAULT_DIRECT_EXCHANGE, RabbitConfiguration.POINT_KEY, unit, new CorrelationData("adf"));
        LOGGER.debug("produce: {}", unit);
        return ImmutableMap.of("code", 20000);
    }

    @RequestMapping(value = "/exchange-exists-but-queue-not", method = RequestMethod.GET)
    public Map<String, ?> exchangeExistsButQueueNot() {
        final Foo unit = new Foo();
        unit.setId(RANDOM.nextInt());
        unit.setPayload(UUID.randomUUID().toString());
        // 有这个exchange，但是无法投递至队列
        amqpTemplate.convertAndSend(RabbitConfiguration.DEFAULT_DIRECT_EXCHANGE, "sadf", unit, new CorrelationData("adf"));
        LOGGER.debug("produce: {}", unit);
        return ImmutableMap.of("code", 20000);
    }

    @RequestMapping(value = "/exchange-not-exist", method = RequestMethod.GET)
    public Map<String, ?> exchangeNotExist() {
        final Foo unit = new Foo();
        unit.setId(RANDOM.nextInt());
        unit.setPayload(UUID.randomUUID().toString());
        // 根本就没有这个exchange
        amqpTemplate.convertAndSend("sdflkjsldf", "sadf", unit, new CorrelationData("adf"));
        LOGGER.debug("produce: {}", unit);
        return ImmutableMap.of("code", 20000);
    }

    //@RabbitListener(queues = {RabbitConfiguration.POINT_QUEUE})
    public void processBootTask(Foo content) {
        LOGGER.debug("consume: {}", content);
    }

    @RequestMapping(value = "/bus/queue-exists", method = RequestMethod.GET)
    public Map<String, ?> queueExistEventBus() {
        eventBus.persistPublishMessage(ImmutableMap.of("hello", "java"), EventBusinessType.ADD_PTS.name());
        return ImmutableMap.of("code", 20000);
    }

    @RequestMapping(value = "/bus/exchange-exists-but-queue-not", method = RequestMethod.GET)
    public Map<String, ?> exchangeExistsButQueueNotEventBus() {
        EventDrivenPublisher.registerType("111", RabbitConfiguration.DEFAULT_DIRECT_EXCHANGE, "02394234");
        eventBus.persistPublishMessage(ImmutableMap.of("hello", "java"), "111");
        return ImmutableMap.of("code", 20000);
    }

    @RequestMapping(value = "/bus/exchange-not-exist", method = RequestMethod.GET)
    public Map<String, ?> exchangeNotExistEventBus() {
        EventDrivenPublisher.registerType("111", "129313", "02394234");
        eventBus.persistPublishMessage(ImmutableMap.of("hello", "java"), "111");
        return ImmutableMap.of("code", 20000);
    }

    @RabbitListener(queues = {RabbitConfiguration.POINT_QUEUE})
    public void processBootTaskBus(Map<String, Object> event) {
        //LOGGER.debug("consume: {}", event);
        subscriber.persistAndHandleMessage(event.get("business_type").toString(), event.get("payload").toString(), event.get("guid").toString());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
    private static class Foo {
        private Integer id;

        @JsonIgnore
        private String payload;

    }*/

}
