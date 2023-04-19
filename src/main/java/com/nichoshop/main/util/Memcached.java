// package com.nichoshop.main.util;

// import java.io.IOException;
// import java.net.InetSocketAddress;
// import java.util.concurrent.Future;
// import java.util.concurrent.TimeUnit;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import net.spy.memcached.MemcachedClient;

// @Component
// public class Memcached {

// @Autowired
// protected MemcachedClient memcachedClient;

// public Memcached() {
// // try {
// // this.memcachedClient = new MemcachedClient(new
// InetSocketAddress("localhost",
// // 11211));
// // } catch (IOException e) {
// // // TODO Auto-generated catch block
// // e.printStackTrace();
// // }
// }

// public Object get(String key) {
// Future<Object> future = memcachedClient.asyncGet(key);
// try {
// return future.get(3, TimeUnit.SECONDS);
// } catch (Exception e) {
// future.cancel(false);
// }
// return null;
// }

// public boolean set(String key, Object value) {
// Future<Boolean> future = memcachedClient.add(key, Constants.cacheTimeout,
// value);
// try {
// return future.get(3, TimeUnit.SECONDS);
// } catch (Exception e) {
// future.cancel(false);
// }
// return false;
// }
// }
