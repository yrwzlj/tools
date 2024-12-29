package com.yrw_.retry.redis;

import com.yrw_.retry.es.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    private static final Topic KEYEVENT_DELETE_TOPIC = new PatternTopic("__keyevent@*__:del");

    private static AtomicLong cnt = new AtomicLong(1);

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("onMessage: body:" + message.getBody() + " channel:" + message.getChannel() + " key:" + new String(pattern));

        try {
            User user = new User(2L, "小王", 22, "男");
            user.setName(message.toString());
            user.setId(cnt.incrementAndGet());

            IndexCoordinates indexCoordinates = IndexCoordinates.of("redis_key_expire");
            User save = esTemplate.save(user, indexCoordinates);
        } finally {
            super.onMessage(message, pattern);
        }
    }

    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, KEYEVENT_DELETE_TOPIC);
        super.doRegister(listenerContainer);
    }
}
