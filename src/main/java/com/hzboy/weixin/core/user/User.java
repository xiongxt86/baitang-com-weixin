package com.hzboy.weixin.core.user;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.ConvertGroup;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hzboy.weixin.core.dept.Dept;
import com.hzboy.weixin.support.First;
import com.hzboy.weixin.support.Second;
@GroupSequence({First.class, Second.class, User.class})  
/* First错误立即返回而不会验证Second分组*/
public class User {
    private int id;
    @NotEmpty(message = "{user.name.null}")  
    @Length(min = 5, max = 20, message = "{user.name.length.illegal}", groups = {First.class})  
    @Pattern(regexp = "[a-zA-Z]{5,20}", message = "{user.name.illegal}", groups = {Second.class})   
    private String name;
     
    private String username;
     
    private String content;
    //1、@Valid级联验证只要在相应的字段
    //2 @ConvertGroup的作用是当验证dept的分组是First时，那么验证dept的分组是Second，即分组验证的转换
    @Valid  
    @ConvertGroup(from=First.class, to=Second.class)  
    private Dept dept;
    
    
    @NotNull(message="{id.empty}",groups={First.class})
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
     
    @NotNull(message="{username.empty}",groups = {First.class, Second.class})
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @NotNull(message="{content.empty}",groups = {First.class, Second.class})
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
