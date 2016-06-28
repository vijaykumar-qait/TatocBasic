
package tatoc;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;

public class TatocBasic {
    
    public static void main(String[] args) throws InterruptedException {
//------------------------------------------------------------------------------
       //create a new instance of fireFox
       File binaryPath= new File("/home/vijaykumar/Downloads/firefox/firefox");
       FirefoxBinary ffBinary= new FirefoxBinary(binaryPath);
       FirefoxProfile ffProfile= new FirefoxProfile();
       
       
       WebDriver webdriver = new FirefoxDriver(ffBinary, ffProfile);
       
       //now use this to visit the url
       webdriver.get("http://10.0.1.86/");
       
       //Find the link element with partial matching visible text.
       webdriver.findElement(By.partialLinkText("tatoc")).click(); 
       webdriver.findElement(By.linkText("Basic Course")).click();
//------------------------------------------------------------------------------      
       //part 1
       //Grid Gate
        webdriver.findElement(By.className("greenbox")).click();
       
//------------------------------------------------------------------------------       
       //part 2
       //Frame Dungeon
       webdriver.switchTo().frame("main");
       String color1, color2;
       boolean color=true;
       do{
           color1 = webdriver.findElement(By.cssSelector("#answer")).getAttribute("class");
           webdriver.switchTo().frame("child");
           color2 = webdriver.findElement(By.cssSelector("#answer")).getAttribute("class");
           if(!(color1.equals(color2))) {
               webdriver.switchTo().parentFrame();
               webdriver.findElement(By.partialLinkText("Repaint Box 2")).click();
           }
           else {
               webdriver.switchTo().parentFrame();
               webdriver.findElement(By.partialLinkText("Proceed")).click();
               break;
           }
           
        } while(color);
       
//------------------------------------------------------------------------------
         //part 3
         //Drag Around
         Actions act = new Actions(webdriver);
         WebElement From = webdriver.findElement(By.id("dragbox"));
         //System.out.println(From.getLocation());
 
         WebElement To = webdriver.findElement(By.id("dropbox"));
         //System.out.println(To.getLocation());
         
         act.dragAndDrop(From, To).build().perform();
         
         webdriver.findElement(By.partialLinkText("Proceed")).click();
         
//------------------------------------------------------------------------------
        //part 4
        //Popup Windows
        webdriver.findElement(By.xpath("html/body/div[1]/div[2]/a[1]")).click();
        //store your parent window
        String handle = webdriver.getWindowHandle();
        for(String webhand: webdriver.getWindowHandles()) {
            webdriver.switchTo().window(webhand);
        }
        webdriver.findElement(By.cssSelector("#name")).sendKeys("vijay kumar");
        webdriver.findElement(By.cssSelector("#submit")).click();
        webdriver.switchTo().window(handle);

        webdriver.findElement(By.linkText("Proceed")).click();
        
//------------------------------------------------------------------------------
        //part 5
        //Cookie Handling
        webdriver.findElement(By.partialLinkText("Generate Token")).click();
        String token=webdriver.findElement(By.cssSelector("#token")).getText();
        String[] tokenarr = token.split(": ");
        
        Cookie cookie = new Cookie("Token",tokenarr[1]);
        webdriver.manage().addCookie(cookie);
        //System.out.println(tokenarr[1]);
        webdriver.findElement(By.partialLinkText("Proceed")).click();
        
//------------------------------------------------------------------------------
        //closing firefox
        //webdriver.quit();
    }
    
}