package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {
    //사용자 파일 이름
    private String uploadFileName;
    //시스템에서 내부적으로 저장 하는 이름
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

}
