package com.memastick.backmem.main.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MigrateAPI {

    private List<String> urls = new ArrayList<>();

}
