package pl.webd.dawid124.ioengine.module.automation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.automation.macro.json.*;

public class AutomationServiceTest {

    @Test
    public void test() {
        Gson gson =  new GsonBuilder()
                .registerTypeAdapter(IUiAction.class, new ExpressionUIActionJsonAdapter(new AutomationContext(null)))
                .create();

        IUiAction obj = gson.fromJson("{\"ioId\":\"rgbw-lobby\",\"brightness\":\"${state.brightness}\",\"color\":{\"r\":0,\"g\":0,\"b\":0,\"w\":255}}", IUiAction.class);

        System.out.println(obj.getBrightness());
    }
}