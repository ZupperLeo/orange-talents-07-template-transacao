package br.com.edu.zup.transacoes.config.kafka;

import br.com.edu.zup.transacoes.config.EventoTransacao;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties properties) {
        this.kafkaProperties = properties;
    }

    public Map<String, Object> consumerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getKeyDeserializer());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getValueDeserializer());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());

        return properties;
    }

    @Bean
    public ConsumerFactory<String, EventoTransacao> transacaoConsumerFactory() {
        StringDeserializer stringDeserializer = new StringDeserializer();
        JsonDeserializer<EventoTransacao> jsonDeserializer = new JsonDeserializer<>(EventoTransacao.class, false);

        return new DefaultKafkaConsumerFactory<>(consumerConfig(), stringDeserializer, jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventoTransacao> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EventoTransacao> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transacaoConsumerFactory());

        return factory;
    }

}
