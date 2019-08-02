import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.ipseek.IpSeekerUtil;

public class TT2 {

    public static void main(String[] args) {
        IpSeekerUtil seeker = new IpSeekerUtil();

        IPLocation location = seeker.getIPLocation("125.73.220.18");
        System.out.println(location.getCountry());
        System.out.println(location.getArea());
    }
}
