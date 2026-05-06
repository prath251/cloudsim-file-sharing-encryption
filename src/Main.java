import org.cloudbus.cloudsim.CloudSim;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {

        System.out.println("🚀 Starting CloudSim Simulation...");

        try {
            int num_user = 1;
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;

            CloudSim.init(num_user, calendar, trace_flag);

            System.out.println("✅ CloudSim initialized successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}