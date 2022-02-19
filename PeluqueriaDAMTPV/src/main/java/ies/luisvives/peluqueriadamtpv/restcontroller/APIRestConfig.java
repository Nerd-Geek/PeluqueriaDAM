package ies.luisvives.peluqueriadamtpv.restcontroller;

public class APIRestConfig {
    private static final String server = "localhost";
    private static final String port = "13169";
    private static final String endpoint = "/";
    private static final String API_URL = "http://" + server + ":" + port + endpoint;

    private APIRestConfig() {

    }

    public RestAPIAppointments getService() {
        return RetrofitClient.getClient(API_URL).create(RestAPIAppointments.class);
    }
}
