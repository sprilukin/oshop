package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class HttpCacheRestCallbackAdapter<T> extends ReturningRestCallbackAdapter<T> {

    public static final String IF_MODIFIED_SINCE_HEADER_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    private Long lastModified;
    private Long ifModifiedSince;
    private int size;

    protected void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    protected void setIfModifiedSince(String ifModifiedSinceHeader) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(IF_MODIFIED_SINCE_HEADER_PATTERN, Locale.US);
        Date ifModifiedSinceDate = simpleDateFormat.parse(ifModifiedSinceHeader);
        this.ifModifiedSince = ifModifiedSinceDate.getTime();
    }

    protected void setSize(int size) {
        this.size = size;
    }

    @Override
    protected ResponseEntity<T> getResponse() throws Exception {
        T result = getResult();

        boolean modified = ifModifiedSince == null || lastModified > ifModifiedSince;

        ResponseBuilder<T> builder = new ResponseBuilder<T>();

        this.getHeaders().setCacheControl("max-age=604800, public");
        this.getHeaders().setLastModified(lastModified);

        if (modified) {
            this.getHeaders().setContentLength(size);
            builder.headers(getHeaders());
            builder.body(result).status(HttpStatus.OK);
        } else {
            builder.headers(getHeaders());
            builder.status(HttpStatus.NOT_MODIFIED);
        }

        return builder.build();
    }
}
