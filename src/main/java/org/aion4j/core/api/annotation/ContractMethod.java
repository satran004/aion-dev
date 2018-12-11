package org.aion4j.core.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ContractMethod {

    //abi method signature without modifier and return type. Example:  get(uint8,bool)
    String name();

    String[] params();

}
