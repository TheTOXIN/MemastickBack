package com.memastick.backmem.main.component;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.evolution.service.EvolveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class HomeMessageGenerator {

    private Pair<LocalDateTime, String> messageCache;

    private String[] messages = {
        "Мемастик в процессе разработки, не ругайте нас",
        "Чувствуй себя как дома! (но не очень сильно)",
        "Мемы - это лучшее на что ты можешь потратить свое время",
        "Новый день! Новый мем!",
        "Вы не создаете мемы на свой страх и риск!",
        "Мемы спасут мир от войны",
        "Мем мне в печень, и я счастлив вечен",
        "Помните и уважайте пожилые мемы",
        "Надейся на лучшие, расчитывай на мемы",
        "Вместе мы сделаем контент лучше!",
        "Без труда не сделаешь и мем никогда.",
        "МУТАГЕН->КРОССОВЕР->МИКРОСКОП->АНТИБИОТИК->ПРОБИРКА"
    };

    private final EvolveService evolveService;

    public String getMessage() {
        if (checkMessageCache()) return this.messageCache.getSecond();

        int day = (int) evolveService.computeEvolution();
        int len = this.messages.length;

        return messages[day % len];
    }

    public void adminMessage(int days, String message) {
        this.messageCache = Pair.of(
            LocalDateTime.now().plusDays(days),
            message
        );
    }

    public void memetickMessage(String name) {
        this.messageCache = Pair.of(
            LocalDateTime.now().plusHours(1),
            "Приветствуем нового меметика - " + name + " \uD83D\uDE0E"
        );
    }

    private boolean checkMessageCache() {
        if (messageCache == null) return false;

        LocalDateTime expire = messageCache.getFirst();

        if (expire.isBefore(LocalDateTime.now())) {
            this.messageCache = null;
            return false;
        }

        return true;
    }
}
