package com.uniovi.tests;

import com.uniovi.tests.pageobjects.*;
import com.uniovi.tests.util.SeleniumUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.assertTrue;

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
    static public void begin() {

    }

    @AfterClass
    static public void end() {
        // Cerramos el navegador al terminar
        driver.quit();
    }

    //PR01. Acceder a la página principal /
    @Test
    public void PR01() {
        PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
    }

    //PR02. OPción de navegación. Pinchar en el enlace Registro en la página home
    @Test
    public void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }

    //PR03. OPción de navegación. Pinchar en el enlace Identificate en la página home
    @Test
    public void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    //PR04. OPción de navegación. Cambio de idioma de Español a Ingles y vuelta a Español
    @Test
    public void PR04() {
        PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish",
                PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
        //SeleniumUtils.esperarSegundos(driver, 2);
    }

    //PR05. Prueba del formulario de registro. registro con datos correctos
    @Test
    public void PR05() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_RegisterView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777",
                "77777");
        //Comprobamos que entramos en la sección privada
        PO_View.checkElement(driver, "text", "Notas del usuario");
    }

    //PR06. Prueba del formulario de registro. DNI repetido en la BD, Nombre corto, .... pagination
    // pagination-centered, Error.signup.dni.length
    @Test
    public void PR06() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_RegisterView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777",
                "77777");
        PO_View.getP();
        //COmprobamos el error de DNI repetido.
        PO_RegisterView.checkKey(driver, "Error.signup.dni.duplicate",
                PO_Properties.getSPANISH());
        //Rellenamos el formulario.
        PO_RegisterView.fillForm(driver, "99999990B", "Jose", "Perez", "77777",
                "77777");
        //Comprobamos el error de Nombre corto .
        PO_RegisterView.checkKey(driver, "Error.signup.name.length",
                PO_Properties.getSPANISH());
        //Rellenamos el formulario.
        PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "Per", "77777",
                "77777");
    }

    // PR07: Identificación válida con usuario de ROL usuario ( 99999990A/123456)
    @Test
    public void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        PO_View.checkElement(driver, "text", "Notas del usuario");
    }

    // PR08: Identificación válida con usuario de ROL profesor ( 99999993D/123456).
    @Test
    public void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, "99999993D", "123456");
        //Comprobamos que entramos en la pagina privada de Profesor
        PO_View.checkElement(driver, "text", "modificar");
    }

    //PR09: Identificación válida con usuario de ROL Administrador (99999988F/123456).
    @Test
    public void PR09() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, "99999988F", "123456");
        //Comprobamos que entramos en la pagina privada de Administrador
        PO_View.checkElement(driver, "text", "Gestión de Usuarios");
    }

    //PR10: PR10: Identificación inválida con usuario de ROL alumno (99999990A/123457).
    @Test
    public void PR10() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, "99999990A", "123457");
        //Comprobamos que estamos aún en Login
        PO_View.checkKey(driver, "login.message", PO_Properties.getSPANISH());
    }

    // PR11: Identificación válida y desconexión con usuario de ROL usuario (99999990A/123456)
    @Test
    public void PR11() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        PO_View.checkElement(driver, "text", "Notas del usuario");
        //Desconectamos
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    //PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse usando el rol de estudiante.
    @Test
    public void PR12() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        PO_View.checkElement(driver, "text", "Notas del usuario");
        //Contamos el número de filas de notas
        List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free",
                "//tbody/tr", PO_View.getTimeout());
        assertTrue(elementos.size() == 4);
        //Ahora nos desconectamos
        PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
    }

    //PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
    //P13. Ver la lista de Notas.
    @Test
    public void PR13() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        PO_View.checkElement(driver, "text", "Notas del usuario");
        SeleniumUtils.esperarSegundos(driver, 1);
        //Contamos las notas
        By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
        SeleniumUtils.esperarSegundos(driver, 1);
        //Esperamos por la ventana de detalle
        PO_View.checkElement(driver, "text", "Detalles de la nota");
        SeleniumUtils.esperarSegundos(driver, 1);
        //Ahora nos desconectamos
        PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
    }

    //P14. Loguearse como profesor y Agregar Nota A2.
    //P14. Esta prueba podría encapsularse mejor ...
    @Test
    public void PR14() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, "99999993D", "123456");
        //COmprobamos que entramos en la pagina privada del Profesor
        PO_View.checkElement(driver, "text", "99999993D");
        //Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
        List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'marks-menu')]/a ");
        elementos.get(0).click();
        //Esperamos a aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
        elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
        //Pinchamos en agregar Nota.
        elementos.get(0).click();
        //Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
        PO_PrivateView.fillFormAddMark(driver, 3, "Nota Nueva 1", "8");
        //Esperamos a que se muestren los enlaces de paginación la lista de notas
        elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la última página
        elementos.get(3).click();
        //Comprobamos que aparece la nota en la pagina
        elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
        //Ahora nos desconectamos
        PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
    }

    //PR15. Loguearse como profesor, vamos a la ultima página y Eliminamos la Nota Nueva 1.
    //PR15. Ver la lista de Notas.
    @Test
    public void PR15() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, "99999993D", "123456");
        //Comprobamos que entramos en la pagina privada del Profesor
        PO_View.checkElement(driver, "text", "99999993D");
        //Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
        List<WebElement> elementos = PO_View.checkElement(driver, "free",
                "//li[contains(@id, 'marks-menu')]/a");
        elementos.get(0).click();
        //Pinchamos en la opción de lista de notas.
        elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/list')]");
        elementos.get(0).click();
        //Esperamos a que se muestren los enlaces de paginacion la lista de notas
        elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la última página
        elementos.get(3).click();
        //Esperamos a que aparezca la Nueva nota en la ultima pagina
        //Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
        //td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(text(), 'mark/delete')]"
        elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'Nota Nueva 1')]" +
                "/following-sibling::*/a[contains(@href, 'mark/delete')]");
        elementos.get(0).click();
        //Volvemos a la última pagina
        elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
        elementos.get(3).click();
        //Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
        SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1",PO_View.getTimeout() );
                //Ahora nos desconectamos
                PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
    }
}
