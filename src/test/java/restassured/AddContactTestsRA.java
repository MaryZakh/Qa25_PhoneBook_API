package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class AddContactTestsRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyYUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcyMTA1NzkxMiwiaWF0IjoxNzIwNDU3OTEyfQ.6NeHqHmSUr70_XoeOaA5E2rlD8r12dlywdRcql_FVZQ";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void addContactSuccess(){

        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Qa25")
                .lastName("Automation")
                .email("qa25_"+i+"@gmail.com")
                .phone("12345678"+i)
                .address("Haifa")
                .description("Students")
                .build();


        given()
                .header("Authorization",token)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200);
                //.assertThat().body("message",containsString("Contact was added"));


    }
}
