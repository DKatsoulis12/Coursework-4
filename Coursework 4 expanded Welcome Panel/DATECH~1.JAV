import java.util.ArrayList;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Write a description of class dateChecker here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DateChecker
{
    private static DateChecker uniqueInstance;
    private ArrayList<String> UniqueDates;
    private String alertTxt;
    
    public static DateChecker getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DateChecker();
        }
        return uniqueInstance;
    }
 
    /**
     * Constructor for objects of class dateChecker
     */
    public DateChecker()
    {
        
        UniqueDates = CovidDataLoader.getInstance().loadUniqueDates();
        //System.out.println("4");
    }

    public boolean checkButtonDisability(LocalDate startDate, LocalDate endDate)
    {
        
        String startDateString = startDate.toString();
        String endDateString = endDate.toString();
        boolean flag1 =  startDate.isAfter(endDate);
        boolean flag2 = !(UniqueDates.contains(startDateString) && UniqueDates.contains(endDateString));
        if (flag1 || flag2) {
            setAlertText(flag1, flag2);
            alertSetUp();
            return true;
        } else {
            return false;
        }
    }
    
    public boolean checkButtonDisability2()
    {
        LocalDate startDate = AppGUI.getInstance().getStartDate();
        LocalDate endDate = AppGUI.getInstance().getEndDate();
        String startDateString = startDate.toString();
        String endDateString = endDate.toString();
        
        
        boolean flag1 =  startDate.isAfter(endDate);
        
        boolean flag2 = !(UniqueDates.contains(startDateString) && UniqueDates.contains(endDateString));
        if (flag1 || flag2) {
            setAlertText(flag1, flag2);
            alertSetUp();
            return true;
        } else {
            return false;
        }
    }
    
    private void setAlertText(boolean startLarger, boolean invalidRange) {
        if (startLarger) {
            alertTxt = "Start Date is bigger than the End Date";
        } else if (invalidRange) {
            alertTxt = "Data not available for all these days. Not a valid range";
        } else {
            alertTxt = "";
        }
    }
    
    private void alertSetUp() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Invalid Date Selection");
        alert.setHeaderText(null);
        alert.setContentText(alertTxt);
            
        alert.showAndWait();
    }
    
}
