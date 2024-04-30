package eus.ehu.sharetrip.ui;

import eus.ehu.sharetrip.configuration.Config;
import eus.ehu.sharetrip.domain.Driver;
import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.businessLogic.BlFacadeImplementation;

import java.util.Locale;

public class ApplicationLauncher {

  public static void main(String[] args) {

    Config config = Config.getInstance();

    Locale.setDefault(new Locale(config.getLocale()));
    System.out.println("Locale: " + Locale.getDefault());

    BlFacade businessLogic;

    try {

      if (config.isBusinessLogicLocal()) {
        businessLogic = new BlFacadeImplementation();

        new MainGUI(businessLogic);
      }
    }
    catch (Exception e) {
      System.err.println("Error in ApplicationLauncher: " + e);
    }

  }


}
