import jdbc.AdvertiseRepository;
import jdbc.PersonRepository;
import model.Advertise;
import org.junit.Assert;
import org.junit.runner.Request;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utility.AdvertiseData;
import utility.DataBaseUtility;
import utility.JsonTest;

import java.sql.SQLException;
import java.util.List;

public class AdvertiseRepositoryTests {

    @BeforeSuite
    void fillTableWithTestData() throws SQLException {
        int length = AdvertiseRepository.getAllAdvertises().size();
        List<Advertise> advertiseList = AdvertiseRepository.getAllAdvertises();
        AdvertiseData[]advertiseDataArray =new AdvertiseData[length];
        for(int i=0;i< length;i++){
            AdvertiseData advertiseData = new AdvertiseData();
            advertiseData.setAdvertise(advertiseList.get(i));
            advertiseDataArray[i] = advertiseData;
        }
        JsonTest.writeAdvertiseDataIntoJson(advertiseDataArray);
        DataBaseUtility.deleteDataFromAdvertisesTable();
        AdvertiseData[] advertiseData = JsonTest.getAdvertiseDataFromJson();
        for (int i = 0; i < advertiseData.length; i++) {
            AdvertiseRepository.postAdvertise(advertiseData[i].getAdvertise().getPerson(), advertiseData[i].getAdvertise());
        }
    }

    @DataProvider(name = "advertiseData")
    public static Object[][] getAdvertiseData() {
        AdvertiseData[] advertiseData = JsonTest.getAdvertiseDataFromJson();
        Object[][] objects = new Object[advertiseData.length][];
        for (int i = 0; i < advertiseData.length; i++) {
            objects[i] = new Object[]{advertiseData[i].getAdvertise()};
        }
        return objects;
    }

    @Test(dataProvider = "advertiseData", expectedExceptions = {SQLException.class})
    void doTestPostAdvertise(Advertise advertise) throws SQLException {
        AdvertiseRepository.postAdvertise(advertise.getPerson(), advertise);
    }

    @Test(dataProvider = "advertiseData")
    void testUpdateAdvertise(Advertise advertise) throws SQLException {
        Assert.assertTrue(AdvertiseRepository.updateAdvertise(advertise, advertise.getPerson()));
    }

    @Test()
    void testGetAllAdvertises() throws SQLException {
        Assert.assertEquals(AdvertiseRepository.getAllAdvertises().size(), JsonTest.getAdvertiseDataFromJson().length);
    }

    @AfterSuite
    void doAfterSuite() throws SQLException {
        DataBaseUtility.deleteDataFromAdvertisesTable();
        AdvertiseData[] advertiseData = JsonTest.recoverAdvertiseTableDataFromJson();
        for (int i = 0; i < advertiseData.length; i++) {
            AdvertiseRepository.postAdvertise(advertiseData[i].getAdvertise().getPerson(),advertiseData[i].getAdvertise());
        }
    }
}
