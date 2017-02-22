package kernel.models;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Contains a task runner that uses a cached thread pool to implement its tasks
 */
public class TaskRunner implements kernel.controllers.TaskRunner{
    private final ExecutorService service = Executors.newCachedThreadPool();

    public TaskRunner(){}

    @Override
    public void execute(@NotNull Runnable command){
        service.execute(command);
    }

}
