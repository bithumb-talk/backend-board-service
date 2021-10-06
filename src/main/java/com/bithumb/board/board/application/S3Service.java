package com.bithumb.board.board.application;

import com.amazonaws.services.s3.model.ObjectListing;
import org.springframework.stereotype.Component;

@Component
public interface S3Service {

    /* s3 object 삭제 */
    void deleteObejct(long userNo, long boardNo);

    /* s3 object 리스트 */
    //ObjectListing objectList(long userNo, long boardNo);
}
