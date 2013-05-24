package oshop.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import oshop.dao.GenericDao;
import oshop.model.BaseEntity;
import oshop.model.City;
import oshop.model.OrderState;
import oshop.model.ShippingType;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class InitialCityLoaderService implements ApplicationListener<ContextRefreshedEvent> {

    public static final String CITIES_FILE_NAME = "/disctionaries/cities.json";
    public static final String ORDER_STATES_FILE_NAME = "/disctionaries/orderStates.json";
    public static final String SHIPPING_TYPES_FILE_NAME = "/disctionaries/shippingTypes.json";

    private static final Log log = LogFactory.getLog(InitialCityLoaderService.class);

    @Resource
    private GenericDao<City, Integer> cityDao;

    @Resource
    private GenericDao<ShippingType, Integer> shippingTypeDao;

    @Resource
    private GenericDao<OrderState, Integer> orderStateDao;

    private ObjectMapper mapper = new ObjectMapper();

    private volatile boolean dictionariesLoaded = false;
    private final Object monitor = new Object();

    @Override
    @Transactional(readOnly = false)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (dictionariesLoaded) {
            return;
        }

        synchronized (monitor) {
            if (dictionariesLoaded) {
                return;
            }

            loadEntities(cityDao, City.class);
            loadEntities(shippingTypeDao, ShippingType.class);
            loadEntities(orderStateDao, OrderState.class);

            dictionariesLoaded = true;
        }
    }

    private void loadEntities(GenericDao dao, Class<? extends BaseEntity> entityClass) {
        int size = dao.list(null, null).size();

        if (size == 0) {
            List<? extends BaseEntity> entities = null;

            try {
                entities = getEntities(entityClass);
            } catch (IOException e) {
                log.error("Could not get entities list: ", e);
                return;
            }

            int i = 1;
            for (BaseEntity entity: entities) {
                dao.add(entity);

                if ( i % 20 == 0 ) {
                    dao.getSession().flush();
                    dao.getSession().clear();
                }

                i++;
            }
        }
    }

    private String getSesourceAsString(String path) throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (is == null) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(is, baos);

        return new String(baos.toByteArray(), "UTF-8");
    }


    private List<? extends BaseEntity<Integer>> getEntities(Class<? extends BaseEntity> entityClass) throws IOException {
        if (entityClass == City.class) {
            return getCities();
        } else if (entityClass == ShippingType.class) {
            return getShippingTypes();
        } else if (entityClass == OrderState.class) {
            return getOrderStates();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private List<City> getCities() throws IOException {
        String string = getSesourceAsString(CITIES_FILE_NAME);
        if (string == null) {
            return null;
        }

        return mapper.readValue(string, new TypeReference<List<City>>(){});
    }

    private List<OrderState> getOrderStates() throws IOException {
        String string = getSesourceAsString(ORDER_STATES_FILE_NAME);
        if (string == null) {
            return null;
        }

        return mapper.readValue(string, new TypeReference<List<OrderState>>(){});
    }

    private List<ShippingType> getShippingTypes() throws IOException {
        String string = getSesourceAsString(SHIPPING_TYPES_FILE_NAME);
        if (string == null) {
            return null;
        }

        return mapper.readValue(string, new TypeReference<List<ShippingType>>(){});
    }
}
