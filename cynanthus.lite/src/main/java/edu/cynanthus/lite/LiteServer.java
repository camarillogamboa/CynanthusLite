package edu.cynanthus.lite;

import edu.cynanthus.bean.BeanValidation;
import edu.cynanthus.bean.FullyValidate;
import edu.cynanthus.common.net.http.HttpException;
import edu.cynanthus.common.net.http.HttpStatusCode;
import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.domain.LiteSample;
import edu.cynanthus.domain.config.LiteConfig;
import edu.cynanthus.microservice.Context;
import edu.cynanthus.microservice.nanoservice.CynanthusServer;
import edu.cynanthus.microservice.net.http.server.engine.RequestHandler;
import edu.cynanthus.microservice.net.http.server.engine.ServerPath;
import edu.cynanthus.microservice.property.ReadOnlyProperty;

import java.util.Collection;

@ServerPath(path = "/cynanthus/lite")
public class LiteServer extends CynanthusServer<LiteConfig> {

    private final Collection<LiteSample> sampleBuffer;
    private final ReadOnlyProperty<String> nodeMac;

    public LiteServer(String id, Context context, Collection<LiteSample> sampleBuffer) {
        super(id, context);
        this.sampleBuffer = sampleBuffer;

        this.nodeMac = getProperty("nodeMac");

        getHttpSecurityManager().setEnabled(false);
    }

    @RequestHandler(context = "/sample", method = RequestMethod.POST)
    public String updateTemp(LiteSample sample) throws HttpException {
        if (sample != null) {
            BeanValidation.validateAndThrow(sample, FullyValidate.class);

            if (this.nodeMac != null) {
                String mac = nodeMac.getValue();
                if (mac != null && !mac.equals(sample.getMac()))
                    throw new HttpException(HttpStatusCode.FORBIDDEN);
            }

            synchronized (sampleBuffer) {
                sampleBuffer.add(sample);
            }

        } else throw new HttpException(HttpStatusCode.BAD_REQUEST);
        return "ok";
    }

}
