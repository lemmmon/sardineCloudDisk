package com.sardine.controller;

import com.sardine.bean.Response;
import com.sardine.common.CustomException;
import com.sardine.service.RepoDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v0")
public class UploadController {
    @Autowired
    private RepoDispatchService repoDispatchService;

    @RequestMapping(value = "/upload_file", method = RequestMethod.POST)
    public Response upload(@RequestParam("file") MultipartFile multipartFile) throws CustomException {
        repoDispatchService.uploadDispatch(multipartFile);
        return Response.success();
    }

}
