package exception;

import java.util.*;

public class GameExceptions extends Exception{

    private static Scanner reader = new Scanner(System.in);


/**
    * <b>Name:</b> IntegerMisMatchException <br>
    * <b>Description:</b> This method handles input exceptions when trying 
    * to read an integer from the input source.
    * It checks if the input is an integer, and if it's not, 
    * it recursively calls itself until a valid input is provided or the user cancels the input operation. <br>
    * <b><i>pre:</i></b> The method must receive an object as input. <br>
    * <b><i>pos:</i></b> The method returns the input object, which can be an Integer object if a valid integer input is provided or an object of another class if the user cancels the input operation. <br>
    *
    * @param input the input object to check if it's an integer
    * @return the input object if it's an integer or a new input object if the user cancels the input operation
    * @throws InputMismatchException if the input is not an integer
    */
    public Object IntegerMisMatchException(Object input) {
        try {
            if (input instanceof Integer) {

                input = reader.nextInt();
                reader.nextLine();
            }
        } catch (InputMismatchException ie) {

            System.out.println("Input must be a int");
            reader.nextLine();
            input = IntegerMisMatchException(input);


        }
        return input;
    }

/**
    * <b>Name:</b> ZeroInputException <br>
    * <b>Description:</b> his method handles input exceptions when the 
    * input value must be greater than zero. It checks if the input is an 
    * integer and if it's less than or equal to zero, it recursively 
    * calls itself until a valid input is provided or the user cancels the input operation. <br>
    * <b><i>pre:</i></b> The method must receive an object as input. <br>
    * <b><i>pos:</i></b> The method returns the input object, which can be an Integer object if a valid integer input is provided or an object of another class if the user cancels the input operation. <br>
    *
    * @param input the input object to check if it's an integer
    * @return the input object if it's an integer or a new input object if the user cancels the input operation
    * @throws InputMismatchException if the input is not an integer
    */
    public Object ZeroInputException(Object input) {
        
        if (input instanceof Integer){
            int value = (Integer) input;
        
            if (value <= 0) {
                System.out.println("Input must be greater than zero");
                input = reader.nextInt();
                input = ZeroInputException(input);
            }
    }else{
        input = IntegerMisMatchException(input);
    }
    
        return input;
    }

/**
    *<b>Name:</b> validateInput <br>
    *<b>Description:</b> This method validates the user 
    *input for either the dimensions of the board or the 
    *number of Snakes/Ladders to be added to the game. 
    *It uses several different methods to handle
    *input exceptions. If the instance parameter 
    *is equal to 1, it means that the input is 
    *for the dimensions of the board, and if it's equal to 0,
    *it means that the input is for the number of Snakes/Ladders. <br>
    *
    *<b><i>pre:</i></b> The method must receive an object that can be cast to an integer and an integer value for the instance parameter that must be either 0 or 1. <br>
    *<b><i>pos:</i></b> The method returns the input object if all input validations are passed, or it recursively calls itself to prompt the user to input a valid value until all validations are passed. <br>
    *@param input the input object to be validated
    *@param instance the integer value representing the type of input being validated (1 for board dimensions, 0 for number of Snakes/Ladders)
    *@return the input object if all input validations are passed, or a new input object if the user cancels the input operation or if an exception is thrown
    *@throws InputMismatchException if the input is not an integer
    *@throws IllegalArgumentException if the input exceeds the maximum number of Snakes/Ladders allowed by the board
    */
    public Object validateInput(Object input, int instance){
        if (instance == 1){
            try {
                input = IntegerMisMatchException(input);

                input = ZeroInputException(input);

            }catch (Exception e){
                System.out.println("Error: " + e);
            }
        }

        return input;
    }
}