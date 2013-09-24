package org.openmrs.module.cpm.pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateProposalPage extends BaseCpmPage {
    public CreateProposalPage(WebDriver driver) {
        super(driver);
    }


    public void enterDetails() {

        driver.findElement(By.name("name")).sendKeys("John Doe");
        driver.findElement(By.name("email")).sendKeys("john@doe.com");
        driver.findElement(By.name("description")).sendKeys("Automated Test Data");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("tb");
        // ERROR: Caught exception [ERROR: Unsupported command [getTable | css=table.searchConceptResults.1.1 | ]]
        driver.findElement(By.xpath("//input[@type='checkbox']")).click();
        driver.findElement(By.cssSelector("div.dialogFooter > button")).click();
        driver.findElement(By.xpath("//form[@id='cpm-edit-proposal-form']/div/div[4]/table/tbody[2]/tr/td[3]/input")).sendKeys("new concept");
        driver.findElement(By.xpath("//form[@id='cpm-edit-proposal-form']/div/div[5]/button")).click();

    }
}
