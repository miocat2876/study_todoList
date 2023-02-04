package com.example.famback.util;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class MailServerClient {

    private String url = "https://mail-server.dotorim.com/sender";
    private final MediaType mediaType = MediaType.MULTIPART_FORM_DATA;
    private final MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String,String> call() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaType);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,new HttpEntity<>(multiValueMap, httpHeaders),String.class);
        if(responseEntity.getStatusCode().value() != 200){
            throw new Exception("메일 발송에 실패하였습니다.");
        }
        log.debug(responseEntity.getBody());
        Gson gson = DataUtil.getGson.GSON.getGson();
        return gson.fromJson(responseEntity.getBody(), HashMap.class);
    }

    public MailServerClient setUrl(String url) {
        this.url = url;
        return this;
    }

    public MailServerClient setFile(File file) throws MessagingException {
        if(file == null) throw new MessagingException("파일이 없습니다.");
        multiValueMap.add("files",new FileSystemResource(file));
        return this;
    }

    public MailServerClient setFile(String fileDir) throws MessagingException {
        if("".equals(fileDir)) throw new MessagingException("파일주소가 없습니다.");
        multiValueMap.add("files",new FileSystemResource(fileDir));
        return this;
    }

    public MailServerClient setSubject(String subject) {
        multiValueMap.add("subject",subject);
        return this;
    }

    public MailServerClient setRecipient(String recipients) {
        multiValueMap.add("recipients",recipients);
        return this;
    }
    public MailServerClient setRecipient(String[] recipients) {
        for(String recipient : recipients){
            multiValueMap.add("recipients",recipient);
        }
        return this;
    }

    public MailServerClient setProjectName(String projectName) {
        multiValueMap.add("projectName",projectName);
        return this;
    }

    public MailServerClient setText(String text) {
        multiValueMap.add("text",text);
        return this;
    }

    public MailServerClient setMailOption(MailOptionType mailOption) {
        multiValueMap.add("mailOption",mailOption.name());
        return this;
    }

    public MailServerClient setMailType(MailType mailType) {
        multiValueMap.add("mailType",mailType.name());
        return this;
    }

    public MailServerClient setMailTemplate(MailTemplateType mailTemplate) {
        multiValueMap.add("mailTemplate",mailTemplate.name());
        return this;
    }

    public enum MailType {
        DEFAULT,
        CUSTOM,
        TEMPLATE;
    }
    public enum MailOptionType {
        RANDOM_KEY
    }
    public enum MailTemplateType {
        DEFAULT;
    }
}
