package com.life.demo.Controller;

import com.life.demo.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {//http://editor.md.ipandao.com/examples/image-upload.html
    @ResponseBody
    @RequestMapping("/files/upload")
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/css/img/1.jpg");
        return fileDTO;
    }
}
