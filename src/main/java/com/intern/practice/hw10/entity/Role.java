package com.intern.practice.hw10.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Role {

    private String id;
    private List<String> privs;

}
