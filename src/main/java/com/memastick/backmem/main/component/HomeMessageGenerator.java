package com.memastick.backmem.main.component;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Component
public class HomeMessageGenerator {

    private Map<Integer, String> adminMessages = new HashMap<>();

    private String[] messages = {
        "Мемастик в процессе разработки, не ругайте нас",
        "Чувствуй себя как дома! (но не очень сильно)",
        "Мемы - это лучшее на что ты можешь потратить свое время",
        "Новый день! Новый мем!",
        "Вы не создаете мемы на свой страх и риск!",
        "Мемы спасут мир от войны",
        "Много хромосом, это хорошо или плохо?",
        "Мем мне в печень, и я счастлив вечен",
        "Помните и уважайте пожилые мемы",
        "Надейся на лучшие, расчитывай на мемы",
        "7 раз лайкни, 1 раз орни",
        "Не хочешь срать, не мучай жопу ",
        "Ну чо погнали еп***го в рот!!!",
        "Вместе мы сделаем контент лучше!",
        "Без труда не сделаешь и мем никогда.",
        "МУТАГЕН->КРОССОВЕР->МИКРОСКОП->АНТИБИОТИК->ПРОБИРКА"
    };

    private final EvolveMemeService evolveMemeService;

    public HomeMessageGenerator(EvolveMemeService evolveMemeService) {
        this.evolveMemeService = evolveMemeService;
    }

    public String getMessage() {
        int day = (int) evolveMemeService.computeEvolution();

        if (adminMessages.containsKey(day)) return adminMessages.get(day);

        int len = this.messages.length;

        return messages[day % len];
    }

    public void adminMessage(int days, String message) {
        int evolution = (int) evolveMemeService.computeEvolution();

        IntStream.range(0, days).forEach(i -> {
            int day = i + evolution;
            adminMessages.put(day, message);
        });
    }
}
