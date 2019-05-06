package com.memastick.backmem.main.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeAPI {

    private String nick;
    private long day;
    private long memes;

}
