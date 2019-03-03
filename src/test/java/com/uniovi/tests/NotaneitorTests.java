package com.uniovi.tests;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.fail;

// Ordenar pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaneitorTests {
    //En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens automáticas)):
    static String PathFirefox65 = "D:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckdriver024 = "D:\\Desktop\\Uni\\SDI\\Workspace\\sdi105-selenium\\geckodriver024win64.exe";
    //En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens automáticas):
    //static String PathFirefox65 = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
    //static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";

    //Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
    static String URL = "http://localhost:8090";

    public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckdriver);
        WebDriver driver = new FirefoxDriver();
        return driver;
    }

    @Before
    public void setUp() throws Exception {
        driver.navigate().to(URL);
    }

    @After
    public void tearDown() throws Exception {
        driver.manage().deleteAllCookies();
    }

    @BeforeClass
    static public void begin(){

    }

    @AfterClass
    static public void end(){
        // Cerramos el navegador al terminar
        driver.quit();
    }

    @Test
    public void test() {
        fail("Not yet implemented");
    }
}
