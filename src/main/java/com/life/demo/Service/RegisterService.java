package com.life.demo.Service;

import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
import com.life.demo.mapper.RegisterMapper;
import com.life.demo.model.Register;
import com.life.demo.model.RegisterExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {
    @Autowired
    private RegisterMapper registerMapper;

    public void CheckRegister(Register register){
        RegisterExample example = new RegisterExample();
        example.createCriteria().andNameEqualTo(register.getName()).andPasswordEqualTo(register.getPassword());
        List<Register> list = registerMapper.selectByExample(example);
        if(list.size() == 0)
            throw new CustomizeException(CustomizeErrorCode.USERNAME_OR_PASSWORD_ERROR);
    }

}
