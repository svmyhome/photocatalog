package guru.qa.photocatalog.controller;

import guru.qa.photocatalog.domain.Photo;
import guru.qa.photocatalog.ex.PhotoNotFoundException;
import guru.qa.photocatalog.service.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/photo")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/all")
    public List<Photo> all() {
        return photoService.allPhotos();
    }

    @GetMapping("/{id}")
    public Photo byId(@PathVariable String id) {
        return photoService.findById(id);
    }


}
