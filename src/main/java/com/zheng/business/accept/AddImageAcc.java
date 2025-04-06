package com.zheng.business.accept;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * author:
 * Date:2022/3/1514:07
 **/
public class AddImageAcc {
    private List<MultipartFile> files;
    @NotNull
    private Long id;

    public AddImageAcc(List<MultipartFile> files, @NotNull Long id) {
        this.files = files;
        this.id = id;
    }

    public AddImageAcc() {
    }

    public List<MultipartFile> getFile() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
