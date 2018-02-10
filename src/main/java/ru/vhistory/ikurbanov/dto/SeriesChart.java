package ru.vhistory.ikurbanov.dto;

public class SeriesChart {
    private String name;
    private int[] data;

    public SeriesChart(String name) {
        this.name = name;
    }

    public SeriesChart() {
    }

    public SeriesChart(String name, int[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }
}
