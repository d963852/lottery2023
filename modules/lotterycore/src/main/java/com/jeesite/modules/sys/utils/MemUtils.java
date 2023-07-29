package com.jeesite.modules.sys.utils;

import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.MemberService;

/**
 * 会员工具类
 * @author ThinkGem
 * @version 2020-9-19
 */
public class MemUtils {

    /**
     * 静态内部类，延迟加载，懒汉式，线程安全的单例模式
     */
    private static final class Static {
        private static MemberService memberService = SpringUtils.getBean(MemberService.class);
    }

    /**
     * 根据会员编码获取员工
     * @author ThinkGem
     */
    public static Member get(String memId){
        return Static.memberService.get(memId);
    }

    /**
     * 根据用户对象获取会员，不是会员返回null
     * @author ThinkGem
     */
    public static Member get(User user){
        if (user != null && User.USER_TYPE_MEMBER.equals(user.getUserType())){
            return user.getRefObj();
        }
        return null;
    }

    /**
     * 根据用户编码获取会员，找不到或不是会员返回null
     * @author ThinkGem
     */
    public static Member getByUserCode(String userCode){
        User user = UserUtils.get(userCode);
        Member entity = get(user);
        return entity;
    }

    /**
     * 根据登录账号获取员工，找不到或不是员工返回null
     * @author ThinkGem
     */
    public static Member getByLoginCode(String loginCode){
        User user = UserUtils.getByLoginCode(loginCode);
        Member entity = get(user);
        return entity;
    }

    /**
     * 获取当前登录的员工
     * @author ThinkGem
     */
    public static Member getMember(){
        User user = UserUtils.getUser();
        Member entity = get(user);
        if (entity == null){
            entity = new Member();
        }
        return entity;
    }

    /**
     * 获取当前登录的员工
     * @author ThinkGem
     */
    public static Double getBalance(){
        User user = UserUtils.getUser();
        Member entity = Static.memberService.get(user.getRefCode());
        if (entity == null){
            return 0.0d;
        }
        return entity.getBalance();
    }

}
