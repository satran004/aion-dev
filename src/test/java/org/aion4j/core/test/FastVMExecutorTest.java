package org.aion4j.core.test;

import org.aion.base.util.Hex;
import org.aion.mcf.vm.types.DataWord;
import org.aion.vm.ExecutionContext;
import org.aion.vm.ExecutionResult;
import org.aion4j.core.vm.DummyRepository;
import org.junit.Test;
import org.aion4j.core.vm.ExecutionContextBuilder;
import org.aion4j.core.vm.fastvm.FastVMExecutor;

public class FastVMExecutorTest {

    public FastVMExecutorTest() {
        System.out.println("Helool....................");
    }

    @Test
    public void contractTest() {
        ExecutionContextBuilder builder = new ExecutionContextBuilder();
        ExecutionContext ctx = builder.nrgPrice(DataWord.ONE)
            .nrgLimit(20000)
            .callValue(DataWord.ZERO)
            .callData(new byte[0])
            .build();

        FastVMExecutor executor = new FastVMExecutor(new DummyRepository());

        byte[] code = Hex.decode("6FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF60020160E052601060E0F3");

        ExecutionResult result = executor.run(ctx, code);

        System.out.println("Result: " + result.getResultCode());
        System.out.println(result.getOutput());

    }

}
