package org.daily.bill.utils.profiling;

import org.daily.bill.utils.annotation.Profiling;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vano on 12.9.17.
 */
@Component
public class ProfilingHandlerBeanBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> annotatedBeans = new HashMap<>();
    private ProfilingController controller = new ProfilingController();

    public ProfilingHandlerBeanBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Class<?> beanClass = o.getClass();
        if(beanClass.isAnnotationPresent(Profiling.class)) {
            annotatedBeans.put(s, beanClass);
        }

        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Class beanClass = o.getClass();
        if(annotatedBeans.get(s) != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                if(controller.isEnabled()) {
                    long start = System.nanoTime();
                    System.out.println("Start Profiling...");
                    Object retVal = method.invoke(o, args);
                    long end = System.nanoTime();
                    System.out.println("End profiling: " + (end - start) + "ns");
                    return retVal;
                } else {
                    return method.invoke(o, args);
                }

            });
        }
        return o;
    }
}
