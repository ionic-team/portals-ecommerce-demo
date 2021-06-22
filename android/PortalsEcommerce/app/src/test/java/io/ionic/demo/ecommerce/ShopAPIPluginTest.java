package io.ionic.demo.ecommerce;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.ionic.demo.ecommerce.data.DataService;
import io.ionic.demo.ecommerce.data.ShoppingCart;
import io.ionic.demo.ecommerce.data.model.Cart;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EcommerceApp.class, BitmapFactory.class, Base64.class})
public class ShopAPIPluginTest {
    private static final String APP_DATA = "data.json";

    Context context = mock(Context.class);
    AssetManager assetManager = mock(AssetManager.class);
    PluginCall call = mock(PluginCall.class);
    Bridge bridge = mock(Bridge.class);
    Bitmap bitmap = mock(Bitmap.class);
    FileOutputStream fileOutputStream = mock(FileOutputStream.class);
    EcommerceApp ecommerceApp = mock(EcommerceApp.class);

    private InputStream getTestInputStream() {
        return this.getClass().getClassLoader().getResourceAsStream(ShopAPIPluginTest.APP_DATA);
    }

    @Before
    public void init() {
        try{
            // general mocks
            when(bridge.getContext()).thenReturn(context);
            when(context.getAssets()).thenReturn(assetManager);
            when(assetManager.open("data.json")).thenReturn(getTestInputStream());

            // image tests
            PowerMockito.mockStatic(EcommerceApp.class);
            PowerMockito.mockStatic(BitmapFactory.class);
            PowerMockito.mockStatic(Base64.class);
            when(EcommerceApp.getContext()).thenReturn(context);
            when(BitmapFactory.decodeResource(context.getResources(), R.drawable.jt_avatar)).thenReturn(bitmap);
            when(Base64.encodeToString(any(byte[].class), eq(Base64.DEFAULT))).thenReturn("storedimagebase64");
            when(context.openFileOutput("user-image.jpg", Context.MODE_PRIVATE)).thenReturn(fileOutputStream);

            // data tests
            when(EcommerceApp.getInstance()).thenReturn(ecommerceApp);
            when(ecommerceApp.getShoppingCart()).thenReturn(new ShoppingCart());

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void getUserPictureShouldReturnPicture() {
        ShopAPIPlugin plugin = new ShopAPIPlugin();
        plugin.setBridge(bridge);
        plugin.getUserPicture(call);

        ArgumentCaptor<JSObject> argument = ArgumentCaptor.forClass(JSObject.class);
        verify(call).resolve(argument.capture());
        assertEquals("storedimagebase64", argument.getValue().getString("picture"));
    }

    @Test
    public void setUserPictureShouldStorePicture() {
        when(call.getString("picture")).thenReturn("user-image.jpg");

        ShopAPIPlugin plugin = new ShopAPIPlugin();
        plugin.setBridge(bridge);
        plugin.setUserPicture(call);

        assertEquals("user-image.jpg", DataService.getInstance(context).getUser().image);
    }

    @Test
    public void getCartShouldReturnCart() {
        ShopAPIPlugin plugin = new ShopAPIPlugin();
        plugin.getCart(call);

        ArgumentCaptor<JSObject> argument = ArgumentCaptor.forClass(JSObject.class);
        verify(call).resolve(argument.capture());
        assertEquals("1", argument.getValue().getString("id"));
    }

    @Test
    public void getUserDetailsShouldReturnDetails() {
        ShopAPIPlugin plugin = new ShopAPIPlugin();
        plugin.getUserDetails(call);

        ArgumentCaptor<JSObject> argument = ArgumentCaptor.forClass(JSObject.class);
        verify(call).resolve(argument.capture());
        JSObject resultCheck = argument.getValue();
        assertEquals("mock - Josh", argument.getValue().getString("firstName"));
    }
}
