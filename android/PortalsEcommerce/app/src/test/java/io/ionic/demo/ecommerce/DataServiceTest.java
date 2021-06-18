package io.ionic.demo.ecommerce;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.ionic.demo.ecommerce.data.DataService;
import io.ionic.demo.ecommerce.data.model.Address;
import io.ionic.demo.ecommerce.data.model.AppData;
import io.ionic.demo.ecommerce.data.model.CreditCard;
import io.ionic.demo.ecommerce.data.model.Product;
import io.ionic.demo.ecommerce.data.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DataServiceTest {
    DataService dataService;
    AppData appData;

    @Before
    public void beforeEach() {
        appData = new AppData();
        appData.user = new User();
        appData.user.firstName = "Joe";
        appData.user.lastName = "Test";
        appData.user.image = "defaultimage";
        appData.user.addresses = new ArrayList<>();
        Address address = new Address() {{
            id = 1;
            street = "1234 Easy St";
            city = "Madison";
            state = "WI";
            postal = "12345";
        }};
        appData.user.addresses.add(address);
        CreditCard creditCard = new CreditCard() {{
            id = 1;
            company = "Visa";
            number = "1111-1111-1111-1111";
            expirationDate = "12/24";
            zip = "12345";
            cvv = "123";
            preferred = true;
        }};
        appData.user.creditCards = new ArrayList<>();
        appData.user.creditCards.add(creditCard);
        Product product = new Product() {{
            id = 1;
            title = "Cool Cap";
            description = "you need one";
            image = "";
            category = "sale";
            price = 10.00f;
        }};
        appData.products = new ArrayList<>();
        appData.products.add(product);
        dataService = new DataService(appData);
    }

    @Test
    public void getUserShouldReturnUser() {
        User user = dataService.getUser();
        assertNotNull(user);
        assertEquals("Joe", user.firstName);
    }

    @Test
    public void setUserShouldStoreNewUser() {
        User user = dataService.getUser();
        user.firstName = "Jane";
        dataService.setUser(user);
        user = null;
        User user2 = dataService.getUser();
        assertEquals("Jane", user2.firstName);
    }

    @Test
    public void addingAddressShouldStoreAddress() {
        User user = dataService.getUser();
        Address address = new Address() {{
            id = 2;
            street = "5678 Hard St";
            city = "Madison";
            state = "WI";
            postal = "12345";
        }};
        user.addresses.add(address);
        dataService.setUser(user);
        user = null;
        User user2 = dataService.getUser();
        assertEquals(2, user2.addresses.size());
        assertEquals("5678 Hard St", user2.addresses.get(1).street);
    }

    @Test
    public void setUserPictureShouldStorePicture() {
        User user = dataService.getUser();
        user.image = "testimage";
        dataService.setUser(user);
        user = null;

        User user2 = dataService.getUser();
        assertEquals("testimage",user2.image);
    }

    @Test
    public void getUserPictureShouldReturnPicture() {
        User user = dataService.getUser();
        assertEquals("defaultimage",user.image);
    }
}
