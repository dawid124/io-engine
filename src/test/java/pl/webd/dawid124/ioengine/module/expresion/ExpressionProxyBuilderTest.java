package pl.webd.dawid124.ioengine.module.expresion;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.utils.ResourceUtils;

import java.io.IOException;

// JDK_JAVA_OPTIONS=--add-opens java.base/java.lang=ALL-UNNAMED
public class ExpressionProxyBuilderTest {


    public static final String YAML1 =
            "ioId: cct-wardrobe-d\n" +
            "brightness: ${cv-home-up-brightness}\n" +
            "color:\n" +
            " r: 0\n" +
            " g: 0\n" +
            " b: 0\n" +
            " w: 255\n" +
            " ww: 255\n" +
            " time: 1000\n" +
            " stepTime: 30";

//    @Test
//    public void test1() throws IOException {
//        JsonElement jsonObject = JsonParser.parseString(ResourceUtils.convertYamlToJson(YAML1));
//
//        UiAction obj = ExpressionProxyBuilder.instance(UiAction.class, new AutomationContext(null))
//                .deserialize(jsonObject);
//
//        Assert.assertEquals(obj.getIoId(), "cct-wardrobe-d");
//        Assert.assertEquals(obj.getIoType(), null);
//        Assert.assertEquals(obj.getBrightness(), "cct-wardrobe-d");
//    }
}