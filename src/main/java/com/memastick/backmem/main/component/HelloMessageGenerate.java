package com.memastick.backmem.main.component;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import org.springframework.stereotype.Component;

@Component
public class HelloMessageGenerate {

    private String[] messages = {
        "Мемастик в процессе разработки, не ругайте нас",
        "Чувствуй себя как дома! (но не очень сильно)",
        "Мемы - это лучшее на что ты можешь потратить свое время",
        "Новый день! Новый мем!",
        "Вы не создаете мемы на свой страх и риск!",
        "Мемы спасут мир от войны",
        "Много хромосом, это хорошо или плохо?",
        "Сделал мемас, гуляй как...",
        "Мем мне в печень, и я счастлив вечен",
        "Ааа ну это уже какая то страшилка получается",
        "Помните и уважайте пожилые мемы",
        "Надейся на лучшие, расчитывай на мемы",
        "7 раз лайкни, 1 раз орни",
        "Не хочешь срать, не мучай жопу ",
        "Ну чо погнали еп***го в рот!!!",
        "МУТАГЕН->КРОССОВЕР->МИКРОСКОП->АНТИБИОТИК->ПРОБИРКА"
    };

    private final EvolveMemeService evolveMemeService;

    public HelloMessageGenerate(EvolveMemeService evolveMemeService) {
        this.evolveMemeService = evolveMemeService;
    }

    public String getMessage() {
        int day = (int) evolveMemeService.computeEvolution();
        int len = this.messages.length;

        return messages[day % len];
    }
}
