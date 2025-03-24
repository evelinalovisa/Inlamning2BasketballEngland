package stepdefinitions;


import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;

public class RegistrationStepDefs {

    WebDriver driver;
    private WebDriverWait wait;

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("Jag är på registreringssidan")
    public void jag_är_på_registreringssidan() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    }


    @When("Jag fyller i alla obligatoriska fält korrekt")
    public void jag_fyller_i_alla_obligatoriska_fält_korrekt() {

        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "evestestfest+" + timestamp + "@gmail.com";
        System.out.println(email);

        driver.findElement(By.id("dp")).sendKeys("01/01/1990");
        driver.findElement(By.id("member_firstname")).sendKeys("Test");
        driver.findElement(By.id("member_lastname")).sendKeys("Testman");
        driver.findElement(By.id("member_emailaddress")).sendKeys(email);
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(email);

        String password = "Test123!";
        driver.findElement(By.id("signupunlicenced_password")).sendKeys(password);
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys(password);
    }

    @When("Jag godkänner alla villkor")
    public void jag_godkänner_alla_villkor() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='sign_up_25']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='sign_up_26']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']"))).click();
        } catch (Exception e) {
            System.out.println("Fel vid klick på villkor");
        }
    }

    @When("Jag klickar på registreringsknappen")
    public void jag_klickar_på_registreringsknappen() {
        driver.findElement(By.name("join")).click();
    }

    @Then("Mitt konto bör skapas framgångsrikt")
    public void mitt_konto_bör_skapas_framgångsrikt() {

        WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("h2.bold.gray.text-center.margin-bottom-40")
        ));

        Assert.assertEquals(
                "Registreringen lyckades inte",
                "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND",
                thankYouMessage.getText());
    }

    @When("Jag fyller i alla obligatoriska fält förutom efternamn")
    public void jag_fyller_f_alla_obligatoriska_fält_förutom_efternamn() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "evestestfest+" + timestamp + "@gmail.com";

        driver.findElement(By.id("dp")).sendKeys("01/01/1990");
        driver.findElement(By.id("member_firstname")).sendKeys("Test");

        driver.findElement(By.id("member_emailaddress")).sendKeys(email);
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(email);

        String password = "Test123!";
        driver.findElement(By.id("signupunlicenced_password")).sendKeys(password);
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys(password);
    }

    @Then("Registreringen ska misslyckas med ett felmeddelande om att efternamn saknas")
    public void registreringen_ska_misslyckas_med_ett_felmeddelande_om_att_efternamn_saknas() {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("span[for='member_lastname']")
        ));

        Assert.assertEquals(
                "Felaktigt eller saknat felmeddelande om efternamn",
                "Last Name is required",
                errorMessage.getText());
    }

    @When("Jag fyller i alla obligatoriska fält med olika lösenord")
    public void jag_fyller_i_alla_obligatoriska_fält_med_olika_lösenord() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "evestestfest+" + timestamp + "@gmail.com";

        driver.findElement(By.id("dp")).sendKeys("01/01/1990");
        driver.findElement(By.id("member_firstname")).sendKeys("Test");
        driver.findElement(By.id("member_lastname")).sendKeys("Testman");
        driver.findElement(By.id("member_emailaddress")).sendKeys(email);
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(email);

        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Test123!");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Test456!");
    }

    @Then("registreringen ska misslyckas med ett felmeddelande om att lösenorden inte matchar")
    public void registreringen_ska_misslyckas_med_ett_felmeddelande_om_att_lösenorden_inte_matchar() {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("span[for='signupunlicenced_confirmpassword']")));

        Assert.assertEquals(
                "Felaktigt eller saknat felmeddelande om icke-matchande lösenord",
                "Password did not match",
                errorMessage.getText());
    }

    @When("Jag godkänner inte terms and conditions")
    public void jag_godkänner_inte_terms_and_conditions() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='sign_up_26']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']"))).click();

        } catch (Exception e) {
            System.out.println("Fel när inte alla obligatoriska villkor är godkända");
        }
    }

    @Then("Registreringen ska misslyckas med ett felmeddelande om att terms and conditions måste godkännas")
    public void registreringen_ska_misslyckas_med_ett_felmeddelande_om_att_terms_and_conditions_måste_godkännas() {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("span[for='TermsAccept']")
        ));

        Assert.assertEquals(
                "Felaktigt eller saknat felmeddelande om ej godkända villkor",
                "You must confirm that you have read and accepted our Terms and Conditions",
                errorMessage.getText());
    }
}