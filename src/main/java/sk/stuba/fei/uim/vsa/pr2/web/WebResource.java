package sk.stuba.fei.uim.vsa.pr2.web;

import sk.stuba.fei.uim.vsa.pr2.BCryptService;
import sk.stuba.fei.uim.vsa.pr2.service.ThesisService;
import sk.stuba.fei.uim.vsa.pr2.service.converter.EntityToDtoConverter;
import sk.stuba.fei.uim.vsa.pr2.service.converter.EntityToDtoConverterFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Base64;

public abstract class WebResource<T> {

    protected ThesisService service = ThesisService.getInstance();
    protected EntityToDtoConverter<T> converter = EntityToDtoConverterFactory.forEntity(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));

    protected String hashPassword(String password) {
        byte[] bytes = null;
        try {
            bytes = Base64.getDecoder().decode(password);
        } catch (Exception e) {
            bytes = password.getBytes();
        }
        String pass = new String(bytes);
        return BCryptService.hash(pass);
    }


}
