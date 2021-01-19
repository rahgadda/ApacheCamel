package org.affinity.com;

import org.apache.camel.main.Main;

public class MainApp {
    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new RestConfig());
        main.configure().addRoutesBuilder(new EquifaxHost());
        main.configure().addRoutesBuilder(new EquifaxClient());
        main.run(args);
    }

}

