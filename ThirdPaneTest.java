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
    
    @Test
    public void testGenerateTotalDeathsLabelEverythingAvailable() {
        Label label = thirdPane.generateTotalDeathsLabel();
        assertNotNull(label);
        assertEquals("Total Deaths: " + 5881, label.getText());
    }

    @Test 
    public void testGenerateTotalDeathsLabelNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2020, 2, 3);
        thirdPane.endDate = LocalDate.of(2023, 3, 7);
        Label label = thirdPane.generateTotalDeathsLabel();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }
    
    @Test
    public void testGenerateGMR1LabelEverythingAvailable() {
        Label label = thirdPane.generateGMR1Label();
        assertNotNull(label);
        assertEquals("Average Retail and Recreation \nPercentage Change: " + -5 + "%", label.getText());
    }
    
    @Test 
    public void testGenerateGMR1LabelNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2023, 1, 1);
        thirdPane.endDate = LocalDate.of(2023, 12, 31);
        Label label = thirdPane.generateGMR1Label();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }
    
    @Test
    public void testAverageCasesPerDayEverythingAvailable() {
        Label label = thirdPane.generateAverageCasesLabel();
        assertNotNull(label);
        assertEquals("Average Cases per Day: " + 4607,label.getText());
    }
    
    @Test
    public void testAverageCasesPerDayNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2023, 1, 1);
        thirdPane.endDate = LocalDate.of(2023, 2, 9);
        Label label = thirdPane.generateAverageCasesLabel();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }
    
    @Test
    public void testGenerateGMR2LabelEverythingAvailable() {
        Label label = thirdPane.generateGMR2Label();
        assertNotNull(label);
        assertEquals("Average Transit Stations \nPercentage Change: " + -6 + "%", label.getText());
    }
    
    @Test 
    public void testGenerateGMR2LabelNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2023, 1, 1);
        thirdPane.endDate = LocalDate.of(2023, 12, 31);
        Label label = thirdPane.generateGMR2Label();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }

    /**
    * Testing the right button on the stats pane correctly functions by using the currentStatNum value and calling 
    * the goRight ActionEvent handler method linked to the right button so as to increment the currentStatNum value.
    */
    
    @Test
    public void testRightButtonWorks() {
        assertEquals(1, thirdPane.currentStatNum); 

        // JUnit representative function passed null as ActionEvent
        thirdPane.goRightStats(null); 

        // checking the next 2 stats are displayed
        
        assertEquals(2, thirdPane.currentStatNum);
        
        thirdPane.goRightStats(null);
        
        assertEquals(3, thirdPane.currentStatNum);
        
    }

    /**
    * Testing the left button on the stats pane correctly "wraps around" such that when the user is on the first 
    * stat and clicks the left button, the user is presented with the last stat.
    * Using the currentStatNum value and calling the goLeft ActionEvent handler method so as to decrement the 
    * currentStatNum value.
    */
    
    @Test
    public void testLeftButtonWrapsAroundWorks() {
        assertEquals(1, thirdPane.currentStatNum);

        thirdPane.goLeftStats(null);

        // checking the 4th stat is displayed
        assertEquals(4, thirdPane.currentStatNum);
        
    }

    /**
    * Testing the right button on the stats pane correctly "wraps around" such that when the user is on the last
    * stat and clicks the right button, the user is presented with the first stat.
    */
    @Test
    public void testRightButtonWrapsAroundWorks() {
        // getting the currentStatNum to equal 4
        thirdPane.goRightStats(null);
        thirdPane.goRightStats(null);
        thirdPane.goRightStats(null);

        // checking the 4th stat is displayed
        assertEquals(4, thirdPane.currentStatNum);
        
        thirdPane.goRightStats(null);

        // checking the 1st stat is displayed
        assertEquals(1, thirdPane.currentStatNum);
        
    }

    /**
    * Testing the left button on the stats pane correctly functions.
    */
    @Test
    public void testLeftButtonWorks() {
        // setting the currentStatNum to 4
        thirdPane.goLeftStats(null);
        
        thirdPane.goLeftStats(null);
        
        assertEquals(3, thirdPane.currentStatNum);
        
        thirdPane.goLeftStats(null);
        
        assertEquals(2, thirdPane.currentStatNum);
    }
    
    
    @AfterEach
    public void tearDown()
    {
        thirdPane = null;
    }
    
}
