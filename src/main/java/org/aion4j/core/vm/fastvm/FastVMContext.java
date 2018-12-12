package org.aion4j.core.vm.fastvm;

import java.math.BigInteger;
import org.aion.base.db.IRepository;
import org.aion.base.db.IRepositoryCache;
import org.aion.base.type.Address;
import org.aion.base.util.Hex;
import org.aion.mcf.vm.types.DataWord;
import org.aion.vm.ExecutionContext;
import org.aion.vm.ExecutionResult;
import org.aion4j.core.api.BaseContract;
import org.aion4j.core.api.VMContext;
import org.aion4j.core.vm.DummyRepository;
import org.aion4j.core.vm.ExecutionContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastVMContext implements VMContext {

  private final static Logger logger = LoggerFactory.getLogger(FastVMContext.class);

  private DummyRepository repository;
  private FastVMExecutor fastVMExecutor;

  public FastVMContext() {
    this.repository = new DummyRepository();
  }

  public FastVMContext(DummyRepository iRepository) {
    this.repository = iRepository;
  }

  @Override
  public void init() {
    fastVMExecutor = new FastVMExecutor(repository);
  }

  @Override
  public String deployContract(String address, byte[] code, Object... args) {
    repository.addContract(Address.wrap(address), code);

    logger.debug("Contract added to address {} ", address);
    return address;
  }

  @Override
  public void addBalance(String address, BigInteger balance) {
    repository.addBalance(Address.wrap(address), balance);
  }

  @Override
  public ExecutionResult execute(ExecutionContext context, byte[] code) {
    if(logger.isDebugEnabled()) {
      logger.debug("Trying to execute function hash {}", Hex.encode(context.callData()));
    }

    ExecutionResult result = fastVMExecutor.run(context, code);

    return result;
  }
}
