package com.vsniu.jupiter.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wangfeng7
 * @Date: 2020/3/10 17:33
 * @Description:
 */
@Slf4j
@Service
public class DistributedLock implements InitializingBean {

    private static final String ROOT_PATH_LOCK = "distributed-lock";

    @Autowired
    private CuratorFramework curatorFramework;

    public void addWatcherForPath(String path){
        try {
            curatorFramework.getData().usingWatcher((CuratorWatcher) (WatchedEvent event)->{
                System.out.println(event.getPath()+","+event.getType().toString());
            }).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        curatorFramework = curatorFramework.usingNamespace("lock-namespace");
        String path = "/" + ROOT_PATH_LOCK;
        if(curatorFramework.checkExists().forPath(path)==null){
            curatorFramework.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(path);
        }
        addWatcherForPath(path);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("003");
        list.add("001");
        list.add("005");
        Collections.sort(list,(one,two)->{
            return two.compareTo(one);
        });
        System.out.println(list);
        System.out.println("000".compareTo("001"));
    }
}
