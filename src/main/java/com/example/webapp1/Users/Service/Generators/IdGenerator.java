package com.example.webapp1.Users.Service.Generators;

public class IdGenerator implements IGenerator<Integer>
{
    private int id = 0;
    public Integer Generate(){
        return id++;
    }
}
