package guru.qa.photocatalog.service;

import guru.qa.photocatalog.domain.Photo;
import guru.qa.photocatalog.domain.graphql.PhotoGql;
import guru.qa.photocatalog.domain.graphql.PhotoInputGql;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PhotoService {

    List<Photo> allPhotos();

    Slice<PhotoGql> allGqlPhotos(Pageable pageable);
    List<PhotoGql> allGqlPhotos();


    Photo photoByDescription(String description);

    Photo findById(String id);

    PhotoGql photoGqlById(String id);

    Photo addPhoto(Photo photo);
    PhotoGql addPhotoGql(PhotoInputGql photoInputGql);

}
