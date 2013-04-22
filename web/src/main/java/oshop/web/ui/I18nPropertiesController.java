package oshop.web.ui;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.web.i18n.ExposedResourceBundleMessageSource;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value = "/i18n")
public class I18nPropertiesController {

    @Resource(name = "messageSource")
    private MessageSource messageSource;

    @RequestMapping(
            value = "/messages",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Map<String, String> getMessagesFromCache() throws Exception {
        ExposedResourceBundleMessageSource exposedResourceBundleMessageSource =
                getExposedResourceBundleMessageSource(messageSource);

        return exposedResourceBundleMessageSource.getAllMessages(LocaleContextHolder.getLocale());
    }

    private ExposedResourceBundleMessageSource getExposedResourceBundleMessageSource(MessageSource messageSource) {
        ExposedResourceBundleMessageSource exposedResourceBundleMessageSource = null;

        if (messageSource instanceof ExposedResourceBundleMessageSource) {
            exposedResourceBundleMessageSource = (ExposedResourceBundleMessageSource)messageSource;
        } else if (messageSource instanceof DelegatingMessageSource) {
            MessageSource parentMessageSource = ((DelegatingMessageSource)messageSource).getParentMessageSource();

            if (parentMessageSource instanceof ExposedResourceBundleMessageSource) {
                exposedResourceBundleMessageSource = (ExposedResourceBundleMessageSource)parentMessageSource;
            } else {
                throw new IllegalStateException("Can not resolve proper message bundle");
            }
        }

        return exposedResourceBundleMessageSource;
    }
}
