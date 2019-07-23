import com.bfly.common.DateUtil;
import com.bfly.common.StringUtil;
import com.bfly.core.context.IpThreadLocal;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

public class TT2 {

    public static void main(String[] args) {

        String root="G:\\test\\ftp\\src";
        String target="app/pages";
        root = Paths.get(root, target).toAbsolutePath().toString();
        System.out.println(root);
    }
}
