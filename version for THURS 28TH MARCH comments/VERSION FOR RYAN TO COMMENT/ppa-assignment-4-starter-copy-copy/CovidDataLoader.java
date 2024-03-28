import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;


/**
 * loads data from a respective csv file and return them to other classes
 */
public class CovidDataLoader {
    
    private static CovidDataLoader uniqueInstance;
    
    /**
     * implements the Singleton design pattern to ensure that only one instance of {@code CovidDataLoader} is used,preventing creation of 
     * multiple instances of CovidDataLoader when multiple classes need data from csv files.
     * @return The single instance of {@link CovidDataLoader}
     */
    public static CovidDataLoader getInstance() {
         if (uniqueInstance == null) {
            uniqueInstance = new CovidDataLoader();
        }
         return uniqueInstance;
    }
 
    /** 
     * Return an ArrayList containing the rows in the Covid London data set csv file.
     */
    public ArrayList<CovidData> load() {
        System.out.println("Begin loading Covid London dataset...");
        ArrayList<CovidData> records = new ArrayList<CovidData>();
        try{
            URL url = getClass().getResource("covid_london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                
                String date    = line[0];
                String borough    = line[1];    
                int retailRecreationGMR    = convertInt(line[2]);    
                int groceryPharmacyGMR    = convertInt(line[3]);    
                int parksGMR    = convertInt(line[4]);    
                int transitGMR    = convertInt(line[5]);    
                int workplacesGMR    = convertInt(line[6]);    
                int residentialGMR    = convertInt(line[7]);    
                int newCases    = convertInt(line[8]);    
                int totalCases    = convertInt(line[9]);    
                int newDeaths    = convertInt(line[10]);    
                int totalDeaths    = convertInt(line[11]);                

                CovidData record = new CovidData(date,borough,retailRecreationGMR,
                    groceryPharmacyGMR,parksGMR,transitGMR,workplacesGMR,
                    residentialGMR,newCases,totalCases,newDeaths,totalDeaths);
                records.add(record);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        }
        System.out.println("Number of Loaded Records: " + records.size());
        return records;
    }
    
    /** 
     * @Return all dates in covid_london.csv without repetition
     */
    public ArrayList<String> loadUniqueDates() {
        System.out.println("Begin loading Covid London dataset...");
        ArrayList<String> foundDates = new ArrayList<String>();
        try{
            URL url = getClass().getResource("covid_london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                
               if (!foundDates.contains(line[0])){
                   foundDates.add(line[0]);
               }
               
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        }
        return foundDates;
    }
    
    /** 
     * calculate and returns the total deaths between two dates, inclusive.
     */
    public Integer loadTotalDeath(LocalDate startDate, LocalDate endDate) {
        System.out.println("Begin loading Covid London dataset...3");
        Integer totalDeaths = 0;
        try{
            URL url = getClass().getResource("covid_london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            if (startDate.isEqual(endDate)){
                while ((line = reader.readNext()) != null) {
                   
                    if(LocalDate.parse(line[0]).isEqual(startDate)){
                        if(line[10].equals("") ){
                            //triggered when database includes a date that matches the requirement but does not have the data required
                            return null;
                        }
                        totalDeaths += Integer.parseInt(line[10]); //if only one date is checked only add new deaths from that date
                    }
    
                }
            }else{
                while ((line = reader.readNext()) != null) {
                    LocalDate checkedDate = LocalDate.parse(line[0]);
                    
                    if(checkedDate.isEqual(endDate)){
                        if(line[10].equals("")){
                            return null;
                        }
                        totalDeaths += Integer.parseInt(line[11]); //sum of all deaths on end date
                    }else if(checkedDate.isEqual(startDate)){
                        if(line[10].equals("")){
                            return null;
                        }
                        totalDeaths -= Integer.parseInt(line[11]); //all new deaths from start to end date other than on start date
                        totalDeaths += Integer.parseInt(line[10]); //add new deaths added on start date
                    }
               
                }
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        }
        return totalDeaths;
    }
    
    /** 
     * calculate and returns the averagecases between two dates, inclusive.
     */
    public Long loadAverageCases(LocalDate startDate, LocalDate endDate) {
         System.out.println("Begin loading Covid London dataset...");
        Long totalcases = new Long(0);
        try{
            URL url = getClass().getResource("covid_london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            
            
            if (startDate.isEqual(endDate)){
                while ((line = reader.readNext()) != null) {
                    
                    if(LocalDate.parse(line[0]).isEqual(startDate)){
                        if(line[8].equals("") ){
                            return null;
                        }
                        totalcases += Integer.parseInt(line[8]); //if only one date is checked only add new cases from that date
                    }
    
                }
            }
            
            else{
                while ((line = reader.readNext()) != null) {
                    LocalDate checkedDate = LocalDate.parse(line[0]);
                    if(checkedDate.isEqual(endDate)){
                        if(line[8].equals("") ){
                            return null;
                        }
                        totalcases += Integer.parseInt(line[9]); //sum of all cases on end date
                    }else if(checkedDate.isEqual(startDate)){
                        if(line[8].equals("") ){
                            return null;
                        }
                        totalcases -= Integer.parseInt(line[9]); //all new cases from start to end date other than on start date
                        totalcases += Integer.parseInt(line[8]); //add new cases added on start date
                    }
               
                }
            }
            
        } catch(IOException | URISyntaxException e){
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        }
        
        //number of days considered is always 1 more day than number of days between the two given dates
        Long averageCases = totalcases/(1+ChronoUnit.DAYS.between(startDate, endDate)); 
        return averageCases;
        
        
    }
    
    /** 
     * calculate and returns the respective averageGMR based on what integer is provided in the parameter, between two dates, inclusive.
     * currently only 2 and 5 is called and have their missing cases addressed, since these GMR are provided the most in the databse.
     * calling this method on columns that are not a GMR is possible, but could result in error and is not its intended use.
     */
    public Long loadAverageGMR(LocalDate startDate, LocalDate endDate, int GMRcolumn) {
         System.out.println("Begin loading Covid London dataset...");
        Long totalMobility = new Long(0);
        try{
            URL url = getClass().getResource("covid_london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            
            while ((line = reader.readNext()) != null) {
                
                LocalDate checkedDate = LocalDate.parse(line[0]);
                
                
                if ((checkedDate.isEqual(startDate))||(checkedDate.isEqual(endDate))||(checkedDate.isAfter(startDate)&&checkedDate.isBefore(startDate))){
                    if(line[2].equals("")){
                        return null;
                    }
                    totalMobility += Integer.parseInt(line[GMRcolumn]);
                }
           
            }
            
            
        } catch(IOException | URISyntaxException e){
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        }
        
        Long averageCases = totalMobility/(1+ChronoUnit.DAYS.between(startDate, endDate)); 
        return averageCases;
        
        
    }
    
    
    // Data Loaders for Challenge Task // 
    
    /**
     * Access the covid_cases file and read all the appropriate dates and case value pairs
     * within the range of the startDate and endDate parameter values.
     * 
     * @param startDate the first date to start reading the file from
     * @param endDate the last date to stop reading the file from
     * @return a LinkedHashMap of date and its associated cases value
     */
    
    public LinkedHashMap<LocalDate, Integer> loadDailyCases (LocalDate startDate, LocalDate endDate) {
        LinkedHashMap<LocalDate, Integer> casesPerDate = new LinkedHashMap<>();
        
        
        try {
            CSVReader reader = new CSVReader(new FileReader("covid_cases.csv"));
            String [] line;
           
            reader.readNext();
           
            if (startDate.isEqual(endDate)) {
                while ((line = reader.readNext()) != null) {
                    LocalDate currentDate = LocalDate.parse(line[3]);
               
                    if (currentDate.isEqual(startDate)) {
                        casesPerDate.put(currentDate, Integer.parseInt(line[4]));
                    }
                }
            }
            else {
                while ((line = reader.readNext()) != null) {
                    LocalDate currentDate = LocalDate.parse(line[3]);
                    
                    int compareStartDate = currentDate.compareTo(startDate);
                    int compareEndDate = currentDate.compareTo(endDate);
                    
                    if (compareStartDate == 0) { // current date is equal to start date
                        casesPerDate.put(currentDate, Integer.parseInt(line[4]));
                    }
                    else if (compareStartDate > 0 && compareEndDate < 0) { // current date is after start date and before end date
                        casesPerDate.put(currentDate, Integer.parseInt(line[4]));
                    }
                    else if (compareEndDate == 0) { // current date is equal to end date
                        casesPerDate.put(currentDate, Integer.parseInt(line[4]));
                    }
                }
            }
        }
        
        catch(IOException e){
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        }
        
        return casesPerDate;
    }
    
    /**
     * Access the covid_deaths file and read all the appropriate dates and death value pairs
     * within the range of the startDate and endDate parameter values.
     * 
     * @param startDate the first date to start reading the file from
     * @param endDate the last date to stop reading the file from
     * @return a LinkedHashMap of each date its associated deaths value
     */
    
    public LinkedHashMap<LocalDate, Integer> loadDailyDeaths (LocalDate startDate, LocalDate endDate) {
        LinkedHashMap <LocalDate, Integer> deathsPerDate = new LinkedHashMap<>();
        
        try {
            CSVReader reader = new CSVReader(new FileReader("covid_deaths.csv"));
            String [] line;
           
            reader.readNext();
           
            if (startDate.isEqual(endDate)) {
                while ((line = reader.readNext()) != null) {
               
                    LocalDate currentDate = LocalDate.parse(line[3]);
               
                    if (currentDate.isEqual(startDate)) {
                        deathsPerDate.put(currentDate, Integer.parseInt(line[4]));
                    }
                }
            }
            else {
                while ((line = reader.readNext()) != null) {
               
                    LocalDate currentDate = LocalDate.parse(line[3]);
               
                    int compareStartDate = currentDate.compareTo(startDate);
                    int compareEndDate = currentDate.compareTo(endDate);
                    
                    if (compareStartDate == 0) { // current date is equal to start date
                        deathsPerDate.put(currentDate, Integer.parseInt(line[4]));
                    }
                    else if (compareStartDate > 0 && compareEndDate < 0) { // current date is after start date and before end date
                        deathsPerDate.put(currentDate, Integer.parseInt(line[4]));
                    }
                    else if (compareEndDate == 0) { // current date is equal to end date
                        deathsPerDate.put(currentDate, Integer.parseInt(line[4]));
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        }
        
        return deathsPerDate;
    }
    
    
    /**
     * Access the reported_tests file and read all the appropriate dates and test value pairs
     * within the range of the startDate and endDate parameter values.
     * 
     * @param startDate the first date to start reading the file from
     * @param endDate the last date to stop reading the file from
     * @return a LinkedHashMap of date and its associated tests value
     */
    
    public LinkedHashMap<LocalDate, Integer> loadReportedTests (LocalDate startDate, LocalDate endDate) {
        LinkedHashMap <LocalDate, Integer> testsReported = new LinkedHashMap<>();
        
        try {
            CSVReader reader = new CSVReader(new FileReader("reported_tests.csv"));
            String [] line;
            
            reader.readNext();
            
            if (startDate.isEqual(endDate)) {
                while ((line = reader.readNext()) != null) {
               
                    LocalDate currentDate = LocalDate.parse(line[3]);
               
                    if (currentDate.isEqual(startDate)) {
                        testsReported.put(currentDate, Integer.parseInt(line[4]));
                    }
                }
            }
            else {
                while ((line = reader.readNext()) != null) {
               
                    LocalDate currentDate = LocalDate.parse(line[3]);
                    
                    int compareStartDate = currentDate.compareTo(startDate);
                    int compareEndDate = currentDate.compareTo(endDate);
                    
                    if (compareStartDate == 0) { // current date is equal to start date
                        testsReported.put(currentDate, Integer.parseInt(line[4]));
                    }
                    else if (compareStartDate > 0 && compareEndDate < 0) { // current date is after start date and before end date
                        testsReported.put(currentDate, Integer.parseInt(line[4]));
                    }
                    else if (compareEndDate == 0) { // current date is equal to end date
                        testsReported.put(currentDate, Integer.parseInt(line[4]));
                    }
                }
            }
        }
        
        catch (IOException e) {
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        }
        
        return testsReported;
    }
    
    
    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return 0.0;
    }

    /**
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return 0;
    }
}
