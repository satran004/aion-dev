package org.aion4j.core.api;

import java.math.BigInteger;
import org.aion.vm.ExecutionContext;
import org.aion.vm.ExecutionResult;

public interface VMContext {

  public void init();

  public String deployContract(String address, byte[] code, Object... args);

  public void addBalance(String address, BigInteger balance);

  public ExecutionResult execute(ExecutionContext context, byte[] code);
}
