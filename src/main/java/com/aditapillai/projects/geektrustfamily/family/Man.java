package com.aditapillai.projects.geektrustfamily.family;

import java.util.Set;

class Man extends Person {
    Man(String name) {
        super(name);
    }

    @Override
    public Set<Person> getChildren() {
        return this.getSpouse()
                   .getChildren();
    }
}
