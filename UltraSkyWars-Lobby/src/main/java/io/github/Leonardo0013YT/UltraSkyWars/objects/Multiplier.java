package io.github.Leonardo0013YT.UltraSkyWars.objects;

import lombok.Getter;

@Getter
public class Multiplier {

    private final String type;
    private final String name;
    private final int id;
    private final long remaining;
    private final double amount;

    public Multiplier(int id, String type, String name, long remaining, double amount) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.remaining = remaining;
        this.amount = amount;
    }

}