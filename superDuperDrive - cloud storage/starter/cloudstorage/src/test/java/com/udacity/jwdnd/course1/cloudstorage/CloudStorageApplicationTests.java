package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	private static String firstName = "FirstName";
	private static String lastName = "LastName";
	private static String username = "user";
	private static String password = "pass";
	private static String noteTitle = "Test Title";
	private static String noteDesc = "Test Description";
	private static String credentialUsername = "credential user";
	private static String updatedCredentialUsername = "Updated Username";
	private static String credentialURL = "google.com";
	private static String updatedCredentialUrl = "yahoo.com";
	private static String credentialPassword = "password";
	private static String updatedCredentialPassword = "newpassword";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/** User Access **/

	public HomePage userSignupAndLogin() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// signup
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		SignupPage signupPage = new SignupPage(driver);

		signupPage.inputFirstName(firstName);
		signupPage.inputLastName(lastName);
		signupPage.inputUsername(username);
		signupPage.inputPassword(password);
		signupPage.clickSubmitButton();

		//login
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.inputUserName(username);
		loginPage.inputPassword(password);
		loginPage.clickLogin();
		return new HomePage(driver);
	}

	@Test
	public void userLoginLogoutTest() {
		HomePage homePage = userSignupAndLogin();
		Assertions.assertEquals("Home", driver.getTitle());

		//logout
		homePage.clickLogout();
		Assertions.assertEquals("Login", driver.getTitle());

		//Try accessing homepage
		driver.get("http://localhost:" + this.port + "/home");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/** Note Tests **/
	public void noteCreate(String noteTitle, String noteDesc, HomePage homePage) {
		ResultPage resultPage = new ResultPage(driver);
		//create note
		homePage.navToNotesTab();
		homePage.addNewNote();
		homePage.inputNoteTitle(noteTitle);
		homePage.inputNoteDescription(noteDesc);
		homePage.saveNoteChanges();
		resultPage.clickOk();
		homePage.navToNotesTab();
	}

	@Test
	public void noteCreationTest() {
		//check note
		HomePage homePage = userSignupAndLogin();
		noteCreate(noteTitle, noteDesc, homePage);
		Note note = homePage.getFirstNote();
		Assertions.assertEquals(noteTitle, note.getNoteTitle());
		Assertions.assertEquals(noteDesc, note.getNoteDesc());
		homePage.clickLogout();
		}

	@Test
	public void noteEditTest() {
		ResultPage resultPage = new ResultPage(driver);

		String modifiedNoteTitle = "Edit note";
		String modifiedNoteDescription = "Edit description";
		//create note
		HomePage homePage = userSignupAndLogin();
		noteCreate(noteTitle, noteDesc, homePage);
		//edit note
		homePage.editNote();
		homePage.modifyNoteTitle(modifiedNoteTitle);
		homePage.modifyNoteDescription(modifiedNoteDescription);
		homePage.saveNoteChanges();
		resultPage.clickOk();
		homePage.navToNotesTab();
		Note newNote = homePage.getFirstNote();
		Assertions.assertEquals(modifiedNoteTitle, newNote.getNoteTitle());
		Assertions.assertEquals(modifiedNoteDescription, newNote.getNoteDesc());
		homePage.clickLogout();
	}

	@Test
	public void noteDeletionTest() {
		ResultPage resultPage = new ResultPage(driver);
		//create note
		HomePage homePage = userSignupAndLogin();
		noteCreate(noteTitle, noteDesc, homePage);
		//delete note
		Assertions.assertFalse(homePage.noNotes(driver));
		homePage.deleteNote();
		resultPage.clickOk();
		Assertions.assertTrue(homePage.noNotes(driver));
		homePage.clickLogout();
	}


	/** Note Tests **/
	public void createCredential(String credentialURL, String credentialUsername, String credentialPassword, HomePage homePage) {
		//create credential
		homePage.navToCredentialsTab();
		homePage.addNewCredential();
		homePage.inputCredentialUrl(credentialURL);
		homePage.inputCredentialUsername(credentialUsername);
		homePage.inputCredentialPassword(credentialPassword);
		homePage.saveCredentialChanges();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		homePage.navToCredentialsTab();
	}

	@Test
	public void credCreationTest() {
		//create cred
		HomePage homePage = userSignupAndLogin();
		createCredential(credentialURL, credentialUsername, credentialPassword, homePage);
		//verify cred data
		Credential credential = homePage.getFirstCredential();
		Assertions.assertEquals(credentialURL, credential.getCredentialUrl());
		Assertions.assertEquals(credentialUsername, credential.getCredentialUsername());
		Assertions.assertNotEquals(credentialPassword, credential.getPassword());
		homePage.clickLogout();
	}

	@Test
	public void credEditTest() {
		ResultPage resultPage = new ResultPage(driver);
		//create cred
		HomePage homePage = userSignupAndLogin();
		createCredential(credentialURL, credentialUsername, credentialPassword, homePage);

		//edit credential
		Credential originalCredential = homePage.getFirstCredential();
		String firstEncryptedPassword = originalCredential.getPassword();
		homePage.editCredential();
		String newCredentialUrl = updatedCredentialUrl;
		String newCredentialUsername = updatedCredentialUsername;
		String newPassword = updatedCredentialPassword;
		homePage.inputCredentialUrl(newCredentialUrl);
		homePage.inputCredentialUsername(newCredentialUsername);
		homePage.inputCredentialPassword(newPassword);
		homePage.saveCredentialChanges();
		resultPage.clickOk();
		homePage.navToCredentialsTab();
		Credential modifiedCredential = homePage.getFirstCredential();
		Assertions.assertEquals(newCredentialUrl, modifiedCredential.getCredentialUrl());
		Assertions.assertEquals(newCredentialUsername, modifiedCredential.getCredentialUsername());
		String modifiedCredentialPassword = modifiedCredential.getPassword();
		Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
		Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredentialPassword);
		homePage.clickLogout();
	}

	@Test
	public void credDeletionTest() {
		ResultPage resultPage = new ResultPage(driver);
		//create cred
		HomePage homePage = userSignupAndLogin();
		createCredential(credentialURL, credentialUsername, credentialPassword, homePage);

		//delete credential
		homePage.deleteCredential();
		resultPage.clickOk();
		homePage.navToCredentialsTab();
		Assertions.assertTrue(homePage.noCredentials(driver));
		homePage.clickLogout();
	}
}
