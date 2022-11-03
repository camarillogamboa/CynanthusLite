package edu.cynanthus.lite;

import edu.cynanthus.common.security.AgentUser;
import edu.cynanthus.domain.LiteSample;
import edu.cynanthus.domain.config.LiteConfig;
import edu.cynanthus.microservice.Context;
import edu.cynanthus.microservice.MicroService;
import edu.cynanthus.microservice.nanoservice.NanoService;
import edu.cynanthus.microservice.nanoservice.NanoServices;

import java.util.LinkedList;

public final class LiteMicroService extends MicroService {


    public LiteMicroService(String[] args) {
        super("Cynanthus Lite MicroService", LiteConfig.class, AgentUser.DEFAUL_AGENT_USER, args);
    }

    @Override
    public NanoService loadNanoService(Context context) {
        LinkedList<LiteSample> sampleBuffer = new LinkedList<>();
        return new NanoServices(
            "liteNanoServices",
            new NotificationSender("notificationSender", context, sampleBuffer),
            new LiteServer("webServer", context, sampleBuffer)
        );
    }

}
