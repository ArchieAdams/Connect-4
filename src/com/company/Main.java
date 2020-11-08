package com.company;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<List<String>> grid = new ArrayList<>();

    private static final int maxGridY = 6;
    private static final int maxGridX = 7;

    private static int row;

    private static int playerTurn = 0;
    private static List<String> playerNames = new ArrayList<>();
    private static List<Character> playerMarker = new ArrayList<>();

    private static String emptyMarker = " + ";

    public static void main(String[] args) {
        onStart();
    }

    // <editor-fold defaultstate="collapsed" desc="Setters">
    public static void setPlayerNames(String name) {
        playerNames.add(name);
    }

    public static void setPlayerMarker(char marker) {
        playerMarker.add(marker);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters">
    public static String getInputString(){
        Scanner input = new Scanner(System.in);
        return input.next();
    }

    public static int getInputInt(){
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

    public static String getPlayerNames(){
        return playerNames.get(playerTurn);
    }

    public static Character getPlayerMarker(){
        return playerMarker.get(playerTurn);
    }
    // </editor-fold>

    public static void  clearScreen(){
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
    }

    public static void onStart(){
        createGrid();
        displayGrid();
        for (int i = 0; i < 2; i++) {
            System.out.print("Player "+(i+1)+" enter your name : ");
            setPlayerNames(getInputString());
            while (true){
                System.out.print("Player "+(i+1)+" enter your marker : ");
                if(isValidMarker(getInputString())){
                    break;
                }
            }
        }
        while (true){
            enterRow();
            nextTurn();
            clearScreen();
        }
    }

    private static void createGrid(){
        for (int i = 0; i < maxGridY; i++) {
            grid.add(new ArrayList<String>());
            for (int j = 0; j < maxGridX; j++) {
                grid.get(i).add(emptyMarker);
            }
        }
    }

    public static void displayGrid(){
        for (int j = 0; j < maxGridX; j++) {
            System.out.print(" "+(j+1)+" ");
        }
        System.out.println();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                System.out.print(grid.get(i).get(j));
            }
            System.out.println();
        }
    }

    public static boolean isValidMarker(String marker){
        if (marker.length() == 1){
            setPlayerMarker(marker.toCharArray()[0]);
            return true;
        }
        return false;
    }

    public static void enterRow() {
        displayGrid();
        while (true){
            System.out.print(getPlayerNames()+ " enter a number row : ");
            row = getInputInt()-1;
            if (checkLocation()){
                break;
            }
        }
    }

    public static boolean checkLocation(){
        if (row >= 1 && row <= 7 && isRowIsFree()){
            setCounter();
            return true;
        }
        else {
            if (row < 1){
                System.out.println("Row is too small.");
            }
            if (row > 7){
                System.out.println("Row is too large.");
            }
            if (!isRowIsFree()){
                System.out.println("Row is full.");
            }
        }
        return false;
    }

    public static void setCounter(){
        if (isRowIsFree()){
            grid.get(getNextFreeSpace()).set(row," "+getPlayerMarker().toString()+" " );
        }
        displayGrid();
    }

    public static boolean isRowIsFree(){
        return grid.get(0).get(row).equals(emptyMarker);
    }

    public static int getNextFreeSpace(){
        for (int i = 0; i < maxGridY; i++) {
            if (!(grid.get(i).get(row).equals(emptyMarker))){
                return i-1;
            }
        }
        return maxGridY-1;
    }

    public static void nextTurn(){
        if (playerTurn == 0){
            playerTurn = 1;
        }
        else {
            playerTurn = 0;
        }
    }
}
