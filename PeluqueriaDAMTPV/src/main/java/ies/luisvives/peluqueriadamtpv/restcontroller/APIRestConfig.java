package ies.luisvives.peluqueriadamtpv.restcontroller;

public class APIRestConfig {
    private static final String server = "localhost";
    private static final String port = "13169";
    private static final String endpoint = "/";
    private static final String API_URL = "http://" + server + ":" + port + endpoint;

    private APIRestConfig() {

    }

    public static RestAPIAppointments getAppointmentsService() {
        return RetrofitClient.getClient(API_URL).create(RestAPIAppointments.class);
    }

    public static RestApiUsers getUsersService() {
        return RetrofitClient.getClient(API_URL).create(RestApiUsers.class);
    }

    public static RestApiService getServicesService() {
        return RetrofitClient.getClient(API_URL).create(RestApiService.class);
    }
}
