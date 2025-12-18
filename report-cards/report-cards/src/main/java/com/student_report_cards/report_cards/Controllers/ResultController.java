package com.student_report_cards.report_cards.Controllers;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RestController
@CrossOrigin(origins = "*")
public class ResultController {


    @PostMapping("/hit")
    public boolean handleRequest(@RequestBody Map<String, Object> data) {
        boolean result=false;
        String grade = data.get("grade").toString();

        System.out.println(grade);
        if(grade.equals("One")||grade.equals("Two")||grade.equals("Three")){


            boolean finalResult =resultLogicForOnetoThree((data));


            System.out.println(finalResult);



            System.out.println(grade);

        }
        else if (grade.equals("ECE")||grade.equals("Nursery")){


            boolean eceNursery = resultLogicEceNursery(data);

            System.out.println(eceNursery+"ECE nursery working perfectly");

        }
        else if (grade.equals("Four")||grade.equals("Five")){


            boolean fourFive = resultLogicFourFive(data);
            System.out.println("fourFive working ok..."+fourFive);

        }


        else if (grade.equals("Six")){


            boolean six = resultLogicSix(data);
            System.out.println("six working ok..."+six);

        }


        return result;
    }


    public boolean resultLogicForOnetoThree(@RequestBody Map<String, Object> data) {

boolean status=false;
        // Taking input for each subject using the same scanner object


        String name = (String) data.get("name");
        String grade = (String) data.get("grade");

        // For numeric marks, cast properly
        int total = ((Number) data.get("total")).intValue();
        int eng = ((Number) data.get("eng")).intValue();
        int urdu = ((Number) data.get("urdu")).intValue();
        int maths = ((Number) data.get("maths")).intValue();
//        int science = ((Number) data.get("science")).intValue();
        int gk = ((Number) data.get("gk")).intValue();
        int isl = ((Number) data.get("isl")).intValue();

        double obtained = 0.0;
        double percentage = 0.0;
        String Remarks = "";
        String Status = "";

        // Parameters
        try {
            // Parameters map
            Map<String, Object> params = new HashMap<>();
            params.put("StudentName", name);       // String
            params.put("ClassName", grade);   // String
            params.put("English", Double.valueOf(eng));      // Double
            params.put("Urdu", Double.valueOf(urdu));       // Double
            params.put("Maths", Double.valueOf(maths));      // Double
//            params.put("Science", Double.valueOf(science));    // Double
            params.put("GK", Double.valueOf(gk));         // Double
            params.put("Islamiat", Double.valueOf(isl));   // Double
            params.put("Total", Double.valueOf(total));

            obtained = eng + urdu + maths + gk + isl;
            percentage = obtained / total * 100;
            int percentag = (int) percentage;
            params.put("Obtained", Double.valueOf(obtained));
            params.put("Percentage", percentag);
            if (percentag < 40) {
                Remarks = "Below Average - You need to work hard and focus more next time.";
            } else if (percentag >= 40 && percentag < 50) {
                Remarks = "Average - Keep practicing, you can improve further.";
            } else if (percentag >= 50 && percentag < 60) {
                Remarks = "Satisfactory - A fair attempt, but more consistency is needed.";
            } else if (percentag >= 60 && percentag < 70) {
                Remarks = "Good - Well done, but there’s room to do even better.";
            } else if (percentag >= 70 && percentag < 80) {
                Remarks = "Very Good - Great effort, keep up the momentum.";
            } else if (percentag >= 80 && percentag < 85) {
                Remarks = "Excellent - Impressive performance, maintain your dedication.";
            } else if (percentag >= 85 && percentag < 90) {
                Remarks = "Outstanding - You are on the right path, aim for perfection.";
            } else if (percentag >= 90) {
                Remarks = "Exceptional - Brilliant work, keep inspiring others with your results.";
            }


            double subjects[] = {eng, urdu, maths, gk, isl};
            int failCount = 0;
            for (int i = 0; i < subjects.length; i++) {

                if (subjects[i] < 20) {

                    failCount++;


                }


            }
            if (failCount >= 2) {


                Status = "FAIL";
                Remarks = "You have failed. Work harder next time.";
            } else if (failCount == 1) {


                Status = "PROMOTED";
                Remarks = "You are promoted, but you need to work harder for next time.. ";
            } else {

                Status = "PASS";
            }


            params.put("Status", Status);
            params.put("Remarks", Remarks);


            // Load jrxml file from resources
//            String jrxmlFile = "C:\\Users\\dell\\IdeaProjects\\Jasperproject\\src\\main\\resources\\ReportCardOneToThree.jrxml";
//            System.out.println(jrxmlFile);
            InputStream reportStream = getClass().getResourceAsStream("/ReportCardOneToThree.jrxml");

if (reportStream==null){

    throw new RuntimeException("file not found..");
}





            // Compile the .jrxml to .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fill the report (use empty datasource if no database)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());


            // Export to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\Results\\Results One to Three\\ReportCardGen" + name + ".pdf");


            System.out.println("Report generated successfully!");
status=true;

        } catch (Exception e) {
            e.printStackTrace();
        }

return status;
    }



