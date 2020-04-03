package com.xz.myapp.entity;

/**
 * @author czr
 * @date 2020/4/3
 */
public class ClassEntity {
    private String name;
    private Class c;

    public ClassEntity(String name, Class<?> c) {
        this.name = name;
        this.c = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getC() {
        return c;
    }

    public void setC(Class<?> c) {
        this.c = c;
    }
}
