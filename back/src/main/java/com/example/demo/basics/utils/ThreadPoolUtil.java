package com.example.demo.basics.utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Tag(name = "线程池配置实体类")
public class ThreadPoolUtil {

    @Schema(name = "线程缓冲队列")
    private static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);

    @Schema(name = "核心线程数")
    private static final int SIZE_CORE_POOL = 5;

    @Schema(name = "最大线程数量")
    private static final int SIZE_MAX_POOL = 10;

    @Schema(name = "空闲线程存活时间")
    private static final long ALIVE_TIME = 2000;

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME, TimeUnit.MILLISECONDS, queue, new ThreadPoolExecutor.CallerRunsPolicy());

    static {
        pool.prestartAllCoreThreads();
    }

    @Operation(summary = "获取可用线程")
    public static ThreadPoolExecutor getPool(){
        return pool;
    }
}
