package com.example.demo.multiagent;

public interface Agent <I,O>{

    String name();

    O execute(I input);
}
