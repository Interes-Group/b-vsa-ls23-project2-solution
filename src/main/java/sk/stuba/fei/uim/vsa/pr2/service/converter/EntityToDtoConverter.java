package sk.stuba.fei.uim.vsa.pr2.service.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class EntityToDtoConverter<T> {

    private final Map<Class<?>, Function<T, ?>> converters;

    public EntityToDtoConverter() {
        converters = new HashMap<>();
    }

    public EntityToDtoConverter(Map<Class<?>, Function<T, ?>> converters) {
        this.converters = converters;
    }

    public <R> R convert(T entity, Class<R> dtoClass) {
        Function<T, R> fun = (Function<T, R>) converters.get(dtoClass);
        if (fun == null)
            throw new IllegalStateException("Cannot find converter function for " + entity.getClass().getSimpleName() + " -> " + dtoClass.getSimpleName());
        return fun.apply(entity);
    }

    protected <R> void addConverter(Class<R> clazz, Function<T, R> fun) {
        converters.put(clazz, fun);
    }


}
