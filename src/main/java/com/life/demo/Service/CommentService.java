package com.life.demo.Service;

import com.life.demo.model.CommentModel;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public void insert(CommentModel commentModel) {
        if (commentModel.getParentId() == null) {
        }
    }
}
