package com.life.demo.Service;

import com.life.demo.mapper.UserModelMapper;
import com.life.demo.model.UserModel;
import com.life.demo.model.UserModelExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserModelMapper userMapper;

    public void createORupdate(UserModel userModel) {

        UserModelExample userModelExample = new UserModelExample();
        userModelExample.createCriteria().andAccountIdEqualTo(userModel.getAccountId());
        List<UserModel> userModelList = userMapper.selectByExample(userModelExample);

        //UserModel dbuser = userMapper.findByaccountid(userModel.getAccountId());
        if (userModelList.size() == 0) {
            userModel.setGmtCreate(System.currentTimeMillis());
            userModel.setGmtModified(userModel.getGmtCreate());
            userMapper.insert(userModel);
        } else {
            UserModel dbuser = userModelList.get(0);

            UserModel updateUserModel = new UserModel();
            updateUserModel.setToken(userModel.getToken());
            updateUserModel.setGmtCreate(System.currentTimeMillis());
            updateUserModel.setAvatarUrl(userModel.getAvatarUrl());
            updateUserModel.setName(userModel.getName());

            UserModelExample example = new UserModelExample();
            example.createCriteria().andIdEqualTo(dbuser.getId());

            userMapper.updateByExampleSelective(updateUserModel, example);
            // userMapper.update(dbuser);
        }
    }
}
