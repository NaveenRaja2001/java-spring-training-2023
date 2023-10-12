import java.beans.Customizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    /**
     *Main method
     * @param args
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ArrayList<Model> modelArrayList = Main.csvReader();
            ArrayList<Integer> uniqueYear = Main.uniqueYear(modelArrayList);
            ArrayList<String> uniqueRegion = Main.uniqueRegion(modelArrayList);
            while (true) {
                Scanner sc = new Scanner(System.in);
                int option = sc.nextInt();
                switch (option) {
                    case 1: {
                        System.out.println("CASE 1");
                        System.out.println("Unique  Region --->ListSize=" + uniqueRegion.size());
                        System.out.println(uniqueRegion);
                        break;
                    }
                    case 2: {
                        System.out.println("CASE 2");
                        System.out.println("Unique  Year --->ListSize=" + uniqueYear.size());
                        System.out.println(uniqueYear);
                        break;
                    }
                    case 3: {
                        System.out.println("CASE 3");
                        for (int year : uniqueYear) {
                            BirthAndDeathCount birthAndDeathCount = birthAndDeathCount(year, modelArrayList);
                            System.out.print("  " + year + "----> birthCount=" + birthAndDeathCount.getBirthCount() + " deathCount=" + birthAndDeathCount.getDeathCount());
                        }
                        System.out.println();
                        break;
                    }
                    case 4: {
                        System.out.println("CASE 4");
                        System.out.print("Enter the year--->");
                        int particularYear = scanner.nextInt();
                        BirthAndDeathCount birthAndDeathCount = birthAndDeathCount(particularYear, modelArrayList);
                        System.out.println("  " + particularYear + "----> birthCount=" + birthAndDeathCount.getBirthCount() + " deathCount=" + birthAndDeathCount.getDeathCount());
                        System.out.println();
                        break;
                    }
                    case 5: {
                        System.out.println("CASE 5");
                        System.out.print("Enter the regions--->");
                        sc.nextLine();
                        String regions = sc.nextLine();
                        BirthAndDeathCountByRegion birthAndDeathCountByRegion = overAllBirthAndDeathCountByRegion(regions, modelArrayList);
                        System.out.println("BirthCount =" + birthAndDeathCountByRegion.getBirthCount());
                        System.out.println("DeathCount =" + birthAndDeathCountByRegion.getDeathCount());
                        break;
                    }
                    case 6: {
                        System.out.println("CASE 6");
                        int maximumBirthRateYearCount = 0;
                        int maximumDeathRateYearCount = 0;
                        int maximumBirthRateYear = 0;
                        int maximumDeathRateYear = 0;
                        for (int year : uniqueYear) {
                            BirthAndDeathCount birthAndDeathCount = birthAndDeathCount(year, modelArrayList);
                            if (maximumBirthRateYearCount < birthAndDeathCount.getBirthCount()) {
                                maximumBirthRateYear = year;
                                maximumBirthRateYearCount = birthAndDeathCount.getBirthCount();
                            }
                            if (maximumDeathRateYearCount < birthAndDeathCount.getDeathCount()) {
                                maximumDeathRateYear = year;
                                maximumDeathRateYearCount = birthAndDeathCount.getDeathCount();
                            }
                        }
                        System.out.println("maximumBirthRateYear =" + maximumBirthRateYear + "--->" + maximumBirthRateYearCount);
                        System.out.println("maximumDeathRateYear =" + maximumDeathRateYear + "--->" + maximumDeathRateYearCount);
                        break;
                    }
                    case 7: {
                        System.out.println("CASE 7");
                        for (String temp : uniqueRegion) {
                            MaximumBirthDeathCountByYear maximumBirthDeathCountByYear = maximumBirthDeathCountByYear(temp, modelArrayList);
                            System.out.println(temp + "  BirthCount=" + maximumBirthDeathCountByYear.getMaximumBirthCount() + "  DeathCount=" + maximumBirthDeathCountByYear.getMaximumDeathCount() + "--->MaximumBirthCountYear=" + maximumBirthDeathCountByYear.getMaximumBirthCountYear() + " MaximumDeathCountYear=" + maximumBirthDeathCountByYear.getMaximumDeathCountYear());
                        }
                        break;
                    }
                }
                if (option == 8) {
                    break;
                }
            }
        }
    }

    /**
     *This method read the dataset from CSV file and store it in a arraylist
     * @return
     */
    static ArrayList<Model> csvReader(){
        String line;
        String splitBy = ",";
        ArrayList<Model> modelArrayList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/naveenraja/Bringo2/JavaProject/src/BirthandDeathDataset.csv"));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] employee = line.split(splitBy);
                modelArrayList.add(new Model(Integer.parseInt(employee[0]), employee[1], employee[2], Integer.parseInt(employee[3])));
//              System.out.println(Integer.parseInt(employee[0]) + "----------->" + employee[1] + "------->" + employee[2] + "---------->" + employee[3]);
            }
