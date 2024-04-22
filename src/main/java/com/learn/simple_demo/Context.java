package com.learn.simple_demo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static com.learn.simple_demo.MysqlFactory.chainedTransactionManager;

public class Context {

    public static ThreadLocal<String> tenantId = new ThreadLocal<>();


    public static ThreadLocal<TransactionStatus> transactionStatus = new ThreadLocal<>();

    private static ThreadLocal<Integer> transactionDepth = ThreadLocal.withInitial(() -> 0);

    public static void setTenantId(String id) {
        tenantId.set(id);
    }

    public static void startTransactionManager() {
        if (transactionStatus.get() == null) {
            transactionStatus.set(chainedTransactionManager().getTransaction(new DefaultTransactionDefinition()));
            transactionDepth.set(1);  // 初始化事务深度
            System.out.println("Transaction started: " + transactionStatus.get());
        } else {
            transactionDepth.set(transactionDepth.get() + 1);  // 增加事务深度
        }
    }

    public static void commitTransactionManager() {
        int depth = transactionDepth.get() - 1;
        transactionDepth.set(depth);
        if (depth == 0 && transactionStatus.get() != null) {
            TransactionStatus status = transactionStatus.get();
            if (!status.isCompleted()) {
                chainedTransactionManager().commit(status);
                System.out.println("Transaction committed.");
                transactionStatus.remove();  // 清理事务状态
            }
        }
    }

    public static void rollbackTransactionManager() {
        TransactionStatus status = transactionStatus.get();
        if (status != null && !status.isCompleted()) {
            chainedTransactionManager().rollback(status);
            System.out.println("Transaction rolled back.");
        }
        transactionStatus.remove();  // 清理事务状态
        transactionDepth.remove();  // 清理事务深度
    }

    public static String getTenantId() {
        return tenantId.get();
    }

    public static void clear() {
        tenantId.remove();
    }


}
