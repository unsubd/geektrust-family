package com.aditapillai.projects.geektrustfamily.family;

import java.util.HashSet;
import java.util.Set;

class Woman extends Person {
    private final Set<Person> children = new HashSet<>();

    Woman(String name) {
        super(name);
    }

    void addChild(Person child) {
        this.children.add(child);
    }

    public Set<Person> getChildren() {
        return new HashSet<>(this.children);
    }

}
