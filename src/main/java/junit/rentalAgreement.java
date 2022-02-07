package junit;

import java.math.BigDecimal;
import java.util.*;
import java.math.*;
import java.time.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class rentalAgreement {
    int rentalDay;
    String toolCode; 
    String toolType; 
    String toolBrand; 

    Calendar checkOutDate; 
    Calendar dueDate; 
    int chargeDays; 
    BigDecimal preDiscountCharge; 
    int discountPercent; 
    BigDecimal discountAmount; 
    BigDecimal finalCharge; 
    BigDecimal dailyCharge; 

    public static final BigDecimal Ladder_Daily_Charge = BigDecimal.valueOf(1.99);
    public static final BigDecimal Chainsaw_Daily_Charge = BigDecimal.valueOf(1.49);
    public static final BigDecimal Jackhammer_Daily_Charge = BigDecimal.valueOf(2.99);
    public static final RoundingMode Round = RoundingMode.HALF_UP; 
    public static final String DATE_FORMAT_PATTERN = "MM/dd/yy"; 
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN); 
    
    boolean weekendCharge; 
    boolean holidayCharge;
 
    public rentalAgreement (){
        rentalDay = 0;
        toolCode = "CHNS"; 
        toolType = "Chainsaw"; 
        toolBrand = "Stihl"; 
        checkOutDate = Calendar.getInstance(); 
        dueDate = Calendar.getInstance(); 
        chargeDays = 0; 
        preDiscountCharge = BigDecimal.valueOf(0.0); 
        discountPercent = 0 ; 
        discountAmount = BigDecimal.valueOf(0.0); 
        finalCharge = BigDecimal.valueOf(0.0);
        weekendCharge = false; 
        holidayCharge = true; 
    }

    /**
     * @param inToolCode   Tool Code: {JAKR, JAKD, LADW, CHNS}
     * @param inCheckOutDate  Formatted as mm/dd/yy
     * @param inRentalDays  Represents the number of days that a user wants to rent the tool.
     * @param inDiscount  Represents the percent discount (0-100).  
     */
    public rentalAgreement(String inToolCode, String inCheckOutDate, int inRentalDays, int inDiscount){
        this.rentalDay = inRentalDays;
        this.discountPercent = inDiscount; 
        this.checkOutDate = Calendar.getInstance(); 
        this.dueDate = Calendar.getInstance(); 
        this.setToolCode(inToolCode);
        this.setCheckOutDate(inCheckOutDate); 
        this.getAndSetRentalDueDate(this.checkOutDate, inRentalDays); 
        this.preDiscountCharge = calculatePreDiscountCharge();  
        this.discountAmount = discountAmount(inDiscount, preDiscountCharge); 
        this.finalCharge = calculateFinalAmount(preDiscountCharge, discountAmount); 
    }

    /**
     * 
     * Validates whether an Integer represents a month. 
     * @param testMonth  An integer to test. 
     * @return boolean   True if testMonth is betwen 1-12.
     */
    public boolean isValidMonth (int testMonth){
        if(testMonth<13 && testMonth > 0){
            return true; 
        }
        return false; 
    }

    /**
     * Validates whether a month and a date represents a date within a year.
     * 
     * @param testMonth  Integer representing a month. Value between 1-12 
     * @param testDate   Integer representing a date. Value between 1-30 and 31. 
     * @return boolean: if the combination of month and date represents an actual date in the year. 
     */
    public boolean isValidDateInMonth(int testMonth, int testDate){
        boolean isValid = false; 

        //Test for February. 
        if(isValidMonth(testMonth)){
            if (testMonth == 2){
                if ( (testDate>0 ) && (testDate<30)) {
                    isValid = true;
                }
            } 

            //Months with 31 Days.
            else if( (testMonth < 8) && ((testMonth % 2) == 1)){
                if ((testDate > 0) && (testDate < 32)){
                    isValid = true;
                }
            } 
            else if( (testMonth < 13) && (testMonth > 7 ) && ( (testMonth % 2) == 0)){
                if ( (testDate > 0) && (testDate < 32)){
                    isValid =  true;
                }
            }

            //Months with 30 Days
            else if( (testMonth < 8) && ((testMonth % 2) == 0)){
                if ((testDate > 0) && (testDate < 31)){
                    isValid = true;
                }
            }
            else if( (testMonth < 13) && (testMonth > 7 ) && ( (testMonth % 2) == 1)){
                if ( (testDate > 0) && (testDate < 31)){
                    isValid =  true;
                }
            }
            
        }
        return isValid; 
    }

    /**
     * Sets the check out date for a String in the format "mm/dd/yy."
     * Creates a Calendar object and sets it to this.checkOutDate variable for the class.
     * 
     * @requires validDate is a String with the format 'mm/dd/yy'. 
     * @param validDate  the String to be parsed into a Calendar object. 
     * @throws Formatting exception. From parse method.
     * 
     */
    public void setCheckOutDate(String validDate){
        Calendar inputCheckOutDate = (Calendar) this.checkOutDate.clone(); 
        try{
        Date tempDate = DATE_FORMAT.parse(validDate);
        inputCheckOutDate.setTime(tempDate);
        this.checkOutDate = inputCheckOutDate; 
        }catch (Exception e){
        System.out.println("There was an error with the format of the check out date. ");
        }
        return; 
    }

    /**
     * Responsible for calculating the due date for the Rental Agreement and setting rentalDay variable for the Rental Agreement.
     * @param checkOutDate  Calendar object representing the check out date.
     * @param rentalDays  Represents the duration that the Item is rented out for in days. 
     * @return Calendar   Represents the calculated due date for the Rental Agreement. 
     */
    public Calendar getAndSetRentalDueDate (Calendar checkOutDate, int rentalDays){
        Calendar date = (Calendar) checkOutDate.clone(); ; 
        date.add(Calendar.DATE, rentalDays-1);
        this.rentalDay = rentalDays; 
        this.dueDate = date;      
        return date; 
    }

    /**
     * Sets the amount of days that the tool is rented out for in the Rental Agreement. 
     * @param days Represents the duration in days. 
     */
    public void setRentalDaysIn(int days){
        this.rentalDay = days; 
        return; 
    }

    public void setDiscountInteger(int discount){
        this.discountPercent = discount; 
    }

    /**
     * Calculates Independence Day for the year of a given Calendar object. 
     * This is used to calculate potential days without a charge for rental.
     * @param testMe  The Calendar Object to calculate Independence Day. 
     * @return Calendar  The day that the Holiday is recognized. If it falls on a weekend day, 
     * the value is changed to the closest weekday. 
     */
    public Calendar getIndependenceDay(Calendar testMe){

        Calendar date = testMe;
        int independenceDayYear = date.get(Calendar.YEAR); 
        Calendar tempVar = Calendar.getInstance(); 
        tempVar.set(independenceDayYear, Calendar.JULY, 4);
            if (tempVar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){ 
                tempVar.add(Calendar.DATE, -1);
            }
            else if(tempVar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){ 
                tempVar.add(Calendar.DATE, 1);
            }
        return tempVar; 
    }

    /**
     * Evaluates whether a given Calendar object represents Independnce Day. 
     * Calls getIndependenceDay()
     * @param testMe  Calendar Object representing the day to be tested.
     * @return boolean validating a Calendar Object as Independence day. 
     * 
     */
    public boolean isIndependenceDay(Calendar testMe){
        Calendar independenceDay = getIndependenceDay(testMe); 
        if ((testMe.get(Calendar.MONTH) == independenceDay.get(Calendar.MONTH)) && (testMe.get(Calendar.DATE) == independenceDay.get(Calendar.DATE))){
            return true;
        }
        return false; 
    }

    /**
     * Validates whether or not a given Calendar object represents a weekend
     * @param testMe  The Calendar object to be tested. 
     * @return boolean represents whether the Calendar object is a weekend. 
     */
    public boolean isWeekend(Calendar testMe){
        Calendar date = testMe; 
        if(date.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY){
            return true;
        }else if ((date.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY)){
            return true; 
        }
        return false; 
    }

    /**
     * Identifies Labor Day for a given Calendar object's year. 
     * It does this by looping through the first week of September and checking which of those 
     * Days represents a Monday. 
     * 
     * @param testMe  the Calendar object to be tested. 
     * @return        A calendar object representing Labor Day for the Year. 
     */
    public Calendar getLaborDay(Calendar testMe){
        Calendar date = (Calendar) testMe.clone();
        int laborDayYear = date.get(Calendar.YEAR); 
        Calendar tempVar = Calendar.getInstance(); 
        tempVar.set(laborDayYear, Calendar.SEPTEMBER, 1);
            while (tempVar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){ 
                tempVar.add(Calendar.DATE, 1);
            }
        return tempVar; 
    }

    /**
     * Validetes whether a given Calendar object represents Labor Day 
     * @param testMe  The Calendar Object that is tested. 
     * @return boolean  represents whether the Calendar object is Labor Day. 
     * 
     */
    public boolean isLaborDay(Calendar testMe){
        Calendar laborDay = this.getLaborDay(testMe); 
        if ((testMe.get(Calendar.MONTH) == laborDay.get(Calendar.MONTH)) && (testMe.get(Calendar.DATE) == laborDay.get(Calendar.DATE))){
            return true;
        }
        return false; 
    }

    // Sets the weekend charge to the Rental Agreement if it is a Ladder.
    public void setWeekendCharge(){
        if (this.toolType == "Ladder"){
            this.weekendCharge = true; 
        }
        else{
            this.holidayCharge = false; 
        }
    }

    // Sets the Holiday Charge for the Rental Agreement if the tool type is a Chainsaw. 
    public void setHolidayCharge(){
        if (this.toolType == "Chainsaw"){
            this.holidayCharge = true; 
        }
        else{
            this.holidayCharge = false; 
        }
    }


    /**
     * Given a valid Tool Code, sets the values toolCode, toolType, toolBrand, holidayCharge, weekendCharge, and dailyCharge for the Rental Agreement. 
     * for the Rental Agreement. 
     * @param testMe  Four letter String representing a valid ToolCode.
     * @requires  testMe must be one of the following {CHNS, JAKR, JAKD, LADW}
     */
    public void setToolCode (String testMe){
        switch(testMe){
            case "CHNS" : 
                this.toolCode = "CHNS"; 
                this.toolType = "Chainsaw"; 
                this.toolBrand = "Stihl"; 
                this.holidayCharge =true;
                this.weekendCharge = false; 
                this.dailyCharge =  BigDecimal.valueOf(1.49);
            break; 

            case "LADW": 
                this.toolCode = "LADW"; 
                this.toolType = "Ladder"; 
                this.toolBrand = "Werner"; 
                this.holidayCharge =false; 
                this.weekendCharge = true; 
                this.dailyCharge =  BigDecimal.valueOf(1.99);
            break; 

            case "JAKD": 
                this.toolCode = "JAKD"; 
                this.toolType = "Jackhammer"; 
                this.toolBrand = "DeWalt"; 
                this.holidayCharge =false; 
                this.weekendCharge = false; 
                this.dailyCharge =  BigDecimal.valueOf(2.99);
            break; 

            case "JAKR":
                this.toolCode = "JAKR"; 
                this.toolType = "Jackhammer"; 
                this.toolBrand = "Ridgid"; 
                this.holidayCharge = false; 
                this.weekendCharge = false; 
                this.dailyCharge =  BigDecimal.valueOf(2.99);
            break;
            default: 
            throw new RuntimeException() ; 
        }
        return; 
    }

    /**
     * Validates whether a given string represents a valid Tool Code. 
     * @param testMe  
     * @return boolean  true if the string is one of the following {CHNS, JAKR, JAKD, LADW}. 
     */
    public boolean isValidToolCode (String testMe){
        boolean isValid = false; 

        switch(testMe){
            case "CHNS" : 
                isValid = true; 
            break; 

            case "LADW": 
                isValid = true;
            break; 

            case "JAKD": 
                isValid = true;
            break; 

            case "JAKR":
                isValid = true; 
            break;
        }
        return isValid; 
    }

    /**
     * Validates whether a given Integer represents a valid input for the discount for a Rental Agreement. 
     * @param percent  Integer value representing the desired discount percent. 
     * @return boolean  true if the Integer is a number between 0-100
     */
    public boolean isValidDiscountPercent(int percent){
        if ( ((percent>=0)) && (percent<=100) ){
            return true; 
        }
        return false; 
    }

    /**
     * Validates whether a given integer is a number >=1. 
     * @param time Integer to be evaluated. 
     * @return boolean  represents whether time is >=1. 
     */
    public boolean isValidRentalDuration(int time){
        if (!(time>=1)){
            return false; 
        }
        return true; 
    }


    /**
     *  Calculates the pre-discount charge for a Rental Agreement and sets this value to preDiscountCharge. 
     *  This also sets the chargeDays for the Rental agreement
     * @return  BigDecimal Representing the charge for renting a tool before a discount is applied. 
     * @requires setToolCode method was called for the Rental Agreement. 
     */
    public BigDecimal calculatePreDiscountCharge(){

        Calendar trackDate = (Calendar) checkOutDate.clone(); 
        BigDecimal total = BigDecimal.valueOf(0.0); 
        int dayCount = 0; 

        this.chargeDays = 0; 

        //The following checks the Tool Type. 
        //Then, loops through each day of the rental period for a charge day. 
        //Finally, it calculates the total cost the rental period. 
        if(this.toolType == "Ladder"){
            while(dayCount<this.rentalDay){
                if((!isIndependenceDay(trackDate) && !isLaborDay(trackDate))){
                total = total.add(Ladder_Daily_Charge);
                this.chargeDays++;
                }
            trackDate.add(Calendar.DATE, 1);
            dayCount++; 
            }
        }
        else if(this.toolType == "Chainsaw"){
            while(dayCount<this.rentalDay){
                if(!(isWeekend(trackDate))){
                total = total.add(Chainsaw_Daily_Charge);
                this.chargeDays++; 
                }
            trackDate.add(Calendar.DATE, 1);
            dayCount++; 
            }
        }
        else if(this.toolType == "Jackhammer"){
            while(dayCount<this.rentalDay){
                if((!isIndependenceDay(trackDate)&&!isLaborDay(trackDate))&&!isWeekend(trackDate)){
                total = total.add(Jackhammer_Daily_Charge);
                this.chargeDays++;
                }
            trackDate.add(Calendar.DATE, 1);
            dayCount++; 
            }
        }
        this.preDiscountCharge = total; 
        return total; 
    }
    

    /**
     * Calculates the discount amount for The Rental Agreement
     * @param percent  Represents a number between 0-100
     * @param preDisc Represents the calculated cost before a discount is applied.  
     * @return  The amount to discount from the Rental Agreement.
     * @requires CalculatePreDiscountCharge was called for the Rental Agreement object.  
     */
    public BigDecimal discountAmount(int percent, BigDecimal preDisc){
        double temp = (double)percent/100.00; 
        BigDecimal calcPercent = BigDecimal.valueOf(temp); 
        BigDecimal result = preDisc.multiply(calcPercent); 
        result = result.setScale(2, RoundingMode.HALF_UP); 
        return result;  
    }

    /**
     * Calculates the final charge for the Rental Agreement.
     * @param preDiscount BigDecimal representing the charge before a dicount is applied. 
     * @param discount The amount to be discounted from the pre discount charge. 
     * @return A BigDecimal value representing the final calculated cost of the Rental Agreement.
     * @requires Calculated Discount amount and calculatedPreDiscountCharge method was invoked.
     * PreDicount > discount.  
     */
    public BigDecimal calculateFinalAmount(BigDecimal preDiscount, BigDecimal discount){
        if(preDiscount.compareTo(discount)==-1){
            throw new ArithmeticException();  
        }
        BigDecimal results = preDiscount.subtract(discount); 
        results = results.setScale(2, RoundingMode.HALF_UP); 
        return results; 
    }

    /**
     * Runs the required methods for the Rental Agreement to fill in the attributes. 
     * @requires toolCode, rentalDay, discountPercent, checkOutDate are not null. 
     */ 
    public void createRentalAgreementReciept(){
        setToolCode(this.toolCode);
        this.getAndSetRentalDueDate(this.checkOutDate, this.rentalDay); 
        Format f = new SimpleDateFormat("MM/dd/yy");
        String strDate = f.format(this.checkOutDate.getTime());
        String strDueDate = f.format(this.dueDate.getTime()); 
        this.setCheckOutDate(strDate);
        this.setWeekendCharge();
        this.setHolidayCharge();
        this.preDiscountCharge =  this.calculatePreDiscountCharge(); 
        this.discountAmount = discountAmount(this.discountPercent,this.preDiscountCharge);
        this.finalCharge = calculateFinalAmount(this.preDiscountCharge, this.discountAmount);  
        return; 
    }

    /**
     * Prints the rental agreement to the console. 
     */
    public void printRentalAgreementReciept(){
        System.out.println("Tool Code: " + this.toolCode);
        System.out.println("Tool Type: " + this.toolType);
        System.out.println("Tool Brand: " + this.toolBrand);
        System.out.println("Rental Days: " + this.rentalDay);
        Format f = new SimpleDateFormat("MM/dd/yy");
        String strDate = f.format(this.checkOutDate.getTime());
        String strDueDate = f.format(this.dueDate.getTime()); 
        System.out.println("Check Out Date: " + strDate);
        System.out.println("Due Date: " + strDueDate);
        System.out.println("Daily Rental Charge: $"+this.dailyCharge);
        System.out.println("Charge Days: "+this.chargeDays+" days.");
        System.out.println("Pre-Discount Charge (Rounded Half up): $"+this.preDiscountCharge);
        System.out.println("Discount Percent: "+ this.discountPercent+"%");
        System.out.println("Discount Amount: $"+ this.discountAmount);
        System.out.println("Final Charge: $"+this.finalCharge);
        return; 
    }
}
