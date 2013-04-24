package oshop.i18n;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class ExposedResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    @Value("_")
    private String dotReplacement;

    @Value("classpath:")
    private String basenamePrefix;

    private final Map<String, Map<Locale, PropertiesHolder>> cachedBaseNameProperties = new HashMap<String, Map<Locale, PropertiesHolder>>();

    @Cacheable(value = "messages", key = "#locale.toString")
    public Map<String, String> getAllMessages(@NotNull Locale locale) {
        return convertPropertiesToStringMap(getMergedProperties(locale).getProperties());
    }

    //@Cacheable(value = "messages", key = "#locale.toString")
    public Map<String, String> getAllMessagesForBaseName(@NotNull String basename, @NotNull Locale locale) {
        synchronized (this.cachedBaseNameProperties) {
            Map<Locale, PropertiesHolder> localePropsMap = this.cachedBaseNameProperties.get(basename);
            if (localePropsMap == null) {
                localePropsMap = new HashMap<Locale, PropertiesHolder>();
                this.cachedBaseNameProperties.put(basename, localePropsMap);
            }

            PropertiesHolder mergedHolder = localePropsMap.get(locale);
            if (mergedHolder != null) {
                return convertPropertiesToStringMap(mergedHolder.getProperties());
            }

            Properties mergedProps = new Properties();
            mergedHolder = new PropertiesHolder(mergedProps, -1);

            List filenames = calculateAllFilenames(basenamePrefix + basename, locale);
            for (int j = filenames.size() - 1; j >= 0; j--) {
                String filename = (String) filenames.get(j);
                PropertiesHolder propHolder = getProperties(filename);
                if (propHolder.getProperties() != null) {
                    mergedProps.putAll(propHolder.getProperties());
                }
            }

            localePropsMap.put(locale, mergedHolder);

            return convertPropertiesToStringMap(mergedHolder.getProperties());
        }
    }

    private Map<String, String> convertPropertiesToStringMap(Properties properties) {
        Map<String, String> messages = new HashMap<String, String>(properties.size());

        for (Map.Entry<Object, Object> entry: properties.entrySet()) {
            String key = ((String) entry.getKey()).replaceAll("\\.", dotReplacement);
            messages.put(key, (String) entry.getValue());
        }

        return messages;
    }
}
