package com.exhibition;

public final class AppConfig {
//    public static final String HOST = "192.168.0.101";
    public static final String HOST = "180.168.35.37";
    public static final int PORT = 8080;
    public static final int MESSAGE_PORT = 8888;
    public static final String CONTEXT_ROOT = "/exhibition";
    public static final String URL_SERVER = "http://" + HOST + ":" + PORT + CONTEXT_ROOT;
    public static final String URL_ALL_DATA = URL_SERVER + "/api/exhibitions/CCBN";
    public static final String URL_CHECK_IN = URL_SERVER + "/api/attendees/check_in";
    public static final String URL_REGISTER = URL_SERVER + "/api/attendees";
    public static final String URL_QRCODE = URL_SERVER + "/api/qrcode/get";
    public static final String URL_IMAGE_ROOT = URL_SERVER + "/upload/image/";
}
