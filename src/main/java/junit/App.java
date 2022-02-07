package junit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** This is the Application main for the Rental Agreement application.
 *  It responsible for getting the user's input and calculating the Rental Agreeement for a point-of-sales application. 
 *  It will then print the results to the console. 
 * Utilizes RentalAgreement Class to calculate and print a Renatal Agreement object to the console. 
 * 
 * The required information for the Rental Agreement includes:  
 * Check out date, Tool Code, Duration in days, and the discount if it applies.
 * 
 @author James Diaz
 @version 1.0
 @since 2022-2-7
 */
public class App {

// Global Constants
public static final String DATE_FORMAT_PATTERN = "MM/dd/yy"; 
public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN); 
// Helper Functions


/**
 * 
 * Tests for potential errors in the creation of a Calendar object Representing the due date for the Rental Agreement
 * Specifically, in regrads to the formatting of the String provided or create from the user's input. 
 * 
 * @params testDate  The String that represents the date to be evaluated. 
 * @return Boolean   indicating that the strings is in the format mm/dd/yy
 * 
 */
    public static boolean isValidCheckOutDate(String testDate) {
        boolean isValid = false; 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        try{ 
            simpleDateFormat.parse(testDate); 
            isValid = true; 
        }
        catch (ParseException e){
            System.out.println("This is not a valid date pattern for the date. Please Try again. ");
        }
    return isValid; 
    }

    public static void main( String[] args ){
        Scanner sc = new Scanner(System.in);
        //Declare Variables


        rentalAgreement agreement = new rentalAgreement(); 

        //Declare variables to 
        // This will hold The input data from the user input and seperate it from the Rental Agreement.  
        String inputToolCode = "";
        int inputRentalDayCount = 0; 
        int discountPercent = 0;
        Calendar inputCheckOutDate = Calendar.getInstance(); 
        

        //Greet the user and begin to prompt the user for the required information. 
        try{
        System.out.println("");
        System.out.println("Hello, Please have the following information available for the Rental Agreement.");
        System.out.println("The Tool Code: JAKD, JAKR, LADW, CHNS. ");
        System.out.println("The check out date");
        System.out.println("The amount of days you wish to rent the tool.");
        System.out.println("The percent discount (Please put 0 when prompted if a discount does not apply)."); 
        System.out.println("A Rental Agreement will be created and printed for you when you are finished.");
        

        //Begin to ask the user for the Tool Code.The loop will continue until the user
        //gives a valid input for the Tool Code.
        boolean toolCode =true; 
            while (toolCode){
                try{
                System.out.println("Please enter the Tool Code for the Rental Agreement.");
                String temp = sc.nextLine();
                temp = temp.toUpperCase(); 
                    if(agreement.isValidToolCode(temp)){
                        System.out.println("The given Tool Code is " + temp);
                        System.out.println();
                        inputToolCode = temp;
                        agreement.toolCode = inputToolCode;  
                        toolCode = false; 
                        agreement.setToolCode(temp);
                    }
                    else{
                        throw new RuntimeException("This is not a valid Tool Code. Please Try Again."); 
                    }
                }
                catch (RuntimeException e){
                    System.out.println(e.getMessage()); 
                    sc.nextLine(); 
                }
            }


        // Prompts the user for a valid rental duration for the Rental Agreement. 
        // The loop continues until an acceptable number is given. 
        boolean rentalDayCount =true; 
            while (rentalDayCount){
                try{
                System.out.println("Please enter the amount of days that you wish to rent the "+agreement.toolType.toLowerCase()+".");
                int temp = sc.nextInt(); 
                    if(agreement.isValidRentalDuration(temp)){
                        System.out.println("The amount of days to rent this item is " + temp);
                        System.out.println();
                        rentalDayCount = false; 
                        inputRentalDayCount = temp; 
                        agreement.rentalDay = temp;  
                    }
                    else{
                        throw new RuntimeException("This is not a valid input for the duration. Please Try Again."); 
                    }
                }
                catch (RuntimeException e){
                    System.out.println(e.getMessage()); 
                    sc.nextLine(); 
                }
            }     
        

        //Prompts the user for a valid discount (a number between 0-100). 
        //The loop continues until an acceptable number is given. 
        boolean discount =true; 
            while (discount){
                try{
                System.out.println("Please enter a valid discount percent (0-100).");
                System.out.println("Do not input a negative value, a value greater than 100, or any other character.");
                int temp = sc.nextInt(); 
                    if(agreement.isValidDiscountPercent(temp)){
                        System.out.println("The discount is " + temp + " percent.");
                        System.out.println();
                        discount = false; 
                        discountPercent= temp; 
                        agreement.discountPercent = temp;  
                    }
                    else{
                        throw new RuntimeException("This is not a valid input for the discount. Please Try Again."); 
                    }
                }      
                catch (RuntimeException e){
                System.out.println(e.getMessage()); 
                System.out.println("The input was invalid. Please Try Again.");
                sc.nextLine(); 
                }
            } 


        // Prompts the user for a valid check out date by asking for the date, the month, and the year seperately. 
        // If the user gives an non-integer input at any time, the scanner will throw an exception and cause the first loop to start over.
        // Each of the inner loops will continue until a valid integer is given.  
        // The first loop will end when the user gives a valid input for the date, the month, and the year. 
        // If the program fails to parse the date, the first loop may also start over. 
        boolean outDate =true; 
        boolean tryAgain = true; 
        int inMonth = 0; 
        int inDate = 0;
        int inYear = -1;  
            while (outDate){
                if (tryAgain){
                    boolean validMonth = true; 
                    while(validMonth){
                        System.out.println();
                        System.out.println("Please enter the two digit number for the month.");
                        try{
                        inMonth = sc.nextInt(); 
                        if(agreement.isValidMonth(inMonth)){
                            validMonth = false; 
                        }
                        else{
                            System.out.println("There was an error with the formatting."); 
                        }
                        }catch(Exception e){
                            System.out.println("The input was invalid. Please Try again."); 
                            sc.nextLine(); 
                        }
                    }
                    boolean validDate = true; 
                        while(validDate){
                            System.out.println();
                            System.out.println("Please enter the two digit number for the Date.");
                            try{
                            inDate = sc.nextInt(); 
                                if(agreement.isValidDateInMonth(inMonth, inDate)){
                                    validDate = false; 
                                }
                                else {
                                    System.out.println("There was an error with the formatting."); 
                                }
                            }catch(Exception e){
                                System.out.println("The input was invalid. Please Try again."); 
                                sc.nextLine(); 

                            }
                        }
                    boolean validYear = true;
                        while(validYear){
                            System.out.println();
                            System.out.println("Please enter the two digit number for the Year.");
                            try{
                            inYear = sc.nextInt(); 
                                if(inYear >-1 && inYear<100){
                                    validYear = false; 
                                }
                                else {
                                    System.out.println("There was an error with the formatting."); 
                                }
                            }catch(Exception e){
                                System.out.println("The input was invalid. Please Try again."); 
                                sc.nextLine(); 
                            }
                        }
            
                    String createDate = Integer.toString(inMonth)+"/"+Integer.toString(inDate)+"/"+Integer.toString(inYear);  
                    System.out.println("The check out date is "+ createDate);
                    outDate = false; 
                    try{
                    Date tempDate = DATE_FORMAT.parse(createDate);
                    inputCheckOutDate.setTime(tempDate);
                    agreement.checkOutDate = inputCheckOutDate; 
                    agreement.getAndSetRentalDueDate(inputCheckOutDate, inputRentalDayCount); 
                    }
                    catch(ParseException e ){
                    tryAgain = true;
                    outDate = true;
                    System.out.println("There is an error with the given date to parse. Please try again");
                    }
                } 
            }    
        }

        // Close scanner. 
        finally{ 
        System.out.println();
        sc.close();
        }

        // Calculates the prices and fills the remaining attributes for the Rental Agreement object. 
        // Prints the results to the console using Rental Agreement methods and gathered information.   
        agreement.createRentalAgreementReciept();
        agreement.printRentalAgreementReciept();
        return; 
    }
}
