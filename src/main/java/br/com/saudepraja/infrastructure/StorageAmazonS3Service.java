package br.com.saudepraja.infrastructure;

import br.com.saudepraja.api.core.storage.StorageProperties;
import br.com.saudepraja.domain.exception.SaudePrajaBusinessException;
import br.com.saudepraja.domain.model.entity.util.Storable;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StorageAmazonS3Service {

    public static final long MAX_FILE_SIZE = 30 * 1024 * 1024; // 30MB

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    public void store(Storable storable) {
        String path = this.getPath(storable.getName());

        var objectMetadata = new ObjectMetadata();

        var putObjectRequest = new PutObjectRequest(
                storageProperties.getS3().getBucket(),
                path,
                storable.getInputStream(),
                objectMetadata
        );

        try {
            amazonS3.putObject(putObjectRequest);
        } catch (Exception ex) {
            throw new SaudePrajaBusinessException("Não foi possível fazer o upload do arquivo");
        }
    }

    public void remover(Long schedulingId) {

    }

    private String getPath(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }


}
