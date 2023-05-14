package com.example.let.controller;

import com.example.let.JwtTokenProvider;
import com.example.let.domain.*;
import com.example.let.domain.res.ResponseDto;
import com.example.let.exception.GlobalException;
import com.example.let.service.FileUploadService;
import com.example.let.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

@Tag(name = "유저", description = "프로필 수정등 유저 권한 이상이 필요한 API")
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class RestUserController {
    private final UserService userService;
    private final FileUploadService fileUploadService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * @Name 프로필 열람
     * @Path "api/user/profile"
     *
     * @text
     * DB에 존재하는 유저 프로필 정보를 변환한다.
     *
     * @Return User
     */
    @Operation(summary = "프로필 가져오기", description = "프로필을 반환합니다")
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String id = jwtTokenProvider.getAccessSubFromToken(token.substring(7));
        if(!id.isEmpty()) {
            return new ResponseEntity<>(
                    ResponseDto.builder()
                            .status(200)
                            .data(userService.get(id))
                            .build()
                    , HttpStatus.OK
            );
        } else throw new GlobalException(HttpStatus.BAD_REQUEST, "can not find user");
    }

    /**
     * @Name 사진 가져오기
     * @Path "api/user/image/{idx}"
     *
     * @text
     * 물리적인 증명사진을 변환한다.
     *
     * @Return User
     */
    @Operation(summary = "사진 가져오기", description = "사진을 반환합니다")
    @GetMapping(value="/image/{idx}")
    public void image(HttpServletRequest request,
                      HttpServletResponse response,
                      Model model,
                      @PathVariable("idx") Long idx)
    {
        if (idx > 0) {
            model.addAttribute("idx",idx);
        }

        UploadedFile fileInfo = fileUploadService.getFile(idx);
        if (fileInfo == null) {
            throw new NullPointerException("File not found");
        }

        try {
            File file = fileUploadService.getPhysicalFile(fileInfo);
            byte[] bytes = FileUtils.readFileToByteArray(file);

            response.setContentType(fileInfo.getContentType());
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileInfo.getOriginalFileName(), "UTF-8")+"\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }
}
