package guru.qa.photocatalog.service;


import com.google.protobuf.util.Timestamps;
import guru.qa.grpc.photocatalog.CounterRequest;
import guru.qa.grpc.photocatalog.PhotoRequest;
import guru.qa.grpc.photocatalog.PhotoResponse;
import guru.qa.grpc.photocatalog.PhotocatalogServeceGrpc;
import guru.qa.grpc.photocatalog.idRequest;
import guru.qa.photocatalog.domain.graphql.PhotoGql;
import guru.qa.photocatalog.domain.graphql.PhotoInputGql;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrpcPhotoService extends PhotocatalogServeceGrpc.PhotocatalogServeceImplBase {
    private final PhotoService photoService;

    @Autowired
    public GrpcPhotoService(PhotoService photoService) {
        super();
        this.photoService = photoService;
    }

    @Override
    public void photo(idRequest request, StreamObserver<PhotoResponse> responseObserver) {
        final PhotoGql photoGql = photoService.photoGqlById(request.getId());
        responseObserver.onNext(
                PhotoResponse.newBuilder()
                        .setId(photoGql.id().toString())
                        .setDescription(photoGql.description())
                        .setLastModifyDate(Timestamps.fromDate(photoGql.lastModifyDate()))
                        .setContent(photoGql.content())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void addPhoto(PhotoRequest request, StreamObserver<PhotoResponse> responseObserver) {
        final PhotoGql photoGql = photoService.addPhotoGql(new PhotoInputGql(
                request.getDescription(),
                request.getContent()
        ));
        responseObserver.onNext(
                PhotoResponse.newBuilder()
                        .setId(photoGql.id().toString())
                        .setDescription(photoGql.description())
                        .setLastModifyDate(Timestamps.fromDate(photoGql.lastModifyDate()))
                        .setContent(photoGql.content())
                        .build()
        );
        responseObserver.onCompleted();

    }

    @Override
    public void randomPhoto(CounterRequest request, StreamObserver<PhotoResponse> responseObserver) {
        Random rand = new Random();
        List<PhotoGql> photos = photoService.allGqlPhotos();
        for (int i = 0; i < request.getCount(); i++) {
            PhotoGql randomPhoto = photos.get(rand.nextInt(photos.size()));
            responseObserver.onNext(
                    PhotoResponse.newBuilder()
                            .setId(randomPhoto.id().toString())
                            .setDescription(randomPhoto.description())
                            .setLastModifyDate(Timestamps.fromDate(randomPhoto.lastModifyDate()))
                            .setContent(randomPhoto.content())
                            .build()
            );
        }
        responseObserver.onCompleted();
    }
}
