import controllers.VendingController;
import repositories.VendingRepository;
import services.VendingService;

public class Main {
    public static void main(String[] args) {
        VendingRepository repository = new VendingRepository();
        VendingService service = new VendingService(repository);
        VendingController controller = new VendingController(service);
        controller.start();
    }
}