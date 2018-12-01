package org.satran.aion.dev.test

import org.aion.base.util.Hex
import org.aion.mcf.vm.types.DataWord
import org.aion.vm.ExecutionContext
import org.aion.vm.ExecutionResult
import org.satran.aion.dev.DummyRepository
import org.satran.aion.dev.ExecutionContextBuilder
import org.satran.aion.dev.FastVMExecutor
import spock.lang.Specification

class FastVMTest extends Specification {

    def "execute contract"() {
        when:
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

        then:
            result != null

    }
}
