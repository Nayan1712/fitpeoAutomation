package StepDefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

public class MyStepdefs {


    public static WebDriver driver;

    @Given("user has started a browser")
    public void userHasStartedABrowser() {
        //browser set up for chrome
        System.setProperty("webdriver.chrome.driver","C:/Users/Kimpu Thingujam/IdeaProjects/fitpeoProject/src/test/java/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("Chrome browser opened");
    }



    @Given("user navigates to Fitpeo Home page")
    public void userNavigatesToFitpeoHomePage() throws InterruptedException {
        //navigating to the fitpeo home page
        driver.get("https://www.fitpeo.com/");
        WebElement fitpeoLogo = driver.findElement(By.xpath("//h4[contains(@class,'MuiTypography')]/img"));
        Thread.sleep(2000);
        //Validating Fitpeo logo
        Assert.assertTrue(fitpeoLogo.isDisplayed());
        System.out.println("Navigated to Fitpeo homepage");
    }


    @Then("user should click on Revenue Calculator tab")
    public void userShouldClickOnRevenueCalculatorTab() throws InterruptedException {
        //Clicking on Revenue Calculator
        driver.findElement(By.xpath("//div[text()='Revenue Calculator']")).click();
        System.out.println("Clicked on Revenue Calculator");
        Thread.sleep(2000);
    }

    @When("user scrolls to the Slider")
    public void userScrollsToTheSlider() throws InterruptedException {

        WebElement mySliderHeader = driver.findElement(By.xpath("(//p[contains(text(),'RPM and CCM Programs')])[2]"));
        //Scrolling down to slider section
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mySliderHeader);
        Thread.sleep(2000);
        System.out.println("Scrolling to slider section completed");

    }

    @And("user moves the slider to {string} value")
    public void userMovesTheSliderToValue(String expectedSliderValue) throws InterruptedException, AWTException {

        WebElement mySlider = driver.findElement(By.xpath("(//span[contains(@class,'MuiSlider')])[1]//span[3]"));
        WebElement sliderInput = driver.findElement(By.xpath("//input[contains(@class,'MuiInputBase')]"));

        Actions myAction = new Actions(driver);
        myAction.click(mySlider);
        //highlighting Slider button
        ((JavascriptExecutor) driver).executeScript("arguments[0]. setAttribute('style', 'border: 2px solid red;');", mySlider);
        System.out.println("Highlighted the slider button");
        //looping until the value reaches the desired value i.e., 820 in this case
        Thread.sleep(2000);
        boolean flag = true;
        while(flag){
            int actualSliderValue = Integer.valueOf(sliderInput.getAttribute("value"));  // slider position taken from actual slider button
            if(actualSliderValue<Integer.valueOf(expectedSliderValue)){             //increase until 820
                myAction.sendKeys(Keys.RIGHT).build().perform();
            }else if(actualSliderValue>Integer.valueOf(expectedSliderValue)){       //decrease until 820
                myAction.sendKeys(Keys.LEFT).build().perform();
            }else{                                                         // if matched assert at 820
                Assert.assertTrue(actualSliderValue==Integer.valueOf(expectedSliderValue));
                System.out.println("Stopped sliding at slider value "+expectedSliderValue);
                flag = false;
            }
        }
//        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,200)", "");
        Thread.sleep(2000);
        //removing highlight on slider
        ((JavascriptExecutor) driver).executeScript("arguments[0]. setAttribute('style', 'border: none;');", mySlider);
        System.out.println("Highlight removed from Slider button");
    }


    @Then("user should validate the slider position when value entered is {string}")
    public void userShouldValidateTheSliderPositionWhenValueEnteredIs(String expectedSliderVal) throws InterruptedException {
        WebElement sliderInput = driver.findElement(By.xpath("//input[contains(@class,'MuiInputBase')]"));
        WebElement mySlider = driver.findElement(By.xpath("(//span[contains(@class,'MuiSlider')])[1]//input"));
        //clearing the existing value in input field
        sliderInput.sendKeys(Keys.BACK_SPACE);
        sliderInput.sendKeys(Keys.BACK_SPACE);
        sliderInput.sendKeys(Keys.BACK_SPACE);
        sliderInput.sendKeys(Keys.BACK_SPACE);
        sliderInput.sendKeys(expectedSliderVal); // entering 560 in this case
        System.out.println("entering input value "+expectedSliderVal);
        Thread.sleep(2000);

        int actualSliderValue = Integer.valueOf(mySlider.getAttribute("value"));
        //comparing the slider value and the input field value
        Assert.assertTrue(actualSliderValue==Integer.valueOf(expectedSliderVal));
        System.out.println("Validation for entered input value: "+expectedSliderVal+". And actual value: "+actualSliderValue);
    }

    @And("user selects below checkboxes")
    public void userSelectsBelowCheckboxes(DataTable tableList) {
        List<String> myList= tableList.asList();
        //lopping through the data table list for the list of checkboxes
        for(String data: myList){
            WebElement desiredCheckbox = driver.findElement(By.xpath("//p[text()='"+data+"']/..//input"));
            //highlight
            ((JavascriptExecutor) driver).executeScript("arguments[0]. setAttribute('style', 'border: 2px solid red;');", desiredCheckbox);
            desiredCheckbox.click(); //clicking on the checkboxes
            System.out.println("Clicked on checkbox: "+ data);
        }

    }

    @Then("user should validate that the total recurring reimbursement value is {string}")
    public void userShouldValidateThatTheTotalRecurringReimbursementValueIs(String expectedTotalReimbValue) {
        WebElement actualTotal = driver.findElement(By.xpath("//p[text()='Total Recurring Reimbursement for all Patients Per Month:']/p"));
        //highlighting the total reimbursement value on screen
        ((JavascriptExecutor) driver).executeScript("arguments[0]. setAttribute('style', 'border: 2px solid red;');", actualTotal);

        //validation of the total reimbursement amount with the actual and desired value
        Assert.assertEquals(actualTotal.getText(),expectedTotalReimbValue);
        System.out.println("Validation for desired calculation: "+expectedTotalReimbValue+". And actual value: "+actualTotal.getText());
    }
}
