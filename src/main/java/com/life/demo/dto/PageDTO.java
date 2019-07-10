package com.life.demo.dto;
//import lombok.Data;
import java.util.ArrayList;
import java.util.List;
//@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    private  boolean  toPre;
    private  boolean  toFirstPage;
    private  boolean  toNext;
    private  boolean  toEndPage;
    private Integer nowPage;
    private List<Integer> nowPages = new ArrayList<>();
    private Integer allPage;//总共多少页

    public Integer getAllPage() {

        return allPage;
    }

    public void setAllPage(Integer allPage) {
        this.allPage = allPage;
    }

    public void  setPageDTO(Integer allcount, Integer nowPage, Integer size){

        this.nowPage = nowPage;
        this.allPage = allPage;


        if(allcount % size == 0){
            allPage = allcount / size;
        }
        else {
            allPage=allcount/size + 1;
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

        if(nowPage == 1){
            toPre = false;
        }else {
            toPre = true;
        }
        if (nowPage == allPage){
            toNext = false;
        }
        else {
            toNext = true;
        }
        //是否展示第一页
        if (nowPages.contains(1)){
            toFirstPage = false;
        }else {
            toFirstPage = true;
        }
        if (nowPages.contains(allPage)){
            toEndPage = false;
        }else {
            toEndPage = true;
        }

    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public boolean isToPre() {
        return toPre;
    }

    public void setToPre(boolean toPre) {
        this.toPre = toPre;
    }

    public boolean isToFirstPage() {
        return toFirstPage;
    }

    public void setToFirstPage(boolean toFirstPage) {
        this.toFirstPage = toFirstPage;
    }

    public boolean isToNext() {
        return toNext;
    }

    public void setToNext(boolean toNext) {
        this.toNext = toNext;
    }

    public boolean isToEndPage() {
        return toEndPage;
    }

    public void setToEndPage(boolean toEndPage) {
        this.toEndPage = toEndPage;
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public List<Integer> getNowPages() {
        return nowPages;
    }

    public void setNowPages(List<Integer> nowPages) {
        this.nowPages = nowPages;
    }
}
