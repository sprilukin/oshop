package oshop.web.i18n;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class ExposedResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    @Value("_")
    private String dotReplacement;

    @Cacheable(value = "messages", key = "#locale.toString")
    public Map<String, String> getAllMessages(@NotNull Locale locale) {
        Properties properties = getMergedProperties(locale).getProperties();

        Map<String, String> messages = new HashMap<String, String>(properties.size());

        for (Map.Entry<Object, Object> entry: properties.entrySet()) {
            String key = ((String) entry.getKey()).replaceAll("\\.", dotReplacement);
            messages.put(key, (String) entry.getValue());
        }

        return messages;
    }
}
