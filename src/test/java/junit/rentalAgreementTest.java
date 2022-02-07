package junit;
import junit.framework.*;
import org.junit.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
import org.hamcrest.*;
import junit.rentalAgreement;

public class rentalAgreementTest {
   
    private static rentalAgreement test1;
    private static rentalAgreement test2;
    private static rentalAgreement dummy; 
    
    //= new rentalAgreement();  
    // set up a before and after
    @BeforeClass public static void SetVars(){
        dummy = new rentalAgreement(); 
        test1 = new rentalAgreement();
        test2 = new rentalAgreement(); 

    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
    @Test 
    public void shouldCreateDefaultAgreement(){
        rentalAgreement agreement = new rentalAgreement(); 
        assertNotNull(agreement);
    }
    
    @Test
    public void shouldCreateRentalAgreement(){
        rentalAgreement agreement = new rentalAgreement("JAKR", "12/12/12", 1, 5); 
        assertNotNull(agreement);
    }

    @Test
    public void shouldBeValidMonth(){
        assertTrue(dummy.isValidMonth(12)); 
    }
    @Test
    public void upperEdgeInvalidMonth(){
        assertFalse(dummy.isValidMonth(13)); 
    }
    @Test
    public void lowEdgeInvalidMonth(){
        assertFalse(dummy.isValidMonth(0));
    }
    @Test 
    public void testEveryValidMonth(){
        boolean temp = true; 
        for (int x = 1; x<13 ; x++){
            if(!dummy.isValidMonth(x)){
                temp = false; 
            }
        }
        assertTrue(temp); 
    }

    @Test
    public void isValidDateInMonthWorks(){
        boolean test = dummy.isValidDateInMonth(1, 31);
        assertEquals(true, test); 
    }

    @Test 
    public void isValidDateInMonthReasonableValidRangeCheck(){
        int invalidDays = 0; 
        int validDays = 0; 
        for (int x = -12; x < 55; x++){
            for (int y = -20; y < 200; y++){
                if (dummy.isValidDateInMonth(x, y)){
                    validDays++;
                }
                else{
                    invalidDays++;
                }
            }
        }
    assertEquals(366, validDays);
    }

    @Test
    public void setCheckOutWorks(){
        dummy.setCheckOutDate("12/12/12");
        assertNotNull(dummy.checkOutDate);
    }

    @Test
    public void getAndSetRentalDueDateWorks(){
        dummy.dueDate = null; 
        dummy.getAndSetRentalDueDate(dummy.checkOutDate, 5); 
        assertNotNull(dummy.dueDate);
    }
    
    @Test 
    public void setCheckOutWorksDate(){
        dummy.setCheckOutDate("12/12/12"); 
        assertEquals(12, dummy.checkOutDate.get(Calendar.DATE));
        assertEquals(11, dummy.checkOutDate.get(Calendar.MONTH));
        assertEquals(2012, dummy.checkOutDate.get(Calendar.YEAR));
    }
    @Test
    public void getAndSetRentalDueDateValidPositive(){
        dummy.dueDate = null; 
        dummy.setCheckOutDate("12/12/12");
        dummy.getAndSetRentalDueDate(dummy.checkOutDate, 5); 
        assertEquals(16, dummy.dueDate.get(Calendar.DATE)); 
    }
    @Test
    public void setWeekendChargeWorks(){
        dummy.setWeekendCharge(); 
        assertFalse(dummy.weekendCharge);
    }
    @Test
    public void setHolidayChargeWorks(){
        dummy.setWeekendCharge(); 
        assertFalse(dummy.holidayCharge);
    }
    @Test 
    public void setToolCodeWorksFor(){
        dummy.setToolCode("JAKR");
    }
    @Test
    public void setToolCodeInvalidCase(){
        try{
        dummy.setToolCode("SLJKDHFHJ");
        fail("should have thrown an exception"); 
        }catch(Exception e){
            assertTrue(true); 
        }
    }
    
    @Test 
    public void setToolCodeAllPossibleCases(){
        rentalAgreement agreement = new rentalAgreement(); 
        boolean valid = true; 

        String[] validCases = {"JAKR", "JAKD", "LADW", "CHNS"}; 
        for( String isCase : validCases){
            agreement.setToolCode(isCase);
            switch(isCase){
                case "CHNS": 
                    if(!(agreement.toolCode == "CHNS")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.toolType == "Chainsaw")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.toolBrand == "Stihl")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.holidayCharge == true)){ valid = false; System.out.println(isCase);}
                    if(!(agreement.weekendCharge == false)){ valid = false; System.out.println(isCase);}
                    if(agreement.dailyCharge.compareTo(BigDecimal.valueOf(1.49))!=0){ valid = false; System.out.println(isCase);}
                break; 

                case "LADW": 
                    if(!(agreement.toolCode == "LADW")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.toolType == "Ladder")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.toolBrand == "Werner")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.holidayCharge == false)){ valid = false; System.out.println(isCase);}
                    if(!(agreement.weekendCharge == true)){ valid = false; System.out.println(isCase);}
                    if(agreement.dailyCharge.compareTo(BigDecimal.valueOf(1.99))!=0){ valid = false; System.out.println(isCase);}
                break;

                case "JAKD": 
                    if(!(agreement.toolCode == "JAKD")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.toolType == "Jackhammer")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.toolBrand == "DeWalt")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.holidayCharge == false)){ valid = false; System.out.println(isCase);}
                    if(!(agreement.weekendCharge == false)){ valid = false; System.out.println(isCase);}
                    if(agreement.dailyCharge.compareTo(BigDecimal.valueOf(2.99))!=0){ valid = false; System.out.println(isCase);}   

                break;

                case "JAKR": 
                    if(!(agreement.toolCode == "JAKR")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.toolType == "Jackhammer")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.toolBrand == "Ridgid")){ valid = false; System.out.println(isCase);}
                    if(!(agreement.holidayCharge == false)){ valid = false; System.out.println(isCase);}
                    if(!(agreement.weekendCharge == false)){ valid = false; System.out.println(isCase);}
                    if(agreement.dailyCharge.compareTo(BigDecimal.valueOf(2.99))!=0){ valid = false; System.out.println(isCase);}
                break;
                default: 
                    System.out.println("Did not find case");
                    valid = false; 
                break; 
                 
            } 
        }
        assertTrue(valid); 
     
    }
    
    @Test
    public void setToolCodelowerCase(){
        try{
            dummy.setToolCode("chns");
            fail("should have thrown an exception"); 
            }catch(Exception e){
                assertTrue(true); 
            }
    }
    
    @Test
    public void isValidToolCodeWorks(){
        rentalAgreement agreement = new rentalAgreement(); 
        boolean valid = false; 
            if(agreement.isValidToolCode("CHNS")){valid = true;}
        assertTrue(valid); 
    }

    @Test
    public void isValidToolCodeAllPossibleCorrect(){
        rentalAgreement agreement = new rentalAgreement(); 
        boolean valid = true; 

        String[] validCases = {"JAKR", "JAKD", "LADW", "CHNS"}; 
            for( String isCase : validCases){
                if(!agreement.isValidToolCode(isCase)){valid = false;}
            }
        assertTrue(valid);
    }

    @Test 
    public void isValidRentalDurationWorks(){
    rentalAgreement agreement = new rentalAgreement(); 
        boolean valid = true; 
            if(!agreement.isValidRentalDuration(42)){
                valid = false;
            }
        assertTrue(valid); 
    }
    @Test 
    public void isValidRentalDurationInvalid0(){
    rentalAgreement agreement = new rentalAgreement(); 
        boolean valid = false; 
            if(!agreement.isValidRentalDuration(0)){
                valid = true;
            }
        assertTrue(valid); 
    }
    @Test 
    public void isValidRentalDurationInvalidNeg(){
    rentalAgreement agreement = new rentalAgreement(); 
        boolean valid = false; 
            if(!agreement.isValidRentalDuration(-1)){
                valid = true;
            }
        assertTrue(valid); 
    }

    @Test
    public void isValiDiscountPercentWorksAndCorrect(){
        rentalAgreement agreement = new rentalAgreement(); 
        boolean valid = false; 
            if(agreement.isValidDiscountPercent(42)){
                valid = true;
            }
        assertTrue(valid); 
    }

    @Test
    public void isValidDiscountPercentRangeCorrect(){
        rentalAgreement agreement = new rentalAgreement(); 
        boolean valid = true; 
            if(agreement.isValidDiscountPercent(-1) || agreement.isValidDiscountPercent(101)){
                valid = false;
            }
            for(int x = 1; x < 101; x++){
                if(!agreement.isValidDiscountPercent(x)){
                    valid = false;
                }
            }
        assertTrue(valid); 
    
    }

    @Test
    public void CalculatePreDiscountChargeWorks(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(1);
        agreement.calculatePreDiscountCharge(); 
        assertNotNull(agreement.preDiscountCharge);
    }

    @Test
    public void CalculatePreDiscountChargeCorrect(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(1);
        agreement.setToolCode("CHNS");
        agreement.setCheckOutDate("12/03/12");
        agreement.getAndSetRentalDueDate(agreement.checkOutDate, 1); 
        agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
        assertEquals(BigDecimal.valueOf(1.49), agreement.preDiscountCharge);
    } 

    @Test
    public void CalculatePreDiscountNoIndependenceDayChargeHolidays(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(3);
        agreement.setToolCode("LADW");
        agreement.setCheckOutDate("07/03/22");
        agreement.getAndSetRentalDueDate(agreement.checkOutDate, 3); 
        agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
        assertEquals(BigDecimal.valueOf(3.98), agreement.preDiscountCharge);

    }
    @Test
    public void CalculatePreDiscountNoLaborDayHolidayCharge(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(3);
        agreement.setToolCode("LADW");
        agreement.setCheckOutDate("09/01/22");
        agreement.getAndSetRentalDueDate(agreement.checkOutDate, 7); 
        agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
        assertEquals(BigDecimal.valueOf(11.94), agreement.preDiscountCharge);

    }

    @Test
    public void CalculatePreDiscountWorksWeekends(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(7);
        agreement.setToolCode("CHNS");
        agreement.setCheckOutDate("09/01/22");
        agreement.getAndSetRentalDueDate(agreement.checkOutDate, 7); 
        agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
        assertEquals(BigDecimal.valueOf(7.45), agreement.preDiscountCharge);

    }

    @Test
    public void discountAmountWorks(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(7);
        agreement.setToolCode("CHNS");
        agreement.setCheckOutDate("09/01/22");
        agreement.getAndSetRentalDueDate(agreement.checkOutDate, 7); 
        agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
        agreement.discountAmount = agreement.discountAmount( 50, agreement.preDiscountCharge); 
        assertEquals(BigDecimal.valueOf(3.73), agreement.discountAmount);
    }

    @Test
    public void discountAmountCorrect(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(7);
        agreement.setToolCode("CHNS");
        agreement.setCheckOutDate("09/01/22");
        agreement.getAndSetRentalDueDate(agreement.checkOutDate, 7); 
        agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
        agreement.discountAmount = agreement.discountAmount( 50, agreement.preDiscountCharge); 
        assertEquals(BigDecimal.valueOf(3.73), agreement.discountAmount);

    }

    @Test
    public void discountAmountRoundUpIfPossible(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(7);
        agreement.setToolCode("CHNS");
        agreement.setCheckOutDate("09/01/22");
        agreement.getAndSetRentalDueDate(agreement.checkOutDate, 7); 
        agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
        agreement.discountAmount = agreement.discountAmount( 50, agreement.preDiscountCharge); 
        assertEquals(BigDecimal.valueOf(3.73), agreement.discountAmount);

    }

    @Test
    public void CalculateFinalAmountWorks(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(7);
        agreement.setToolCode("CHNS");
        agreement.setCheckOutDate("09/01/22");
        agreement.getAndSetRentalDueDate(agreement.checkOutDate, 7); 
        agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
        agreement.discountAmount = agreement.discountAmount( 50, agreement.preDiscountCharge); 
        agreement.finalCharge = agreement.calculateFinalAmount(agreement.preDiscountCharge, agreement.discountAmount); 
        assertEquals(BigDecimal.valueOf(3.72), agreement.finalCharge);
    }

    @Test
    public void CalculateFinalAmountCorrect(){
        rentalAgreement agreement = new rentalAgreement(); 
        agreement.setRentalDaysIn(7);
        agreement.setToolCode("CHNS");
        agreement.setCheckOutDate("09/01/22");
        agreement.getAndSetRentalDueDate(agreement.checkOutDate, 7); 
        agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
        agreement.discountAmount = agreement.discountAmount( 50, agreement.preDiscountCharge); 
        agreement.finalCharge = agreement.calculateFinalAmount(agreement.preDiscountCharge, agreement.discountAmount); 
        assertEquals(BigDecimal.valueOf(3.72), agreement.finalCharge);

    }

    @Test
    public void CalculateFinalAmountInvalidSubtraction(){
        try{
            rentalAgreement agreement = new rentalAgreement(); 
            agreement.setRentalDaysIn(7);
            agreement.setToolCode("CHNS");
            agreement.setCheckOutDate("09/01/22");
            agreement.getAndSetRentalDueDate(agreement.checkOutDate, 7); 
            agreement.preDiscountCharge = agreement.calculatePreDiscountCharge(); 
            agreement.discountAmount = agreement.discountAmount( 50, agreement.preDiscountCharge); 
            agreement.finalCharge = agreement.calculateFinalAmount(BigDecimal.valueOf(1), BigDecimal.valueOf(9000));
            fail("This should have thrown an arithmetic exception"); 
            
        }catch(Exception e) {
            assertTrue(true); 
        }
    }
  
    @Test
    public void testCase1(){
        if(test1.isValidDiscountPercent(101)){
            fail("This cannot return true."); 
        }
        assertTrue(true);  
    }

    // Simulating the methods that would be executed for the user input to make the test valid. 

    @Test
    public void testCase2(){
        try{
        if (!test2.isValidToolCode("LADW")){
            fail("The Tool Code is supposed to be valid for the Test Case."); 
        } 
        test2.setToolCode("LADW");
        if(!test2.isValidDiscountPercent(10)){
            fail("The discount is valid for the test case."); 
        }
        test2.discountPercent = 10; 
        if(!test2.isValidRentalDuration(3)){
            fail("The rental duration of 3 is valid for the test case."); 
        }
        test2.rentalDay = 3; 
        test2.setCheckOutDate("07/02/20");
        test2.getAndSetRentalDueDate(test2.checkOutDate, test2.rentalDay); 
        test2.createRentalAgreementReciept();
        test2.printRentalAgreementReciept();
        }catch(Exception e){
            fail("Test Case 2 should pass but it caused an exception"); 
        }

        // If all methods go without fail. It should print results. 
        // Check if all the attibutes create the expected print results. 
        if(test2.toolCode != "LADW"){fail("The Tool Code should be valid.");}
        if(test2.toolType!="Ladder"){fail("The Tool type is supposed to be valid");}
        if(test2.toolBrand!="Werner"){fail("The Tool brand is supposed to be valid");}
        if(test2.rentalDay!=3){fail("The Rental Days is supposed to be valid");}
        if(test2.toolBrand!="Werner"){fail("The Tool brand is supposed to be valid");}
        Format f = new SimpleDateFormat("MM/dd/yy");
        String strDate = f.format(test2.checkOutDate.getTime());
        String strDueDate = f.format(test2.dueDate.getTime()); 
        if(!strDate.equals("07/02/20")){fail("The check out date is supposed to be valid");}
        if(!strDueDate.equals("07/04/20")){fail("The due date is supposed to be valid");}
        if(test2.dailyCharge.compareTo(BigDecimal.valueOf(1.99))!=0){fail("The daily charge is supppsed to be valid");}
        if(test2.chargeDays!= 2){fail("The daily charge is supppsed to be valid and 2 for this test case");}
        if(test2.discountPercent!= 10){fail("The pre-discount percent is supposed to be valid and 10%");}
        if(test2.preDiscountCharge.compareTo(BigDecimal.valueOf(3.98))!=0){fail("The pre-discount charge is supposed to be valid and 7.96");}
        if(test2.discountAmount.compareTo(BigDecimal.valueOf(0.40))!=0){fail("The discount is supposed to be valid and 0.80");}
        if(test2.finalCharge.compareTo(BigDecimal.valueOf(3.58))!=0){fail("The final charge is supposed to be valid and 7.16");}

        assertTrue(true);  
    }
    @Test
    public void testCase3(){
        try{
        if (!test2.isValidToolCode("CHNS")){
            fail("The Tool Code is supposed to be valid for the Test Case."); 
        } 
        test2.setToolCode("CHNS");
        if(!test2.isValidDiscountPercent(25)){
            fail("The discount is valid for the test case."); 
        }
        test2.discountPercent = 25; 
        if(!test2.isValidRentalDuration(5)){
            fail("The rental duration of 3 is valid for the test case."); 
        }
        test2.rentalDay = 5; 
        test2.setCheckOutDate("07/02/15");
        test2.getAndSetRentalDueDate(test2.checkOutDate, test2.rentalDay); 
        test2.createRentalAgreementReciept();
        test2.printRentalAgreementReciept();
        }catch(Exception e){
            fail("Test Case 2 should pass but it caused an exception"); 
        }

        // If all methods go without fail. It should print results. 
        // Check if all the attibutes create the expected print results. 
        if(test2.toolCode != "CHNS"){fail("The Tool Code should be valid.");}
        if(test2.toolType!="Chainsaw"){fail("The Tool type is supposed to be valid");}
        if(test2.toolBrand!="Stihl"){fail("The Tool brand is supposed to be valid");}
        if(test2.rentalDay!=5){fail("The Rental Days is supposed to be valid");}
        Format f = new SimpleDateFormat("MM/dd/yy");
        String strDate = f.format(test2.checkOutDate.getTime());
        String strDueDate = f.format(test2.dueDate.getTime()); 
        if(!strDate.equals("07/02/15")){fail("The check out date is supposed to be valid");}
        if(!strDueDate.equals("07/06/15")){fail("The due date is supposed to be valid");}
        if(test2.dailyCharge.compareTo(BigDecimal.valueOf(1.49))!=0){fail("The daily charge is supppsed to be valid");}
        if(test2.chargeDays!= 3){fail("The daily charge is supppsed to be valid and 4 for this test case");}
        if(test2.discountPercent!= 25){fail("The pre-discount percent is supposed to be valid and 10%");}
        if(test2.preDiscountCharge.compareTo(BigDecimal.valueOf(4.47))!=0){fail("The pre-discount charge is supposed to be valid and 7.96");}
        if(test2.discountAmount.compareTo(BigDecimal.valueOf(1.12))!=0){fail("The discount is supposed to be valid and 0.80");}
        if(test2.finalCharge.compareTo(BigDecimal.valueOf(3.35))!=0){fail("The final charge is supposed to be valid and 7.16");}

        assertTrue(true);  
    }
    @Test
    public void testCase4(){
        try{
        if (!test2.isValidToolCode("JAKD")){
            fail("The Tool Code is supposed to be valid for the Test Case."); 
        } 
        test2.setToolCode("JAKD");
        if(!test2.isValidDiscountPercent(25)){
            fail("The discount is valid for the test case."); 
        }
        test2.discountPercent = 0; 
        if(!test2.isValidRentalDuration(6)){
            fail("The rental duration of 6 is valid for the test case."); 
        }
        test2.rentalDay = 6; 
        test2.setCheckOutDate("09/03/15");
        test2.getAndSetRentalDueDate(test2.checkOutDate, test2.rentalDay); 
        test2.createRentalAgreementReciept();
        test2.printRentalAgreementReciept();
        }catch(Exception e){
            fail("Test Case 2 should pass but it caused an exception"); 
        }

        // If all methods go without fail. It should print results. 
        // Check if all the attibutes create the expected print results. 
        if(test2.toolCode != "JAKD"){fail("The Tool Code should be valid.");}
        if(test2.toolType!="Jackhammer"){fail("The Tool type is supposed to be valid");}
        if(test2.toolBrand!="DeWalt"){fail("The Tool brand is supposed to be valid");}
        if(test2.rentalDay!=6){fail("The Rental Days is supposed to be valid");}
        Format f = new SimpleDateFormat("MM/dd/yy");
        String strDate = f.format(test2.checkOutDate.getTime());
        String strDueDate = f.format(test2.dueDate.getTime()); 
        if(!strDate.equals("09/03/15")){fail("The check out date is supposed to be valid");}
        if(!strDueDate.equals("09/08/15")){fail("The due date is supposed to be valid");}
        if(test2.dailyCharge.compareTo(BigDecimal.valueOf(2.99))!=0){fail("The daily charge is supppsed to be valid");}
        if(test2.chargeDays!= 3){fail("The daily charge is supppsed to be valid and 3 for this test case");}
        if(test2.discountPercent!= 0){fail("The pre-discount percent is supposed to be valid and 10%");}
        if(test2.preDiscountCharge.compareTo(BigDecimal.valueOf(8.97))!=0){fail("The pre-discount charge is supposed to be valid and 7.96");}
        if(test2.discountAmount.compareTo(BigDecimal.valueOf(0.00))!=0){fail("The discount is supposed to be valid and 0.80");}
        if(test2.finalCharge.compareTo(BigDecimal.valueOf(8.97))!=0){fail("The final charge is supposed to be valid and 7.16");}

        assertTrue(true);  
    }

    @Test
    public void testCase5(){
        try{
        if (!test2.isValidToolCode("JAKR")){
            fail("The Tool Code is supposed to be valid for the Test Case."); 
        } 
        test2.setToolCode("JAKR");
        if(!test2.isValidDiscountPercent(0)){
            fail("The discount is valid for the test case."); 
        }
        test2.discountPercent = 0; 
        if(!test2.isValidRentalDuration(9)){
            fail("The rental duration of 9 is valid for the test case."); 
        }
        test2.rentalDay = 9; 
        test2.setCheckOutDate("07/02/15");
        test2.getAndSetRentalDueDate(test2.checkOutDate, test2.rentalDay); 
        test2.createRentalAgreementReciept();
        test2.printRentalAgreementReciept();
        }catch(Exception e){
            fail("Test Case 2 should pass but it caused an exception"); 
        }

        // If all methods go without fail. It should print results. 
        // Check if all the attibutes create the expected print results. 
        if(test2.toolCode != "JAKR"){fail("The Tool Code should be valid.");}
        if(test2.toolType!="Jackhammer"){fail("The Tool type is supposed to be valid");}
        if(test2.toolBrand!="Ridgid"){fail("The Tool brand is supposed to be valid");}
        if(test2.rentalDay!=9){fail("The Rental Days is supposed to be valid");}
        Format f = new SimpleDateFormat("MM/dd/yy");
        String strDate = f.format(test2.checkOutDate.getTime());
        String strDueDate = f.format(test2.dueDate.getTime()); 
        if(!strDate.equals("07/02/15")){fail("The check out date is supposed to be valid");}
        if(!strDueDate.equals("07/10/15")){fail("The due date is supposed to be valid");}
        if(test2.dailyCharge.compareTo(BigDecimal.valueOf(2.99))!=0){fail("The daily charge is supppsed to be valid");}
        if(test2.chargeDays!= 6){fail("The daily charge is supppsed to be valid and 3 for this test case");}
        if(test2.discountPercent!= 0){fail("The pre-discount percent is supposed to be valid and 10%");}
        if(test2.preDiscountCharge.compareTo(BigDecimal.valueOf(17.94))!=0){fail("The pre-discount charge is supposed to be valid and 7.96");}
        if(test2.discountAmount.compareTo(BigDecimal.valueOf(0))!=0){fail("The discount is supposed to be valid and 0.80");}
        if(test2.finalCharge.compareTo(BigDecimal.valueOf(17.94))!=0){fail("The final charge is supposed to be valid and 7.16");}

        assertTrue(true);  
    }
    @Test
    public void testCase6(){
        try{
        if (!test2.isValidToolCode("JAKR")){
            fail("The Tool Code is supposed to be valid for the Test Case."); 
        } 
        test2.setToolCode("JAKR");
        if(!test2.isValidDiscountPercent(50)){
            fail("The discount is valid for the test case."); 
        }
        test2.discountPercent = 50; 
        if(!test2.isValidRentalDuration(4)){
            fail("The rental duration of 9 is valid for the test case."); 
        }
        test2.rentalDay = 4; 
        test2.setCheckOutDate("07/02/15");
        test2.getAndSetRentalDueDate(test2.checkOutDate, test2.rentalDay); 
        test2.createRentalAgreementReciept();
        test2.printRentalAgreementReciept();
        }catch(Exception e){
            fail("Test Case 2 should pass but it caused an exception"); 
        }

        // If all methods go without fail. It should print results. 
        // Check if all the attibutes create the expected print results. 
        if(test2.toolCode != "JAKR"){fail("The Tool Code should be valid.");}
        if(test2.toolType!="Jackhammer"){fail("The Tool type is supposed to be valid");}
        if(test2.toolBrand!="Ridgid"){fail("The Tool brand is supposed to be valid");}
        if(test2.rentalDay!=4){fail("The Rental Days is supposed to be valid");}
        Format f = new SimpleDateFormat("MM/dd/yy");
        String strDate = f.format(test2.checkOutDate.getTime());
        String strDueDate = f.format(test2.dueDate.getTime()); 
        if(!strDate.equals("07/02/15")){fail("The check out date is supposed to be valid");}
        if(!strDueDate.equals("07/05/15")){fail("The due date is supposed to be valid");}
        if(test2.dailyCharge.compareTo(BigDecimal.valueOf(2.99))!=0){fail("The daily charge is supppsed to be valid");}
        if(test2.chargeDays!=1 ){fail("The daily charge is supppsed to be valid and 3 for this test case");}
        if(test2.discountPercent!= 50){fail("The pre-discount percent is supposed to be valid and 10%");}
        if(test2.preDiscountCharge.compareTo(BigDecimal.valueOf(2.99))!=0){fail("The pre-discount charge is supposed to be valid and 7.96");}
        if(test2.discountAmount.compareTo(BigDecimal.valueOf(1.50))!=0){fail("The discount is supposed to be valid and 0.80");}
        if(test2.finalCharge.compareTo(BigDecimal.valueOf(1.49))!=0){fail("The final charge is supposed to be valid and 7.16");}

        assertTrue(true);  
    }

    @Test 
    public void getIndependenceDayWorks(){
        dummy.setCheckOutDate("07/04/22"); 
        Calendar cal = dummy.getIndependenceDay(dummy.checkOutDate);
        assertEquals(04, cal.get(Calendar.DATE));
        assertEquals(06, cal.get(Calendar.MONTH));
        assertEquals(2022, cal.get(Calendar.YEAR));
    }

    
    @Test 
    public void getIndependenceDayWeekendValidFriday(){
        dummy.setCheckOutDate("07/04/15"); 
        Calendar cal = dummy.getIndependenceDay(dummy.checkOutDate);
        assertEquals(03, cal.get(Calendar.DATE));
        assertEquals(06, cal.get(Calendar.MONTH));
        assertEquals(2015, cal.get(Calendar.YEAR));
        assertEquals(6 ,cal.get(Calendar.DAY_OF_WEEK));
    }
    @Test 
    public void getIndependenceDayWeekendValidMonday(){
        dummy.setCheckOutDate("07/04/10"); 
        Calendar cal = dummy.getIndependenceDay(dummy.checkOutDate);
        assertEquals(05, cal.get(Calendar.DATE));
        assertEquals(06, cal.get(Calendar.MONTH));
        assertEquals(2010, cal.get(Calendar.YEAR));
        assertEquals(2 ,cal.get(Calendar.DAY_OF_WEEK));
    }

    @Test 
    public void isValidIndependenceDay(){
        dummy.setCheckOutDate("07/04/12"); 
        Calendar cal = dummy.getIndependenceDay(dummy.checkOutDate);
        assertTrue(dummy.isIndependenceDay(cal)); 
    }
    public void isInValidIndependenceDay(){
        dummy.setCheckOutDate("07/20/12"); 
        assertFalse(dummy.isIndependenceDay(dummy.checkOutDate)); 
    }
    @Test 
    public void isValidIndependenceDayMonday(){
        dummy.setCheckOutDate("07/05/10"); 
        Calendar cal = dummy.getIndependenceDay(dummy.checkOutDate);
        assertTrue(dummy.isIndependenceDay(cal)); 
    }
    @Test 
    public void isValidIndependenceDayFriday(){
        dummy.setCheckOutDate("07/03/15"); 
        Calendar cal = dummy.getIndependenceDay(dummy.checkOutDate);
        assertTrue(dummy.isIndependenceDay(cal)); 
    }
    public void getLaborDay(){
        dummy.setCheckOutDate("09/06/21"); 
        Calendar cal = dummy.getLaborDay(dummy.checkOutDate);
        assertEquals(06, cal.get(Calendar.DATE));
        assertEquals(8, cal.get(Calendar.MONTH));
        assertEquals(2021, cal.get(Calendar.YEAR));
        assertEquals(2 ,cal.get(Calendar.DAY_OF_WEEK));
    }

    @Test 
    public void isValidLaborDay(){
        dummy.setCheckOutDate("09/06/21"); 
        Calendar cal = dummy.getLaborDay(dummy.checkOutDate);
        assertTrue(dummy.isLaborDay(cal)); 
    }
    
    @Test
    public void isInValidLaborDay(){
        dummy.setCheckOutDate("07/20/12"); 
        assertFalse(dummy.isLaborDay(dummy.checkOutDate)); 
    }
       

}
