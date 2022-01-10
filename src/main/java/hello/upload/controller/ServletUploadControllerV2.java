package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String savaFileV2(HttpServletRequest request) throws ServletException, IOException
    {
        log.info("request=-{}",request);
        String itemName = request.getParameter("itemName");
        log.info("itemName={}",itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts={}",parts);
        //헤더에 있는 데이터 가져오기
        for (Part part : parts) {
            log.info("====PART====");
            log.info("name={}", part.getName());
            Collection<String> headerNames = part.getHeaderNames();
            for(String headerName : headerNames)
            {
                log.info("header {} : {}", headerName, part.getHeader(headerName));
            }

            //편의 메소드 (사이즈 가져오기 및 파일 이름 가져오기)
            log.info("submittedFileName={}",part.getSubmittedFileName());
            log.info("size={}", part.getSize());

            //body에 있는 데이터 가져오기
            //바이너리 파일을 문자로 가져와서 log로 보여주는 코드이다.
            //용량이 큰 파일을 업로드 하려면 이러한 코드는 꺼주어야 한다.
//            InputStream inputStream =part.getInputStream();
//            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//            log.info("body={}",body);

            // 파일 저장
            if(StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("파일 저장 fullPath={}",fullPath);
                part.write(fullPath);
            }
        }
        return "upload-form";
    }
}
