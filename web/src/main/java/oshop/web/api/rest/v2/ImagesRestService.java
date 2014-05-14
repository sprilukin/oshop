package oshop.web.api.rest.v2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import oshop.dao.GenericDao;
import oshop.model.Image;
import oshop.services.ImageConverterService;
import oshop.web.api.rest.adapter.HttpCacheRestCallbackAdapter;
import oshop.web.api.rest.adapter.ReturningRestCallbackAdapter;
import oshop.web.api.rest.adapter.VoidRestCallbackAdapter;
import oshop.web.dto.FileUploadDto;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v2")
@Transactional(readOnly = true)
public class ImagesRestService {

    private static final Log log = LogFactory.getLog(ImagesRestService.class);

    @Resource
    private ImageConverterService imageConverterService;

    @Resource
    private GenericDao<Image, Integer> imageDao;

    // ==== ADD image(s) =====

    /**
     * Add raw image - image bytes should be sent in a request body
     *
     * @param requestEntity request entity with an image in body
     * @return id of newly created image
     */
    @RequestMapping(
            value = "/images/raw",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> add(final HttpEntity<byte[]> requestEntity) {
        return new ReturningRestCallbackAdapter<Integer>() {
            @Override
            protected Integer getResult() throws Exception {
                if (!requestEntity.hasBody()) {
                    throw new Exception("No image to save");
                }

                Image image = new Image();
                image.setContentType(requestEntity.getHeaders().getContentType().toString());
                image.setData(requestEntity.getBody());

                return imageDao.add(image);
            }
        }.invoke();
    }

    /**
     * Add one or more images via form submit.
     *
     * @param uploadDto DTO object which
     * @param maxWidth if set - image will be deflated so width will be equal to maxWidth before save
     * @return list of images ids
     */
    @RequestMapping(
            value = "/images",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> addViaFormSubmit(
            final FileUploadDto uploadDto,
            final @RequestParam(value = "width", required = false) Integer maxWidth) {
        return new ReturningRestCallbackAdapter<List<Integer>>() {
            @Override
            protected List<Integer> getResult() throws Exception {
                List<Integer> ids = new ArrayList<Integer>(uploadDto.getFiles().size());

                for (MultipartFile file: uploadDto.getFiles()) {
                    Image image = new Image();
                    fillImageWithData(image, file, maxWidth);

                    ids.add(imageDao.add(image));
                }

                return ids;
            }
        }.invoke();
    }

    // ==== DELETE image(s) =====

    /**
     * Delete an image with given id
     *
     * @param id id if the image to delete
     * @return HttpStatus.NO_CONTENT
     */
    @RequestMapping(
            value = "/images/{id}",
            method = RequestMethod.DELETE)
    @Transactional(readOnly = false)
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                imageDao.remove(id);            }
        }.invoke();
    }

    /**
     * <p>Delete several images by their id</p>
     * <p>Example:</p>
     * <code>/api/images/batch;ids=1,2,3/delete</code>
     *
     * @param ids ids of images to delete
     * @return HttpStatus.NO_CONTENT
     */
    @RequestMapping(
            value = "/images/{batch}/delete",
            method = RequestMethod.DELETE)
    @Transactional(readOnly = false)
    public ResponseEntity<?> batchDelete(
            @MatrixVariable(value = "ids", pathVar="batch", required = true) final List<Integer> ids) {
        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                imageDao.executeQuery("delete from Image where id in :ids", new GenericDao.QueryManipulator() {
                    @Override
                    public void manipulateWithQuery(Query query) {
                        query.setParameterList("ids", ids);
                    }
                });
            }
        }.invoke();
    }

    // ==== GET image =====

    /**
     * Return content of the image (bytes)
     * Use HTTP cache headers to improve performance
     *
     * @param id id of the image to retrieve
     * @param ifModifiedSinceHeader "If-Modified-Since" request header
     * @return bytes of the image
     */
    @RequestMapping(
            value = "/images/{id}",
            method = RequestMethod.GET,
            produces = {
                    MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    @ResponseBody
    public ResponseEntity<?> get(
            @PathVariable final Integer id,
            final @RequestHeader(value = "If-Modified-Since", required = false) String ifModifiedSinceHeader) {

        return new HttpCacheRestCallbackAdapter<byte[]>() {
            private Image image;

            @Override
            protected void setModifiedTimes() throws Exception {
                this.image = imageDao.get(id);

                this.getHeaders().setContentType(MediaType.parseMediaType(image.getContentType()));

                this.setLastModified(image.getLastUpdate().getTime());
                this.setIfModifiedSince(ifModifiedSinceHeader);
            }

            @Override
            protected byte[] getResult() throws Exception {
                this.setSize(image.getData().length);
                return image.getData();
            }
        }.invoke();
    }

    // ==== UPDATE image(s) =====

    /**
     * Update image sent as a request body
     *
     * @param id id of the image to update
     * @param requestEntity request entity which contains image bytes in body
     * @return HttpStatus.NO_CONTENT
     */
    @RequestMapping(
            value = "/images/raw/{id}",
            method = RequestMethod.PUT)
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> update(
            @PathVariable final Integer id,
            final HttpEntity<byte[]> requestEntity) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                if (!requestEntity.hasBody()) {
                    throw new Exception("No image to save");
                }

                Image image = imageDao.get(id);
                image.setContentType(requestEntity.getHeaders().getContentType().toString());
                image.setData(requestEntity.getBody());
                imageDao.update(image);
            }
        }.invoke();
    }

    /**
     * Update image sent via form submit
     *
     * @param id id of the image to update
     * @param file DTO which contains new image content and content type
     * @return HttpStatus.NO_CONTENT
     */
    @RequestMapping(
            value = "/images/{id}",
            method = RequestMethod.PUT)
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> updateViaFormSubmit(
            @PathVariable final Integer id,
            final @RequestParam MultipartFile file) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                Image image = imageDao.get(id);
                image.setContentType(file.getContentType());
                image.setData(file.getBytes());

                imageDao.update(image);
            }
        }.invoke();
    }

    /**
     * <p>Batch update of images via form submit.</p>
     * <p>Example:</p>
     * <code>/api/images/batch;ids=1,2,3/update</code>
     *
     * @param ids ids of images to update
     * @param maxWidth see {@link #addViaFormSubmit(oshop.web.dto.FileUploadDto, Integer)}
     * @param uploadDto DTO which contains images and their content types
     * @return
     */
    @RequestMapping(
            value = "/images/{batch}/update",
            method = {RequestMethod.PUT})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> updateMultipleViaFormSubmit(
            final @MatrixVariable(value = "ids", pathVar="batch", required = true) List<Integer> ids,
            final @RequestParam(value = "width", required = false) Integer maxWidth,
            final FileUploadDto uploadDto) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {

                for (int i = 0; i < uploadDto.getFiles().size(); i++) {
                    MultipartFile file = uploadDto.getFiles().get(i);

                    Image image = imageDao.get(ids.get(i));
                    fillImageWithData(image, file, maxWidth);

                    imageDao.update(image);
                }
            }
        }.invoke();
    }

    private void fillImageWithData(Image image, MultipartFile file, Integer maxWidth) throws Exception {
        if (file.getBytes() == null || file.getBytes().length == 0) {
            throw new Exception("No image to save");
        }

        String contentType = file.getContentType();
        byte[] imageData = file.getBytes();
        if (maxWidth != null) {
            contentType = MediaType.IMAGE_JPEG_VALUE;
            imageData = imageConverterService.deflate(imageData, maxWidth, MediaType.IMAGE_JPEG.getSubtype());
            if (imageData == file.getBytes()) {
                contentType = file.getContentType();
            }
        }

        image.setContentType(contentType);
        image.setData(imageData);
    }
}
