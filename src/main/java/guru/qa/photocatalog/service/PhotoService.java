package guru.qa.photocatalog.service;

import guru.qa.photocatalog.domain.Photo;
import java.util.List;
import java.util.UUID;

public interface PhotoService {

    List<Photo> allPhotos();

    Photo photoByDescription(String description);

    Photo findById(String id);
}
