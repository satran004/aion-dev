package org.aion4j.core.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Contract {

    String name();

    String abi();

    String source(); //only required for contract deployment support

  //  String address(); //Required for contract remote address
}
