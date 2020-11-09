package com.connect4;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<List<String>> grid = new ArrayList<>();

    private static final int maxGridY = 6;
    private static final int maxGridX = 7;

    private static int column;
    private static int row;

    private static int count = 1;

    private static int playerTurn = 0;
    private static List<String> playerNames = new ArrayList<>();
    private static List<Character> playerMarker = new ArrayList<>();


    private static String emptyMarker = " + ";

    private static boolean needToCheck = false;


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

    public static String getPlayerMarker(){
        return (" "+playerMarker.get(playerTurn)+" ");
    }
    // </editor-fold>

    public static void  clearScreen(){
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
    }

    public static void onStart(){
        playerTurn = 0;
        playerNames = new ArrayList<>();
        playerMarker = new ArrayList<>();
        grid = new ArrayList<>();

        createGrid();
        for (int i = 0; i < 2; i++) {
            while (true) {
                System.out.print("Player " + (i + 1) + " enter your name : ");
                if (isValidName(getInputString())){
                    break;
                }
            }
            while (true){
                System.out.print("Player "+(i+1)+" enter your marker : ");
                if(isValidMarker(getInputString())){
                    break;
                }
            }
        }
        while (true){
            enterColumn();
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

    public static boolean isValidName(String name){
        if (!(playerNames.contains(name))){
            setPlayerNames(name);
            return true;
        }
        return false;
    }

    public static boolean isValidMarker(String marker){
        if (marker.length() == 1 && !(playerMarker.contains(marker.toCharArray()[0]))){
            setPlayerMarker(marker.toCharArray()[0]);
            return true;
        }
        return false;
    }

    public static void enterColumn() {
        displayGrid();
        while (true){
            System.out.print(getPlayerNames()+ " enter a number column : ");
            column = getInputInt()-1;
            if (checkLocation()){
                break;
            }
        }
        hasPlayerWon(row,column);
    }

    public static boolean checkLocation(){
        if (column >= 0 && column <= 6 && isColumnIsFree()){
            setCounter();
            return true;
        }
        else {
            if (column < 0){
                System.out.println("Column is too small.");
            }
            if (column > 6){
                System.out.println("Column is too large.");
            }
            if (!isColumnIsFree()){
                System.out.println("Column is full.");
            }
        }
        return false;
    }

    public static void setCounter(){
        if (isColumnIsFree()){
            grid.get(getNextFreeSpace()).set(column,getPlayerMarker());
        }
        displayGrid();
    }

    public static boolean isColumnIsFree(){
        return grid.get(0).get(column).equals(emptyMarker);
    }

    public static int getNextFreeSpace(){
        for (int i = 0; i < maxGridY; i++) {
            if (!(grid.get(i).get(column).equals(emptyMarker))){
                row=i-1;
                return row;
            }
        }
        row = maxGridY-1;
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

    public static void playerWon(){
        System.out.println(getPlayerNames()+" has won!");
        onStart();
    }


    // <editor-fold defaultstate="collapsed" desc="Very efficient direction checkers">
    public static boolean hasPlayerWon(int row, int column){
        int columnMax;
        int columnMin;
        int rowMax;
        int rowMin;

        for (int i = 0; i < 4; i++) {
            needToCheck = false;
            count = 1;

            List<Integer> iterators = getDirectionIterators(i);

            int columnMaxIt = iterators.get(0);
            int columnMinIt = iterators.get(1);
            int rowMaxIt = iterators.get(2);
            int rowMinIt = iterators.get(3);

            columnMax = column + columnMaxIt;
            columnMin = column + columnMinIt;
            rowMax = row + rowMaxIt;
            rowMin = row + rowMinIt;
            while (true) {
                needToCheck = false;
                try {
                    if (grid.get(rowMax).get(columnMax).equals(getPlayerMarker())) {
                        count++;
                        needToCheck = true;
                        columnMax = columnMax + columnMaxIt;
                        rowMax = rowMax + rowMaxIt;
                    }
                } catch (IndexOutOfBoundsException e) {
                    //
                }
                try {
                    if (grid.get(rowMin).get(columnMin).equals(getPlayerMarker())) {
                        count++;
                        needToCheck = true;
                        columnMin = columnMin + columnMinIt;
                        rowMin = rowMin + rowMinIt;
                    }
                } catch (IndexOutOfBoundsException e) {
                    //
                }
                if (!needToCheck) {
                    break;
                }
                if (count == 4) {
                    playerWon();
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Integer> getDirectionIterators(int direction){

        //Efficiency

        List<Integer> iterators = new ArrayList<>();
        switch (direction){
            case (0):
                iterators.add(Directions.NS.getCollumMax());
                iterators.add(Directions.NS.getCollumMin());
                iterators.add(Directions.NS.getRowMax());
                iterators.add(Directions.NS.getRowMin());
                break;
            case (1):
                iterators.add(Directions.EW.getCollumMax());
                iterators.add(Directions.EW.getCollumMin());
                iterators.add(Directions.EW.getRowMax());
                iterators.add(Directions.EW.getRowMin());
                break;
            case (2):
                iterators.add(Directions.NESW.getCollumMax());
                iterators.add(Directions.NESW.getCollumMin());
                iterators.add(Directions.NESW.getRowMax());
                iterators.add(Directions.NESW.getRowMin());
                break;
            case (3):
                iterators.add(Directions.NWSE.getCollumMax());
                iterators.add(Directions.NWSE.getCollumMin());
                iterators.add(Directions.NWSE.getRowMax());
                iterators.add(Directions.NWSE.getRowMin());
                break;
        }
        return iterators;
    }
    // </editor-fold>


}
