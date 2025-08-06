package com.aig.AI_imageGenerator.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RemoveBgService {

    @Value("${removebg.api.key}")
    private String apiKey;

    public byte[] removeBackground(MultipartFile file) throws Exception {
        String url = "https://api.remove.bg/v1.0/removebg";
        //This is the url path where we wanna send the image.

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);//MULTIPART_FORM_DATA is used when you're uploading files.
        headers.set("X-Api-Key", apiKey);//We are sending apiKey to the headers.

        // File part
        HttpEntity<ByteArrayResource> fileEntity = new HttpEntity<>(
            new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            },
            headers
        );
        
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image_file", fileEntity);
        body.add("size", "auto");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody(); // contains the image
        } else {
            throw new RuntimeException("Remove.bg API error: " + response.getStatusCode());
        }
    }
}
