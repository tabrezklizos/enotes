package com.tab.EnoteApp.util;

import com.tab.EnoteApp.config.security.CustomUserDetails;
import com.tab.EnoteApp.entity.User;
import com.tab.EnoteApp.handler.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtil {

    public static ResponseEntity<?> createResponse(Object data, HttpStatus responseStatus){

        GenericResponse response = GenericResponse.builder()
                .status("succes")
                .message("succes")
                .responseStatus(responseStatus)
                .data(data)
                .build();

        return  response.create();

    }

    public static ResponseEntity<?> createResponseMessage(String message, HttpStatus responseStatus){

        GenericResponse response = GenericResponse.builder()
                .status("succes")
                .message(message)
                .responseStatus(responseStatus)
                .build();

        return  response.create();

    }

    public static ResponseEntity<?> errorResponse(Object data, HttpStatus responseStatus){

        GenericResponse response = GenericResponse.builder()
                .status("failed")
                .message("failed")
                .data(data)
                .responseStatus(responseStatus)
                .build();

        return  response.create();

    }

    public static ResponseEntity<?> errorResponseMessage(String message, HttpStatus responseStatus){

        GenericResponse response = GenericResponse.builder()
                .status("failed")
                .message(message)
                .responseStatus(responseStatus)
                .build();

        return  response.create();

    }

    public static String getContentType(String originalFileName) {

        String extension = FilenameUtils.getExtension(originalFileName);

        switch(extension){
            case "pdf":
                return "application/pdf";
            case "png":
                return "image/png";
            case "jpeg":
                return "image/jpeg";
            case "jpg":
                return "image/jpg";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "txt":
                return "text/plan";
            default:
                return "application/octet-stream";

        }
    }

    public static String getUrl(HttpServletRequest request) {
        String apiUrl = request.getRequestURL().toString();
        String url=apiUrl.replace(request.getServletPath(),"");
        return url;

    }

    public static User getLogUser(){
        try {
            CustomUserDetails logUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            return logUser.getUser();
        } catch (Exception e) {
            throw e;
        }

    }
}
