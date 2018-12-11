package org.aion4j.core.annotation;

import java.lang.reflect.Method;
import org.aion.solidity.Abi.Function;
import org.aion4j.core.abi.ContractFunction;

public class MethodWrapper {

    private Method method;
    private Function function;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Function getAbiFunction() {
        return function;
    }

    public void setAbiFunction(Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "MethodWrapper{" +
            "method=" + method +
            ", function=" + function +
            '}';
    }
}
