package sk.stuba.fei.uim.vsa.pr2;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class ExceptionLogger implements ApplicationEventListener, RequestEventListener {

    @Override
    public void onEvent(final ApplicationEvent applicationEvent) {
    }

    @Override
    public RequestEventListener onRequest(final RequestEvent requestEvent) {
        return this;
    }

    @Override
    public void onEvent(RequestEvent paramRequestEvent) {
        if (paramRequestEvent.getType() == RequestEvent.Type.ON_EXCEPTION) {
            log.error(paramRequestEvent.getContainerResponse().getEntity().toString(), paramRequestEvent.getException());
        }
    }
}
