package zorange.online.blogserver.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import zorange.online.blogserver.common.Result;
import zorange.online.blogserver.entity.User;
import zorange.online.blogserver.service.IUserService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zorange
 * @since 2024-02-16
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;


    @GetMapping("/nickname")
    public Result findByNickname(@RequestParam String nickname) {
        return Result.success(userService.findByNickname(nickname));
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return Result.success(userService.login(user));
    }

    @PostMapping
    public Result save(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 查看昵称是否存在
        wrapper.eq("nickname", user.getNickname());
        User one = userService.getOne(wrapper);
        // 如果用户id为空,则为新增
        if (user.getUserId() == null) {
            if (one != null) {
                return Result.error("用户名已存在");
            }
            // 保存用户
            return Result.success(userService.save(user));
            // 如果用户id不为空,则为修改
        } else {
            if (one != null && !one.getUserId().equals(user.getUserId())) {
                return Result.error("用户名已存在");
            }
            // 更新用户
            return Result.success(userService.updateById(user));
        }
    }

    /**
     * 根据id删除
     *
     * @param id 主键id
     * @return boolean
     */
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    /**
     * 查询所有
     *
     * @return List<User>
     */
    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    /**
     * 根据id查询
     *
     * @param id 主键id
     * @return User
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页显示的条数
     * @return Page<User>
     */
    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        return userService.page(new Page<>(pageNum, pageSize), wrapper);
    }

    /**
     * 批量删除
     *
     * @param ids 主键id集合
     * @return boolean
     */
    @DeleteMapping("del/batch")
    public boolean deleteBatchById(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);
    }

    @GetMapping("/username")
    public Result findByUsername(@RequestParam String username) {
        return Result.success(userService.findByUsername(username));
    }


    public void updatePassword(String password, String userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        User one = userService.getOne(wrapper);
        one.setPassword(password);
        userService.updateById(one);
    }

    //根据id查询作者信息
    @GetMapping("/author/{id}")
    public Result findAuthorById(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    /*
    检测token是否有效
     */
    @GetMapping("/checkToken")
    public Result checkToken(@RequestParam String token){
        return Result.success(userService.checkToken(token));
    }
}

