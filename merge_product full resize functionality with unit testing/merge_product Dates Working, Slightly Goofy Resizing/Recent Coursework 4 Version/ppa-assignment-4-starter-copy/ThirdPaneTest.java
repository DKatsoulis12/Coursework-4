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
        Label label = thirdPane.generateTotalDeathsLabel_JUnitTest();
        assertNotNull(label);
        assertEquals("Total Deaths: " + 5881, label.getText());
    }

    @Test 
    public void testGenerateTotalDeathsLabelNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2020, 2, 3);
        thirdPane.endDate = LocalDate.of(2023, 3, 7);
        Label label = thirdPane.generateTotalDeathsLabel_JUnitTest();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }
    
    @Test
    public void testGenerateGMR1LabelEverythingAvailable() {
        Label label = thirdPane.generateGMR1Label_JUnitTest();
        assertNotNull(label);
        assertEquals("Average Retail and Recreation \nPercentage Change: " + -5 + "%", label.getText());
    }
    
    @Test 
    public void testGenerateGMR1LabelNotEverythingAvailable() {
        thirdPane.startDate = LocalDate.of(2023, 1, 1);
        thirdPane.endDate = LocalDate.of(2023, 12, 31);
        Label label = thirdPane.generateGMR1Label_JUnitTest();
        assertNotNull(label);
        assertTrue(label.getText().contains("data base does not fully cover selected period"));
    }
    
    @Test
    public void testAverageCasesPerDayEverythingAvailable() {
        Label label = thirdPane.generateAverageCasesLabel_JUnitTest();
        assertNotNull(label);
        assertEquals("Average Cases per Day: " + 4607,label.getText());
    }
    
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
    
    @AfterEach
    public void tearDown()
    {
        thirdPane = null;
    }
    
    
    // Similarly, write tests for generateAverageCasesLabel, generateGMR1Label, generateGMR2Label, goLeftStats, and goRightStats methods
}