    public boolean resultLogicEceNursery(@RequestBody Map<String, Object> data) {

        boolean status=false;
        // Taking input for each subject using the same scanner object


        String name = (String) data.get("name");
        String grade = (String) data.get("grade");

        // For numeric marks, cast properly
        int total = ((Number) data.get("total")).intValue();
        int eng = ((Number) data.get("eng")).intValue();
        int urdu = ((Number) data.get("urdu")).intValue();
        int maths = ((Number) data.get("maths")).intValue();


        double obtained = 0.0;
        double percentage = 0.0;
        String Remarks = "";
        String Status = "";

        // Parameters
        try {
            // Parameters map
            Map<String, Object> params = new HashMap<>();
            params.put("StudentName", name);       // String
            params.put("ClassName", grade);   // String
            params.put("English", Double.valueOf(eng));      // Double
            params.put("Urdu", Double.valueOf(urdu));       // Double
            params.put("Maths", Double.valueOf(maths));      // Double
  // Double
            params.put("Total", Double.valueOf(total));

            obtained = eng + urdu + maths ;
            percentage = obtained / total * 100;
            int percentag = (int) percentage;
            params.put("Obtained", Double.valueOf(obtained));
            params.put("Percentage", percentag);
            if (percentag < 40) {
                Remarks = "Below Average - You need to work hard and focus more next time.";
            } else if (percentag >= 40 && percentag < 50) {
                Remarks = "Average - Keep practicing, you can improve further.";
            } else if (percentag >= 50 && percentag < 60) {
                Remarks = "Satisfactory - A fair attempt, but more consistency is needed.";
            } else if (percentag >= 60 && percentag < 70) {
                Remarks = "Good - Well done, but there’s room to do even better.";
            } else if (percentag >= 70 && percentag < 80) {
                Remarks = "Very Good - Great effort, keep up the momentum.";
            } else if (percentag >= 80 && percentag < 85) {
                Remarks = "Excellent - Impressive performance, maintain your dedication.";
            } else if (percentag >= 85 && percentag < 90) {
                Remarks = "Outstanding - You are on the right path, aim for perfection.";
            } else if (percentag >= 90) {
                Remarks = "Exceptional - Brilliant work, keep inspiring others with your results.";
            }


            double subjects[] = {eng, urdu, maths};
            int failCount = 0;
            for (int i = 0; i < subjects.length; i++) {

                if (subjects[i] < 20) {

                    failCount++;


                }


            }
            if (failCount >= 2) {


                Status = "FAIL";
                Remarks = "You have failed. Work harder next time.";
            } else if (failCount == 1) {


                Status = "PROMOTED";
                Remarks = "You are promoted, but you need to work harder for next time.. ";
            } else {

                Status = "PASS";
            }


            params.put("Status", Status);
            params.put("Remarks", Remarks);


            // Load jrxml file from resources

            InputStream reportStream = getClass().getResourceAsStream("/ReportCardECENursery.jrxml");

            if (reportStream==null){

                throw new RuntimeException("file not found..");
            }





            // Compile the .jrxml to .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fill the report (use empty datasource if no database)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());


            // Export to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\Results\\Results ECEn\\ReportCardGen" + name + ".pdf");


            System.out.println("Report generated successfully!");
            status=true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }




    public boolean resultLogicFourFive(@RequestBody Map<String, Object> data){

        boolean status=false;
        // Taking input for each subject using the same scanner object


        String name = (String) data.get("name");
        String grade = (String) data.get("grade");

        // For numeric marks, cast properly
        int total = ((Number) data.get("total")).intValue();
        int eng = ((Number) data.get("eng")).intValue();
        int urdu = ((Number) data.get("urdu")).intValue();
        int maths = ((Number) data.get("maths")).intValue();

        int sst = ((Number) data.get("sst")).intValue();
        int isl = ((Number) data.get("isl")).intValue();

        int sci = ((Number) data.get("sci")).intValue();


        double obtained = 0.0;
        double percentage = 0.0;
        String Remarks = "";
        String Status = "";

        // Parameters
        try {
            // Parameters map
            Map<String, Object> params = new HashMap<>();
            params.put("StudentName", name);       // String
            params.put("ClassName", grade);   // String
            params.put("English", Double.valueOf(eng));      // Double
            params.put("Urdu", Double.valueOf(urdu));       // Double
            params.put("Maths", Double.valueOf(maths));      // Double
            params.put("Science", Double.valueOf(sci));    // Double
            params.put("GK", Double.valueOf(sst));         // Double
            params.put("Islamiat", Double.valueOf(isl));   // Double
            params.put("Total", Double.valueOf(total));

            obtained = eng + urdu + maths + sst + isl+sci;
            percentage = obtained / total * 100;
            int percentag = (int) percentage;
            params.put("Obtained", Double.valueOf(obtained));
            params.put("Percentage", percentag);
            if (percentag < 40) {
                Remarks = "Below Average - You need to work hard and focus more next time.";
            } else if (percentag >= 40 && percentag < 50) {
                Remarks = "Average - Keep practicing, you can improve further.";
            } else if (percentag >= 50 && percentag < 60) {
                Remarks = "Satisfactory - A fair attempt, but more consistency is needed.";
            } else if (percentag >= 60 && percentag < 70) {
                Remarks = "Good - Well done, but there’s room to do even better.";
            } else if (percentag >= 70 && percentag < 80) {
                Remarks = "Very Good - Great effort, keep up the momentum.";
            } else if (percentag >= 80 && percentag < 85) {
                Remarks = "Excellent - Impressive performance, maintain your dedication.";
            } else if (percentag >= 85 && percentag < 90) {
                Remarks = "Outstanding - You are on the right path, aim for perfection.";
            } else if (percentag >= 90) {
                Remarks = "Exceptional - Brilliant work, keep inspiring others with your results.";
            }


            double subjects[] = {eng, urdu, maths, sci, isl,sst};
            int failCount = 0;
            for (int i = 0; i < subjects.length; i++) {

                if (subjects[i] < 20) {

                    failCount++;


                }


            }
            if (failCount >= 2) {


                Status = "FAIL";
                Remarks = "You have failed. Work harder next time.";
            } else if (failCount == 1) {


                Status = "PROMOTED";
                Remarks = "You are promoted, but you need to work harder for next time.. ";
            } else {

                Status = "PASS";
            }


            params.put("Status", Status);
            params.put("Remarks", Remarks);


            // Load jrxml file from resources

            InputStream reportStream = getClass().getResourceAsStream("/ReportCard4-5.jrxml");

            if (reportStream==null){

                throw new RuntimeException("file not found..");
            }





            // Compile the .jrxml to .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fill the report (use empty datasource if no database)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());


            // Export to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\Results\\Results Four to Five\\ReportCardGen" + name + ".pdf");


            System.out.println("Report generated successfully!");
            status=true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;







    }



    public boolean resultLogicSix(@RequestBody Map<String, Object> data){

        boolean status=false;
        // Taking input for each subject using the same scanner object


        String name = (String) data.get("name");
        String grade = (String) data.get("grade");

        // For numeric marks, cast properly
        int total = ((Number) data.get("total")).intValue();
        int eng = ((Number) data.get("eng")).intValue();
        int urdu = ((Number) data.get("urdu")).intValue();
        int maths = ((Number) data.get("maths")).intValue();

        int his = ((Number) data.get("his")).intValue();
        int isl = ((Number) data.get("isl")).intValue();

        int sci = ((Number) data.get("sci")).intValue();
        int comp = ((Number) data.get("comp")).intValue();
        int tq = ((Number) data.get("tq")).intValue();

        double obtained = 0.0;
        double percentage = 0.0;
        String Remarks = "";
        String Status = "";

        // Parameters
        try {
            // Parameters map
            Map<String, Object> params = new HashMap<>();
            params.put("StudentName", name);       // String
            params.put("ClassName", grade);   // String
            params.put("English", Double.valueOf(eng));      // Double
            params.put("Urdu", Double.valueOf(urdu));       // Double
            params.put("Maths", Double.valueOf(maths));      // Double
            params.put("Science", Double.valueOf(sci));    // Double
            params.put("GK", Double.valueOf(his));
            params.put("comp", Double.valueOf(comp));   // Double
            params.put("tq", Double.valueOf(tq));   // Double
            params.put("Islamiat", Double.valueOf(isl));   // Double
            params.put("Total", Double.valueOf(total));

            obtained = eng + urdu + maths + his + isl+sci+comp+tq;
            percentage = obtained / total * 100;
            int percentag = (int) percentage;
            params.put("Obtained", Double.valueOf(obtained));
            params.put("Percentage", percentag);
            if (percentag < 40) {
                Remarks = "Below Average - You need to work hard and focus more next time.";
            } else if (percentag >= 40 && percentag < 50) {
                Remarks = "Average - Keep practicing, you can improve further.";
            } else if (percentag >= 50 && percentag < 60) {
                Remarks = "Satisfactory - A fair attempt, but more consistency is needed.";
            } else if (percentag >= 60 && percentag < 70) {
                Remarks = "Good - Well done, but there’s room to do even better.";
            } else if (percentag >= 70 && percentag < 80) {
                Remarks = "Very Good - Great effort, keep up the momentum.";
            } else if (percentag >= 80 && percentag < 85) {
                Remarks = "Excellent - Impressive performance, maintain your dedication.";
            } else if (percentag >= 85 && percentag < 90) {
                Remarks = "Outstanding - You are on the right path, aim for perfection.";
            } else if (percentag >= 90) {
                Remarks = "Exceptional - Brilliant work, keep inspiring others with your results.";
            }


            double subjects[] = {eng, urdu, maths, sci, isl,tq,comp,his};
            int failCount = 0;
            for (int i = 0; i < subjects.length; i++) {

                if (subjects[i] < 20) {

                    failCount++;


                }


            }
            if (failCount >= 2) {


                Status = "FAIL";
                Remarks = "You have failed. Work harder next time.";
            } else if (failCount == 1) {


                Status = "PROMOTED";
                Remarks = "You are promoted, but you need to work harder for next time.. ";
            } else {

                Status = "PASS";
            }


            params.put("Status", Status);
            params.put("Remarks", Remarks);


            // Load jrxml file from resources

            InputStream reportStream = getClass().getResourceAsStream("/ReportCardSix.jrxml");

            if (reportStream==null){

                throw new RuntimeException("file not found..");
            }





            // Compile the .jrxml to .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fill the report (use empty datasource if no database)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());


            // Export to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\Results\\Results Six\\ReportCardGen" + name + ".pdf");


            System.out.println("Report generated successfully!");
            status=true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;







    }









}






