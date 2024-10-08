package com.alten.backend.util;

import com.alten.backend.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Utils {

    public static ResponseEntity<PageableResponse> buildPageableResponse(Map<String,Object> map , String calledPath, Integer currentPage, Integer perPage ){

        PageableResponse response = new PageableResponse();

        if(map == null){
            response.timestamp(formatDateTime(new Date()));
            response.code(HttpStatus.OK.value());
            response.data(new ArrayList<>());
            return ResponseEntity.ok(response);
        }

        @SuppressWarnings("unchecked")
        List<CollectionsInner> data =  (List<CollectionsInner>) map.get("data");
        response.setData(data);
        response.timestamp(formatDateTime(new Date()));
        Double totalSize = (Double) map.get("nbResult");
        int resultSize = data.size();

        Links links = new Links();
        Meta meta= new Meta();
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        String url = baseUrl+calledPath ;
        url = url.contains("?") ? url+"&" : url+"?" ;
        links.first(url+"page=0&per-page="+perPage);
        links.current(url+"page="+currentPage+"&per-page="+perPage);

        double lastPage = Math.ceil(totalSize/perPage) -1;
        meta.setPages((int) lastPage+1);

        //If the total size number is greater than per-page number
        if(totalSize > perPage){

            //If the current page is greater then 1
            if(currentPage>0){
                links.prev(url+"page="+(currentPage-1)+"&per-page="+perPage);
                meta.setPreviousPage(currentPage-1);
            }
            //If the current page isn't the last page
            if(currentPage != (int) lastPage){
                links.next(url+"page="+(currentPage+1)+"&per-page="+perPage);
                meta.setNextPage(currentPage+1);
            }
            links.last(url+"page="+ (int) lastPage +"&per-page="+perPage);
            meta.setLastPage((int) lastPage);
        }

        meta.setCurrentPage(currentPage);
        meta.setFirstPage(0);
        meta.setPageSize(perPage);
        meta.totalSize(totalSize.intValue());
        meta.resultSize(resultSize);

        response.setLinks(links);
        response.setMeta(meta);

        //Partial result
        if(totalSize > perPage && currentPage != (int) lastPage){
            response.code(HttpStatus.PARTIAL_CONTENT.value());
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
        }
        //Full result
        response.code(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    public  static Response buildResponse(Object data , Integer code){
        Response response = new Response();
        response.code(code);
        response.timestamp(Utils.formatDateTime(new Date()));
        response.data(data);
        return response;
    }

    public static String formatDateTime(Date date){
        if (date == null){
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static void updateNonNullProperties(Object src, Object target) throws IllegalAccessException, NoSuchFieldException {
        Field[] fields = src.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(src);

            if (value != null && !"serialVersionUID".equals(field.getName()) ) {
                Field targetField;
                targetField = target.getClass().getDeclaredField(field.getName());
                targetField.setAccessible(true);
                targetField.set(target, value);
            }
        }
    }
}
