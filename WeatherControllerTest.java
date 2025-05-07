package sit707_week5;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherControllerTest {

    @Test
    public void testStudentIdentity() {
        String studentId = "224244945"; // Replace with actual student ID
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Vansh "; // Replace with actual name
        Assert.assertNotNull("Student name is null", studentName);
    }

    @Test
    public void testTemperatureMin() {
        System.out.println("+++ testTemperatureMin +++");

        WeatherController wController = WeatherController.getInstance();

        int nHours = wController.getTotalHours();
        double minTemperature = 1000;
        for (int i = 0; i < nHours; i++) {
            double temperatureVal = wController.getTemperatureForHour(i + 1);
            if (minTemperature > temperatureVal) {
                minTemperature = temperatureVal;
            }
        }

        Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);

        wController.close();
    }

    @Test
    public void testTemperatureMax() {
        System.out.println("+++ testTemperatureMax +++");

        WeatherController wController = WeatherController.getInstance();

        int nHours = wController.getTotalHours();
        double maxTemperature = -1;
        for (int i = 0; i < nHours; i++) {
            double temperatureVal = wController.getTemperatureForHour(i + 1);
            if (maxTemperature < temperatureVal) {
                maxTemperature = temperatureVal;
            }
        }

        Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);

        wController.close();
    }

    @Test
    public void testTemperatureAverage() {
        System.out.println("+++ testTemperatureAverage +++");

        WeatherController wController = WeatherController.getInstance();

        int nHours = wController.getTotalHours();
        double sumTemp = 0;
        for (int i = 0; i < nHours; i++) {
            double temperatureVal = wController.getTemperatureForHour(i + 1);
            sumTemp += temperatureVal;
        }
        double averageTemp = sumTemp / nHours;

        Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);

        wController.close();
    }

    @Test
    public void testTemperaturePersist() {
        System.out.println("+++ testTemperaturePersist +++");

        WeatherController wController = WeatherController.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");

        String beforePersist = sdf.format(new Date());
        String persistTime = wController.persistTemperature(2, 19.5);
        String afterPersist = sdf.format(new Date());

        try {
            Date before = sdf.parse(beforePersist);
            Date persisted = sdf.parse(persistTime);
            Date after = sdf.parse(afterPersist);

            Assert.assertTrue("Persisted time not between start and end time",
                    !persisted.before(before) && !persisted.after(after));
        } catch (ParseException e) {
            e.printStackTrace();
            Assert.fail("Date parsing failed");
        }

        wController.close();
    }
}
