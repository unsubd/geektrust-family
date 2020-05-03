package com.aditapillai.projects.geektrustfamily.family;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class Man extends Person {
    Man(String name) {
        super(name);
    }

    @Override
    public Set<Person> getChildren() {
        return Optional.ofNullable(this.getSpouse())
                       .map(Person::getChildren)
                       .orElse(new HashSet<>());
    }
}
