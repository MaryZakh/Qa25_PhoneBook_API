package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ErrorDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIDOkhttp {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyYUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcxNTE3NzQ5MiwiaWF0IjoxNzE0NTc3NDkyfQ.A1dSaWGAiBvnUzE6WR8jSR2gjp4rJ-T8QTZgCsICPLs";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    String id;


    @BeforeMethod
    public void preCondition() throws IOException {

        //create contact
        int i = new Random().nextInt(1000) + 1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Maya")
                .lastName("Dow")
                .address("NY")
                .email("maya" + i + "@gmail.com")
                .phone("123456" + i)
                .description("Friend")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        MessageDTO messageDTO = gson.fromJson(response.body().string(), MessageDTO.class);
        String message = messageDTO.getMessage();
        //get id from "message" :"Contact was added! ID: 0c26a91e-762c-4fae-b35c-0c3010983e7d"
        String[] all = message.split(": ");
        //id
        id = all[1];
    }

    @Test
    public void deleteContactByIDSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/" + id)
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        MessageDTO dto = gson.fromJson(response.body().string(), MessageDTO.class);
        System.out.println(dto.getMessage());
        Assert.assertEquals(dto.getMessage(), "Contact was deleted!");
    }


    @Test
    public void deleteContactByIDWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/" + id)
                .delete()
                .addHeader("Authorization", "ghjudo")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(), "Unauthorized");
    }

    @Test
    public void deleteContactByIDNotFound() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/" + 123)
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 400);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(), "Bad Request");
        System.out.println(errorDTO.getMessage());
        Assert.assertEquals(errorDTO.getMessage(),"Contact with id: 123 not found in your contacts!");
    }

}
//df117b1e-cd90-4989-a5af-23b8dd32194d
//stark487@gmail.com
//===================
//f63f597c-9fe9-460c-9126-0ee99a6dae82
//stark603@gmail.com
//===================
//f7b66501-aa6d-4043-ab59-024fcb763f7e
//stark891@gmail.com
//===================
//b4de5237-7e82-4abd-bbe6-d7b4ef54fa75
//stark1512@gmail.com
//===================
//        5018f793-224d-41b0-8e09-5d6f069460c4
//stark1733@gmail.com
//===================
//b38d247c-176d-4365-ae2d-903e04fb2fba
//stark2519@gmail.com
//===================
//        62bcc38f-39ee-498e-a690-3cd8d9dee46f
//stark1831@gmail.com
//===================
//b7f078bf-37e5-46aa-a614-6a384a403778
//stark547@gmail.com
//===================