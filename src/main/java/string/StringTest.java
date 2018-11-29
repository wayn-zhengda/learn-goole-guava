package string;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * String类中的一些操作
 */
public class StringTest {
    /**
     * 字符串的合并
     */
    @Test
    public void joinDemo(){
        String demo = Joiner.on("---").join(Arrays.asList(1,3,5,8,9));
        System.out.println(demo);
        // 将Map合并为字符串
        Map<String, String> map = new HashMap();
        map.put("a","65");
        map.put("b","66");
        String joinMap = Joiner.on("++++++").withKeyValueSeparator("=").join(map);
        System.out.println(joinMap);
    }

    /**
     * 字符串的拆分
     */
    @Test
    public void splitterDemo(){
        String testString = "a,b,c,d,e,f";
//        Splitter.onPattern()  传入正则表达式
        Iterable<String> result = Splitter.on(",").split(testString);
        List<String> list = Splitter.on(",").splitToList(testString);
//        System.out.println(result);
//        System.out.println(list);
        for (String s : result){
            System.out.print(s);
            System.out.println("**");
        }
        for (String s : list){
            System.out.print(s);
            System.out.println("-");
        }

        // 将字符串拆分为Map
        String mapString = "a=65/b=66";
        Map map = Splitter.on("/").withKeyValueSeparator("=").split(mapString);
        System.out.println(map.keySet());
        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
    }

    /**
    * 判断字符串是否为空
    * */
    @Test
    public void nullDemo(){
        // Strings.isNullOrEmpty()
        String temp = "";
        String test = null;
        System.out.println(Strings.isNullOrEmpty(temp));
        System.out.println(Strings.isNullOrEmpty(test));
    }


}
