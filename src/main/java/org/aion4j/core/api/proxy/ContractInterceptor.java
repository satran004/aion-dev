package org.aion4j.core.api.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.Pipe;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import org.aion.base.util.Hex;
import org.aion.mcf.vm.types.DataWord;
import org.aion.vm.ExecutionContext;
import org.aion.vm.ExecutionResult;
import org.aion4j.core.api.BaseContract;
import org.aion4j.core.api.ContractContext;
import org.aion4j.core.exception.A4jProcessingException;
import org.aion4j.core.vm.DummyRepository;
import org.aion4j.core.vm.ExecutionContextBuilder;
import org.aion4j.core.vm.fastvm.FastVMExecutor;

public class ContractInterceptor {

    @RuntimeType
    public static Object intercept(@Origin Method method,@This BaseContract baseContract, @AllArguments Object... args) {

        if("setContext".equals(method.getName())) {
            Field field = null;
            try {
                field = baseContract.getClass().getDeclaredField("context");
                field.setAccessible(true);
                field.set(baseContract, args[0]);
            } catch (Exception e) {
                e.printStackTrace();
                throw new A4jProcessingException("error in initialization", e);
            }

            return null;
        }

        if("getContext".equals(method.getName())) {
            Field field = null;
            try {
                field = baseContract.getClass().getDeclaredField("context");
                field.setAccessible(true);
                return field.get(baseContract);
            } catch (Exception e) {
                e.printStackTrace();
                throw new A4jProcessingException("error in initialization", e);
            }
        }

        //Other contract methods.
        return baseContract.getContext().execute(method, args);

    }


}
