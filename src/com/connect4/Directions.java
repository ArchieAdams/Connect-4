package com.connect4;

public enum Directions {
    NS(0,0,1,-1),EW(1,-1,0,0),NESW(1,-1,1,-1),NWSE(-1,1,1,-1);

    private int collumMax;
    private int collumMin;
    private int rowMax;
    private int rowMin;

    Directions(final int collumMax,final int collumMin, final int rowMax,final int rowMin){
        this.collumMax = collumMax;
        this.collumMin = collumMin;
        this.rowMax = rowMax;
        this.rowMin = rowMin;
    }

    public int getCollumMax() {
        return collumMax;
    }

    public int getCollumMin() {
        return collumMin;
    }

    public int getRowMax() {
        return rowMax;
    }

    public int getRowMin() {
        return rowMin;
    }
}