//            System.out.println(modelArrayList);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return modelArrayList;
    }

    /**
     * This method return a arraylist with contains unique region
     * @param modelArrayList
     * @return
     */
    static ArrayList<String> uniqueRegion(ArrayList<Model> modelArrayList){
        HashSet<String> regions=new HashSet<>();
                for(Model model:modelArrayList) {
                    regions.add(model.getRegion());
            }
        return new ArrayList<>(regions);
    }

    /**
     * This method return a arraylist with contains unique year
     * @param modelArrayList
     * @return
     */
    static ArrayList<Integer> uniqueYear(ArrayList<Model> modelArrayList){
        HashSet<Integer> years=new HashSet<>();
        HashMap<Customizer, Integer> hashMapEvent=new HashMap<>();
            for(Model model:modelArrayList) {
                years.add(model.getPeriod());
            }
                return new ArrayList<>(years);

    }

    /**
     * This method return a class with contains  Birth abd Death Count by a year
     * @param year
     * @param modelArrayList
     * @return
     */
    static BirthAndDeathCount birthAndDeathCount(int year,ArrayList<Model> modelArrayList){
        int birthCount=0;
        int deathCount=0;
        for(Model model:modelArrayList) {
            if(model.getPeriod()==year && model.getBirthDeath().equals(CommonConstants.Births)){
                birthCount+=model.getCount();
            }
            if(model.getPeriod()==year && model.getBirthDeath().equals(CommonConstants.Deaths)){
                   deathCount+= model.getCount();
            }
        }
        BirthAndDeathCount birthAndDeathCount=new BirthAndDeathCount();
        birthAndDeathCount.setBirthCount(birthCount);
        birthAndDeathCount.setDeathCount(deathCount);
       return birthAndDeathCount;
    }

    /**
     * This method return a class with contains over all Birth abd Death Count by a region
     * @param region
     * @param modelArrayList
     * @return
     */
    static BirthAndDeathCountByRegion overAllBirthAndDeathCountByRegion(String region,ArrayList<Model> modelArrayList){
        int birthCount=0;
        int deathCount=0;
        for(Model model:modelArrayList) {
        if(model.getRegion().equals(region) && model.getBirthDeath().equals(CommonConstants.Births)){
            birthCount+=model.getCount();
        }
        if(model.getRegion().equals(region) && model.getBirthDeath().equals(CommonConstants.Deaths)){
            deathCount+= model.getCount();
        }
        }
        BirthAndDeathCountByRegion birthAndDeathCountByRegion=new BirthAndDeathCountByRegion();
        birthAndDeathCountByRegion.setBirthCount(birthCount);
        birthAndDeathCountByRegion.setDeathCount(deathCount);
        return birthAndDeathCountByRegion;
    }

    /**
     * This method return a class with contains maximum Birth abd Death Count by Year
     * @param region
     * @param modelArrayList
     * @return
     */
    static MaximumBirthDeathCountByYear maximumBirthDeathCountByYear(String region,ArrayList<Model> modelArrayList){
        int maximumBirthCount=0;
        int maximumDeathCount=0;
        int maximumBirthCountYear=0;
        int maximumDeathCountYear=0;

        for(Model model:modelArrayList){
            if(model.getRegion().equals(region)){
                if(model.getBirthDeath().equals(CommonConstants.Births) && maximumBirthCount<model.getCount()){
                    maximumBirthCount=model.getCount();
                    maximumBirthCountYear=model.getPeriod();
                }
                if(model.getBirthDeath().equals(CommonConstants.Deaths) && maximumDeathCount<model.getCount()){
                    maximumDeathCount=model.getCount();
                    maximumDeathCountYear=model.getPeriod();
                }
            }
        }
        MaximumBirthDeathCountByYear maximumBirthDeathCountByYear=new MaximumBirthDeathCountByYear();
        maximumBirthDeathCountByYear.setMaximumBirthCount(maximumBirthCount);
        maximumBirthDeathCountByYear.setMaximumDeathCount(maximumDeathCount);
        maximumBirthDeathCountByYear.setMaximumBirthCountYear(maximumBirthCountYear);
        maximumBirthDeathCountByYear.setMaximumDeathCountYear(maximumDeathCountYear);
        return maximumBirthDeathCountByYear;
    }
}