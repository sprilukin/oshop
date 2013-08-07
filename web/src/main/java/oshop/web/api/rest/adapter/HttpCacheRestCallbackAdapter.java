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
    private Integer size;
    private Long maxAge = 345600L;

    protected void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    protected void setIfModifiedSince(String ifModifiedSinceHeader) throws Exception {
        if (ifModifiedSinceHeader == null) {
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(IF_MODIFIED_SINCE_HEADER_PATTERN, Locale.US);
        Date ifModifiedSinceDate = simpleDateFormat.parse(ifModifiedSinceHeader);
        this.ifModifiedSince = ifModifiedSinceDate.getTime();
    }

    protected void setSize(Integer size) {
        this.size = size;
    }

    protected void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    protected abstract void setModifiedTimes() throws Exception;

    @Override
    protected ResponseEntity<T> getResponse() throws Exception {
        setModifiedTimes();

        boolean modified = ifModifiedSince == null || lastModified > ifModifiedSince;

        ResponseBuilder<T> builder = new ResponseBuilder<T>();

        this.getHeaders().setCacheControl("max-age=" + maxAge);
        this.getHeaders().setLastModified(lastModified);
        this.getHeaders().setExpires(lastModified + maxAge * 1000);

        if (modified) {
            T result = getResult();

            if (size != null) {
                this.getHeaders().setContentLength(size);
            }

            builder.headers(getHeaders());
            builder.body(result).status(HttpStatus.OK);
        } else {
            builder.headers(getHeaders());
            builder.status(HttpStatus.NOT_MODIFIED);
        }

        return builder.build();
    }
}
