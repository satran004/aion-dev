package org.aion4j.core.api.proxy;

import java.lang.reflect.Modifier;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.aion4j.core.annotation.AnnotationHelper;
import org.aion4j.core.api.BaseContract;
import org.aion4j.core.api.ContractContext;
import org.aion4j.core.api.annotation.Contract;
import org.aion4j.core.api.annotation.ContractMethod;
import org.aion4j.core.exception.A4jAnnotationException;
import org.aion4j.core.exception.A4jProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContractFactory {

  private static final Logger logger = LoggerFactory.getLogger(ContractFactory.class);

  public static <T> T get(Class<T> tClass, boolean initialize) {

    try {
      Contract contract = AnnotationHelper.getContructorAnnotation(tClass);

      if (contract == null)
        throw new A4jAnnotationException(
            "Contract annotation is missing. Please add @Contract to this class first");

      T instance =
          new ByteBuddy()
              .subclass(tClass)
              .method(
                  ElementMatchers.declaresAnnotation(
                          ElementMatchers.annotationType(ContractMethod.class))
                      .or(ElementMatchers.named("setContext"))
                      .or(ElementMatchers.named("getContext")))
              .intercept(MethodDelegation.to(ContractInterceptor.class))
              .defineField("context", ContractContext.class, Modifier.PUBLIC)
              .make()
              .load(tClass.getClassLoader())
              .getLoaded()
              .newInstance();

      if (initialize) {
        BaseContract contractObj = ((BaseContract) instance);

        ContractContext contractContext = new ContractContext(tClass);
        contractContext.setAbiJson(contract.abi());
        contractContext.setSourceFile(contract.source());
        contractContext.setName(contract.name());

        //set VM context
        contractContext.initVMContext();
        contractObj.setContext(contractContext);
      }

      return instance;
    } catch (Exception e) {
      logger.error("Error initializing proxy class for the contract: " + tClass, e);
      throw new A4jProcessingException("error initializing proxy class : " + tClass, e);
    }
  }

  public static <T> T get(Class<T> tClass) {
    return get(tClass, true);
  }

}