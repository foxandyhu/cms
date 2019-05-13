import java.util.HashMap;
import java.util.Map;

public class TT2 {

    public static void main(String[] args) {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("a", 1);
        map1.put("b", 2);

        Map<String, Object> map2 = new HashMap<>();
        map2.putAll(map1);
        map2.remove("sa");

        System.out.println(map1.get("dd"));

    }
}
