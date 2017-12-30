package btc.update.in.api;

/**
 * Created by subhashhardaha on 03/12/17.
 */

public class UpdateAPIUtils {
    public UpdateAPIUtils() {
    }

    public static final String BASE_URL = "http://192.168.0.103:8080/";

    public static UpdateAPIService getAPIService() {

        return UpdateClient.getClient(BASE_URL).create(UpdateAPIService.class);
    }
}
