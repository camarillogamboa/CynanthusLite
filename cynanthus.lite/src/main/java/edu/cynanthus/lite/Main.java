package edu.cynanthus.lite;

import edu.cynanthus.microservice.MicroService;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        MicroService microService = new LiteMicroService(args);
        microService.start();
    }

}
