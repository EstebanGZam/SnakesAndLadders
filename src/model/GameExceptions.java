package model;

import java.util.InputMismatchException;

public class GameExceptions {

    private static int max_Value;
    public static boolean checkBoardExceptions(int max){
        if (checkStringException(max) && checkZeroException(max)){
            max_Value = max;
            return true;
        }
        return false;
    }

    private static boolean checkZeroException(int max) throws IllegalArgumentException {
        if (max < 0){
            throw new IllegalArgumentException("User Input has to be different from zero");
        }
        return false;
    }

    private static boolean checkStringException(int value) throws InputMismatchException {
        throw new InputMismatchException("User input was not a number.");
    }

    public static boolean checkMaxSnakes(int value, int max) throws IllegalArgumentException{
        // use conditional statement to check value
        if(value > max || value > max/2){
            // we throw InvalidAgeException when the value is greater/ half of the board
            throw new IllegalArgumentException("The number of snakes cannot be greater than or equal to half the board.");
        }
        return false;
    }

    public static boolean checkMaxLadders (int value, int max) throws IllegalArgumentException{
        // use conditional statement to check value
        if(value > max || value > (max/2)){
            // we throw InvalidAgeException when the value is greater/ half of the board
            throw new IllegalArgumentException("The number of ladders cannot be greater than or equal to half the board.");
        }
        return false;
    }

    public static boolean checkHighestAlphabet(int value) throws IllegalArgumentException{
        if (value > 27){
            throw new IllegalArgumentException("The number of stairs is greater than the letters of the alphabet");
        }
        return false;
    }
    public boolean checkNonExceptions(int value, int option) {
        if (option == 0){
            if (!checkStringException(value) && !checkZeroException(value)) {
                return true;
            }
        }
        if (option == 1){
            if (!checkMaxLadders(value,max_Value) && !checkStringException(value) && !checkZeroException(value) && !checkHighestAlphabet(value)) {
                return true;
            }
        }
        return false;
    }
}