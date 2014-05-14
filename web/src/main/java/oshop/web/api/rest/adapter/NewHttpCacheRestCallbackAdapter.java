package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class NewHttpCacheRestCallbackAdapter<T> extends ReturningRestCallbackAdapter<T> {

    public static final String IF_MODIFIED_SINCE_HEADER_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    private Long lastModified;
    private Long ifModifiedSince;
    private Long maxAge = 345600L;

    public NewHttpCacheRestCallbackAdapter(Long lastModified, String ifModifiedSince) {
        this.lastModified = lastModified;
        this.ifModifiedSince = getIfModifiedSince(ifModifiedSince);
    }

    protected Long getIfModifiedSince(String ifModifiedSinceHeader) {
        if (ifModifiedSinceHeader == null) {
            return -1L;
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(IF_MODIFIED_SINCE_HEADER_PATTERN, Locale.US);
            Date ifModifiedSinceDate = simpleDateFormat.parse(ifModifiedSinceHeader);
            return ifModifiedSinceDate.getTime();
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected ResponseEntity<T> getResponse() throws Exception {
        boolean modified = ifModifiedSince == null || lastModified > ifModifiedSince;

        ResponseBuilder<T> builder = new ResponseBuilder<T>();

        this.getHeaders().setCacheControl("public;max-age=" + maxAge);
        this.getHeaders().setLastModified(lastModified);
        this.getHeaders().setExpires(lastModified + maxAge * 1000);

        if (modified) {
            T result = getResult();

            builder.headers(getHeaders());
            builder.body(result).status(HttpStatus.OK);
        } else {
            builder.headers(getHeaders());
            builder.status(HttpStatus.NOT_MODIFIED);
        }

        return builder.build();
    }
}
