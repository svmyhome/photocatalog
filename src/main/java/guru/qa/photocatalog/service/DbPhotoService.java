package guru.qa.photocatalog.service;

import guru.qa.photocatalog.date.PhotoRepository;
import guru.qa.photocatalog.domain.Photo;
import guru.qa.photocatalog.ex.PhotoNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbPhotoService implements PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public DbPhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public List<Photo> allPhotos() {
        return photoRepository.findAll()
                .stream()
                .map(fe -> {
                    return new Photo(
                            fe.getDescription(),
                            fe.getLastModifyDate(),
                            fe.getContent() != null ? new String(fe.getContent()) : ""
                    );
                        }
                ).toList();
    }

    @Override
    public Photo photoByDescription(String description) {
        return null;
    }

    @Override
    public Photo findById(String id) {
        return photoRepository.findById(UUID.fromString(id))
                .map(fe -> new Photo(
                            fe.getDescription(),
                            fe.getLastModifyDate(),
                            fe.getContent() != null ? new String(fe.getContent()) : ""
                    )).orElseThrow(PhotoNotFoundException::new);
    }
}
