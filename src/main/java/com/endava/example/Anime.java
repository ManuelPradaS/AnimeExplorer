package com.endava.example;


public class Anime {
    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Anime() {

    }

    @Override
    public String toString() {
        return "Anime{" +
                "data=" + data +
                '}';
    }
}
