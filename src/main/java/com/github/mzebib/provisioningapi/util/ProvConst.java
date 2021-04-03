package com.github.mzebib.provisioningapi.util;

/**
 * @author mzebib
 */
public final class ProvConst {

    // Base Package
    public static final String BASE_PACKAGE = "com.github.mzebib.provisioningapi";

    // Base URI
    public static final String URI_API = "/api/v1";

    // Auth URIs
    public static final String URI_AUTH = "/auth";
    public static final String URI_LOGIN = "/login";
    public static final String URI_TOKEN =  "/token";
    public static final String URI_PASSWORD = "/password";

    // User URIs
    public static final String URI_USER = "/user";

    // Org URIs
    public static final String URI_ORG = "/org";

    // Spring Actuator URIs
    public static final String URI_AUDITEVENTS = "/auditevents";
    public static final String URI_BEANS = "/beans";
    public static final String URI_CONFIGPROPS = "/configprops";
    public static final String URI_ENV = "/env";
    public static final String URI_INFO = "/info";
    public static final String URI_HEALTH = "/health";
    public static final String URI_LOGGERS = "/loggers";
    public static final String URI_MAPPINGS = "/mappings";
    public static final String URI_METRICS = "/metrics";
    public static final String URI_TRACE = "/trace";

    // Path Params
    public static final String PATH_PARAM_ID = "/{id}";

    // Param
    public static final String PARAM_TOKEN = "token";

    private ProvConst() {
    }

}
