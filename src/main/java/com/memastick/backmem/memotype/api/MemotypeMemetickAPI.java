package com.memastick.backmem.memotype.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemotypeMemetickAPI {

    private List<MemotypeSetAPI> content = new ArrayList<>();
}
