package kernel.controllers;

import java.util.concurrent.Executor;

/**
 * Defines a runner for asynchronous tasks that the kernel is expected to
 * perform
 */
public interface TaskRunner extends Executor {
}
