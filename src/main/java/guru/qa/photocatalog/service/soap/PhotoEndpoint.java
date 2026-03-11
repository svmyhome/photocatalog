package guru.qa.photocatalog.service.soap;


import static guru.qa.photocatalog.config.AppConfig.SOAP_NAMESPACE;
import guru.qa.photocatalog.IdUserRequest;
import guru.qa.photocatalog.PageUserRequest;
import guru.qa.photocatalog.Photo;
import guru.qa.photocatalog.PhotoResponse;
import guru.qa.photocatalog.PhotosResponse;
import guru.qa.photocatalog.domain.graphql.PhotoGql;
import guru.qa.photocatalog.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PhotoEndpoint {

    private final PhotoService photoService;

    @Autowired
    public PhotoEndpoint(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "idUserRequest")
    @ResponsePayload
    public PhotoResponse photo(@RequestPayload IdUserRequest request) {
        final PhotoGql photo = photoService.photoGqlById(request.getId());
        PhotoResponse photoResponse = new PhotoResponse();
        Photo xmlPhoto = new Photo();
        xmlPhoto.setId(photo.id().toString());
        xmlPhoto.setContent(photo.content());
        xmlPhoto.setDescription(photo.description());
        photoResponse.setPhoto(xmlPhoto);
        return photoResponse;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "pageUserRequest")
    @ResponsePayload
    public PhotosResponse photo(@RequestPayload PageUserRequest request) {
        Page<PhotoGql> photo = (Page<PhotoGql>) photoService.allGqlPhotos(
                PageRequest.of(
                        request.getPage(),
                        request.getSize()
                )
        );
        PhotosResponse photosResponse = new PhotosResponse();
        photosResponse.setTotalPages(photo.getTotalPages());
        photosResponse.setTotalElements(photo.getTotalElements());
        photosResponse.getPhotos().addAll(
                photo.getContent().stream().map(
                        gqlPhoto -> {
                            Photo xmlPhoto = new Photo();
                            xmlPhoto.setId(gqlPhoto.id().toString());
                            xmlPhoto.setContent(gqlPhoto.content());
                            xmlPhoto.setDescription(gqlPhoto.description());
                            return xmlPhoto;
                        }
                ).toList()
        );
        return photosResponse;
    }
}
