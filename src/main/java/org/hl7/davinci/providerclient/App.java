package org.hl7.davinci.providerclient;

import org.apache.meecrowave.Meecrowave;

import ca.uhn.fhir.context.FhirContext;

/**
 * The client for the Provider to use Prior Authorization is launched using this
 * app
 */
public class App {

  /**
   * HAPI FHIR Context. HAPI FHIR warns that the context creation is expensive,
   * and should be performed per-application, not per-record.
   */
  public static final FhirContext FHIR_CTX = FhirContext.forR4();

  public static boolean debugMode = false;

  /**
   * Launch the Provider Client microservice.
   * 
   * @param args - ignored.
   */
  public static void main(String[] args) {
    if (args.length > 0) {
      if (args[0].equalsIgnoreCase("debug")) {
        debugMode = true;
      }
    }

    if (System.getenv("debug") != null) {
      if (System.getenv("debug").equalsIgnoreCase("true")) {
        debugMode = true;
      }
    }

    // Assemble the microservice
    Meecrowave.Builder builder = new Meecrowave.Builder();
    builder.setHttpPort(9090);
    builder.setScanningPackageIncludes("org.hl7.davinci.providerclient");
    builder.setJaxrsMapping("/fhir/*");
    builder.setJsonpPrettify(true);

    // Launch the microservice
    try (Meecrowave meecrowave = new Meecrowave(builder)) {
      meecrowave.bake().await();
    }
  }
}
