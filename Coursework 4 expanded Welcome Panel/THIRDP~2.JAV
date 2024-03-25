import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.control.Label;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class ThirdPaneTest {
    
    private ThirdPane thirdPane;
    
    @BeforeEach
    public void setUp()
    {
        thirdPane = new ThirdPane();
        thirdPane.startDate = LocalDate.of(2021, 2, 8);
        thirdPane.endDate = LocalDate.of(2022, 7, 5);
    }
    
    /**
     * This function tests if the total Deaths given a start date and and end Date is correctly generated and rendered onto the Stats Panel
     * It uses the JUnit Test representative function to generate the totalDeaths and then gets the label's text for comparison
     * This function tests if the total deaths generated are correct when there is data available every single day between the start and end date
     */
    @Test
    public void testGenerateTotalDeathsLabelEverythingAvailable() {
        Label label = thirdPane.generateTotalDeathsLabel_JUnitTest();
        assertNotNull(label);
        assertEquals("Total Deaths: " + 5881, label.getText());
    }

    /**
     * This function tests if the total Deaths error message given another start date and and end Date is correctly generated and rendered onto the Stats Panel
     * It uses the JUnit Test representative function to generate the totalDeaths and then gets the label's text for comparison
     * This function tests if the total deaths generated are correct when data is NOT available for every single day between the start and end date
     * This should display an error message saying that the data base does not fully cover selected period
     */
    @Test 
    public void testGenerateTotalDeathsLabelNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2020, 2, 3);
        thirdPane.endDate = LocalDate.of(2023, 3, 7);
        Label label = thirdPane.generateTotalDeathsLabel_JUnitTest();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }
    
    /**
     * This function tests if the average retail and recreation GMR data given a start date and and end Date is correctly generated and rendered onto the Stats Panel
     * It uses the JUnit Test representative function to generate the average retail and recreation GMR and then gets the label's text for comparison
     * This function tests if the average retail and recreation GMR generated are correct when there is data available every single day between the start and end date
     */
    @Test
    public void testGenerateGMR1LabelEverythingAvailable() {
        Label label = thirdPane.generateGMR1Label_JUnitTest();
        assertNotNull(label);
        assertEquals("Average Retail and Recreation \nPercentage Change: " + -5 + "%", label.getText());
    }
    
    /**
     * This function tests if the average retail and recreation GMR error message given another start date and and end Date is correctly generated and rendered onto the Stats Panel
     * It uses the JUnit Test representative function to generate the average retail and recreation GMR and then gets the label's text for comparison
     * This function tests if the average retail and recreation GMR generated are correct when data is NOT available for every single day between the start and end date
     * This should display an error message saying that the data base does not fully cover selected period
     */
    @Test 
    public void testGenerateGMR1LabelNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2023, 1, 1);
        thirdPane.endDate = LocalDate.of(2023, 12, 31);
        Label label = thirdPane.generateGMR1Label_JUnitTest();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }
    
    /**
     * This function tests if the average retail and recreation GMR data given a start date and and end Date is correctly generated and rendered onto the Stats Panel
     * It uses the JUnit Test representative function to generate the average retail and recreation GMR and then gets the label's text for comparison
     * This function tests if the average retail and recreation GMR generated are correct when there is data available every single day between the start and end date
     */
    @Test
    public void testAverageCasesPerDayEverythingAvailable() {
        Label label = thirdPane.generateAverageCasesLabel_JUnitTest();
        assertNotNull(label);
        assertEquals("Average Cases per Day: " + 4607,label.getText());
    }
    
    /**
     * This function tests if the average retail and recreation GMR error message given another start date and and end Date is correctly generated and rendered onto the Stats Panel
     * It uses the JUnit Test representative function to generate the average retail and recreation GMR and then gets the label's text for comparison
     * This function tests if the average retail and recreation GMR generated are correct when data is NOT available for every single day between the start and end date
     * This should display an error message saying that the data base does not fully cover selected period
     */
    @Test
    public void testAverageCasesPerDayNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2023, 1, 1);
        thirdPane.endDate = LocalDate.of(2023, 2, 9);
        Label label = thirdPane.generateAverageCasesLabel_JUnitTest();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }
    
    @Test
    public void testGenerateGMR2LabelEverythingAvailable() {
        Label label = thirdPane.generateGMR2Label_JUnitTest();
        assertNotNull(label);
        assertEquals("Average Transit Stations \nPercentage Change: " + -6 + "%", label.getText());
    }
    
    @Test 
    public void testGenerateGMR2LabelNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2023, 1, 1);
        thirdPane.endDate = LocalDate.of(2023, 12, 31);
        Label label = thirdPane.generateGMR2Label_JUnitTest();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }
    
    @Test
    public void testRightButtonWorks() {
        
        
        assertEquals(1, thirdPane.currentStatNum);
        
        thirdPane.goRightStats_JUnitTest(null); //JUNit Representative function is passed null as ActionEvent to process going on internally
        
        assertEquals(2, thirdPane.currentStatNum);
        
        thirdPane.goRightStats_JUnitTest(null);
        
        assertEquals(3, thirdPane.currentStatNum);
        
    }
    
    @Test
    public void testLeftButtonWrapsAroundWorks() {
        assertEquals(1, thirdPane.currentStatNum);
        
        thirdPane.goLeftStats_JUnitTest(null);
        
        assertEquals(4, thirdPane.currentStatNum);
        
    }
    
    @Test
    public void testRightButtonWrapsAroundWorks() {
        thirdPane.goRightStats_JUnitTest(null);
        thirdPane.goRightStats_JUnitTest(null);
        thirdPane.goRightStats_JUnitTest(null);
        
        assertEquals(4, thirdPane.currentStatNum);
        
        thirdPane.goRightStats_JUnitTest(null);
        
        assertEquals(1, thirdPane.currentStatNum);
        
    }
    
    @Test
    public void testLeftButtonWorks() {
        thirdPane.goLeftStats_JUnitTest(null);
        
        thirdPane.goLeftStats_JUnitTest(null);
        
        assertEquals(3, thirdPane.currentStatNum);
        
        thirdPane.goLeftStats_JUnitTest(null);
        
        assertEquals(2, thirdPane.currentStatNum);
    }
    
    
    @AfterEach
    public void tearDown()
    {
        thirdPane = null;
    }
    
    
    // Similarly, write tests for generateAverageCasesLabel, generateGMR1Label, generateGMR2Label, goLeftStats, and goRightStats methods
}