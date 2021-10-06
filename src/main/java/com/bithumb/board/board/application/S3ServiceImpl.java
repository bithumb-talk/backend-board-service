package com.bithumb.board.board.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3Client amazonS3ClientPublic;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름
    /* s3 object 삭제 */
    public void deleteObejct(long userNo, long boardNo){
        String prefix = "user"+ String.valueOf(userNo)+"/board"+ String.valueOf(boardNo);
        ObjectListing objectListing = amazonS3ClientPublic.listObjects(bucket,prefix);
        for(S3ObjectSummary s : objectListing.getObjectSummaries()){
            amazonS3ClientPublic.deleteObject(new DeleteObjectRequest(bucket,s.getKey()));
        }
    }

    /* s3 object 리스트 */
//    public ObjectListing objectList(long userNo, long boardNo){
//        ObjectListing objectListing = amazonS3ClientPublic.listObjects(bucket,"user"+ String.valueOf(userNo)+"/board"+ String.valueOf(boardNo));
        //for(S3ObjectSummary s : objectListing.getObjectSummaries()){
        //    System.out.println(s.getKey());
        //}
//        return objectListing;
//    }
}
