package com.gaoap.opf.admin.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gaoap.opf.admin.entity.SysUser;
import com.gaoap.opf.admin.service.ISysUserService;
import com.gaoap.opf.common.core.exception.OpfException;
import com.gaoap.opf.common.core.http.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Api(tags = "用户信息管理")
@RestController
@RequestMapping("/admin/sysUser")
@Slf4j
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private ISysUserService sysUserService;


    @ApiOperation("获取全部用户")
    @GetMapping("userAll")
    public HttpResult selectAll() {
        return HttpResult.ok(sysUserService.list());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public HttpResult selectOne(@PathVariable Serializable id) {
        return HttpResult.ok(sysUserService.getById(id));
    }


    @ApiOperation("新增数据")
    @PostMapping("insert")
    public HttpResult insert(@RequestBody SysUser sysUser) {
        return HttpResult.ok(this.sysUserService.save(sysUser));
    }


    @ApiOperation("修改数据")
    @PutMapping("update")
    public HttpResult update(@RequestBody SysUser sysUser) {
        return HttpResult.ok(this.sysUserService.updateById(sysUser));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @DeleteMapping("delete")
    public HttpResult delete(@RequestParam("idList") List<Serializable> idList) {
        return HttpResult.ok(this.sysUserService.removeByIds(idList));
    }

    /**
     * 根据用户名查找用户
     * 普通资源，非http请求资源，可以用注解 @SentinelResource("helloJob")
     */
    @ApiOperation("根据用户名查找用户")
    @GetMapping("/findByUsername/{username}")
    @SentinelResource(value = "findByUsername", blockHandler = "blockGetPayment", fallback = "fallback")
    public HttpResult findByUsername(@PathVariable("username") String username) {
        log.info("进入用户查询功能：查询用户：" + username);
        if (username.equals("caocao")) {
            throw new OpfException("用户‘caocao(曹操)’触发一个异常错误，用于测试抛出异常触发熔断");
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("name", username); //name为 "Jone" 的用户
        return HttpResult.ok(this.sysUserService.getOne(wrapper));
    }

    /**
     * 注意：这里的@SentinelResource注解的blockHandler只处理sentinel控制台的错误，
     * 不能处理程序错误，程序错误会走fallback。
     * 坑：blockHandler指定的方法，参数里必须要有BlockException blockException
     */

    public HttpResult blockGetPayment(@PathVariable("username") String username, BlockException blockException) {
        log.info("服务降级，测试方便，返回liubei(刘备)信息，进入方法体：blockGetPayment");
        username = "liubei";
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();

        wrapper.eq("name", username); //name为 "Jone" 的用户
        return HttpResult.ok(this.sysUserService.getOne(wrapper));
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public HttpResult fallback(@PathVariable("username") String username) {
        log.info("用于测试抛出异常触发熔断,进入方法体：fallback");
        SysUser u = new SysUser();
        u.setId(0l);
        u.setName("曹操");
        u.setPassword("");
        return HttpResult.ok(u);
    }

    /**
     * 定义隔离规则,这是写死规则，不通过Dashboard来控制
     *
     * @PostConstruct 当前类的构造函数执行之后执行该方法
     */
    @PostConstruct
    public void initFlowRule() {
        List<FlowRule> ruleList = new ArrayList<>();
        FlowRule rule = new FlowRule();
        // 设置资源名称
        rule.setResource("findByUsername");
        // 指定限流模式为QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 指定QPS限流阈值
        rule.setCount(1);
        ruleList.add(rule);
        // 加载该规则
        FlowRuleManager.loadRules(ruleList);
    }

}

