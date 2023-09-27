package com.kaka.mapper;

import com.kaka.model.Role;
import com.kaka.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper // 标记这是一个MyBatis的Mapper接口，Spring将会扫描到这个接口并创建它的实现类
public interface UserMapper {

    /**
     * 根据用户名查询用户是否存在
     *
     * @param username 要查询的用户名
     * @return 如果存在具有指定用户名的用户，则返回对应的 User 对象；否则返回 null。
     */
    User userNameIsExist(String username);

    /**
     * 根据手机号码查询用户是否存在
     *
     * @param phone 要查询的手机号码
     * @return 如果存在具有指定手机号码的用户，则返回对应的 User 对象；否则返回 null。
     */
    User phoneIsExist(String phone);

    /**
     * 插入用户数据到数据库中
     *
     * @param user 包含用户信息的 User 对象
     */
    void insertUser(User user);

    /**
     * 更新用户最近登录时间
     *
     * @param phone         用户手机号码
     * @param recentlyLanded 最近登录时间
     */
    void updatRecentlyLanded(@Param(value = "phone") String phone,
                             @Param(value = "recentlyLanded") String recentlyLanded);

    /**
     * 根据手机号码查询用户的角色列表
     *
     * @param phone 用户手机号码
     * @return 包含用户角色信息的 Role 对象列表
     */
    List<Role> queryRolesByPhone(@Param("phone") String phone);


    /**
     * 根据手机号码找到对应的用户
     *
     * @param phone 要查询的手机号码
     * @return 如果找到了与手机号码匹配的用户，则返回对应的 User 对象；否则返回 null。
     */
    User findUserByPhone(@Param(value = "phone") String phone);

    /**
     * 更新用户的角色信息
     *
     * @param userId 用户的ID
     * @param roleId 新的角色ID
     */
    static void updateRoleByUserId(@Param(value = "userId") int userId,
                                   @Param(value = "roleId") int roleId) {
        // 这个方法是静态的，并且是空的。你可能需要实现它的逻辑或者移除它。
    }

}
