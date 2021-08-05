import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import java.nio.charset.StandardCharsets;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;



public class AutoInstagram {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		System.setProperty("webdriver.edge.driver","C:\\dev\\tools\\msedgedriver.exe");
		WebDriver driver = new EdgeDriver();
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
        driver.get("https://www.instagram.com");
        
        //driver.manage().window().maximize();
        
        //login to instagram, dont forget to hide your login credentials
        driver.findElement(By.name("username")).sendKeys(Resources.InstaUsername);
        driver.findElement(By.name("password")).sendKeys(Resources.InstaPassword + Keys.ENTER);
        
        //click on Not now button of the dialogue box.
        WebElement NotnowDiv = driver.findElement(By.className("cmbtv"));
        NotnowDiv.findElement(By.tagName("button")).click();
        
        // turn notifications off
        WebElement NotificationBoxDiv = driver.findElement(By.className("mt3GC"));
        NotificationBoxDiv.findElements(By.tagName("button")).get(1).click();
        
        //navigating to messages
        driver.findElement(By.className("xWeGp")).click();
        
        
        
        //lets search the user in the search bar
        driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/div/div[1]/div/div[2]/input")).sendKeys(Resources.UsernameOfPersonToChatWith);
        
        //Now navigate to the first person's profile coming in suggestions
        driver.findElements(By.className("-qQT3")).get(0).click();
       
        
       //click the message button in the profile
        driver.findElements(By.cssSelector("div._7UhW9.xLCgt.qyrsm.KV-D4.fDxYl.T0kll")).get(0).click();
   
        // get the chatwindow element
        WebElement ChatWindow = driver.findElement(By.cssSelector("div.uueGX"));
        
        // Get all the messages sent by that person
        List<WebElement> AllMessagesOfUser = ChatWindow.findElements(By.cssSelector("div.PaBnr.JdNBm"));
        
        // print the last reply 
        String LastMsg = AllMessagesOfUser.get(AllMessagesOfUser.size()-1).getText();
        System.out.println(LastMsg);
        
        
        // until the users says Bye/bye we will be actively listening for messages
        while(!LastMsg.equals("Bye") && !LastMsg.equals("bye")) {
        	
    
        	
        	// now check if you have received new message from the user
        	
        	List<WebElement> AllUpdatedMessagesOfUser = ChatWindow.findElements(By.cssSelector("div.PaBnr.JdNBm"));
            
            String UpdatedLastMsg = AllUpdatedMessagesOfUser.get(AllUpdatedMessagesOfUser.size()-1).getText();
            
            // if new message is there
            if (!LastMsg.equals(UpdatedLastMsg)) {
            	String requestStringToAPI = URLEncoder.encode(UpdatedLastMsg, StandardCharsets.UTF_8.name());
            	System.out.println(requestStringToAPI);
            	
            	
            	//GENERATING AN AI CHATBOT REPLY
          		HttpRequest request = HttpRequest.newBuilder()
          				.uri(URI.create("https://ai-chatbot.p.rapidapi.com/chat/free?message=" + requestStringToAPI + "&uid=user1"))
          				.header("x-rapidapi-key", "f4235e0bf6mshdb402b382da408dp1e7a7ejsn6928052f377f")
          				.header("x-rapidapi-host", "ai-chatbot.p.rapidapi.com")
          				.method("GET", HttpRequest.BodyPublishers.noBody())
          				.build();
              	HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
              
              	//Extracting reply from the response of the API	
          		JSONObject ChatBotResponse = (JSONObject) new JSONObject(response.body()).get("chatbot");
          		String automatedReply = ChatBotResponse.getString("response");
          		System.out.println(automatedReply);
          		
          		//filling text area with the automated reply
          		driver.findElement(By.tagName("textarea")).sendKeys(automatedReply + Keys.ENTER);
          		
          		LastMsg = UpdatedLastMsg ;
            }
        	
        }
        
        
        
        
      driver.close();
  		
    
        
	}

}