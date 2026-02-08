
package qamarvel.framework.reporter;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import qamarvel.framework.context.RunContext;

public final class ExtentReporterNG {

    private static ExtentReports extent;

    private ExtentReporterNG() {}

    public static ExtentReports getReportObject() {

        if (extent == null) {

            String runDir = RunContext.getRunDirectory();

            if (runDir == null) {
                throw new RuntimeException(
                        "RunContext is NULL. @BeforeSuite did not execute.");
            }

            // THIS creates: reports/run-YYYY-MM-DD_HH-mm
            File runDirectory = new File(runDir);
            if (!runDirectory.exists()) {
                boolean created = runDirectory.mkdirs();
                System.out.println(">>> Run directory created: " + created);
            }

            String reportPath = runDir + "/index.html";

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter(reportPath);

            reporter.config().setReportName("Web Automation Results");
            reporter.config().setDocumentTitle("Test Suite Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            extent.setSystemInfo("Layered POM Framework", "QAMarvel");
            extent.setSystemInfo("Software Architect", "Arun Kumar");
        }

        return extent;
    }
}