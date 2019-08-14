package com.life.demo.dto;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO<T> {
    private List<T> data;
    //private List<QuestionDTO> questions;
    private boolean toPre;
    private boolean toFirstPage;
    private boolean toNext;
    private boolean toEndPage;
    private Integer nowPage;
    private List<Integer> nowPages = new ArrayList<>();
    private Integer allPage;

    public void setPageDTO(Integer allcount, Integer nowPage, Integer size) {

        this.nowPage = nowPage;
        this.allPage = allPage;

            if (allcount % size == 0 && allcount != 0) {
                allPage = allcount / size;
            } else {
                allPage = allcount / size + 1;
            }
            if (allcount == 0) {
                toEndPage = false;
                toNext = false;
            }

            nowPages.add(nowPage);
            for (int i = 1; i <= 3; i++) {
                if (nowPage - i > 0) {
                    nowPages.add(0, nowPage - i);
                }

                if (nowPage + i <= allPage) {
                    nowPages.add(nowPage + i);
                }
            }

            if (nowPage == 1) {
                toPre = false;
            } else {
                toPre = true;
            }
            if (nowPage == allPage) {
                toNext = false;
            } else {
                toNext = true;
            }
            //是否展示第一页
            if (nowPages.contains(1)) {
                toFirstPage = false;
            } else {
                toFirstPage = true;
            }
            if (nowPages.contains(allPage)) {
                toEndPage = false;
            } else {
                toEndPage = true;
            }
        }
    }
