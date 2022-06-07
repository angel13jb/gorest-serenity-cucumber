package co.in.gorest.userinfo;

import co.in.gorest.testbase.TestBase;
import co.in.gorest.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCURDTestSteps extends TestBase {
    static String name = "Tenali Ramakrishna" ;
    static String email = "tenali.ramakrishna@yahoo.co.uk";
    static String gender = "male";
    static String status = "active";
    static int userId;

    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public void test001() {
        userSteps.createUser(name,email,gender,status).statusCode(201).log().all();
    }

    @Title("Verify if the user was added ")
    @Test
    public void test002() {
        name = "Tenali Ramakrishna";
        HashMap<String, Object> userMap = userSteps.getUserInfoByFirstName(name);
        Assert.assertThat(userMap, hasValue(name));
        userId = (int) userMap.get("id");
        System.out.println(userId);
    }

    @Title("Update the user information and verify the updated ")
    @Test
    public void test003() {
        name = "Tenali Shanker";
        gender="male";
        email="tenali@gmail.com";
        status="active";
        userId=5862;
        userSteps.updateUser(userId,name,email,gender,status).statusCode(200).log().body().body("name", equalTo(name),"email",equalTo(email));
    }

    @Title("Delete the user and verify if the user is deleted! ")
    @Test
    public void test004() {
        userId=5862;
        userSteps.deleteUser(userId).statusCode(204).log().status();
        userSteps.getUserById(userId).statusCode(404).log().status();
    }
}
