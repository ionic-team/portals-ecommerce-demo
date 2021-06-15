package io.ionic.demo.ecommerce;

import android.content.Context;
import android.content.res.AssetManager;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.InputStream;

import io.ionic.demo.ecommerce.data.DataService;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EcommerceApp.class)
public class ShopAPIPluginTest {
    private static final String APP_DATA = "data.json";

    Context context = mock(Context.class);
    AssetManager assetManager = mock(AssetManager.class);
    PluginCall call = mock(PluginCall.class);

    private InputStream getTestInputStream() {
        return this.getClass().getClassLoader().getResourceAsStream(ShopAPIPluginTest.APP_DATA);
    }

    @Before
    public void init() {
        try{
            PowerMockito.mockStatic(EcommerceApp.class);
            when(context.getAssets()).thenReturn(assetManager);
            when(assetManager.open("webapp/data.json")).thenReturn(getTestInputStream());
            when(EcommerceApp.getContext()).thenReturn(context);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void getUserPictureShouldReturnPicture() {
        ShopAPIPlugin plugin = new ShopAPIPlugin();
        plugin.getUserPicture(call);

        ArgumentCaptor<JSObject> argument = ArgumentCaptor.forClass(JSObject.class);
        verify(call).resolve(argument.capture());
        assertEquals("jt-avatar.png", argument.getValue().getString("picture"));
    }

    @Test
    public void setUserPictureShouldStorePicture() {
        when(call.getString("picture")).thenReturn("testimage");

        ShopAPIPlugin plugin = new ShopAPIPlugin();
        plugin.setUserPicture(call);

        assertEquals("testimage", DataService.getInstance(context).getUser().image);
    }
}
