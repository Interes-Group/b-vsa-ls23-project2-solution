package sk.stuba.fei.uim.vsa.pr2.service.converter;

import lombok.Getter;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Student;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Teacher;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Thesis;

import java.util.HashMap;
import java.util.Map;

public class EntityToDtoConverterFactory {

    private static EntityToDtoConverterFactory instance;

    @Getter
    private final Map<Class<?>, EntityToDtoConverter<?>> converterClasses;

    public static EntityToDtoConverterFactory getInstance() {
        if (instance == null) {
            instance = new EntityToDtoConverterFactory();
        }
        return instance;
    }

    private EntityToDtoConverterFactory() {
        converterClasses = new HashMap<>();
        converterClasses.put(Student.class, new StudentToDtoConverter());
        converterClasses.put(Teacher.class, new TeacherToDtoConverter());
        converterClasses.put(Thesis.class, new ThesisToDtoConverter());
    }

    public static <T> EntityToDtoConverter<T> forEntity(Class<T> entityClass) {
        return (EntityToDtoConverter<T>) EntityToDtoConverterFactory.getInstance().getConverterClasses().get(entityClass);
    }


}
