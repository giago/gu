package novoda.lib.httpservice.service.executor;

import java.util.concurrent.Callable;

import novoda.lib.httpservice.provider.IntentWrapper;

public interface CallableExecutor<T> {

    Callable<T> getCallable(IntentWrapper request);
    
}
