package com.life.demo.dto;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO<T> {
    private List<T> data;
    private boolean toPre;
    private boolean toNext;
    private boolean toFirstPage;
    private boolean toEndPage;
    private Integer nowPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPageLogic(Integer totalCount, Integer nowPage, Integer size) {
        this.nowPage = nowPage;

            if (totalCount % size == 0 && totalCount != 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }
            if (totalCount == 0) {
                toEndPage = false;
                toNext = false;
            }

            pages.add(nowPage);
            for (int i = 1; i <= 3; i++) {
                if (nowPage - i > 0) {
                    pages.add(0, nowPage - i);
                }

                if (nowPage + i <= totalPage) {
                    pages.add(nowPage + i);
                }
            }

            if (nowPage == 1) {
                toPre = false;
            } else {
                toPre = true;
            }
            if (nowPage == totalPage) {
                toNext = false;
            } else {
                toNext = true;
            }

            if (pages.contains(1)) {
                toFirstPage = false;
            } else {
                toFirstPage = true;
            }
            if (pages.contains(totalPage)) {
                toEndPage = false;
            } else {
                toEndPage = true;
            }
        }
    }
