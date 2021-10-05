package io.reddist.config;

import io.reddist.services.JobApplyService;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;

//@Configuration
//@EnableTransactionManagement
class TransactionManagerConfig {

//    @Bean(initMethod = "init", destroyMethod = "close")
//    public AtomikosDataSourceBean fullDataSource() {
//        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
////        dataSource.afterPropertiesSet();
//        // Configure database holding order data
//        return dataSource;
//    }
//
//    /*@Bean(initMethod = "init", destroyMethod = "close")
//    public UserTransactionManager userTransactionManager() throws SystemException {
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        userTransactionManager.setTransactionTimeout(300);
//        userTransactionManager.setForceShutdown(true);
//        return userTransactionManager;
//    }*/
//
//    @Bean
//    public JtaTransactionManager jtaTransactionManager() throws SystemException {
//        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
//        jtaTransactionManager.setTransactionManager(userTransactionManager());
//        jtaTransactionManager.setUserTransaction(userTransactionManager());
//        return jtaTransactionManager;
//    }
//
//    @Bean
//    public JobApplyService createJobApplyService() {
//        return new JobApplyService();
//    }
}