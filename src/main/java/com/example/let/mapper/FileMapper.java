package com.example.let.mapper;

import com.example.let.domain.UploadedFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileMapper {

    /**
     * 파일을 저장한다.
     *
     * @param file 파일 정보
     * @return 파일 idx
     */
    public Long insertFile(UploadedFile file);

    /**
     * 파일 정보를 읽어온다.
     *
     * @param idx idx
     * @return 등록된 파일 정보
     */
    public UploadedFile selectFile(@Param("idx") Long idx);

    /**
     * 주어진 파일을 삭제한다.
     *
     * @param idx 파일 idx
     */
    public void deleteFile(@Param("fileIdx") Long idx);

}
