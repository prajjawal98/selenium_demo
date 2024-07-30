
package Listeners;

import com.aventstack.extentreports.reporter.ExtentAventReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class ExtentReport implements IReporter {
    private ExtentReports extent1;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        Properties prop = new Properties();
        FileInputStream FileInputStream = null;
        String reportPath = null;

        try {
            FileInputStream = new FileInputStream(
                    new File("src/test/resources/object.properties"));
            prop.load(FileInputStream);
            reportPath = prop.getProperty("ReportPath");
        } catch (IOException e) {
            e.printStackTrace();
        }

        extent1 = new ExtentReports(reportPath, true);
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                try {
                    buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ExtentAventReporter reporter= new ExtentAventReporter(reportPath);
        extent1.setTestRunnerOutput("Prajjawal Kansal");
        extent1.flush();
        extent1.close();
    }


    private void buildTestNodes(IResultMap tests, LogStatus status) throws Exception {
        ExtentTest test1;

        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                //testCaseCount++;
                test1 = extent1.startTest("Test cases Result");
                test1.setStartedTime(getTime(result.getStartMillis()));
                test1.setEndedTime(getTime(result.getEndMillis()));

                for (String group : result.getMethod().getGroups()){
                    test1.assignCategory(group);
                }
                if (result.getThrowable() != null) {

                    test1.log(LogStatus.FAIL, "failed " );
                    test1.log(LogStatus.FAIL,test1.addScreenCapture(".//test-output//Screenshot//last1.png") );

                } else {
                    test1.log(LogStatus.PASS, "  testcase passed");


                }
                extent1.endTest(test1);
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
