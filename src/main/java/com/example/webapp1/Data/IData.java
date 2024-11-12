package com.example.webapp1.Data;

import java.util.ArrayList;

public interface IData<T>  {
    void Add(T val);

    void Remove(T val);

    boolean Update(T val);

    ArrayList<T> GetAll();
}
