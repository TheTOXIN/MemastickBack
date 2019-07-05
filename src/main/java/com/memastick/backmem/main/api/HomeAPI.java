package com.memastick.backmem.main.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeAPI {

    private String nick;

    private long day;
    private long memes;

    private long countItems;
    private long countBells;

    private LocalTime selectTimer;
}
