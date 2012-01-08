package fi.opendemocracy.web;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.vaadin.addon.jpacontainer.MutableEntityProvider;
import com.vaadin.addon.jpacontainer.provider.MutableLocalEntityProvider;

/**
 * Utility class for constructing/obtaining a shared entity provider instance for an entity class.
 */
public class EntityProviderUtil {
    private Map<Class<?>, MutableEntityProvider<?>> providerMap = new HashMap<Class<?>, MutableEntityProvider<?>>();

    private static EntityProviderUtil instance;

    private EntityProviderUtil() {
        // singleton pattern
    }

    public static EntityProviderUtil get() {
        if (null == instance) {
            instance = new EntityProviderUtil();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> MutableEntityProvider<T> getEntityProvider(Class<T> cls) {
        synchronized (providerMap) {
            if (!providerMap.containsKey(cls)) {
                MutableLocalEntityProvider<?> provider = buildEntityProvider(cls);
                if (null != provider) {
                    providerMap.put(cls, provider);
                }
            }
            return (MutableEntityProvider<T>) providerMap.get(cls);
        }
    }

    private <T> MutableLocalEntityProvider<T> buildEntityProvider(Class<T> cls) {
        EntityManager entityManager = getEntityManager(cls);
        if (null != entityManager) {
            MutableLocalEntityProvider<T> provider = new MutableLocalEntityProvider<T>(cls, entityManager);
            // Spring should manage transactions - writable
            provider.setTransactionsHandledByProvider(false);
            return provider;
        } else {
            return null;
        }
    }
    
    private EntityManager getEntityManager(Class<?> cls) {
        try {
            Method method = cls.getMethod("entityManager");
            if (null != method && (method.getModifiers() & Modifier.STATIC) != 0) {
                return (EntityManager) method.invoke(null);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not obtain entity manager for the entity class "+cls.getCanonicalName(), e);
        }
        throw new IllegalArgumentException("Could not obtain entity manager for the entity class "+cls.getCanonicalName() + ": missing static method entityManager()");
    }
}
