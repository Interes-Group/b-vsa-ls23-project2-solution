package sk.stuba.fei.uim.vsa.pr2;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.stuba.fei.uim.vsa.pr2.web.BonusSearchWebService;
import sk.stuba.fei.uim.vsa.pr2.web.SearchWebService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.Set;

import static org.reflections.scanners.Scanners.TypesAnnotated;

@Slf4j
@ApplicationPath("api")
public class JAXRSApplicationConfiguration extends Application {

    public static final Boolean BONUS = Boolean.parseBoolean(System.getenv("BONUS_ENABLED"));

    private Set<Class<?>> classes;

    public JAXRSApplicationConfiguration() {
        classes = new HashSet<>();
        Reflections reflections = new Reflections("sk.stuba.fei.uim.vsa.pr2");
        classes = reflections.get(TypesAnnotated.with(Path.class).asClass());
        log.info("Registered resource classes: " + classes.toString());
        Set<Class<?>> providers = reflections.get(TypesAnnotated.with(Provider.class).asClass());
        log.info("Registered feature providers: " + providers.toString());
        classes.addAll(providers);

        if (BONUS) {
            classes.remove(SearchWebService.class);
            log.info("Bonus implementation of search endpoints was enabled. Search endpoint implementation: " + BonusSearchWebService.class.getSimpleName());
        } else {
            classes.remove(BonusSearchWebService.class);
            log.info("Bonus implementation of search endpoints was disabled. Search endpoint implementation: " + SearchWebService.class.getSimpleName());
        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
