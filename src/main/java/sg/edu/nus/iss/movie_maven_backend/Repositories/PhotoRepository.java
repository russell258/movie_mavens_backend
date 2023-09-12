package sg.edu.nus.iss.movie_maven_backend.Repositories;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class PhotoRepository {
    
    @Autowired
    private AmazonS3 S3;

    public String uploadFile(MultipartFile file) throws IOException{

	ObjectMetadata metadata = new ObjectMetadata();
	metadata.setContentType(file.getContentType());
	metadata.setContentLength(file.getSize());
	String uuid = UUID.randomUUID().toString().substring(0,8);
	InputStream is = file.getInputStream();

	PutObjectRequest putReq = new PutObjectRequest("csfday37", uuid, is, metadata);
	putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);

	S3.putObject(putReq);
	return uuid;
    }

}
