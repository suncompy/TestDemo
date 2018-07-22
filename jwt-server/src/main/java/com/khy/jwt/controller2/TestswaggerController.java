package com.khy.jwt.controller2;

import com.khy.jwt.entity.JwtUser;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "TestswaggerController",description = "iTestswaggerController测试接口")
public class TestswaggerController {

    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserById(@PathVariable(value = "id") Integer id) {
        String r = "{\"name\":\"name0\",\"age\":0}";
        return ResponseEntity.ok(r);
    }

    /**
     * 查询用户列表
     *
     * @return
     */
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity getUserList() {
        String r = "[{\"name\":\"name0\",\"age\":0}][{\"name\":\"name1\",\"age\":5}][{\"name\":\"name2\",\"age\":10}]";
        return ResponseEntity.ok(r);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "JwtUser")
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody JwtUser user) {
        return ResponseEntity.ok("success");
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {

        return ResponseEntity.ok("success");
    }

    /**
     * 根据id修改用户信息
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "更新信息", notes = "根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "JwtUser")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 405,message = "Invalid input integer",response = Integer.class),
            @ApiResponse(code = 404,message = "Invalid input JwtUser",response = JwtUser.class)
    })
    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody JwtUser user) {
        return ResponseEntity.ok("success");
    }

    @ApiIgnore//使用该注解忽略这个API
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String jsonTest() {
        return " hi you!";
    }

}
