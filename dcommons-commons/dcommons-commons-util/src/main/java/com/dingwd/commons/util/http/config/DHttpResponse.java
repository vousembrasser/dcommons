package com.dingwd.commons.util.http.config;

import org.apache.hc.core5.http.ReasonPhraseCatalog;
import org.apache.hc.core5.http.message.BasicHttpResponse;

import java.util.Locale;

public class DHttpResponse extends BasicHttpResponse {
    public DHttpResponse(int code, ReasonPhraseCatalog catalog, Locale locale) {
        super(code, catalog, locale);
    }

    public DHttpResponse(int code, String reasonPhrase) {
        super(code, reasonPhrase);
    }

    public DHttpResponse(int code) {
        super(code);
    }
}
