package khantova.autotest;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test {
	
	private  WebDriver My_driver;
	private  WebDriverWait wait;
	
    public void link_click(String text){
		try
    	{
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(text)));
		WebElement button = My_driver.findElement(By.linkText(text));
		wait.until(ExpectedConditions.elementToBeClickable(button));
	    button.click();
		}
		catch(Exception ex){ 
			System.out.println("Ошибка: link_click, " + text );   
			My_driver.close();  
		}		
	}    
    
    public void catalog_link_click(String text){
    	try{
     		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='catalog-menu__list']/*[ text() ='"+text+"']")));     
    		WebElement link = My_driver.findElement(By.xpath("//*[@class='catalog-menu__list']/*[text() ='"+text+"']"));       	
    		wait.until(ExpectedConditions.elementToBeClickable(link));
    		link.click();
    	}
   	    catch(Exception ex){
   	    	System.out.println("Ошибка: catalog_link_click, "+text);
   	        My_driver.close();   
   	    }
    }        
    
    public void button_click(String text){
		try{
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(.,'"+text+"')]")));
		WebElement button = My_driver.findElement(By.xpath("//button[contains(.,'"+text+"')]"));
		wait.until(ExpectedConditions.elementToBeClickable(button));
	    button.click();	    
		}
		catch(Exception ex){ 
			System.out.println("Ошибка:  button_click, " + text ); 
			My_driver.close();  
		}		
	}    
    
    public void scroll_checkbox(String label){    	
    	try{   		 
    		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[contains(.,'"+label+"')]/..")));  	 
    		WebElement check_box = My_driver.findElement(By.xpath("//label[contains(.,'"+label+"')]/.."));
    		// прокручиваем панель
     		((JavascriptExecutor)My_driver).executeScript("arguments[0].scrollIntoView(false);",My_driver.findElement(By.xpath("//label[contains(.,'"+label+"')]/../../following-sibling::div")));
    		wait.until(ExpectedConditions.visibilityOf(check_box));
    		wait.until(ExpectedConditions.elementToBeClickable(check_box));
   		    check_box.click();
    	}
    	catch(Exception ex){
    		System.out.println("Ошибка: scroll_checkbox, "+label); 
    		My_driver.close();  
    	}    	
    }    
    
    public void max_price(String price){	
    	try{  
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("glf-priceto-var")));
	        WebElement price_to = My_driver.findElement(By.id("glf-priceto-var"));
	        price_to.sendKeys(price);
    	}
    	catch(Exception ex){
    		System.out.println("Ошибка: max_price, "+price); 
    		My_driver.close();  
    	}  		
	}    
    
    public void min_price(String price){		
    	try{ 
    	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("glf-pricefrom-var")));
    	    WebElement price_to = My_driver.findElement(By.id("glf-pricefrom-var"));
    	    price_to.sendKeys(price);
    	}
    	catch(Exception ex){
    		System.out.println("Ошибка: min_price, "+price); 
    		My_driver.close();  
    	}      		
    }    
    
    public void test_one(){
    	try{  	
    	    My_driver = new ChromeDriver();    
            wait = new WebDriverWait (My_driver, 20);
            My_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);    	

            My_driver.get("https://www.yandex.ru/");	 //Зайти на yandex.ru.
            My_driver.get("https://market.yandex.ru/");  //Перейти в яндекс маркет  	
            link_click("Компьютеры");                    //Выбрать раздел Компьютеры
            catalog_link_click("Ноутбуки");	             //Выбрать раздел Ноутбуки
    	    max_price("30000");     	                 //Задать параметр поиска до 30000 рублей.
    	    link_click("HP");                            //Выбрать производителя HP и Lenovo
    	    link_click("Lenovo");                        //Выбрать производителя HP и Lenovo 
    	                  
        	//Нажать кнопку Применить.
    	    // ждём исчезновения старых элементов
    	    List <WebElement> old_elements=My_driver.findElements(By.className("snippet-card__header-text"));
    	    button_click("Применить"); 
    	    for(WebElement wel:old_elements)
	            wait.until(ExpectedConditions.stalenessOf(wel));
    	
    	    // ждём появления новых элементов
    	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("snippet-card__header-text")));    	
    	    List <WebElement> elements= My_driver.findElements(By.className("snippet-card__header-text"));
    	    for(WebElement wel:elements)//ожидание загрузки всех элементов
	            wait.until(ExpectedConditions.visibilityOf(wel));
    	    elements= My_driver.findElements(By.className("snippet-card__header-text"));
    	    System.out.println("количество элементов на странице "+elements.size());   //Проверить, что элементов на странице 12.
    	    assert(elements.size()!=12); 
     
            for(WebElement wel:elements)       	
                System.out.println(wel.getText());  //Проверить, что в найденных результатах (в первых 12) указаны выбранные производители    	
            My_driver.close();   
    	}
        catch(Exception ex){
    		System.out.println("Ошибка: test_one"); 
    		My_driver.close();  
    	}    
    }
    
    
    public void test_two(){
    	try{
    	    My_driver = new ChromeDriver();    
            wait = new WebDriverWait (My_driver, 30);
            My_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
         
            My_driver.get("https://www.yandex.ru/");	  //Зайти на yandex.ru.
            My_driver.get("https://market.yandex.ru/");  //Перейти в яндекс маркет
            link_click("Компьютеры");                    //Выбрать раздел  Компьютеры
            catalog_link_click("Планшеты");	          //Выбрать раздел Планшеты
     	    min_price("20000");                          //Задать параметр поиска от 20000 рублей.
    	    max_price("25000");                          //Задать параметр поиска от 20000 рублей.
    	
    	    //Выбрать производителей Acer и DEL
    	    WebElement b=My_driver.findElement(By.xpath("//button[contains(.,'"+"Ещё"+"')]"));
    	    button_click("Ещё"); 
    	    wait.until(ExpectedConditions.stalenessOf(b));    	
    	    scroll_checkbox("Acer");
    	    scroll_checkbox("DELL");    	 	
    	
    	    //Нажать кнопку Применить.
    	    // ждём исчезновения старых элементов
    	    List <WebElement> old_elements=My_driver.findElements(By.className("snippet-card__price"));
    	    button_click("Применить");     	
    	    for(WebElement wel:old_elements)
	            wait.until(ExpectedConditions.stalenessOf(wel));
    	
    	    // ждём появления новых элементов
    	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("snippet-card__price")));
    	    List <WebElement> price= My_driver.findElements(By.className("snippet-card__price"));
    	    for(WebElement wel:price)//ожидание загрузки всех элементов
	            wait.until(ExpectedConditions.visibilityOf(wel));
    	    price= My_driver.findElements(By.className("snippet-card__price"));
    	
    	    List <WebElement> subprice= My_driver.findElements(By.className("snippet-card__subprice"));    	
    	    System.out.println("количество элементов на странице "+price.size());  //Проверить, что элементов на странице 12. 
    	    assert(price.size()!=12); 
    	
    	    //Проверить, что найденные элементы находятся в заданном диапазоне цен.	
            for(int i=0;i<price.size();i++){ 
                System.out.println("цена "+price.get(i).getText()+" "+subprice.get(i).getText());	
            }    	
            My_driver.close();        	
    	}
        catch(Exception ex){
    		System.out.println("Ошибка: test_two "); 
    		My_driver.close();  
    	} 
        
    }    
   
	public static void main(String[] args){		
    System.setProperty("webdriver.chrome.driver","C:\\Program Files\\Java\\chromedriver-2.30-win32\\chromedriver.exe");	
     Test My_test=new Test();      
     My_test.test_one();
     My_test.test_two();		
	}
	
}
