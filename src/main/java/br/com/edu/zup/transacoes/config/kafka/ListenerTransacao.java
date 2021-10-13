package br.com.edu.zup.transacoes.config.kafka;

import br.com.edu.zup.transacoes.config.EventoTransacao;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerTransacao {

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void ouvir(EventoTransacao eventoTransacao) {
        System.out.println(eventoTransacao);
    }
}
