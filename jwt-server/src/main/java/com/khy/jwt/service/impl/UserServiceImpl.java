package com.khy.jwt.service.impl;

import com.khy.jwt.entity.JwtUser;
import com.khy.jwt.entity.Role;
import com.khy.jwt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /*模拟数据库操作*/
        if("10086".equals(s)){
            JwtUser user = new JwtUser();
            user.setUsername("10086");
            user.setPassword("$2a$10$p3tvTg03hcETVqK4O1W71OC/1mxY8HM.cblWmazRqiyEfRZRuMEzi");
            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setId(1L);
            role.setName("ADMIN");
            roles.add(role);

            Role role1 = new Role();
            role1.setId(1L);
            role1.setName("ADMIN1");
            roles.add(role1);

            user.setRoles(roles);
            return user;
        }else{
            JwtUser user = new JwtUser();
            user.setUsername("10087");
            user.setPassword("$2a$10$p3tvTg03hcETVqK4O1W71OC/1mxY8HM.cblWmazRqiyEfRZRuMEzi");
            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setId(1L);
            role.setName("ADMIN");
            roles.add(role);

            Role role1 = new Role();
            role1.setId(1L);
            role1.setName("ADMIN1");
            roles.add(role1);

            user.setRoles(roles);
            return user;
        }

        /*SysUser user = sysUserRepository.findByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;*/
    }

    /**
     * 该@Cacheable(cacheNames = "user")， 这个注释的意思是，当调用这个方法的时候，会从一个名叫 user 的缓存中查询，
     * 如果没有，则执行实际的方法（即查询数据库），并将执行的结果存入缓存中，否则返回缓存中的对象。
     * <p>
     * 当@Cacheable 没有指定key时，函数的返回值以函数参数为key,缓存在了名为test的缓存中。
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(cacheNames = "user") //指定方法的返回值是可以缓存的
    public JwtUser get(String id, String name) {
        // 记录数据产生的时间，用于测试对比
        long time = new Date().getTime();
        JwtUser user = new JwtUser();
        user.setUsername("10086");
        user.setPassword("$2a$10$p3tvTg03hcETVqK4O1W71OC/1mxY8HM.cblWmazRqiyEfRZRuMEzi");
        logger.info("Get value by id=" + id + ", The time is " + time);
        return user;
    }

    @Override
    @Cacheable(cacheNames = "my-redis-cache1") //指定方法的返回值是可以缓存的
    public JwtUser get() {
        // 记录数据产生的时间，用于测试对比
        long time = new Date().getTime();
        JwtUser user = new JwtUser();
        user.setUsername("10086");
        user.setPassword("$2a$10$p3tvTg03hcETVqK4O1W71OC/1mxY8HM.cblWmazRqiyEfRZRuMEzi");
        logger.info("this cacheName is my-redis-cache1 , The time is " + time);
        return user;
    }

    /**
     * cacheNames指定了删除哪个cache中的数据，默认会使用方法的参数作为删除的key
     *
     * @param id
     * @return
     */
    @Override
    @CacheEvict(value = "user", key = "#id")// 清空 accountCache 缓存
    public String delete(String id) {
        logger.info("delete success " + id);
        return "delete success";
    }

    @Override
    public String save(String id, String value) {
        logger.info("save value " + value + " with key " + id);
        return "save success";
    }

    /**
     * 此处@CachePut注解，这个注解的作用是将方法的返回值按照给定的key,写入到cacheNames指定的cache中去.
     * <p>
     * 注意:@Cacheable和@Cacheput都会将方法的执行结果按指定的key放到缓存中，@Cacheable在执行时，会先检测缓存中是否有数
     * 据存在，如果有，直接从缓存中读取。如果没有，执行方法，将返回值放入缓存，而@Cacheput会先执行方法，然后再将执行结
     * 果写入缓存。一定会执行使用@Cacheput的方法
     *
     * @param id
     * @param value
     * @return
     */
    @Override
    @CachePut(cacheNames = "user", key = "#id") //将方法的返回值缓存到指定的key中
    public String update(String id, String value) {
        logger.info("update success with key " + id);
        return "update success";
    }
}
