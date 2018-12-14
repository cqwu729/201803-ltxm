package com.snow.ly.blog.common.util;

import java.util.List;
import java.util.stream.Collectors;

public class PagingUtils<T> {



    public List<T> page(List<T> list,Integer page,Integer pageSize){
        return list.stream().skip(page*pageSize).limit(pageSize).collect(Collectors.toList());

    }







}
