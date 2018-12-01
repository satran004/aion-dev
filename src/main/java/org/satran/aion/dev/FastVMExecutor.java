package org.satran.aion.dev;

import org.aion.base.db.IRepositoryCache;
import org.aion.fastvm.FastVM;
import org.aion.vm.ExecutionContext;
import org.aion.vm.ExecutionResult;

public class FastVMExecutor {

    IRepositoryCache repo;

    public FastVMExecutor(IRepositoryCache repo) {
        this.repo = repo;
    }

    public ExecutionResult run(ExecutionContext ctx, byte[] code) {
        FastVM vm = new FastVM();

       // byte[] code = Hex.decode("6FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF60020160E052601060E0F3");
        ExecutionResult result = vm.run(code, ctx, repo);

        return result;
    }

    public void setRepository(IRepositoryCache repo) {
        this.repo = repo;
    }

}
