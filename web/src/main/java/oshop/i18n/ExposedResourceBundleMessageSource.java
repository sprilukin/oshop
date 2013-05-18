package oshop.i18n;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class ExposedResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    @Value("classpath:")
    private String basenamePrefix;

    private volatile long lastUpdateTime = 0;
    private long cacheSeconds = -1;

    private final Map<String, Map<Locale, PropertiesHolder>> cachedBaseNameProperties = new HashMap<String, Map<Locale, PropertiesHolder>>();

    @Override
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        lastUpdateTime = System.currentTimeMillis();

        return super.refreshProperties(filename, propHolder);
    }

    public void setCacheInSeconds(int cacheSeconds) {
        this.cacheSeconds = cacheSeconds;
        super.setCacheSeconds(cacheSeconds);
    }

    public long getCacheSeconds() {
        return cacheSeconds;
    }

    public long getLastModifiedTime() {
        return  lastUpdateTime;
    }

    public Map<String, String> getAllMessages(@NotNull Locale locale) {
        //This method call will cause recaching bundles in case they were modified and cacheSeconds > 0
        try {
            getMessage("NON_EXISTING_CODE", null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            /* just ignore this exception since we know that message doesnt exists */
        }

        return convertPropertiesToStringMap(getMergedProperties(locale).getProperties());
    }

    public Map<String, String> getAllMessagesForBaseName(@NotNull String baseName, @NotNull Locale locale) {
        synchronized (this.cachedBaseNameProperties) {
            Map<Locale, PropertiesHolder> localePropsMap = this.cachedBaseNameProperties.get(baseName);
            if (localePropsMap == null) {
                localePropsMap = new HashMap<Locale, PropertiesHolder>();
                this.cachedBaseNameProperties.put(baseName, localePropsMap);
            }

            PropertiesHolder mergedHolder = localePropsMap.get(locale);
            if (mergedHolder != null) {
                return convertPropertiesToStringMap(mergedHolder.getProperties());
            }

            Properties mergedProps = new Properties();
            mergedHolder = new PropertiesHolder(mergedProps, -1);

            List filenames = calculateAllFilenames(basenamePrefix + baseName, locale);
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
            messages.put((String)entry.getKey(), (String) entry.getValue());
        }

        return messages;
    }
}
